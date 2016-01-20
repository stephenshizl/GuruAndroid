#ContentProvider Demo和原理解析

###ContentProvider自动生成框架
- https://github.com/BoD/android-contentprovider-generator
- https://github.com/SimonVT/schematic
- https://github.com/TimotheeJeannin/ProviGen
- http://triple-t.github.io/simpleprovider/
- https://github.com/foxykeep/ContentProviderCodeGenerator
- https://code.google.com/p/mdsd-android-content-provider/
- https://github.com/hamsterksu/Android-AnnotatedSQL
- http://robotoworks.com/mechanoid/doc/db/api.html
- https://github.com/robUx4/android-contentprovider-generator (a fork of this project that generates more code)
- https://github.com/novoda/sqlite-analyzer (based on sql statements, not json)
- https://github.com/ckurtm/simple-sql-provider

###ContentProvider内部原理
- http://blog.csdn.net/luoshengyang/article/details/6963418
- http://www.cnblogs.com/hnrainll/archive/2012/03/29/2424116.html


###ContentProvider权限 (参考BookProvider和BookGallery)
![](https://raw.githubusercontent.com/fitzlee/GuruAndroid/master/contentprovider/_images/bookgallery.png =200)
```java
  //Server
 <permission android:name="com.android.provider.book.READ_DATABASE" android:protectionLevel="normal" />
 <permission android:name="com.android.provider.book.WRITE_DATABASE" android:protectionLevel="normal" />
 <provider android:name="BookProvider"
            android:exported="true"
            android:authorities="com.android.provider.book"
            android:readPermission="com.android.provider.book.READ_DATABASE"
            android:writePermission="com.android.provider.book.WRITE_DATABASE" />
 //Client
 <uses-permission android:name="com.android.provider.book.READ_DATABASE" />
 <uses-permission android:name="com.android.provider.book.WRITE_DATABASE" />
```
* android:authorities要写和CONTENT_URI常量
* 如果不设置权限，无论ContentResolver是否在一个程序里，都可以直接访问; 但是为了数据安全，我们一般都会给ContentProvider设置权限

###要点
主要包含以下方法：
```Java
final Uri insert(Uri url, ContentValues values)
//Inserts a row into a table at the given URL.
final int delete(Uri url, String where, String[] selectionArgs)
//Deletes row(s) specified by a content URI.
final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
//Query the given URI, returning a Cursor over the result set.
final int update(Uri uri, ContentValues values, String where, String[] selectionArgs)
//Update row(s) in a content URI.
```

```
public boolean onCreate()：该方法在ContentProvider创建后就会被调用，Android系统运行后，ContentProvider只有在被第一次使用它时才会被创建。
	public Uri insert(Uri uri, ContentValues values)：外部应用程序通过这个方法向 ContentProvider添加数据。
	uri—— 标识操作数据的URI
	values—— 需要添加数据的键值对
public int delete(Uri uri, String selection, String[] selectionArgs)：外部应用程序通过这个方法从 ContentProvider中删除数据。
	uri——标识操作数据的URI
	selection——构成筛选添加的语句，如”id=1″ 或者 “id=?”
	selectionArgs——对应selection的两种情况可以传入null 或者 new String[]{“1″}
public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)：外部应用程序通过这个方法对 ContentProvider中的数据进行更新。
	values——对应需要更新的键值对，键为对应共享数据中的字段，值为对应的修改值
	其余参数同delete方法
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)：外部应用程序通过这个方法从ContentProvider中获取数据，并返回一个Cursor对象。
	projection——需要从Contentprovider中选择的字段，如果为空，则返回的Cursor将包含所有的字段。
	sortOrder——默认的排序规则
	其余参数同delete方法　　　　
public String getType(Uri uri)：该方法用于返回当前Url所代表数据的MIME类型。
	如果操作的数据属于集合类型，那么MIME类型字符串应该以vnd.android.cursor.dir/开头，
	例如：要得到所有person记录的Uri为content://com.wirelessqa.content.provider/profile，那么返回的MIME类型字符串应该为：”vnd.android.cursor.dir/profile”。
	如果要操作的数据属于非集合类型数据，那么MIME类型字符串应该以vnd.android.cursor.item/开头，
	例如：得到id为10的person记录，Uri为content://com.wirelessqa.content.provider/profile/10，那么返回的MIME类型字符串为：”vnd.android.cursor.item/profile”。
```

具体实现：(参考provider3,程序输出在log；Provider1和2也可)
![](https://raw.githubusercontent.com/fitzlee/GuruAndroid/master/contentprovider/_images/provider2.png =300)
![](https://raw.githubusercontent.com/fitzlee/GuruAndroid/master/contentprovider/_images/provider1.png =400)
```java
//提供Client完整接口
public class Student implements BaseColumns {
    public static final String AUTHORITY="com.example.provider.students";
    private Student(){} //构造方法
    public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/student");
    public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.example.provider.students";
    public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.example.provider.students";
    //数据库中的表字段
    public static final String NMAE="name";//姓名
    public static final String GENDER="gender";//性别
    public static final String AGE="age";//年龄
}

//创建数据库
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


//实现Provider
public class StudentContentProvider extends ContentProvider{
	//声明数据库帮助类对象
    private DbHelper dbHelper;
    //定义ContentResolver 对象
    private ContentResolver resolver;
    //定义Uri工具类
    private static final UriMatcher Urimatcher;
    //匹配码
    private static final int STUDENT=1;
    private static final int STUDENT_ID=2;
	static
	{
		Urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
		//添加需要匹配的Uri，匹配则返回相应的匹配码
		Urimatcher.addURI(AUTHORITY, "student", STUDENT);
		Urimatcher.addURI(AUTHORITY, "student/#", STUDENT_ID);
	}
	
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


```


###调用方法
```java

//get
ContentResolver resolver=this.getContentResolver();

//query
Cursor cs = resolver.query(Book.CONTENT_URI, null, null, null, Book.ID + " ASC");
Book book = new Book(cs);
cs.close();

//insert
String countryText = String.valueOf(etTitle.getText());  
String codeNum = String.valueOf(etPrice.getText());  
contentValues = new ContentValues();  
contentValues.put(Book.TITLE, countryText);  
contentValues.put(Book.PRICE, codeNum);  
resolver.insert(Book.CONTENT_URI, contentValues); 

//delete
Uri uri=ContentUris.withAppendedId(Book.CONTENT_URI, 2);
resolver.delete(uri, null, null);

//update
Uri uri=ContentUris.withAppendedId(Student.CONTENT_URI, id);
resolver=this.getContentResolver();
ContentValues values=new ContentValues();
values.put(Student.NMAE, "xiaofang");
values.put(Student.GENDER, "女");
values.put(Student.AGE, 25);
resolver.update(uri, values, null, null);

```


###系统uri
```
联系人的URI：
ContactsContract.Contacts.CONTENT_URI 管理联系人的Uri
ContactsContract.CommonDataKinds.Phone.CONTENT_URI 管理联系人的电话的Uri
ContactsContract.CommonDataKinds.Email.CONTENT_URI 管理联系人的Email的Uri
（注：Contacts有两个表，分别是rawContact和Data，rawContact记录了用户的id和name，
其中id栏名称 为：ContactsContract.Contacts._ID，name名称栏为ContactContract.Contracts.DISPLAY_NAME，
电话信息表的外键id为 ContactsContract.CommonDataKinds.Phone.CONTACT_ID，
电话号码栏名称为：ContactsContract.CommonDataKinds.Phone.NUMBER。
data表中Email地址栏名称为：ContactsContract.CommonDataKinds.Email.DATA
其外键栏为：ContactsContract.CommonDataKinds.Email.CONTACT_ID)

多媒体的ContentProvider的Uri如下：
MediaStore.Audio.Media.EXTERNAL_CONTENT_URI  存储在sd卡上的音频文件
MediaStore.Audio.Media.INTERNAL_CONTENT_URI  存储在手机内部存储器上的音频文件
MediaStore.Audio.Images.EXTERNAL_CONTENT_URI SD卡上的图片文件内容
MediaStore.Audio.Images.INTERNAL_CONTENT_URI 手机内部存储器上的图片
MediaStore.Audio.Video.EXTERNAL_CONTENT_URI SD卡上的视频
MediaStore.Audio.Video.INTERNAL_CONTENT_URI  手机内部存储器上的视频
(注：图片的显示名栏：Media.DISPLAY_NAME，图片的详细描述栏为：Media.DESCRIPTION  图片的保存位置：Media.DATA
短信URI： Content://sms
发送箱中的短信URI： Content://sms/outbox
```


###参考
- http://aijiawang-126-com.iteye.com/blog/655268
- http://blog.csdn.net/bage1988320/article/details/6749870#
- http://blog.csdn.net/wirelessqa/article/details/8618831
- http://blog.chinaunix.net/uid-24129645-id-3758633.html