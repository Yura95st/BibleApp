package com.example.bibleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class StartActivity extends Activity {
	
	public final static String EXTRA_TESTAMENT = "com.example.bibleapp.TESTAMENT";

	private Button oldTestamentButton;
	private Button newTestamentButton;
	private Button specialVersesButton;
	private Button bookmarksButton;
	private Button settingsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

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

	private OnClickListener startButtonsListener = new OnClickListener() {

		Intent intent;

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.oldTestamentButton:
				intent = new Intent(StartActivity.this,
						TestamentActivity.class);
				intent.putExtra(EXTRA_TESTAMENT, "old");
				break;

			case R.id.newTestamentButton:
				intent = new Intent(StartActivity.this,
						TestamentActivity.class);
				intent.putExtra(EXTRA_TESTAMENT, "new");
				break;

//			case R.id.specialVersesButton:
//				intent = new Intent(StartActivity.this,
//						SpecialVersesActivity.class);
//				break;
//
//			case R.id.bookmarksButton:
//				intent = new Intent(StartActivity.this,
//						BookmarksActivity.class);
//				break;
//
//			case R.id.settingsButton:
//				intent = new Intent(StartActivity.this,
//						SettingsActivity.class);
//				break;
			}

			startActivity(intent);

		}

	};

}
