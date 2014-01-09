package com.example.bibleapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.bibleapp.BibleParseTask;
import com.example.bibleapp.R;
import com.example.bibleapp.ResourceGetter;

public class ChapterActivity extends Activity
{
	public final static String EXTRA_VERSE_NUMBER = "com.example.bibleapp.VERSE_NUMBER";

	private ActionBar actionBar;

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

		setupActionBar();

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chapter_activity_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		setActionBookNameButton(menu);
		setActionPrevButton(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	private void setActionPrevButton(Menu menu)
	{
		MenuItem menuItem = menu.findItem(R.id.action_prevChapter);
		menuItem.setEnabled(chapterNumber != getPreviousChapter());
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		int itemId = item.getItemId();

		switch (itemId)
		{
			case android.R.id.home:
				goToStartActivity();
				break;
			case R.id.action_bookName:
				showBookChapters();
				break;
			case R.id.action_nextChapter:
				showNextChapter();
				break;
			case R.id.action_prevChapter:
				showPreviousChapter();
				break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void setupActionBar()
	{
		actionBar = getActionBar();

		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setDisplayShowTitleEnabled(false);

		// LayoutInflater inflater = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View myView = inflater.inflate(layout_actionBar, null);
		//
		// actionBar.setCustomView(myView);
		// actionBar.setDisplayShowCustomEnabled(true);
	}

	private void goToStartActivity()
	{
		Context context = this.getApplicationContext();
		Intent intent = new Intent(context, StartActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private void setActionBookNameButton(Menu menu)
	{
		MenuItem menuItem = menu.findItem(R.id.action_bookName);
		menuItem.setTitle(getCurrentBookTitle());
	}

	private String getCurrentBookTitle()
	{
		String[] books = null;
		Resources res = getResources();

		switch (testamentNumber)
		{
			case 0:
				books = res.getStringArray(R.array.books_old_testament);
				break;
			case 1:
				books = res.getStringArray(R.array.books_new_testament);
				break;
		}

		return books[bookNumber] + ":" + Integer.toString(chapterNumber + 1);
	}

	private void showBookChapters()
	{
		Context context = this.getApplicationContext();
		Intent intent = new Intent(context, BookActivity.class);

		intent.putExtra(TestamentActivity.EXTRA_BOOK_NUMBER, bookNumber);
		intent.putExtra(StartActivity.EXTRA_TESTAMENT_NUMBER, testamentNumber);

		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private void showNextChapter()
	{
		int nextChapterNumber = getNextChapter();
		int nextBookNumber = bookNumber;
		int nextTestamentNumber = testamentNumber;

		if (nextChapterNumber == 0) {
			nextBookNumber = getNextBook();
			if (nextBookNumber == 0) {
				nextTestamentNumber = getNextTestament();
			}
		}

		showChapter(nextTestamentNumber, nextBookNumber, nextChapterNumber);
	}

	private int getNextChapter()
	{
		int nextChapterNumber = chapterNumber + 1;
		Resources res = getResources();
		int[] chaptersCount = ResourceGetter.getChaptersCountArray(res,
				testamentNumber);

		// last chapter in the book
		if (nextChapterNumber == chaptersCount[bookNumber]) {
			return 0;
		}
		else {
			return nextChapterNumber;
		}
	}

	private int getNextBook()
	{
		int nextBookNumber = bookNumber + 1;
		Resources res = getResources();

		// last chapter in the book
		if (nextBookNumber == ResourceGetter.getBooksArrayCount(res,
				testamentNumber)) {
			return 0;
		}
		else {
			return nextBookNumber;
		}
	}

	private int getNextTestament()
	{
		return (testamentNumber == 0 ? 1 : 0);
	}

	private void showPreviousChapter()
	{
		int prevChapterNumber = getPreviousChapter();

		showChapter(testamentNumber, bookNumber, prevChapterNumber);
	}

	private void showChapter(int testamentNumber, int bookNumber,
			int chapterNumber)
	{
		this.testamentNumber = testamentNumber;
		this.bookNumber = bookNumber;
		this.chapterNumber = chapterNumber;

		fillVersesListView();
		invalidateOptionsMenu();
	}

	private int getPreviousChapter()
	{
		int prevChapterNumber = chapterNumber - 1;

		// last chapter in the book
		if (prevChapterNumber < 0) {
			return 0;
		}
		else {
			return prevChapterNumber;
		}
	}
}