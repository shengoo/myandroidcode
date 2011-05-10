/**
 * 
 */
package com.sheng00.CustomContentProvider;

import java.util.ArrayList;

import com.sheng00.CustomContentProvider.Note.Notes;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.widget.Toast;

/**
 * @author Qing
 * @date 2011-5-10
 * 
 */
public class main extends Activity {
	private NotesContentProvider p;
	private Uri uri;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// p = new NotesContentProvider();
		// Cursor cursor = p.query(Notes.CONTENT_URI, null, null, null, null);
		ArrayList<String> contactList;
		contactList = new ArrayList<String>();

		uri = getContentResolver().insert(Notes.CONTENT_URI, null);
		ContentValues values = new ContentValues();
		values.put(Notes.TITLE, "tttttttttttttt");
		getContentResolver().update(uri, values, null, null);
		String[] columns = new String[] { Notes.TITLE /*
													 * People.NAME,
													 * People.NUMBER
													 */};
		Uri mContacts = People.CONTENT_URI;
		Cursor mCur = managedQuery(Notes.CONTENT_URI, columns, null, null, null);
		if (mCur != null) {
			if (mCur.moveToFirst()) {
				do {
					contactList.add(mCur.getString(mCur
							.getColumnIndex(Notes.TITLE)));
					System.out.println(mCur
							.getColumnIndex(Notes.TITLE));
				} while (mCur.moveToNext());
			}
		}
		Toast.makeText(this, contactList.size() + "", Toast.LENGTH_LONG).show();
	}
}
