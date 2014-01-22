package com.example.bibleapp.history;

public class History
{
	private int id;
	private int bookId;
	private int chapterId;
	private int viewCount;
	private String viewedAt;

	public History()
	{
	}

	public History(int id, int bookId, int chapterId, int viewCount,
			String createdAt)
	{
		this.id = id;
		this.bookId = bookId;
		this.chapterId = chapterId;
		this.viewCount = viewCount;
		this.viewedAt = createdAt;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getBookId()
	{
		return bookId;
	}

	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}

	public int getChapterId()
	{
		return chapterId;
	}

	public void setChapterId(int chapterId)
	{
		this.chapterId = chapterId;
	}

	public int getViewCount()
	{
		return viewCount;
	}

	public void setViewCount(int viewCount)
	{
		this.viewCount = viewCount;
	}

	public String getViewedAt()
	{
		return viewedAt;
	}

	public void setViewedAt(String viewedAt)
	{
		this.viewedAt = viewedAt;
	}

}
