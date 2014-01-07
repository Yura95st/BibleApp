package com.example.bibleapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.example.bibleapp.entity.Book;

public class BibleParseTask extends AsyncTask<String, String, String>
{
	private BibleParserHandler bibleParser = new BibleParserHandler();

	private Context context;
	private String fileName = "bible.xml";
	private int bookNumber = BibleParserHandler.READ_ALL_BOOKS;
	private int chapterNumber = BibleParserHandler.READ_ALL_CHAPTERS;
	private int verseNumber = BibleParserHandler.READ_ALL_VERSES;

	public BibleParseTask(Context context)
	{
		this.context = context;
	}

	@Override
	protected String doInBackground(String... args)
	{
		parseBible();
		return null;
	}

	@Override
	protected void onPostExecute(String result)
	{
		updateListView();
	}

	public void setChapterNumber(int chapterNumber)
	{
		this.chapterNumber = chapterNumber;
	}

	public void setBookNumber(int bookNumber)
	{
		this.bookNumber = bookNumber;
	}

	public void setVerseNumber(int verseNumber)
	{
		this.verseNumber = verseNumber;
	}

	private void updateListView()
	{
		// List<Book> books = getBooks();
		//
		// try {
		// for (Book book : books) {
		// if (book.getNumber() == bookNumber) {
		// Chapter chapter = book.getChapter(chapterNumber);
		//
		// for (Verse verse : chapter.getVerses()) {
		// verses.add(verse.getText());
		// }
		// }
		// }
		// }
		// finally {
		// }
	}

	private void parseBible()
	{
		AssetManager manager = this.context.getAssets();
		InputStream inputStream;

		try {
			inputStream = manager.open(fileName);

			bibleParser.parse(new InputStreamReader(inputStream), bookNumber,
					chapterNumber, verseNumber);
		}
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Book> getBooks()
	{
		return bibleParser.getBooks();
	}
}
