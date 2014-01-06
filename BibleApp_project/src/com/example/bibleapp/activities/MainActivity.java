package com.example.bibleapp.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent;

		if (true) {
			intent = new Intent(this, StartActivity.class);
		} else {
			intent = new Intent(this, StartActivity.class);
		}

		startActivity(intent);
		finish();
	}

}
