package cn.dcs.leef.provider4;

import android.net.Uri;
import android.provider.BaseColumns;

public class Mytable{
	public static class MyColumns implements BaseColumns {/*
		 * BaseColumns 是一个接口，里边有两个变量，一个是_ID=“_id”，一个是_COUNT="_ count" 。
		 * 在Android当中，每一个数据库表至少有一个字段，而且这个字段是_id。
		 * 所以当我们构造列名的辅助类时，直接实现BaseColumns ，这样我们便默认地拥有了_id字段。
		 * 如果没有继承BaseColum类，就要自己定义变量_id
		 */
		private MyColumns() {}
		public static final String AUTHORITY = "hx.android.test.mycontentprovider";
	    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/mytable");
	    
	    
	    public static final String CONTENT_TYPE = "vnd.Android.cursor.dir/vnd.hx.mytable";
	    public static final String CONTENT_ITEM_TYPE = "vnd.Android.cursor.item/vnd.hx.mytable";
	    public static final String DEFAULT_SORT_ORDER = "created DESC";
	    public static final String TABLE_NAME = "mytable";
	    public static final int VERSION = 1;
	    
	    public static final String TITLE = "title";
	    public static final String BODY = "body";
	    public static final String NAME = "name";
}
	

}
