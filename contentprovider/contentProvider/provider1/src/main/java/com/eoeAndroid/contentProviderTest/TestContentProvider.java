package com.eoeAndroid.contentProviderTest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.eoeAndroid.contentProvider.Diary;

/**
 * Created by hahong on 15-10-20.
 */
public class TestContentProvider extends InstrumentationTestCase {
    public void testInsert()throws Exception{   //插入记录
        Log.v("TestContentProvider", "TestNow");

        ContentResolver resolver = this.getInstrumentation().getContext().getContentResolver();

        ContentValues values = new ContentValues();
//        values.put(Diary.DiaryColumns.CREATED, DiaryContentProvider
//                .getFormateCreatedDate());
        values.put(Diary.DiaryColumns.TITLE, "title");
        values.put(Diary.DiaryColumns.BODY, "body");
        resolver.insert(Diary.DiaryColumns.CONTENT_URI, values);
    }
    public void testQuery()throws Exception{    //插入全部记录并显示
         //查询所有记录
        ContentResolver resolver = this.getInstrumentation().getContext().getContentResolver();
        Cursor cursor = resolver.query(Diary.DiaryColumns.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()){

            Log.v("TestContentProvider", cursor.getColumnName(0));
        }
    }
}
