package com.example.bibleapp.history;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HistoryDBHelper extends SQLiteOpenHelper
{
	// Logcat tag
	private static final String LOG = HistoryDBHelper.class.getName();

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Table Names
	private static final String TABLE_HISTORY = "history";

	// Common column names
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_CREATED_AT = "created_at";

	// HISTORY table - column names
	private static final String HISTORY_COLUMN_BOOK_ID = "book_id";
	private static final String HISTORY_COLUMN_CHAPTER_ID = "chapter_id";
	private static final String HISTORY_COLUMN_VIEW_COUNT = "view_count";
	private static final String HISTORY_COLUMN_VIEWED_AT = "viewed_at";

	// Type names
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String TEXT_TYPE = " TEXT";
	private static final String DATETIME_TYPE = " DATETIME";
	private static final String COMMA_SEP = ",";

	// Table Create Statements

	// HISTORY table create statement
	private static final String CREATE_TABLE_HISTORY = "CREATE TABLE "
			+ TABLE_HISTORY + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
			+ HISTORY_COLUMN_BOOK_ID + INTEGER_TYPE + COMMA_SEP
			+ HISTORY_COLUMN_CHAPTER_ID + INTEGER_TYPE + COMMA_SEP
			+ HISTORY_COLUMN_VIEW_COUNT + INTEGER_TYPE + COMMA_SEP
			+ HISTORY_COLUMN_VIEWED_AT + DATETIME_TYPE + ")";

	public HistoryDBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// creating required tables
		db.execSQL(CREATE_TABLE_HISTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_HISTORY);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "HISTORY" table methods ----------------//

	/**
	 * Creating a history item
	 */
	public int addHistory(History history) throws SQLiteException,
			IllegalArgumentException
	{
		int historyId = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		History historyOld = getHistory(history.getBookId(),
				history.getChapterId());

		// if such history already exists - update ViewCount
		if (historyOld != null) {
			historyId = historyOld.getId();

			history.setId(historyId);
			history.setViewCount(historyOld.getViewCount() + 1);

			updateHistory(history);

		}
		else {

			ContentValues values = new ContentValues();
			values.put(HISTORY_COLUMN_BOOK_ID, history.getBookId());
			values.put(HISTORY_COLUMN_CHAPTER_ID, history.getChapterId());
			values.put(HISTORY_COLUMN_VIEW_COUNT, history.getViewCount());
			values.put(HISTORY_COLUMN_VIEWED_AT, getDateTime());

			// insert row
			historyId = (int) db.insert(TABLE_HISTORY, null, values);
		}

		db.close();

		return historyId;
	}

	/**
	 * Get single history item
	 */
	public History getHistory(int historyId) throws SQLiteException,
			IllegalArgumentException
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_HISTORY + " WHERE "
				+ COLUMN_ID + " = " + String.valueOf(historyId);

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		db.close();

		return getHistoryItemFromQueryResult(c);
	}

	public History getHistory(int bookId, int chapterId)
			throws SQLiteException, IllegalArgumentException
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_HISTORY + " WHERE "
				+ HISTORY_COLUMN_BOOK_ID + " = " + String.valueOf(bookId)
				+ " AND " + HISTORY_COLUMN_CHAPTER_ID + " = "
				+ String.valueOf(chapterId);

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		db.close();

		return getHistoryItemFromQueryResult(c);
	}

	private History getHistoryItemFromQueryResult(Cursor c)
			throws IllegalArgumentException
	{
		History historyItem = null;

		if (c != null && c.moveToFirst()) {

			historyItem = new History();

			try {

				historyItem.setId(c.getInt(c.getColumnIndexOrThrow(COLUMN_ID)));

				historyItem.setBookId(c.getInt(c
						.getColumnIndexOrThrow(HISTORY_COLUMN_BOOK_ID)));

				historyItem.setChapterId(c.getInt(c
						.getColumnIndexOrThrow(HISTORY_COLUMN_CHAPTER_ID)));

				historyItem.setViewCount(c.getInt(c
						.getColumnIndexOrThrow(HISTORY_COLUMN_VIEW_COUNT)));

				historyItem.setViewedAt(c.getString(c
						.getColumnIndexOrThrow(HISTORY_COLUMN_VIEWED_AT)));
			}
			catch (IllegalArgumentException e) {
				throw e;
			}

		}
		return historyItem;
	}

	/**
	 * Getting all history items
	 * */
	public List<History> getAllHistory() throws SQLiteException,
			IllegalArgumentException
	{
		List<History> items = new ArrayList<History>();
		String selectQuery = "SELECT * FROM " + TABLE_HISTORY;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		int count = c.getCount();

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				History historyItem = getHistoryItemFromQueryResult(c);

				if (historyItem != null) {
					// adding to item list
					items.add(historyItem);
				}
			}
			while (c.moveToNext());
		}

		db.close();

		return items;
	}

	/**
	 * Getting history count
	 */
	public int getHistoryCount() throws SQLiteException
	{
		String countQuery = "SELECT " + HISTORY_COLUMN_BOOK_ID + " FROM "
				+ TABLE_HISTORY;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		db.close();

		// return count
		return count;
	}

	/**
	 * Updating a history item
	 */
	public void updateHistory(History historyItem) throws SQLiteException
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(HISTORY_COLUMN_BOOK_ID, historyItem.getBookId());
		values.put(HISTORY_COLUMN_CHAPTER_ID, historyItem.getChapterId());
		values.put(HISTORY_COLUMN_VIEW_COUNT, historyItem.getViewCount());
		values.put(HISTORY_COLUMN_VIEWED_AT, getDateTime());

		// updating row
		db.update(TABLE_HISTORY, values, COLUMN_ID + " = ?",
				new String[] { String.valueOf(historyItem.getId()) });

		db.close();
	}

	/**
	 * Deleting a history item
	 */
	public void deleteHistory(long historyId) throws SQLiteException
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_HISTORY, COLUMN_ID + " = ?",
				new String[] { String.valueOf(historyId) });

		db.close();
	}

	/**
	 * Deleting all history
	 */
	public void deleteAllHistory() throws SQLiteException
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_HISTORY, "1", new String[] {});

		db.close();
	}

	// Closing database
	public void closeDB()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen()) {
			db.close();
		}
	}

	/**
	 * Get dateTime
	 * */
	private String getDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
