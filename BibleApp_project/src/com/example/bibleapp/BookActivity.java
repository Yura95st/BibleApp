package com.example.bibleapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookActivity extends Activity {
	
	public final static String EXTRA_CHAPTER_NUMBER = "com.example.bibleapp.CHAPTER_NUMBER";

	private ListView chapterListView;
	private List<String> chapters = new ArrayList<String>();
	private int[] chapters_count;
	private int bookNumber;
	private String testament;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);

		Intent intent = getIntent();

		testament = intent.getStringExtra(StartActivity.EXTRA_TESTAMENT);
		bookNumber = intent.getIntExtra(
				TestamentActivity.EXTRA_BOOK_NUMBER, 0);

		Resources res = getResources();
		
		if (testament.equals("old")) {
			chapters_count = res
					.getIntArray(R.array.chapters_count_old_testament);
		} else if (testament.equals("new")) {
			chapters_count = res
					.getIntArray(R.array.chapters_count_new_testament);
		}

		chapterListView = (ListView) findViewById(R.id.chapterListView);

		for (int i = 0; i < chapters_count[bookNumber]; i++) {
			chapters.add("Chapter " + (i + 1));
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.book_listview_item, R.id.bookItemTextView, chapters);

		chapterListView.setAdapter(adapter);
		chapterListView.setOnItemClickListener(chapterListItemClickListener);
	}

	private OnItemClickListener chapterListItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Intent intent = new Intent(BookActivity.this, ChapterActivity.class);
			intent.putExtra(EXTRA_CHAPTER_NUMBER, position);
			intent.putExtra(TestamentActivity.EXTRA_BOOK_NUMBER, bookNumber);
			intent.putExtra(StartActivity.EXTRA_TESTAMENT, testament);

			startActivity(intent);
		}
	};
}
