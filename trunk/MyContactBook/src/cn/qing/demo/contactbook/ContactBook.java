package cn.qing.demo.contactbook;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.Phones;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class ContactBook extends Activity {
	private LinearLayout linearLayout;
	private ScrollView scrollView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		linearLayout = (LinearLayout) this.findViewById(R.id.linearLayout1);
		scrollView = (ScrollView) this.findViewById(R.id.scrollView1);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(getLayoutInflater().inflate(R.layout.row_item,
				null), LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		linearLayout.addView(getLayoutInflater().inflate(
				R.layout.separate_line, null), LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		Cursor people = getContentResolver().query(Contacts.People.CONTENT_URI,
				null, null, null, null);

		while (people.moveToNext()) {
			int nameFieldColumnIndex = people
					.getColumnIndex(Contacts.Phones.DISPLAY_NAME);
			String contact = people.getString(nameFieldColumnIndex);
			int numberFieldColumnIndex = people.getColumnIndex(Phones.NUMBER);
			String number = people.getString(numberFieldColumnIndex);
			// int imageColumnIndex = people.getColumnIndex(Phones..PHOTO_ID);
			// String photoString = people.getString(imageColumnIndex);

			LinearLayout rowLayout = (LinearLayout) getLayoutInflater()
					.inflate(R.layout.row_item, null);
			TextView tView = (TextView) rowLayout.findViewById(R.id.textView1);
			tView.setText(contact + "\t" + number);

			linearLayout.setOrientation(LinearLayout.VERTICAL);
			linearLayout.addView(rowLayout, LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			if (!people.isLast()) {

				linearLayout.addView(getLayoutInflater().inflate(
						R.layout.separate_line, null),
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			}
		}

		people.close();
	}
}