package com.example.bibleapp;

public class Partision {

	private int nubmer;
	private String title;
	private Chapter[] chapters;

	public Partision(int nubmer, String title, Chapter[] chapters) {
		super();
		this.nubmer = nubmer;
		this.title = title;
		this.chapters = chapters;
	}

	public int getNubmer() {
		return nubmer;
	}

	public void setNubmer(int nubmer) {
		this.nubmer = nubmer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Chapter[] getChapters() {
		return chapters;
	}

	public void setChapters(Chapter[] chapters) {
		this.chapters = chapters;
	}

}
