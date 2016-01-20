package cn.dcs.leef.provider3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //数据库的名称
    private static final String DATABASE_NAME="student.db";
    private static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="student";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    /**
     * 创建table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建学生信息表，包括四个字段
        //包括id、姓名、性别、年龄
        String sql="create table "+TABLE_NAME+" ("
                +Student._ID+" integer primary key,"
                +Student.NMAE+" text,"
                +Student.GENDER+" text,"
                +Student.AGE+" integer "+");";
        db.execSQL(sql);
    }
    /**
     * 更新表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql="drop table if exits student";
        db.execSQL(sql);
    }
}
