package com.example.bibleapp;

public class Verse {

	private int number;
	private String text;

	public Verse(int number, String text) {
		super();
		this.number = number;
		this.text = text;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
