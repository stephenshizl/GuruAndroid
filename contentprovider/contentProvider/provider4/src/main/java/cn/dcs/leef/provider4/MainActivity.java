package cn.dcs.leef.provider4;

 
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.dcs.leef.provider4.Mytable.MyColumns;

public class MainActivity extends Activity {
	
	ContentResolver cr ;
	Uri uri = Uri.parse("content://hx.android.test.mycontentprovider/mytable");
	Uri uri1 = Uri.parse("content://hx.android.test.mycontentprovider/mytable/2");
	ContentValues values;
	
	TextView tv;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cr = getContentResolver();
        
        
        Button btnInsert = (Button) this.findViewById(R.id.Button01);
        btnInsert.setOnClickListener(listener);
        Button btnDelete = (Button) this.findViewById(R.id.Button02);
        btnDelete.setOnClickListener(listener);
        Button btnQuery = (Button) this.findViewById(R.id.Button03);
        btnQuery.setOnClickListener(listener);
       
        Button btnReset = (Button) this.findViewById(R.id.Button05);
        btnReset.setOnClickListener(listener);
        
        tv = (TextView) this.findViewById(R.id.TextView01);
        
        
    }
    
    View.OnClickListener listener = new View.OnClickListener() {
		int num = 0;
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.Button01:
				  ContentValues values = new ContentValues();
			       values.put(MyColumns.TITLE, "iphone"+num);
			       values.put(MyColumns.BODY, "dddddddddddd");
			       values.put(MyColumns.NAME, "bage");
			   
			       num++;
				cr.insert(uri, values);
				break;
			case R.id.Button02:			 
				 cr.delete(uri,null, null);//where选项传入null,就删除该表所有信息
				break;
			case R.id.Button03:
				tv.setText("");
				Cursor c = cr.query(uri, null, null, null, null);
			 
				Log.i("length",c.getCount()+"" );
				while(c.moveToNext()){
			 
					tv.append(c.getString(c.getColumnIndex(MyColumns.TITLE))+"\n");
					tv.append(c.getString(c.getColumnIndex(MyColumns.BODY))+"\n");
					tv.append(c.getString(c.getColumnIndex(MyColumns.NAME))+"\n");
				}
				c.close();
				break;
			case R.id.Button05:
				values = new ContentValues();
				values.put("body", "newnew");
				cr.update(uri1, values, null, null);
				break;
				default:break;
			}
			
		}
	};
}