package com.example.bibleapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.bibleapp.entity.Book;
import com.example.bibleapp.entity.Chapter;
import com.example.bibleapp.entity.Verse;

public class BibleParserHandler
{
	public static final int PARSE_ALL_BOOKS = -1;
	public static final int PARSE_ALL_CHAPTERS = -1;
	public static final int PARSE_ALL_VERSES = -1;

	// XML node keys
	private static final String NODE_BIBLE = "bible"; // root node

	private static final String NODE_BOOK = "book";
	private static final String NODE_BOOK_NUMBER = "number";
	private static final String NODE_BOOK_CHAPTERS = "chapters";

	private static final String NODE_CHAPTER = "chapter";
	private static final String NODE_CHAPTER_NUMBER = "number";
	private static final String NODE_CHAPTER_VERSES = "verses";

	private static final String NODE_VERSE = "verse";
	private static final String NODE_VERSE_NUMBER = "number";
	private static final String NODE_VERSE_TEXT = "text";

	private List<Book> booksList = new ArrayList<Book>();

	public BibleParserHandler()
	{
	}

	public List<Book> getBooks()
	{
		return booksList;
	}

	public void parse(InputStreamReader inputStreamReader)
			throws XmlPullParserException, IOException
	{
		this.parse(inputStreamReader, PARSE_ALL_BOOKS, PARSE_ALL_CHAPTERS,
				PARSE_ALL_VERSES);
	}

	public void parse(InputStreamReader inputStreamReader, int bookNumber)
			throws XmlPullParserException, IOException
	{
		this.parse(inputStreamReader, bookNumber, PARSE_ALL_CHAPTERS,
				PARSE_ALL_VERSES);
	}

	public void parse(InputStreamReader inputStreamReader, int bookNumber,
			int chapterNumber) throws XmlPullParserException, IOException
	{
		this.parse(inputStreamReader, bookNumber, chapterNumber,
				PARSE_ALL_VERSES);
	}

	public void parse(InputStreamReader inputStreamReader, int bookNumber,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{

		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;

		factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		parser = factory.newPullParser();

		// parser.setInput(is, null);

		parser.setInput(inputStreamReader);

		this.booksList = parseBook(parser, bookNumber, chapterNumber,
				verseNumber);
	}

	private List<Book> parseBook(XmlPullParser parser, int bookNumber,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{
		List<Book> booksList = new ArrayList<Book>();
		Book book = null;
		String tagName = "";
		String text = "";
		boolean canAddToList = false;

		int eventType = parser.next();

		while (eventType != XmlPullParser.END_DOCUMENT) {

			if (eventType == XmlPullParser.START_TAG
					|| eventType == XmlPullParser.END_TAG) {
				tagName = parser.getName();

				if (eventType == XmlPullParser.START_TAG) {

					if (tagName.equalsIgnoreCase(NODE_BOOK)) {
						// create a new instance of book
						book = new Book();
						canAddToList = true;
					}
					else if (canAddToList
							&& tagName.equalsIgnoreCase(NODE_BOOK_CHAPTERS)) {
						// parse chapters
						book.setChapters(parseChapters(parser, chapterNumber,
								verseNumber));
						eventType = parser.getEventType();
						continue;
					}
				}
				else if (canAddToList) {
					if (tagName.equalsIgnoreCase(NODE_BOOK_NUMBER)) {
						int number = Integer.parseInt(text);

						if (number == bookNumber
								|| bookNumber == PARSE_ALL_BOOKS) {
							book.setNumber(number);
						}
						else {
							canAddToList = false;
						}
					}
					else if (tagName.equalsIgnoreCase(NODE_BOOK)) {
						// add book object to list
						booksList.add(book);
						canAddToList = false;
					}
				}
			}
			else if (eventType == XmlPullParser.TEXT) {
				if (canAddToList) {
					text = parser.getText();
				}
			}

			eventType = parser.next();
		}

		return booksList;
	}

	private List<Chapter> parseChapters(XmlPullParser parser,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{

		List<Chapter> chaptersList = new ArrayList<Chapter>();
		Chapter chapter = null;
		String tagName = "";
		String text = "";
		boolean canAddToList = false;

		int eventType = parser.next();

		while (eventType != XmlPullParser.END_DOCUMENT) {

			if (eventType == XmlPullParser.START_TAG
					|| eventType == XmlPullParser.END_TAG) {
				tagName = parser.getName();

				if (tagName.equalsIgnoreCase(NODE_BOOK)) {
					break;
				}

				if (eventType == XmlPullParser.START_TAG) {

					if (tagName.equalsIgnoreCase(NODE_CHAPTER)) {
						// create a new instance of chapter
						chapter = new Chapter();
						canAddToList = true;
					}
					else if (canAddToList
							&& tagName.equalsIgnoreCase(NODE_CHAPTER_VERSES)) {
						// parse verses
						chapter.setVerses(parseVerses(parser, verseNumber));
						eventType = parser.getEventType();
						continue;
					}
				}
				else if (canAddToList) {
					if (tagName.equalsIgnoreCase(NODE_CHAPTER_NUMBER)) {
						int number = Integer.parseInt(text);

						if (number == chapterNumber
								|| chapterNumber == PARSE_ALL_CHAPTERS) {
							chapter.setNumber(number);
						}
						else {
							canAddToList = false;
						}
					}
					else if (tagName.equalsIgnoreCase(NODE_CHAPTER)) {
						// add chapter object to list
						chaptersList.add(chapter);
						canAddToList = false;
					}
				}
			}
			else if (eventType == XmlPullParser.TEXT) {
				if (canAddToList) {
					text = parser.getText();
				}
			}

			eventType = parser.next();
		}

		return chaptersList;
	}

	private List<Verse> parseVerses(XmlPullParser parser, int verseNumber)
			throws XmlPullParserException, IOException
	{

		List<Verse> versesList = new ArrayList<Verse>();
		Verse verse = null;
		String tagName = "";
		String text = "";
		boolean canAddToList = false;

		int eventType = parser.next();

		while (eventType != XmlPullParser.END_DOCUMENT) {

			if (eventType == XmlPullParser.START_TAG
					|| eventType == XmlPullParser.END_TAG) {
				tagName = parser.getName();

				if (tagName.equalsIgnoreCase(NODE_CHAPTER)) {
					break;
				}

				if (eventType == XmlPullParser.START_TAG) {

					if (tagName.equalsIgnoreCase(NODE_VERSE)) {
						// create a new instance of verse
						verse = new Verse();
						canAddToList = true;
					}
				}
				else if (canAddToList) {
					if (tagName.equalsIgnoreCase(NODE_VERSE_NUMBER)) {
						int number = Integer.parseInt(text);

						if (number == verseNumber
								|| verseNumber == PARSE_ALL_VERSES) {
							verse.setNumber(number);
						}
						else {
							canAddToList = false;
						}
					}
					else if (tagName.equalsIgnoreCase(NODE_VERSE_TEXT)) {
						verse.setText(text);
					}
					else if (tagName.equalsIgnoreCase(NODE_VERSE)) {
						// add verse object to list
						versesList.add(verse);
						canAddToList = false;
					}
				}
			}
			else if (eventType == XmlPullParser.TEXT) {
				if (canAddToList) {
					text = parser.getText();
				}
			}

			eventType = parser.next();
		}

		return versesList;
	}
}