package com.example.bibleapp.entity;

public class Verse
{
	private int number;
	private String text;

	public Verse(int number, String text)
	{
		super();
		this.number = number;
		this.text = text;
	}

	public int getNumber()
	{
		return number;
	}

	public String getText()
	{
		return text;
	}

}
