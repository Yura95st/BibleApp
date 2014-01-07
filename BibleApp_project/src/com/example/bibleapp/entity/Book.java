package com.example.bibleapp.entity;

import java.util.List;

public class Book
{
	private int number;
	private String title;
	private List<Chapter> chapters;

	public Book(int nubmer, String title, List<Chapter> chapters)
	{
		super();
		this.number = nubmer;
		this.title = title;
		this.chapters = chapters;
	}

	public int getNumber()
	{
		return number;
	}

	public String getTitle()
	{
		return title;
	}

	public List<Chapter> getChapters()
	{
		return chapters;
	}

	public Chapter getChapter(int chapterNumber)
	{
		for (Chapter chapter : chapters) {
			if (chapter.getNumber() == chapterNumber) {
				return chapter;
			}
		}
		return null;
	}
}
