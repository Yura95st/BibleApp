package com.example.bibleapp.activities;

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

public class TestamentActivity extends Activity
{
	public final static String EXTRA_BOOK_NUMBER = "com.example.bibleapp.BOOK_NUMBER";

	private ListView bookListView;
	private String[] books;
	private int testamentNumber;

	private int layout_testamentActivity = R.layout.activity_testament;
	private int id_bookListView = R.id.bookListView;
	private int layout_bookListViewItem = R.layout.book_listview_item;
	private int id_bookItemTextView = R.id.bookItemTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(layout_testamentActivity);

		Resources res = getResources();

		testamentNumber = getTestamentNumberFromIntent();
		books = ResourceGetter.getBooksArray(res, testamentNumber);
		bookListView = (ListView) findViewById(id_bookListView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				layout_bookListViewItem, id_bookItemTextView, books);

		bookListView.setAdapter(adapter);
		bookListView.setOnItemClickListener(bookListItemClickListener);
	}

	private int getTestamentNumberFromIntent()
	{
		Intent intent = getIntent();

		return intent.getIntExtra(StartActivity.EXTRA_TESTAMENT_NUMBER, 0);
	}

	private OnItemClickListener bookListItemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			openBook(position);
		}
	};

	private void openBook(int bookNumber)
	{
		if (bookNumber < 0) {
			return;
		}

		Context context = this.getApplicationContext();
		Intent intent = new Intent(context, BookActivity.class);

		intent.putExtra(EXTRA_BOOK_NUMBER, bookNumber);
		intent.putExtra(StartActivity.EXTRA_TESTAMENT_NUMBER, testamentNumber);

		startActivity(intent);
	}
}
