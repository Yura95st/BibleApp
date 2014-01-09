package com.example.bibleapp.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bibleapp.R;
import com.example.bibleapp.ResourceGetter;

public class BookActivity extends Activity
{
	public final static String EXTRA_CHAPTER_NUMBER = "com.example.bibleapp.CHAPTER_NUMBER";

	private ListView chaptersListView;
	private List<String> chapters = new ArrayList<String>();
	private int testamentNumber;
	private int bookNumber;

	private int layout_bookActivity = R.layout.activity_book;
	private int id_chapterListView = R.id.chapterListView;
	private int layout_chapterListViewItem = R.layout.book_listview_item;
	private int id_chapterItemTextView = R.id.bookItemTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(layout_bookActivity);

		testamentNumber = getTestamentNumberFromIntent();
		bookNumber = getBookNumberFromIntent();
		chapters = getChapters();
		chaptersListView = (ListView) findViewById(id_chapterListView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				layout_chapterListViewItem, id_chapterItemTextView, chapters);

		chaptersListView.setAdapter(adapter);
		chaptersListView.setOnItemClickListener(chapterListItemClickListener);
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

	private List<String> getChapters()
	{
		List<String> chapters = new ArrayList<String>();
		Resources res = getResources();

		int[] chapters_count = ResourceGetter.getChaptersCountArray(res,
				testamentNumber);

		// fill the chapter's names array from "Chapter 1" to
		// "Chapter " + chapters_count[this.bookNumber]
		for (int i = 0; i < chapters_count[this.bookNumber]; i++) {
			chapters.add("Chapter " + (i + 1));
		}

		return chapters;
	}

	private OnItemClickListener chapterListItemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			openChapter(position);
		}
	};

	private void openChapter(int chapterNumber)
	{
		// start ChapterActivity and send testament, book and chapter
		// numbers to it
		if (chapterNumber < 0) {
			return;
		}

		Context context = this.getApplicationContext();
		Intent intent = new Intent(context, ChapterActivity.class);

		intent.putExtra(StartActivity.EXTRA_TESTAMENT_NUMBER,
				this.testamentNumber);
		intent.putExtra(TestamentActivity.EXTRA_BOOK_NUMBER, this.bookNumber);
		intent.putExtra(EXTRA_CHAPTER_NUMBER, chapterNumber);

		startActivity(intent);
	}
}
