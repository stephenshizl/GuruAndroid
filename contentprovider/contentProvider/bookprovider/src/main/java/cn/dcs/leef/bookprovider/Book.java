package cn.dcs.leef.bookprovider;

import android.net.Uri;

public class Book {
	public static final String 	DATABASE_NAME="books.db";
	public static final String    TABLE_NAME="book";
	public static final int  VERSION=1;
	
	public static final String ID="_id";
	public static final String TITLE="title";
	public static final String PRICE ="price";
	
	public static final String AUTHORITY="com.android.provider.book";
	
	public static final int ITEM=1;
	public static final int ITEM_ID=2;
	
	public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.com.android.book";
	
	public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.com.android.book";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/item");
}
