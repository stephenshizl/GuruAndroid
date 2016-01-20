package cn.dcs.leef.bookgallery;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BookGallery extends Activity {
	/** Called when the activity is first created. */
	EditText etTitle;
	EditText etPrice;
	Button btnAdd;
	Button btnDelete;
	ListView lvBooks;
	ContentResolver resolver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("BookGallery: onCreate.... ");
		setContentView(R.layout.activity_main);
		etTitle = (EditText) findViewById(R.id.ettitle);
		etPrice = (EditText) findViewById(R.id.etprice);
		btnAdd = (Button) findViewById(R.id.add);
		btnDelete = (Button) findViewById(R.id.delete);
		lvBooks = (ListView) findViewById(R.id.booklist);
		resolver = getContentResolver();
		Cursor cs = resolver.query(Book.CONTENT_URI, null, null, null, Book.ID
				+ " ASC");
		CursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cs, new String[] {
						Book.TITLE, Book.PRICE }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		lvBooks.setAdapter(adapter);
	btnAdd.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ContentValues contentValues;
			try {  
				String countryText =  
				String.valueOf(etTitle.getText());  
				String codeNum =  
				String.valueOf(etPrice.getText());  
				contentValues = new ContentValues();  
				contentValues.put(Book.TITLE, countryText);  
				contentValues.put(Book.PRICE, codeNum);  
				} catch (Exception e) {  
					contentValues = new ContentValues();  
				}  
				resolver.insert(Book.CONTENT_URI, contentValues);  
			
		}
		
	});
	
	btnDelete.setOnClickListener( new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Uri uri=ContentUris.withAppendedId(Book.CONTENT_URI, 2);
			System.out.println("btnDelete.setOnClickListener...."+uri.toString());
			resolver.delete(uri, null, null);
			
		}
		
	});
	}
}