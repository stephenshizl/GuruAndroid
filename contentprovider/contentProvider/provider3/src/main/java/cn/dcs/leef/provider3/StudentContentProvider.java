package cn.dcs.leef.provider3;

/**
 * Created by iof on 2016/1/20.
 */
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class StudentContentProvider extends ContentProvider {
    //声明数据库帮助类对象
    private DbHelper dbHelper;
    //定义ContentResolver 对象
    private ContentResolver resolver;
    //定义Uri工具类
    private static final UriMatcher Urimatcher;
    //匹配码
    private static final int STUDENT=1;
    private static final int STUDENT_ID=2;
    //需要查询 的列集合
    private static HashMap<String,String> stu;
    //

    static
    {
        Urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
        //添加需要匹配的Uri，匹配则返回相应的匹配码
        Urimatcher.addURI(Student.AUTHORITY, "student", STUDENT);
        Urimatcher.addURI(Student.AUTHORITY, "student/#", STUDENT_ID);
        //实例化集合
        stu=new HashMap<String,String>();
        //添加查询列map
        stu.put(Student._ID, Student._ID);
        stu.put(Student.NMAE,Student.NMAE );
        stu.put(Student.GENDER, Student.GENDER);
        stu.put(Student.AGE,Student.AGE );
    }

    /**
     * 初始化
     */
    @Override
    public boolean onCreate() {
        //创建DbHelper对象
        dbHelper=new DbHelper(getContext());
        return true;
    }
    /**
     * 查询数据
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor;
        switch(Urimatcher.match(uri))
        {
            case STUDENT:
                cursor=db.query(DbHelper.TABLE_NAME,projection , selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case STUDENT_ID:
                String stuId=uri.getPathSegments().get(1);
                String where="Student._ID="+stuId;
                if(selection!=null&&"".equals(selection.trim()))//trim()函数的功能是去掉首尾空格
                {
                    where+=" and "+selection;
                }
                cursor=db.query(DbHelper.TABLE_NAME, projection, where, selectionArgs, null, null, sortOrder);
                break;
            default:
                //如果传进来的Uri不是我们需要的类型
                throw new IllegalArgumentException("this is Unknown Uri："+uri);
        }
        return cursor;
    }
    /**
     * 获得类型
     */
    @Override
    public String getType(Uri uri) {


        return null;
    }
    /**
     * 插入数据
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        //获得可写入的数据库
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentResolver resolver=this.getContext().getContentResolver();
        //插入数据，返回行号ID
        long rowid=db.insert(DbHelper.TABLE_NAME, Student.NMAE, values);
        //如果插入成功，返回Uri
        if(rowid>0)
        {
            Uri stuUri=ContentUris.withAppendedId(uri, rowid);
            resolver.notifyChange(stuUri, null);//数据发送变化时候，发出通知给注册了相应uri的
            return stuUri;
        }
        return null;
    }
    /**
     * 删除数据
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        resolver=this.getContext().getContentResolver();
        int count;
        //根据返回的匹配码进行相应的删除动作
        switch(Urimatcher.match(uri))
        {
            case STUDENT:
                count=db.delete(DbHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_ID: //只删除对于的id
                //getPathSegments()方法得到一个String的List
                String stuId=uri.getPathSegments().get(1);
                count=db.delete(DbHelper.TABLE_NAME, Student._ID+"="+stuId+(!TextUtils.isEmpty(selection) ?
                        " and ("+selection+')':""), selectionArgs);
                break;
            default:
                //如果传进来的Uri不是我们需要的类型
                throw new IllegalArgumentException("this is Unknown Uri："+uri);
        }
        resolver.notifyChange(uri, null);
        return count;
    }
    /**
     * 更新数据
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db=dbHelper.getWritableDatabase();//创建一个可读的数据库
        resolver=this.getContext().getContentResolver();
        int count;
        switch(Urimatcher.match(uri))
        {
            case STUDENT:
                count=db.update(DbHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STUDENT_ID:
                String stuId=uri.getPathSegments().get(1);//获得id
                count=db.update(DbHelper.TABLE_NAME, values,Student._ID+"="+stuId+(!TextUtils.isEmpty(selection) ?
                        " and ("+selection+')':""), selectionArgs);
                break;
            default:
                //如果传进来的Uri不是我们需要的类型
                throw new IllegalArgumentException("this is Unknown Uri："+uri);
        }
        resolver.notifyChange(uri, null);
        return count;
    }
}
