package cn.dcs.leef.provider4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static String DATABASE_NAME = "mydatabase2.db";
	public static int version = 1;
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, version);
		// TODO Auto-generated constructor stub
	}
	
    @Override  
    public void onOpen(SQLiteDatabase db) {  
        super.onOpen(db);  
        //这是当打开数据库时的回调函数，一般也不会用到。
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 在数据库第一次生成的时候会调用这个方法，一般我们在这个方法里边生成数据库表
		// SQL语句
//		String sql = "CREATE TABLE " + MyColumns.TABLE_NAME + " (" +MyColumns._ID +"int not null,"+ MyColumns.TITLE
//		+ " text not null, " + MyColumns.BODY + " text not null," +MyColumns.NAME+"text not null"+ ");";
		String s = "CREATE TABLE \"mytable\"( [_id] int PRIMARY KEY ,[title] varchar(100) ,[body] varchar(10) ,[name] varchar(100) ) ";
		//执行这条SQL语句
		db.execSQL(s);

		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 当数据库需要升级的时候，Android系统会主动的调用这个方法。一般我们在这个方法里边删除数据表，并建立新的数据表，
		db.execSQL("DROP TABLE IF EXISTS notes");
		onCreate(db);

	}  

}
