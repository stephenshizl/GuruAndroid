package cn.dcs.leef.bookprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class BookProvider extends ContentProvider {

	public class BookDatabasehelper extends SQLiteOpenHelper {

		public BookDatabasehelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("BookDatabasehelper onCreate... ");
			db.execSQL("CREATE TABLE " + Book.TABLE_NAME + " (" + Book.ID
					+ " INTEGER PRIMARY KEY," + Book.TITLE + " TEXT,"
					+ Book.PRICE + " TEXT" + ");");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			System.out.println("BookDatabasehelper onUpgrade... ");
			Log.w("BookDatabasehelper", "Upgrading database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}

	}

	public BookDatabasehelper databasehelper;
	public static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Book.AUTHORITY, "item", Book.ITEM);
		uriMatcher.addURI(Book.AUTHORITY, "item/#", Book.ITEM_ID);
	}

	@Override
	public int delete(Uri uri, String where, String[] args) {
		// TODO Auto-generated method stub
		System.out.println("BookProvider delete....");
		SQLiteDatabase db = databasehelper.getWritableDatabase();
		int count;
		switch (uriMatcher.match(uri)) {
		case Book.ITEM:
			count = db.delete(Book.TABLE_NAME, where, args);
			System.out.println("BookProvider delete....count"+count);
			break;
		case Book.ITEM_ID:
			String id = uri.getPathSegments().get(1);
			count = db.delete(Book.TABLE_NAME,
					Book.ID
							+ "="
							+ id
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), args);
			System.out.println("BookProvider delete....count"+count+"id="+id);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		System.out.println(" BookProvider getType...");
		int code = uriMatcher.match(uri);
		switch (code) {
		case Book.ITEM:
			System.out.println(" BookProvider getType..." +Book.CONTENT_TYPE);
			return Book.CONTENT_TYPE;
		case Book.ITEM_ID:
			System.out.println(" BookProvider getType..." +Book.CONTENT_ITEM_TYPE);
			return Book.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		System.out.println("BookProvider insert...");
		SQLiteDatabase db = databasehelper.getWritableDatabase();
		long rowId;
		if (uriMatcher.match(uri) != Book.ITEM) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		rowId = db.insert(Book.TABLE_NAME, Book.ID, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(Book.CONTENT_URI, rowId);
			System.out.println("BookProvider insert  notifyChange");
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		System.out.println("BookProvider  onCreate:");
		databasehelper = new BookDatabasehelper(getContext(),
				Book.DATABASE_NAME, null, Book.VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		System.out.println("BookProvider  query:");
		SQLiteDatabase db = databasehelper.getReadableDatabase();
		Cursor c;
		switch (uriMatcher.match(uri)) {
		case Book.ITEM:
			c = db.query(Book.TABLE_NAME, projection, selection, selectionArgs,
					null, null, sortOrder);
			System.out.println("BookProvider  query:" +Book.CONTENT_TYPE);
			break;
		case Book.ITEM_ID:
			String id = uri.getPathSegments().get(1);
			c = db.query(Book.TABLE_NAME, projection, Book.ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs, null, null, sortOrder);
			System.out.println("BookProvider  query:" +Book.CONTENT_ITEM_TYPE);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		c.setNotificationUri(getContext().getContentResolver(), uri);  
		return c;  
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] args) {
		// TODO Auto-generated method stub
		System.out.println("BookProviderupdate.... ");
		SQLiteDatabase db = databasehelper.getWritableDatabase();
		int count;
		switch (uriMatcher.match(uri)) {
		case Book.ITEM:
			count = db.update(Book.TABLE_NAME, values, where, args);
			break;
		case Book.ITEM_ID:
			String id = uri.getPathSegments().get(1);
			count = db.update(Book.TABLE_NAME, values,
					Book.ID
							+ "="
							+ id
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), args);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
