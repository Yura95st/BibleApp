package com.example.bibleapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.bibleapp.BibleParseTask;
import com.example.bibleapp.R;

public class ChapterActivity extends Activity
{
	public final static String EXTRA_VERSE_NUMBER = "com.example.bibleapp.VERSE_NUMBER";

	private ListView verseListView;
	private int testamentNumber;
	private int bookNumber;
	private int chapterNumber;

	private int layout_chapterActivity = R.layout.activity_chapter;
	private int id_verseListView = R.id.verseListView;
	private int layout_verseListViewItem = R.layout.verse_row;
	private int id_verseItemTextView = R.id.bookItemTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(layout_chapterActivity);

		testamentNumber = getTestamentNumberFromIntent();
		bookNumber = getBookNumberFromIntent();
		chapterNumber = getChapterNumberFromIntent();

		verseListView = (ListView) findViewById(id_verseListView);

		fillVersesListView();
	}

	private int getTestamentNumberFromIntent()
	{
		Intent intent = getIntent();

		return intent.getIntExtra(StartActivity.EXTRA_TESTAMENT_NUMBER, 0);
	}

	private int getBookNumberFromIntent()
	{
		Intent intent = getIntent();

		return intent.getIntExtra(TestamentActivity.EXTRA_BOOK_NUMBER, 0);
	}

	private int getChapterNumberFromIntent()
	{
		Intent intent = getIntent();

		return intent.getIntExtra(BookActivity.EXTRA_CHAPTER_NUMBER, 0);
	}

	private void fillVersesListView()
	{
		BibleParseTask bibleParseTask = new BibleParseTask(this);

		bibleParseTask.setBookNumber(bookNumber);
		bibleParseTask.setChapterNumber(chapterNumber);
		bibleParseTask.setListView(verseListView);
		bibleParseTask.setLayout_verseListViewItem(layout_verseListViewItem);
		bibleParseTask.setId_verseItemTextView(id_verseItemTextView);

		bibleParseTask.execute();
	}
}