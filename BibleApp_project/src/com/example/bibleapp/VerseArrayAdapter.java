package com.example.bibleapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bibleapp.entity.Verse;

public class VerseArrayAdapter extends BaseAdapter
{
	private Activity activity;
	private List<Verse> data;
	private static LayoutInflater inflater = null;

	public VerseArrayAdapter(Activity activity, List<Verse> data)
	{
		this.activity = activity;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.verse_row, null);
		}

		TextView verseNumber = (TextView) vi
				.findViewById(R.id.verseNumberTextView);
		TextView verseText = (TextView) vi.findViewById(R.id.verseTextTextView);

		Verse verse = data.get(position);

		// Setting all values in ListView
		verseNumber.setText(Integer.toString(verse.getNumber()));
		verseText.setText(verse.getText());

		return vi;
	}
}
