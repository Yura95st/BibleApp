package com.example.bibleapp.entity;

import java.util.List;

public class Testament
{
	private int number;
	private String title;
	private List<Book> books;

	public Testament(int number, String title, List<Book> books)
	{
		super();
		this.number = number;
		this.title = title;
		this.books = books;
	}

	public int getNumber()
	{
		return number;
	}

	public String getTitle()
	{
		return title;
	}

	public List<Book> getBooks()
	{
		return books;
	}
}
