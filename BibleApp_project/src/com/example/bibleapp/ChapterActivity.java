package com.example.bibleapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

public class ChapterActivity extends Activity {

	private ListView verseListView;
	private int chapterNumber;
	private int bookNumber;
	private String testament;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);

		Intent intent = getIntent();

		testament = intent.getStringExtra(StartActivity.EXTRA_TESTAMENT);
		bookNumber = intent.getIntExtra(TestamentActivity.EXTRA_BOOK_NUMBER, 0);
		chapterNumber = intent
				.getIntExtra(BookActivity.EXTRA_CHAPTER_NUMBER, 0);

		Resources res = getResources();
	}
}
