package com.example.bibleapp.history;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class HistoryProcessor
{
	private static String LOG = "HistoryProcessor: ";
	private Context context;
	private HistoryDBHelper db;

	public HistoryProcessor(Context context)
	{
		this.context = context;
		db = new HistoryDBHelper(context);
	}

	public int put(int bookNumber, int chapterNumber)
	{
		int result = -1;

		History history = new History();
		history.setBookId(bookNumber);
		history.setChapterId(chapterNumber);
		history.setViewCount(1);

		try {
			result = (int) db.addHistory(history);
		}
		catch (SQLiteException e) {
			Log.e(LOG + "put", e.getLocalizedMessage());
		}
		finally {
			db.closeDB();
			return result;
		}
	}

	public History get(int historyId)
	{
		History history = null;

		try {
			history = db.getHistory(historyId);
		}
		catch (SQLiteException e) {
			Log.e(LOG + "get", e.getMessage());
		}
		catch (IllegalArgumentException e) {
			Log.e(LOG + "get", e.getMessage());
		}
		finally {
			db.closeDB();
			return history;
		}
	}

	// PROBLEM with data and time
	public List<History> getAll()
	{
		List<History> items = null;

		try {
			items = db.getAllHistory();
		}
		catch (SQLiteException e) {
			Log.e(LOG + "getAll", e.getMessage());
		}
		catch (IllegalArgumentException e) {
			Log.e(LOG + "getAll", e.getMessage());
		}
		finally {
			db.closeDB();
			return items;
		}
	}

	public void delete(int historyId)
	{
		try {
			db.deleteHistory(historyId);
		}
		catch (SQLiteException e) {
			Log.e(LOG + "delete", e.getLocalizedMessage());
		}
		finally {
			db.closeDB();
		}
	}

	public void clean()
	{
		try {
			db.deleteAllHistory();
		}
		catch (SQLiteException e) {
			Log.e(LOG + "clean", e.getLocalizedMessage());
		}
		finally {
			db.closeDB();
		}
	}

	public int count()
	{
		int result = 0;

		try {
			result = db.getHistoryCount();
		}
		catch (SQLiteException e) {
			Log.e(LOG + "clean", e.getLocalizedMessage());
		}
		finally {
			db.closeDB();
			return result;
		}
	}
}
