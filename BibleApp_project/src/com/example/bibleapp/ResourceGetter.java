package com.example.bibleapp;

import android.content.res.Resources;

public class ResourceGetter
{
	public static String[] getBooksArray(Resources res, int testamentNumber)
	{
		String[] books = null;

		switch (testamentNumber)
		{
			case 0:
				books = res.getStringArray(R.array.books_old_testament);
				break;
			case 1:
				books = res.getStringArray(R.array.books_new_testament);
				break;
		}
		return books;
	}

	public static int[] getChaptersCountArray(Resources res, int testamentNumber)
	{
		int[] chapters_count = null;

		// get chapters_count array from resources depends on testament number
		switch (testamentNumber)
		{
			case 0:
				chapters_count = res
						.getIntArray(R.array.chapters_count_old_testament);
				break;
			case 1:
				chapters_count = res
						.getIntArray(R.array.chapters_count_new_testament);
				break;
		}

		return chapters_count;
	}

	public static int getBooksArrayCount(Resources res, int testamentNumber)
	{
		return getBooksArray(res, testamentNumber).length;
	}
}
