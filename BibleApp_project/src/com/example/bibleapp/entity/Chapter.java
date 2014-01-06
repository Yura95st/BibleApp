package com.example.bibleapp.entity;

import java.util.List;

public class Chapter
{

	private int number;
	private List<Verse> verses;

	public Chapter()
	{

	}

	public Chapter(int nubmer, List<Verse> verses)
	{
		super();
		this.number = nubmer;
		this.verses = verses;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int nubmer)
	{
		this.number = nubmer;
	}

	public List<Verse> getVerses()
	{
		return verses;
	}

	public void setVerses(List<Verse> verses)
	{
		this.verses = verses;
	}

	public Verse getVerse(int verseNumber)
	{
		for (Verse verse : verses) {
			if (verse.getNumber() == verseNumber) {
				return verse;
			}
		}
		return null;
	}
}
