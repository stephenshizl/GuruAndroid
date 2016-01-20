package cn.dcs.leef.provider4;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * ContentProvider是什么时候创建的，是谁创建的？访问某个应用程序共享的数据，是否需要启动这个应用程序？
 * 这个问题在 Android SDK中没有明确说明，但是从数据共享的角度出发，ContentProvider应该是Android在系统启动时就创建了，否则就谈不上数据共享了。 
 * 这就要求在AndroidManifest.XML中使用元素明确定义。
 *  可能会有多个程序同时通过ContentResolver访问一个ContentProvider，会不会导致像数据库那样的“脏数 据”？
 *  这个问题一方面需要数据库访问的同步，尤其是数据写入的同步，在AndroidManifest.XML中定义ContentProvider的时 候，需要考虑是元素multiprocess属性的值；
 *  另外一方面Android在ContentResolver中提供了notifyChange() 接口，在数据改变时会通知其他ContentObserver，这个地方应该使用了观察者模式，
 *  在ContentResolver中应该有一些类似 register，unregister的接口。 
 *  至此，已经对ContentProvider提供了比较全面的分析，至于如何创建ContentProvider，
 *  可通过2种方法：创建一个属于你自己的 ContentProvider或者将你的数据添加到一个已经存在的ContentProvider中，当然前提是有相同数据类型并且有写入 Content provider的权限。
 * 
 * */
public class MyContentProvider extends ContentProvider {
	
	private static final String TABLE_NAME = "mytable";
	private static final int TABLES = 1;
	private static final int TABLE_ID = 2;
	private static final UriMatcher sUriMatcher ;
	static{//注册需要匹配的Uri
		/**
		 * 	什么是URI？
			将其分为A，B，C，D 4个部分：
			A：标准前缀，用来说明一个Content Provider控制这些数据，无法改变的；"content://"
			B：URI的标识，它定义了是哪个Content Provider提供这些数据。对于第三方应用程序，为了保证URI标识的唯一性，它必须是一个完整的、小写的 类名。这个标识在 元素的 authorities属性中说明：一般是定义该ContentProvider的包.类的名称 ;"content://hx.android.text.myprovider"
			C：路径，不知道是不是路径，通俗的讲就是你要操作的数据库中表的名字，或者你也可以自己定义，记得在使用的时候保持一致就ok了；"content://hx.android.text.myprovider/tablename"
			D：如果URI中包含表示需要获取的记录的ID；则就返回该id对应的数据，如果没有ID，就表示返回全部； "content://hx.android.text.myprovider/tablename/#" #表示数据id
		 * 
		 * 
		 * 
		 *  UriMatcher：用于匹配Uri，它的用法如下：
			1.首先把你需要匹配Uri路径全部给注册上，如下： 
			//常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码(-1)。
			 *  UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); //如果match()方法匹配
			 *  content://com.changcheng.sqlite.provider.contactprovider/contact路径，返回匹配码为1 
			 *  uriMatcher.addURI(“com.changcheng.sqlite.provider.contactprovider”, “contact”, 1); //添加需要匹配uri，如果匹配就会返回匹配码 //如果match()方法匹配 
			 *  content://com.changcheng.sqlite.provider.contactprovider/contact/230路径，返回匹配码为2 
			 *  uriMatcher.addURI(“com.changcheng.sqlite.provider.contactprovider”, “contact/#”, 2);//#号为通配符
			2.注册完需要匹配的Uri后，就可以使用uriMatcher.match(uri)方法对输入的Uri进行匹配，如果匹配就返回匹配码，匹配码是调用addURI()方法传入的第三个参数，假设匹配content://com.changcheng.sqlite.provider.contactprovider/contact路径，返回的匹配码为1。
		 * 
		 * 
		 * */
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(Mytable.MyColumns.AUTHORITY, TABLE_NAME, TABLES);
		sUriMatcher.addURI(Mytable.MyColumns.AUTHORITY, TABLE_NAME+"/#", TABLE_ID);
	
	};


	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		 
		return db.delete(TABLE_NAME, arg1, arg2);

	}
	 
	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
			case TABLES:
			return Mytable.MyColumns.CONTENT_TYPE;
			case TABLE_ID:
			return Mytable.MyColumns.CONTENT_ITEM_TYPE;
			default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

	}

	int i = 1;
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// TODO Auto-generated method stub
		if (sUriMatcher.match(uri) != TABLES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		ContentValues values;
		if (initialValues != null) {
			Log.i("##not null", "initialValues");
			values = new ContentValues(initialValues);
		} 
		else {
			Log.i("##null", "initialValues");
			values = new ContentValues();
		}
		
		if (values.containsKey(Mytable.MyColumns._ID) == false) {
			values.put(Mytable.MyColumns._ID, i);
			i++;
		}
		if (values.containsKey(Mytable.MyColumns.TITLE) == false) {
			values.put(Mytable.MyColumns.TITLE, "title"+i);
		}
		if (values.containsKey(Mytable.MyColumns.BODY) == false) {
			values.put(Mytable.MyColumns.BODY,"body"+i);
		}
		if (values.containsKey(Mytable.MyColumns.NAME) == false) {
		values.put(Mytable.MyColumns.NAME, "name"+i);
		}
		
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_NAME, Mytable.MyColumns.BODY, values);
		if (rowId > 0) {
			/**
			 * 下面会用到辅助类ContentUris 的两个实用方法
			 * withAppendedId(uri, id)用于为路径加上ID部分
			 * parseId(uri)方法用于从路径中获取ID部分
			 */
			Uri myUri= ContentUris.withAppendedId(Mytable.MyColumns.CONTENT_URI, rowId);
			return myUri;//返回值为一个uri
		}
		throw new SQLException("Failed to insert row into " + uri);

	}

	DatabaseHelper mOpenHelper ;
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mOpenHelper = new DatabaseHelper(getContext());	
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();//是一个构造SQL查询语句的辅助类
		switch (sUriMatcher.match(uri)) {
			case TABLES:
				qb.setTables(TABLE_NAME);
				break;
			case TABLE_ID:
				qb.setTables(TABLE_NAME);
				qb.appendWhere(Mytable.MyColumns._ID + "="
						+ uri.getPathSegments().get(1));
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
 
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		return c;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String rowId = uri.getPathSegments().get(1);
		return db.update(TABLE_NAME, values, Mytable.MyColumns._ID + "="
		+ rowId, null);

	}

}
