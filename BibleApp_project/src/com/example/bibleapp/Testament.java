package com.example.bibleapp;

public class Testament {

	private int number;
	private String title;
	private Partision[] partisions;

	public Testament(int number, String title, Partision[] partisions) {
		super();
		this.number = number;
		this.title = title;
		this.partisions = partisions;
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

	public Partision[] getPartisions() {
		return partisions;
	}

	public void setPartisions(Partision[] partisions) {
		this.partisions = partisions;
	}

}
