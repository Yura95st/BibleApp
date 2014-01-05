package com.example.bibleapp;

public class Chapter {
	
	private int nubmer;
	private Verse[] verses;
	
	public Chapter(int nubmer, Verse[] verses) {
		super();
		this.nubmer = nubmer;
		this.verses = verses;
	}

	public int getNubmer() {
		return nubmer;
	}

	public void setNubmer(int nubmer) {
		this.nubmer = nubmer;
	}

	public Verse[] getVerses() {
		return verses;
	}

	public void setVerses(Verse[] verses) {
		this.verses = verses;
	}
}
