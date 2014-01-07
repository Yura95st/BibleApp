package com.example.bibleapp.entity;

import java.util.List;

public class Chapter
{
	private int number;
	private List<Verse> verses;

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

	public List<Verse> getVerses()
	{
		return verses;
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
