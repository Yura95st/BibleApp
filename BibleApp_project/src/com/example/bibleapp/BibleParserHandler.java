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
	public static final int READ_ALL_BOOKS = 0;
	public static final int READ_ALL_CHAPTERS = 0;
	public static final int READ_ALL_VERSES = 0;

	// Don't use namespaces in parses
	private static final String namespace = null;

	// XML node keys
	private static final String NODE_BIBLE = "bible"; // root node

	private static final String NODE_BOOK = "book";
	private static final String NODE_BOOK_NUMBER = "number";
	private static final String NODE_BOOK_TITLE = "title";
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
		this.parse(inputStreamReader, READ_ALL_BOOKS, READ_ALL_CHAPTERS,
				READ_ALL_VERSES);
	}

	public void parse(InputStreamReader inputStreamReader, int bookNumber)
			throws XmlPullParserException, IOException
	{
		this.parse(inputStreamReader, bookNumber, READ_ALL_CHAPTERS,
				READ_ALL_VERSES);
	}

	public void parse(InputStreamReader inputStreamReader, int bookNumber,
			int chapterNumber) throws XmlPullParserException, IOException
	{
		this.parse(inputStreamReader, bookNumber, chapterNumber,
				READ_ALL_VERSES);
	}

	public void parse(InputStreamReader inputStreamReader, int bookNumber,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{

		try {
			XmlPullParserFactory factory = null;
			XmlPullParser parser = null;

			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();

			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputStreamReader);
			parser.nextTag();

			this.booksList = readBible(parser, bookNumber, chapterNumber,
					verseNumber);
		}
		finally {
			inputStreamReader.close();
		}
	}

	private List<Book> readBible(XmlPullParser parser, int bookNumber,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{
		List<Book> books = new ArrayList<Book>();

		parser.require(XmlPullParser.START_TAG, namespace, NODE_BIBLE);

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the book tag
			if (name.equals(NODE_BOOK)) {
				Book book = readBook(parser, bookNumber, chapterNumber,
						verseNumber);
				if (book != null) {
					books.add(book);
				}
			}
			else {
				skip(parser);
			}
		}
		return books;
	}

	private Book readBook(XmlPullParser parser, int bookNumber,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_BOOK);

		int number = 0;
		String title = null;
		List<Chapter> chapters = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			// Read only necessary books
			if (number != 0 && number != bookNumber
					&& bookNumber != READ_ALL_BOOKS) {
				return null;
			}

			String name = parser.getName();
			if (name.equals(NODE_BOOK_NUMBER)) {
				number = readBookNumber(parser);
			}
			else if (name.equals(NODE_BOOK_TITLE)) {
				title = readBookTitle(parser);
			}
			else if (name.equals(NODE_BOOK_CHAPTERS)) {
				chapters = readBookChapters(parser, chapterNumber, verseNumber);
			}
			else {
				skip(parser);
			}
		}
		return new Book(number, title, chapters);
	}

	// Processes number tag in the book.
	private int readBookNumber(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_BOOK_NUMBER);
		int number = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.END_TAG, namespace, NODE_BOOK_NUMBER);
		return number;
	}

	// Processes title tag in the book.
	private String readBookTitle(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_BOOK_TITLE);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, namespace, NODE_BOOK_TITLE);
		return title;
	}

	// Processes chapters tag in the book.
	private List<Chapter> readBookChapters(XmlPullParser parser,
			int chapterNumber, int verseNumber) throws XmlPullParserException,
			IOException
	{
		List<Chapter> chapters = new ArrayList<Chapter>();

		parser.require(XmlPullParser.START_TAG, namespace, NODE_BOOK_CHAPTERS);

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the chapter tag
			if (name.equals(NODE_CHAPTER)) {
				Chapter chapter = readChapter(parser, chapterNumber,
						verseNumber);
				if (chapter != null) {
					chapters.add(chapter);
				}
			}
			else {
				skip(parser);
			}
		}
		return chapters;
	}

	// Processes chapter tag in the chapters.
	private Chapter readChapter(XmlPullParser parser, int chapterNumber,
			int verseNumber) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_CHAPTER);

		int number = 0;
		List<Verse> verses = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			// Read only necessary chapters
			if (number != 0 && number != chapterNumber
					&& chapterNumber != READ_ALL_CHAPTERS) {
				return null;
			}

			String name = parser.getName();
			if (name.equals(NODE_CHAPTER_NUMBER)) {
				number = readChapterNumber(parser);
			}
			else if (name.equals(NODE_CHAPTER_VERSES)) {
				verses = readChapterVerses(parser, verseNumber);
			}
			else {
				skip(parser);
			}
		}
		return new Chapter(number, verses);
	}

	// Processes number tag in the chapter.
	private int readChapterNumber(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_CHAPTER_NUMBER);
		int number = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.END_TAG, namespace, NODE_CHAPTER_NUMBER);
		return number;
	}

	// Processes verses tag in the chapter
	private List<Verse> readChapterVerses(XmlPullParser parser, int verseNumber)
			throws XmlPullParserException, IOException
	{
		List<Verse> verses = new ArrayList<Verse>();

		parser.require(XmlPullParser.START_TAG, namespace, NODE_CHAPTER_VERSES);

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the verse tag
			if (name.equals(NODE_VERSE)) {
				Verse verse = readVerse(parser, verseNumber);
				if (verse != null) {
					verses.add(verse);
				}
			}
			else {
				skip(parser);
			}
		}
		return verses;
	}

	// Processes verse tag in the verses.
	private Verse readVerse(XmlPullParser parser, int verseNumber)
			throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_VERSE);

		int number = 0;
		String text = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			// Read only necessary verses
			if (number != 0 && number != verseNumber
					&& verseNumber != READ_ALL_VERSES) {
				return null;
			}

			String name = parser.getName();
			if (name.equals(NODE_VERSE_NUMBER)) {
				number = readVerseNumber(parser);
			}
			else if (name.equals(NODE_VERSE_TEXT)) {
				text = readVerseText(parser);
			}
			else {
				skip(parser);
			}
		}
		return new Verse(number, text);
	}

	// Processes number tag in the verse.
	private int readVerseNumber(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_VERSE_NUMBER);
		int number = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.END_TAG, namespace, NODE_VERSE_NUMBER);
		return number;
	}

	// Processes text tag in the verse.
	private String readVerseText(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, namespace, NODE_VERSE_TEXT);
		String text = readText(parser);
		parser.require(XmlPullParser.END_TAG, namespace, NODE_VERSE_TEXT);
		return text;
	}

	// Extracts tags text values.
	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next())
			{
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}
}