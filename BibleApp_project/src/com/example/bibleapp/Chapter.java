package com.example.bibleapp;

public class Chapter {

	private int number;
	private String title;
	private Verse[] verses;

	public Chapter(int number, String title, Verse[] verses) {
		super();
		this.number = number;
		this.title = title;
		this.verses = verses;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Verse[] getVerses() {
		return verses;
	}

	public void setVerses(Verse[] verses) {
		this.verses = verses;
	}
}
