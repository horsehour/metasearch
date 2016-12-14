package com.horsehour.util;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Making fully use of the advantages of HtmlParser, jSoup, 
 * HtmlCleaner, TagSoup,  HtmlUnit, jTidy, nekoHtml, WebHarvest, 
 * Jericho, dom4j, and develop an efficient parser which can handle
 * varieties of html, xml and other doms.
 * 
 * @author Chunheng Jiang
 * @version 1.5
 * @since 12 Oct 2011
 */
public class DOMUtils {
	public enum TOOL {
		JSOUP, HTMLPARSER;
	}

	/**
	 * @param url
	 * @param enc
	 * @return Parser
	 */
	public static Parser getParser(URL url, String enc){
		Parser parser = null;
		try {
			ConnectionManager manager = Page.getConnectionManager();
			HttpURLConnection huc = (HttpURLConnection) manager.openConnection(url);

			int code = huc.getResponseCode();
			if (code == 500 || code == 404)
				return null;

			parser = new Parser(huc);
			if (enc == null || enc.isEmpty())
				enc = "utf-8";
			parser.setEncoding(enc);
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return parser;
	}

	/**
	 * @param url
	 * @param enc
	 * @return Parser
	 */
	public static Parser getParser(String url, String enc){
		Parser parser = null;
		try {
			ConnectionManager manager = Page.getConnectionManager();
			HttpURLConnection huc = (HttpURLConnection) manager.openConnection(url);

			int code = huc.getResponseCode();
			if (code == 500)
				return null;

			parser = new Parser(huc);
			if (enc == null || enc.isEmpty())
				enc = "utf-8";
			parser.setEncoding(enc);
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return parser;
	}

	/**
	 * create parser on the fly
	 * @param content
	 * @param enc
	 * @return Parser
	 */
	public static Parser createParser(String content, String enc){
		Parser parser = Parser.createParser(content, enc);
		return parser;
	}

	/**
	 * @param content
	 * @param baseURI
	 * @return xml document
	 */
	public static Document getXMLDoc(String content, String baseURI){
		return Jsoup.parse(content, baseURI, org.jsoup.parser.Parser.xmlParser());
	}

	/**
	 * @param content
	 * @return xml document
	 */
	public static Document getXMLDoc(String content){
		return Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser());
	}

	/**
	 * @param content
	 * @param baseURI
	 * @return html document
	 */
	public static Document getHTMLDoc(String content, String baseURI){
		return Jsoup.parse(content, baseURI, org.jsoup.parser.Parser.htmlParser());
	}

	/**
	 * @param content
	 * @return html document
	 */
	public static Document getHTMLDoc(String content){
		return Jsoup.parse(content, "", org.jsoup.parser.Parser.htmlParser());
	}

	/**
	 * load document from local disk
	 * @param file
	 * @param baseURI
	 * @param enc
	 * @return document
	 */
	public static Document getDocument(File file, String baseURI, String enc){
		Document doc = null;
		try {
			doc = Jsoup.parse(file, baseURI, enc);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}

	/**
	 * load document from url
	 * @param url
	 * @return document
	 */
	public static Document getDocument(String url){
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}

	/**
	 * @param parser
	 * @param nf
	 * @return NodeList
	 */
	public static NodeList getNodeList(Parser parser, NodeFilter nf){
		NodeList nodes = null;
		try {
			nodes = parser.extractAllNodesThatMatch(nf);
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}
		return nodes;
	}

	/**
	 * extract visitor's trace
	 * @param visitor
	 * @param nodes
	 * @return visitor's trace
	 */
	public static String getVisitorTrace(NodeVisitor visitor, NodeList nodes){
		if (nodes == null)
			return null;

		try {
			nodes.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}
		return visitor.toString();
	}

	/**
	 * @param parser
	 * @param visitor
	 * @param nf
	 * @return visitor's trace
	 */
	public static String getVisitorTrace(Parser parser, NodeVisitor visitor, NodeFilter nf){
		NodeList nodes = getNodeList(parser, nf);
		return getVisitorTrace(visitor, nodes);
	}

	/**
	 * visit all html tag
	 * @param parser
	 */
	public static void visit(Parser parser){
		NodeVisitor visitor = new NodeVisitor(true, false) {
			@Override
			public void visitTag(Tag tag){
				System.out.println("tag: " + tag.getText());
			}

			@Override
			public void visitStringNode(Text string){
				System.out.println("text: " + string);
			}

			@Override
			public void beginParsing(){
				System.out.println("begin...");
			}

			@Override
			public void visitEndTag(Tag tag){
				System.out.println("end tag: " + tag.getText());
			}

			@Override
			public void finishedParsing(){
				System.out.println("stop.");
			}
		};

		try {
			parser.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @param nodes
	 */
	public static void visit(NodeList nodes){
		NodeVisitor visitor = new NodeVisitor(true, false) {
			@Override
			public void visitTag(Tag tag){
				System.out.println(tag.getText());
			}

			@Override
			public void visitStringNode(Text string){
				System.out.println(string);
			}

			@Override
			public void beginParsing(){
				System.out.println("begin...");
			}

			@Override
			public void visitEndTag(Tag tag){
				System.out.println("end tag: " + tag.getText());
			}

			@Override
			public void finishedParsing(){
				System.out.println("stop.");
			}
		};

		try {
			nodes.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @return NodeVisitor
	 */
	public static NodeVisitor getVisitor(){
		NodeVisitor visitor = new NodeVisitor(true, true) {
			String ret = "";
			String tagName = "";

			// plain tag
			@Override
			public void visitTag(Tag tag){
				tagName = tag.getTagName().toLowerCase();
				// paragraph
				if (tag instanceof ParagraphTag)
					ret += "<p>";
				// line break
				else if (tagName.equals("br") || tagName.equals("strong") || tagName.equals("b"))
					ret += "<" + tagName + ">";
			}

			// text node
			@Override
			public void visitStringNode(Text text){
				Tag tag = (Tag) text.getParent();// 取得父节点
				String str = text.toPlainTextString().trim();
				if ("\n" == str) {
				} else if (tag instanceof Span)
					ret += "<br><b>" + str + "</b><br>";
				else
					ret += str;
			}

			// stop
			@Override
			public void visitEndTag(Tag tag){
				tagName = tag.getTagName().toLowerCase();
				// paragraph
				if (tagName.startsWith("p") || tagName.startsWith("strong") || tagName.equals("b"))
					ret += "</" + tagName + ">";
			}

			@Override
			public String toString(){
				return ret;
			}
		};
		return visitor;
	}

	/**
	 * @param nodes
	 * @return text block
	 */
	public static String getTextBlock(NodeList nodes){
		NodeVisitor visitor = getVisitor();
		if (nodes.size() > 0)
			try {
				nodes.visitAllNodesWith(visitor);
			} catch (ParserException e) {
				e.printStackTrace();
				return null;
			}
		return visitor.toString();
	}

	/**
	 * @param src
	 * @param enc
	 * @param nf
	 * @return text block
	 */
	public static String getTextBlock(String src, String enc, NodeFilter nf){
		Parser parser = getParser(src, enc);
		NodeList nodes = null;
		try {
			nodes = parser.extractAllNodesThatMatch(nf);
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}

		Node node;
		if (nodes.size() > 0) {
			node = nodes.elementAt(0);
			nodes = node.getChildren();
		}
		return getTextBlock(nodes);
	}
}