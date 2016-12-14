package com.horsehour.search.metasearch;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import com.horsehour.util.DOMUtils;
import com.horsehour.util.StringUtils;
import com.horsehour.util.TickClock;

/**
 * Submit queries to specific search engine and extract useful information from
 * the returned searching results.
 * 
 * @author Chunheng Jiang
 * @version 1.0
 * @since 20130419
 */
public class EngineHunter {
	public static Map<String, SearchEngine> engineTable;

	static {
		engineTable = new HashMap<String, SearchEngine>();
		engineTable.put("Ask", SearchEngine.ASK);
		engineTable.put("Baidu", SearchEngine.BAIDU);
		engineTable.put("Bing", SearchEngine.BING);
		engineTable.put("Blekko", SearchEngine.BLEKKO);
		engineTable.put("CN360", SearchEngine.CN360);
		engineTable.put("Excite", SearchEngine.EXCITE);
		engineTable.put("Google", SearchEngine.GOOGLE);
		engineTable.put("Hotbot", SearchEngine.HOTBOT);
		engineTable.put("Lycos", SearchEngine.LYCOS);
		engineTable.put("Sogou", SearchEngine.SOGOU);
		engineTable.put("Yandex", SearchEngine.YANDEX);
		engineTable.put("Yahoo", SearchEngine.YAHOO);

		engineTable.put("G.Scholar", SearchEngine.GOOGLESCHOLAR);
		engineTable.put("MS.Academic", SearchEngine.MICROSOFTACADEMIC);
		engineTable.put("SocialScholar", SearchEngine.SOCIALSCHOLAR);
	}

	/**
	 * @param src
	 * @param enc
	 * @return get redirect link
	 */
	public static String getRedirectLink(String src) {
		String trueLink = "";
		try {
			URL url = new URL(src);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setConnectTimeout(6000);

			// accept redirection
			HttpURLConnection.setFollowRedirects(true);
			huc.setInstanceFollowRedirects(true);

			int code = huc.getResponseCode();
			if (code == 200)
				trueLink = huc.getURL().toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return trueLink;
	}

	/**
	 * Process the extracted link and anchor information
	 * 
	 * @param link
	 * @param anchor
	 * @return make sure the search entry be legal
	 */
	private static SearchEntry process(String link, String anchor) {
		SearchEntry entry = new SearchEntry();
		if (link == null || !link.startsWith("http"))
			return null;

		if (anchor == null || anchor.isEmpty())
			anchor = "";
		else
			anchor = anchor.replaceAll("&[\\s\\S]+?;", "").trim();

		entry.setAnchor(anchor);
		entry.setLink(link.trim());
		return entry;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Teoma's top ranking
	 */
	public static List<SearchEntry> huntAskTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int pageNum = 1;
		while (entryList.size() < k) {
			engineLink = "http://www.ask.com/web?q=" + query + "&page=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "web-result-title-link");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;

				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();

					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						entryList.add(entry);
				}
			}

			if (entryList.size() == 0)// avoid cyclic recursion
				break;

			pageNum++;
		}
		return entryList;
	}

	/**
	 * <li>wd: keywords</li>
	 * <li>cl: class,3(web),2(image)</li>
	 * <li>pn: page num</li>
	 * <li>rn: record num</li>
	 * 
	 * @param query
	 * @param k
	 * @return Searching entries from Baidu top ranking
	 */
	public static List<SearchEntry> huntBaiduTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		if (k > 10 && k % 10 > 0)
			k = (1 + (k / 10)) * 10;

		// no need to encode the query
		engineLink = "http://www.baidu.com/s?ie=utf-8&wd=" + query + "&rn=" + k;
		linkFilter = new HasAttributeFilter("class", "t");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			// transform html, avoid incosistent formats
			parser = DOMUtils.createParser(node.toHtml(), enc);
			NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
			if (nodes.size() == 0)
				continue;

			node = nodes.elementAt(0);

			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				if (link.startsWith("http")) {
					link = getRedirectLink(link);
					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						entryList.add(entry);
				}
			}
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Bing's top ranking
	 */
	public static List<SearchEntry> huntBingTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		engineLink = "http://www.bing.com/search?q=" + query + "&count=" + k;
		linkFilter = new HasAttributeFilter("class", "b_algo");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			parser = DOMUtils.createParser(node.toHtml(), enc);
			NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
			if (nodes.size() == 0)
				continue;
			node = nodes.elementAt(0);

			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				entry = process(link, ((LinkTag) node).getLinkText());
				if (entry != null)
					entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * The team joined IBM Watson
	 * 
	 * @param query
	 * @param k
	 * @return Searching entries from Blekko top ranking
	 */
	public static List<SearchEntry> huntBlekkoTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int pageNum = 0;
		while (entryList.size() < k) {
			engineLink = "http://blekko.com/ws/?q=" + query + "&p=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "title");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						entryList.add(entry);
				}
			}

			if (entryList.size() == 0)
				break;

			pageNum++;
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from 360 top ranking
	 */
	public static List<SearchEntry> huntCN360TopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";
		int pageNum = 1;
		while (entryList.size() < k) {
			engineLink = "http://www.haosou.com/s?q=" + query + "&pn=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "res-list");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						entryList.add(entry);
				}
			}

			if (entryList.size() == 0)
				break;

			pageNum++;
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Duckduckgo's top ranking
	 */
	public static List<SearchEntry> huntDuckduckgoTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		while (entryList.size() < k) {
			engineLink = "https://duckduckgo.com/?q=" + query + "&ia=web";
			linkFilter = new HasAttributeFilter("class", "result__title");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());

					if (entry != null)
						entryList.add(entry);
				}
			}
			if (entryList.size() == 0)
				break;
		}
		return entryList;
	}

	/**
	 * Excite is a meta-search engine, and its individual search engines include
	 * Google, Blekko, Yahoo and Yandex
	 * 
	 * @param query
	 * @param k
	 * @return Searching entries from Excite's top ranking
	 */
	public static List<SearchEntry> huntExciteTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int pageNum = 1;
		while (entryList.size() < k) {
			engineLink = "http://msxml.excite.com/search/web?q=" + query + "&p=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "resultTitle");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						entryList.add(entry);
				}
			}
			if (entryList.size() == 0)
				break;
			pageNum++;
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Google's top ranking
	 */
	public static List<SearchEntry> huntGoogleTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		engineLink = "https://www.google.com.sg/search?&q=" + query + "&num=" + k;
		linkFilter = new HasAttributeFilter("class", "r");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			parser = DOMUtils.createParser(node.toHtml(), enc);
			NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
			if (nodes.size() == 0)
				continue;
			node = nodes.elementAt(0);

			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				String url = StringUtils.match(link, "q=[\\s\\S]*?&");
				if (!url.isEmpty())
					link = url.replaceAll("q=|&", "").trim();
				entry = process(link, ((LinkTag) node).getLinkText());
				if (entry != null)
					entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * Hotbot has been sold to lycos, the searching results are the same
	 * 
	 * @param query
	 * @param k
	 * @return Searching entries from Hotbot's top ranking
	 */
	public static List<SearchEntry> huntHotBotTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		int pageNum = 1;
		while (entryList.size() < k) {
			engineLink = "http://www.hotbot.com/search/web?q=" + query + "&pn=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "web-url resultTitle");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						entryList.add(entry);
				}
			}
			if (entryList.size() == 0)
				break;
			pageNum++;
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Lycos's top ranking
	 */
	public static List<SearchEntry> huntLycosTopkLink(String query, int k) {
		List<SearchEntry> links = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int pageNum = 0;

		while (links.size() < k) {
			engineLink = "http://search1.lycos.com/web/?q=" + query + "&page2=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "result-title");
			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				if (node instanceof LinkTag) {
					link = ((LinkTag) node).extractLink();
					entry = process(link, ((LinkTag) node).getLinkText());
					if (entry != null)
						links.add(entry);
				}
			}
			if (links.size() == 0)
				break;

			pageNum++;
		}
		return links;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Sogou's top ranking
	 */
	public static List<SearchEntry> huntSogouTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		engineLink = "http://www.sogou.com/sogou?query=" + query + "&num=" + k;
		linkFilter = new HasAttributeFilter("class", "pt");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			parser = DOMUtils.createParser(node.toHtml(), enc);
			NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
			if (nodes.size() == 0)
				continue;
			node = nodes.elementAt(0);

			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				entry = process(link, ((LinkTag) node).getLinkText());
				if (entry != null)
					entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * Deploying with the detection of auto-crawling program
	 * 
	 * @param query
	 * @param k
	 * @return Searching entries from Yandex's top ranking
	 */
	public static List<SearchEntry> huntYandexTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int pageNum = 0;
		while (entryList.size() < k) {
			engineLink = "https://yandex.com/search/?text=" + query + "&p=" + pageNum;
			linkFilter = new HasAttributeFilter("class", "link organic__url link link_cropped_no");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());

					if (entry != null)
						entryList.add(entry);
				}
			}
			if (entryList.size() == 0)
				break;
			pageNum++;
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Yahoo's top ranking
	 */
	public static List<SearchEntry> huntYahooTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		while (entryList.size() < k) {
			engineLink = "https://search.yahoo.com/search?p=" + query;
			linkFilter = new HasAttributeFilter("class", " ac-algo ac-21th lh-24");

			Parser parser = DOMUtils.getParser(engineLink, enc);
			NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

			Node node;
			String link;
			SearchEntry entry;
			for (int i = 0; i < nodeList.size(); i++) {
				node = nodeList.elementAt(i);
				parser = DOMUtils.createParser(node.toHtml(), enc);
				NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
				if (nodes.size() == 0)
					continue;
				node = nodes.elementAt(0);

				if (node instanceof LinkTag) {
					link = ((LinkTag) node).getLink();
					entry = process(link, ((LinkTag) node).getLinkText());

					if (entry != null)
						entryList.add(entry);
				}
			}
			if (entryList.size() == 0)
				break;
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Microsoft Academic's top ranking
	 */
	public static List<SearchEntry> huntMicrosoftAcademicTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		engineLink = "http://academic.research.microsoft.com/Search?query=" + query;
		linkFilter = new HasAttributeFilter("class", "paper-item");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			parser = DOMUtils.createParser(node.toHtml(), enc);
			NodeList nodes = DOMUtils.getNodeList(parser, new NodeClassFilter(LinkTag.class));
			if (nodes.size() > 0)
				node = nodes.elementAt(0);
			else
				continue;

			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				if (!link.startsWith("http:"))
					link = "http://academic.research.microsoft.com/" + link;

				entry = process(link, ((LinkTag) node).getLinkText());
				if (entry != null)
					entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Google's scholar
	 */
	public static List<SearchEntry> huntGoogleScholarTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		engineLink = "https://scholar.google.com/scholar?q=" + query + "&num=" + k + "&hl=en";
		linkFilter = new HasAttributeFilter("class", "gs_rt");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i).getFirstChild();
			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				entry = process(link, ((LinkTag) node).getLinkText());

				if (entry != null)
					entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * @param query
	 * @param k
	 * @return Searching entries from Social Scholar or Tianji Scholar
	 */
	public static List<SearchEntry> huntSocialScholarTopkLink(String query, int k) {
		List<SearchEntry> entryList = new ArrayList<>();

		String enc = "utf-8";
		NodeFilter linkFilter = null;
		String engineLink = "";

		try {
			query = URLEncoder.encode(query, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		engineLink = "http://soscholar.com/search?q=" + query;
		linkFilter = new HasAttributeFilter("class", "p_title");

		Parser parser = DOMUtils.getParser(engineLink, enc);
		NodeList nodeList = DOMUtils.getNodeList(parser, linkFilter);

		Node node;
		String link;
		SearchEntry entry;
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i).getFirstChild();
			if (node instanceof LinkTag) {
				link = ((LinkTag) node).getLink();
				entry = process(link, ((LinkTag) node).getLinkText());
				if (entry != null)
					entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * @param engine
	 * @param query
	 * @param k
	 * @return
	 */
	public static List<SearchEntry> huntTopkLink(SearchEngine engine, String query, int k) {
		List<SearchEntry> entryList = null;
		if (engine == SearchEngine.ASK)
			entryList = huntAskTopkLink(query, k);

		if (engine == SearchEngine.BAIDU)
			entryList = huntBaiduTopkLink(query, k);

		if (engine == SearchEngine.BING)
			entryList = huntBingTopkLink(query, k);

		if (engine == SearchEngine.BLEKKO)
			entryList = huntBlekkoTopkLink(query, k);

		if (engine == SearchEngine.CN360)
			entryList = huntCN360TopkLink(query, k);

		if (engine == SearchEngine.EXCITE)
			entryList = huntExciteTopkLink(query, k);

		if (engine == SearchEngine.GOOGLE)
			entryList = huntGoogleTopkLink(query, k);

		if (engine == SearchEngine.HOTBOT)
			entryList = huntHotBotTopkLink(query, k);

		if (engine == SearchEngine.LYCOS)
			entryList = huntLycosTopkLink(query, k);

		if (engine == SearchEngine.SOGOU)
			entryList = huntSogouTopkLink(query, k);

		if (engine == SearchEngine.YANDEX)
			entryList = huntYandexTopkLink(query, k);

		if (engine == SearchEngine.YAHOO)
			entryList = huntYahooTopkLink(query, k);

		if (engine == SearchEngine.GOOGLESCHOLAR)
			entryList = huntGoogleScholarTopkLink(query, k);

		if (engine == SearchEngine.MICROSOFTACADEMIC)
			entryList = huntMicrosoftAcademicTopkLink(query, k);

		if (engine == SearchEngine.SOCIALSCHOLAR)
			entryList = huntSocialScholarTopkLink(query, k);
		return entryList;
	}

	/**
	 * @param engine
	 * @param query
	 * @return Extract the first searching entry
	 */
	public static SearchEntry huntTopOne(SearchEngine engine, String query) {
		List<SearchEntry> links = huntTopkLink(engine, query, 1);
		if (links.size() > 0)
			return links.get(0);
		else
			return null;
	}

	public static void main(String[] args) {
		TickClock.beginTick();

		String query = "hyperparameter optimization";
		int topk = 3;
		List<SearchEntry> list = EngineHunter.huntDuckduckgoTopkLink(query, topk);
		if (list == null || list.size() == 0)
			return;

		System.out.println(StringUtils.join(list, "\n"));

		TickClock.stopTick();
	}
}