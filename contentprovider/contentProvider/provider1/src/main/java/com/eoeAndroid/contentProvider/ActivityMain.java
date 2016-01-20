package com.eoeAndroid.contentProvider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.eoeAndroid.R;
import com.eoeAndroid.contentProvider.Diary.DiaryColumns;

import java.lang.reflect.Field;

public class ActivityMain extends Activity {

    // 插入一条新纪录
    public static final int MENU_ITEM_INSERT = Menu.FIRST;
    // 编辑内容
    public static final int MENU_ITEM_EDIT = Menu.FIRST + 1;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 2;

    private static final String[] PROJECTION = new String[] { DiaryColumns._ID,
            DiaryColumns.TITLE, DiaryColumns.CREATED };
    private SimpleCursorAdapter mAdapter;
    private int RecordId = 0;
    private ListView myListView;
    private Cursor myCursor;
    private static final String TAG = "ActivityMain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);
        myListView = (ListView) this.findViewById(R.id.myListView);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(DiaryColumns.CONTENT_URI);
        }
        myCursor = managedQuery(getIntent().getData(), PROJECTION, null,
                null, DiaryColumns.DEFAULT_SORT_ORDER);

        mAdapter = new SimpleCursorAdapter(this,
                R.layout.diary_row, myCursor, new String[] { DiaryColumns.TITLE,
                DiaryColumns.CREATED }, new int[] { R.id.text1,
                R.id.created });
        myListView.setAdapter(mAdapter);
         /* 将myListView添加OnItemClickListener */
        myListView.setOnItemClickListener
                (new AdapterView.OnItemClickListener() {

                    public void onItemClick
                            (AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	        /* 将myCursor移到所点击的值 */
                        myCursor.moveToPosition(arg2);
	        /* 取得字段_id的值 */
                        RecordId = myCursor.getInt(0);
                        Log.v(TAG,"setOnItemClickListener = "+ RecordId);

                    }

                });
        myListView.setOnItemSelectedListener
                (new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected
                            (AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	        /* getSelectedItem所取得的是SQLiteCursor */
                        SQLiteCursor sc = (SQLiteCursor) arg0.getSelectedItem();
                        RecordId = sc.getInt(0);
                        Log.v(TAG,"setOnItemSelectedListener = "+ RecordId);
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        Log.v(TAG,"onCreate");
        getOverflowMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.v(TAG,"onCreateOptionsMenu");
        menu.add(0, MENU_ITEM_INSERT, 0, R.string.menu_insert);
//        menu.add(0, MENU_ITEM_EDIT, 0, R.string.title_edit);
//        menu.add(0, MENU_ITEM_DELETE, 0, R.string.title_delete);
        return true;
    }
    //force to show overflow menu in actionbar
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
	/*
	 * 在每一次menu生成的时候前都会调用这个方法，在这个方法里边可以动态的修改生成的menu。
	 */
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.v(TAG,"onPrepareOptionsMenu"+RecordId);
        if (RecordId>0) {
            // 如果选中一个Item的话

                menu.removeGroup(1);
                Uri uri = ContentUris.withAppendedId(getIntent().getData(),
                        RecordId);
                Intent intent = new Intent(null, uri);
                menu.add(1, MENU_ITEM_EDIT, 1, "编辑内容").setIntent(intent);
                menu.add(1, MENU_ITEM_DELETE, 1, "删除当前日记");


        }else{
            menu.removeGroup(1);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 插入一条数据
            case MENU_ITEM_INSERT:
                Intent intent0 = new Intent(this, ActivityDiaryEditor.class);
                intent0.setAction(ActivityDiaryEditor.INSERT_DIARY_ACTION);
                intent0.setData(getIntent().getData());
                startActivityForResult(intent0,1);
                RecordId =0;
                return true;
            // 编辑当前数据内容
            case MENU_ITEM_EDIT:
                if(RecordId ==0)
                    return true;
                Intent intent = new Intent(this, ActivityDiaryEditor.class);
                intent.setData(ContentUris.withAppendedId(getIntent().getData(),
                        RecordId));
                intent.setAction(ActivityDiaryEditor.EDIT_DIARY_ACTION);
                startActivityForResult(intent,0);
                RecordId =0;
                return true;
            // 删除当前数据
            case MENU_ITEM_DELETE:
                if(RecordId ==0)
                   return true;
                Uri uri = ContentUris.withAppendedId(getIntent().getData(),
                        RecordId);
                getContentResolver().delete(uri, null, null);
                renderListView();
                RecordId = 0;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK)
            {
                Log.v(TAG,"onActivityResult");
            }

    }

    private void renderListView() {
        myCursor = managedQuery(getIntent().getData(), PROJECTION, null,
                null, DiaryColumns.DEFAULT_SORT_ORDER);

        mAdapter.changeCursor(myCursor);

    }
}
