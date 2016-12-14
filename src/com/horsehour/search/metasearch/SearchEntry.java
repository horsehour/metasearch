package com.horsehour.search.metasearch;

/**
 * @author Chunheng Jiang
 * @version 1.0
 * @since May 7, 2014 4:09:15
 **/
public class SearchEntry {
	private String link;
	private String anchor;
	// name of search engine
	String engine;

	private float score;

	public SearchEntry() {
		link = "";
		anchor = "";
		score = 0;
	}

	public SearchEntry(String link, String title) {
		this();
		this.link = link;
		this.anchor = title;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String url) {
		this.link = url;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Link=" + link + ", Title=" + anchor + ", score=" + score;
	}
}
