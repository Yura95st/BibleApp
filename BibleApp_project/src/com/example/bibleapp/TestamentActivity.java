package com.example.bibleapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TestamentActivity extends Activity {

	public final static String EXTRA_BOOK_NUMBER = "com.example.bibleapp.BOOK_NUMBER";

	private ListView bookListView;
	private String[] books;
	private String testament;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testament);

		Intent intent = getIntent();

		testament = intent.getStringExtra(StartActivity.EXTRA_TESTAMENT);

		Resources res = getResources();
		
		if (testament.equals("old")) {
			books = res.getStringArray(R.array.books_old_testament);
		} else if (testament.equals("new")) {
			books = res.getStringArray(R.array.books_new_testament);
		}

		bookListView = (ListView) findViewById(R.id.bookListView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.book_listview_item, R.id.bookItemTextView, books);

		bookListView.setAdapter(adapter);

		// ListView Item Click Listener
		bookListView.setOnItemClickListener(bookListItemClickListener);
	}

	private OnItemClickListener bookListItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Intent intent = new Intent(TestamentActivity.this,
					BookActivity.class);
			intent.putExtra(EXTRA_BOOK_NUMBER, position);
			intent.putExtra(StartActivity.EXTRA_TESTAMENT, testament);

			startActivity(intent);
		}
	};
}
