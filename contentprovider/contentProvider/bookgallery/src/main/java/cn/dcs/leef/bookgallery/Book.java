package cn.dcs.leef.bookgallery;

import android.net.Uri;

public class Book {
	public static final String ID="_id";
	public static final String TITLE="title";
	public static final String PRICE ="price";
	public static final String AUTHORITY="com.android.provider.book";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/item");
}
