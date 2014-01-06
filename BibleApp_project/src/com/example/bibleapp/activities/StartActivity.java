package com.example.bibleapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.bibleapp.R;

public class StartActivity extends Activity
{
	public final static String EXTRA_TESTAMENT_NUMBER = "com.example.bibleapp.TESTAMENT_NUMBER";

	private Button oldTestamentButton;
	private Button newTestamentButton;
	private Button specialVersesButton;
	private Button bookmarksButton;
	private Button settingsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		this.init();
	}

	private void init()
	{
		oldTestamentButton = (Button) findViewById(R.id.oldTestamentButton);
		newTestamentButton = (Button) findViewById(R.id.newTestamentButton);
		specialVersesButton = (Button) findViewById(R.id.specialVersesButton);
		bookmarksButton = (Button) findViewById(R.id.bookmarksButton);
		settingsButton = (Button) findViewById(R.id.settingsButton);

		oldTestamentButton.setOnClickListener(startButtonsListener);
		newTestamentButton.setOnClickListener(startButtonsListener);
		specialVersesButton.setOnClickListener(startButtonsListener);
		bookmarksButton.setOnClickListener(startButtonsListener);
		settingsButton.setOnClickListener(startButtonsListener);
	}

	private OnClickListener startButtonsListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			// choose the reaction depends on what button is clicked
			switch (v.getId())
			{
				case R.id.oldTestamentButton:
					openOldTestament();
					break;

				case R.id.newTestamentButton:
					openNewTestament();
					break;

				case R.id.specialVersesButton:
					openSpecialVerses();
					break;

				case R.id.bookmarksButton:
					openBookmarks();
					break;

				case R.id.settingsButton:
					openSettings();
					break;
			}
		}
	};

	private void openTestament(int testamentNumber)
	{
		// testament number: 0 - old, 1 - new
		if (testamentNumber != 0 && testamentNumber != 1) {
			return;
		}

		Context context = this.getApplicationContext();
		Intent intent = new Intent(context, TestamentActivity.class);

		intent.putExtra(EXTRA_TESTAMENT_NUMBER, testamentNumber);

		startActivity(intent);
	}

	private void openOldTestament()
	{
		openTestament(0);
	}

	private void openNewTestament()
	{
		openTestament(1);
	}

	private void openSettings()
	{
		// TODO Auto-generated method stub

	}

	private void openBookmarks()
	{
		// TODO Auto-generated method stub

	}

	private void openSpecialVerses()
	{
		// TODO Auto-generated method stub

	}
}
