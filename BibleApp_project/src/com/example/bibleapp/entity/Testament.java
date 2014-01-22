package com.example.bibleapp.entity;

import java.util.List;

public class Testament
{
	private int number;
	private String title;
	private List<Book> books;

	public Testament(int number, String title, List<Book> books)
	{
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

	public Book getBook(int bookNumber)
	{
		for (Book book : books) {
			if (book.getNumber() == bookNumber) {
				return book;
			}
		}
		return null;
	}

	public int getBooksCount()
	{
		return books.size();
	}
}
