package com.example.bibleapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.bibleapp.entity.Book;
import com.example.bibleapp.entity.Chapter;
import com.example.bibleapp.entity.Verse;

public class BibleParseTask extends AsyncTask<String, String, String>
{
	private BibleParserHandler bibleParser = new BibleParserHandler();

	private Activity activity;
	private String fileName = "bible.xml";
	private int bookNumber = BibleParserHandler.READ_ALL_BOOKS;
	private int chapterNumber = BibleParserHandler.READ_ALL_CHAPTERS;
	private int verseNumber = BibleParserHandler.READ_ALL_VERSES;

	private ListView listView;
	private int layout_verseListViewItem;
	private int id_verseItemTextView;

	public BibleParseTask(Activity activity)
	{
		this.activity = activity;
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

	public void setListView(ListView listView)
	{
		this.listView = listView;
	}

	public void setLayout_verseListViewItem(int layout_verseListViewItem)
	{
		this.layout_verseListViewItem = layout_verseListViewItem;
	}

	public void setId_verseItemTextView(int id_verseItemTextView)
	{
		this.id_verseItemTextView = id_verseItemTextView;
	}

	private void updateListView()
	{
		List<Verse> verses = new ArrayList<Verse>();
		List<Book> books = getBooks();

		try {
			for (Book book : books) {
				if (book.getNumber() == bookNumber) {
					Chapter chapter = book.getChapter(chapterNumber);
					if (chapter != null) {
						verses = chapter.getVerses();
					}
				}
			}
		}
		finally {
		}

		VerseArrayAdapter adapter = new VerseArrayAdapter(activity, verses);

		listView.setAdapter(adapter);
	}

	private void parseBible()
	{
		AssetManager manager = activity.getApplicationContext().getAssets();
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
