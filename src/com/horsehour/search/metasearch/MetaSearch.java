package com.horsehour.search.metasearch;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.lang3.tuple.Pair;

import com.horsehour.search.meta.BordaCount;
import com.horsehour.search.meta.PairVoteRule;
import com.horsehour.util.MathUtils;
import com.horsehour.util.MulticoreExecutor;

/**
 * Meta search engine combined the searching results returned from some
 * individual search engines. It brings to users with diverse and higher quality
 * results after filtering and reorganization. Another plus is that it will has
 * less distractions from the adversments. The implementation includes Google,
 * Bing, Ask, Baidu, Yahoo as its member engines. The aggregated algorithm to
 * integrate searching results and sort the web pages is very crucial and under
 * further development.
 * 
 * @author Chunheng Jiang
 * @version 1.0
 * @since Apr 23, 2013
 **/
public class MetaSearch extends JFrame {
	private static final long serialVersionUID = 2177510598859918847L;

	public float[] scoreRange = { 30.0F, 100.0F };

	private JTextField textField;

	private JCheckBox checkBoxBaidu;
	private JCheckBox checkBoxBing;
	private JCheckBox checkBoxBlekko;
	private JCheckBox checkBoxGoogle;
	// private JCheckBox checkBoxLycos;
	// private JCheckBox checkBoxSogou;
	private JCheckBox checkBoxAsk;
	// private JCheckBox checkBoxCN360;
	private JCheckBox checkBoxYahoo;
	private JCheckBox checkBoxMicrosoftAcademic;
	private JCheckBox checkBoxGoogleScholar;
	// private JCheckBox checkBoxSocialScholar;
	// private JCheckBox checkBoxYandex;

	private JCheckBox checkBoxAllOrNone;

	private List<JCheckBox> checkBoxList;

	private JPanel panel_1;
	private JPanel panel_2;
	private JEditorPane editorPane;

	private List<Integer> memberList;

	private List<SearchEntry> cachedList;

	private BordaCount<String> meta;
	private final int numMember = 8;
	private int topk;
	private String query;

	private StringBuffer page;

	public MetaSearch() {
		topk = 20;

		checkBoxList = new ArrayList<>();
		memberList = new ArrayList<>();
		cachedList = new ArrayList<>();

		// meta = new BordaCount<>();
		// meta = new ReciprocalRank<>();
		meta = new PairVoteRule<>();

		initPage();
		initializeFrame();
	}

	private void initPage() {
		page = new StringBuffer();
		page.append("<html>");
		page.append("<body>");
		page.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initializeFrame() {
		setTitle("http://www.horsehour.com/");
		setVisible(true);
		getContentPane().setBackground(SystemColor.activeCaption);
		setBounds(20, 10, 721, 827);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel_2 = new JPanel();
		panel_2.setBounds(46, 109, 524, 37);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Garamond", Font.PLAIN, 17));
		textField.setBounds(0, 0, 524, 37);
		panel_2.add(textField);
		textField.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new TitledBorder(null, "Select Your Favorite Search Engine(s)", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(46, 10, 621, 93);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		checkBoxAsk = new JCheckBox("Ask");
		checkBoxAsk.setBounds(6, 17, 80, 37);
		panel_1.add(checkBoxAsk);
		checkBoxAsk.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxAsk.addActionListener(event -> updateMemberList(checkBoxAsk));
		checkBoxAsk.setBackground(SystemColor.activeCaption);
		checkBoxList.add(checkBoxAsk);

		checkBoxBaidu = new JCheckBox("Baidu");
		// checkBoxBaidu.setBounds(69, 17, 61, 37);
		checkBoxBaidu.setBounds(132, 17, 80, 37);
		panel_1.add(checkBoxBaidu);
		checkBoxBaidu.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxBaidu.addActionListener(event -> updateMemberList(checkBoxBaidu));
		checkBoxBaidu.setBackground(SystemColor.activeCaption);
		checkBoxList.add(checkBoxBaidu);

		checkBoxBing = new JCheckBox("Bing");
		// checkBoxBing.setBounds(132, 17, 61, 37);
		checkBoxBing.setBounds(258, 17, 80, 37);
		panel_1.add(checkBoxBing);
		checkBoxBing.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxBing.addActionListener(event -> updateMemberList(checkBoxBing));
		checkBoxBing.setBackground(SystemColor.activeCaption);
		checkBoxList.add(checkBoxBing);

		checkBoxBlekko = new JCheckBox("Blekko");
		// checkBoxBlekko.setBounds(195, 17, 61, 37);
		checkBoxBlekko.setBounds(384, 17, 80, 37);
		panel_1.add(checkBoxBlekko);
		checkBoxBlekko.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxBlekko.addActionListener(event -> updateMemberList(checkBoxBlekko));
		checkBoxBlekko.setBackground(SystemColor.activeCaption);
		checkBoxList.add(checkBoxBlekko);

		// checkBoxCN360 = new JCheckBox("CN360");
		// checkBoxCN360.setBounds(258, 17, 61, 37);
		// panel_1.add(checkBoxCN360);
		// checkBoxCN360.setFont(new Font("Garamond", Font.PLAIN, 12));
		// checkBoxCN360.addActionListener(event ->
		// updateMemberList(checkBoxCN360));
		// checkBoxCN360.setBackground(SystemColor.activeCaption);
		// checkBoxList.add(checkBoxCN360);

		checkBoxGoogle = new JCheckBox("Google");
		// checkBoxGoogle.setBounds(321, 17, 61, 37);
		checkBoxGoogle.setBounds(522, 17, 80, 37);
		panel_1.add(checkBoxGoogle);
		checkBoxGoogle.addActionListener(event -> updateMemberList(checkBoxGoogle));
		checkBoxGoogle.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxGoogle.setBackground(SystemColor.activeCaption);
		checkBoxList.add(checkBoxGoogle);

		// checkBoxLycos = new JCheckBox("Lycos");
		// checkBoxLycos.setBounds(384, 17, 61, 37);
		// panel_1.add(checkBoxLycos);
		// checkBoxLycos.setFont(new Font("Garamond", Font.PLAIN, 12));
		// checkBoxLycos.addActionListener(event ->
		// updateMemberList(checkBoxLycos));
		// checkBoxLycos.setBackground(SystemColor.activeCaption);
		// checkBoxList.add(checkBoxLycos);

		// checkBoxSogou = new JCheckBox("Sogou");
		// checkBoxSogou.setBounds(447, 17, 61, 37);
		// panel_1.add(checkBoxSogou);
		// checkBoxSogou.setFont(new Font("Garamond", Font.PLAIN, 12));
		// checkBoxSogou.addActionListener(event ->
		// updateMemberList(checkBoxSogou));
		// checkBoxSogou.setBackground(SystemColor.activeCaption);
		// checkBoxList.add(checkBoxSogou);

		// checkBoxYandex = new JCheckBox("Yandex");
		// checkBoxYandex.setFont(new Font("Garamond", Font.PLAIN, 12));
		// checkBoxYandex.setBackground(SystemColor.activeCaption);
		// checkBoxYandex.setBounds(521, 17, 94, 37);
		// panel_1.add(checkBoxYandex);
		// checkBoxYandex.addActionListener(event ->
		// updateMemberList(checkBoxYandex));
		// checkBoxList.add(checkBoxYandex);

		checkBoxYahoo = new JCheckBox("Yahoo");
		checkBoxYahoo.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxYahoo.setBackground(SystemColor.activeCaption);
		checkBoxYahoo.setBounds(6, 52, 80, 37);
		panel_1.add(checkBoxYahoo);
		checkBoxYahoo.addActionListener(event -> updateMemberList(checkBoxYahoo));
		checkBoxList.add(checkBoxYahoo);

		checkBoxMicrosoftAcademic = new JCheckBox("MS.Academic");
		checkBoxMicrosoftAcademic.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxMicrosoftAcademic.setBackground(SystemColor.activeCaption);
		// checkBoxMicrosoftAcademic.setBounds(132, 52, 124, 37);
		checkBoxMicrosoftAcademic.setBounds(132, 52, 120, 37);
		panel_1.add(checkBoxMicrosoftAcademic);
		checkBoxMicrosoftAcademic.addActionListener(event -> updateMemberList(checkBoxMicrosoftAcademic));
		checkBoxList.add(checkBoxMicrosoftAcademic);

		checkBoxGoogleScholar = new JCheckBox("G.Scholar");
		checkBoxGoogleScholar.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxGoogleScholar.setBackground(SystemColor.activeCaption);
		// checkBoxGoogleScholar.setBounds(6, 52, 107, 37);
		checkBoxGoogleScholar.setBounds(258, 52, 120, 37);
		panel_1.add(checkBoxGoogleScholar);
		checkBoxGoogleScholar.addActionListener(event -> updateMemberList(checkBoxGoogleScholar));
		checkBoxList.add(checkBoxGoogleScholar);

		// checkBoxSocialScholar = new JCheckBox("SocialScholar");
		// checkBoxSocialScholar.setFont(new Font("Garamond", Font.PLAIN, 12));
		// checkBoxSocialScholar.setBackground(SystemColor.activeCaption);
		// checkBoxSocialScholar.setBounds(258, 52, 107, 37);
		// panel_1.add(checkBoxSocialScholar);
		// checkBoxSocialScholar.addActionListener(event ->
		// updateMemberList(checkBoxSocialScholar));
		// checkBoxList.add(checkBoxSocialScholar);

		checkBoxAllOrNone = new JCheckBox("All/None");
		checkBoxAllOrNone.setBounds(522, 52, 120, 37);
		panel_1.add(checkBoxAllOrNone);
		checkBoxAllOrNone.setFont(new Font("Garamond", Font.PLAIN, 12));
		checkBoxAllOrNone.setBackground(SystemColor.activeCaption);
		checkBoxAllOrNone.addActionListener(event -> updateMemberList(checkBoxAllOrNone.isSelected()));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 156, 621, 624);
		getContentPane().add(scrollPane);

		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		scrollPane.setViewportView(editorPane);
		HTMLEditorKit kit = new HTMLEditorKit();

		editorPane.setEditorKit(kit);
		Document doc = kit.createDefaultDocument();
		editorPane.setDocument(doc);
		editorPane.addHyperlinkListener(event -> {
			HyperlinkEvent.EventType type = event.getEventType();
			URL url = event.getURL();
			if (type == HyperlinkEvent.EventType.ACTIVATED) {
				try {
					Desktop.getDesktop().browse(url.toURI());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});

		JButton button = new JButton("Search");
		button.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		button.setBounds(571, 109, 96, 37);
		button.addActionListener(event -> {
			query = textField.getText();
			cachedList = collectAndAggregate(memberList, query, topk);
			reportSearchResult(cachedList);
		});
		getContentPane().setLayout(null);
		getContentPane().add(button);
	}

	/**
	 * Report the aggregated search results
	 * 
	 * @param resultList
	 */
	void reportSearchResult(List<SearchEntry> resultList) {
		if (resultList == null || resultList.size() == 0)
			return;

		int size = resultList.size();
		float min = resultList.get(size - 1).getScore();
		float max = resultList.get(0).getScore();

		StringBuffer html = new StringBuffer();
		SearchEntry entry;
		for (int i = 0; i < size; i++) {
			entry = resultList.get(i);
			String host = "";
			try {
				host = new URL(entry.getLink()).getHost();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			// make sure all the scores are among [30,100]
			float score = resultList.get(i).getScore();
			score = 100 * (0.7F * score + 0.3F * max - min) / (max - min);
			html.append("<li type=circle>(" + MathUtils.round(score, 0) + ")");
			html.append("<a href=\"" + entry.getLink() + "\">" + entry.getAnchor() + "</a></li>");
			html.append("source:<i>" + host + "</i>");
		}
		html.append("</ul></body></html>");
		editorPane.setText(page.toString() + html.toString());
	}

	/**
	 * action responses for checkBoxAllOrNone
	 * 
	 * @param isSelected
	 */
	void updateMemberList(boolean isSelected) {
		// select all
		if (isSelected) {
			checkBoxAllOrNone.setSelected(true);
			JCheckBox member;
			for (int i = 0; i < numMember; i++) {
				member = checkBoxList.get(i);
				if (member.isSelected())
					continue;

				// has not been selected
				member.setSelected(true);
				memberList.add(i);
			}
			return;
		}

		// clear all
		checkBoxAllOrNone.setSelected(false);
		int size = memberList.size();
		if (size > 0) {
			int memberId;
			for (int i = 0; i < size; i++) {
				memberId = memberList.get(i);
				checkBoxList.get(memberId).setSelected(false);
			}
			memberList.clear();
		}
	}

	void updateMemberList(JCheckBox checkBox) {
		boolean isSelected = checkBox.isSelected();
		int id = checkBoxList.indexOf(checkBox);
		if (id == -1)
			return;

		if (isSelected) {
			checkBox.setSelected(true);
			memberList.add(id);
			return;
		}

		// cleared
		checkBox.setSelected(false);
		if (memberList.contains(id))
			memberList.remove(new Integer(id));
	}

	/**
	 * Collect all the searching results from all selected individual search
	 * engines and aggregate them
	 * 
	 * @param memberList
	 * @param query
	 * @param topk
	 * @return fusion data
	 */
	public List<SearchEntry> collectAndAggregate(List<Integer> memberList, String query, int topk) {
		if (memberList == null || query == null)
			return null;

		List<Callable<List<SearchEntry>>> collectors = new ArrayList<>();
		for (int member : memberList) {
			collectors.add(() -> {
				String name = checkBoxList.get(member).getText();
				SearchEngine engine = EngineHunter.engineTable.get(name);
				return EngineHunter.huntTopkLink(engine, query, topk);
			});
		}

		List<List<SearchEntry>> serps = null;
		try {
			serps = MulticoreExecutor.run(collectors);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Map<String, String> linkAnchorTable = new HashMap<>();
		List<String> linkList = new ArrayList<>();
		List<Integer> beginId = new ArrayList<>();

		int count = 0;

		for (List<SearchEntry> serp : serps) {
			beginId.add(count);
			for (SearchEntry entry : serp) {
				String link = entry.getLink();
				String anchor = entry.getAnchor();
				if (linkAnchorTable.containsKey(link)) {
					int prev = linkAnchorTable.get(link).length();
					if (prev < anchor.length())
						linkAnchorTable.put(link, anchor);
				} else
					linkAnchorTable.put(link, anchor);
				linkList.add(link);
			}
			count += serp.size();
		}
		return aggregate(meta.score(linkList, beginId), linkAnchorTable);
	}

	/**
	 * @param linkScoreTable
	 *            link-score
	 * @param linkAnchorTable
	 *            link-anchors
	 * @return search results page
	 */
	List<SearchEntry> aggregate(Map<String, Float> linkScoreTable, Map<String, String> linkAnchorTable) {
		List<Pair<Float, String>> pairList = new ArrayList<>();
		for (String link : linkScoreTable.keySet())
			pairList.add(Pair.of(linkScoreTable.get(link), link));
		// sorted in ascending
		Collections.sort(pairList);

		List<SearchEntry> entryList = new ArrayList<>();
		SearchEntry entry = null;
		int size = pairList.size();
		for (int i = size - 1; i >= 0; i--) {
			String link = pairList.get(i).getRight();
			String anchor = linkAnchorTable.get(link);
			if (anchor.isEmpty())
				anchor = link;

			entry = new SearchEntry(link, anchor);
			entry.setScore(pairList.get(i).getLeft());
			entryList.add(entry);
		}
		return entryList;
	}

	public void setTopk(int k) {
		topk = k;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new MetaSearch());
	}
}