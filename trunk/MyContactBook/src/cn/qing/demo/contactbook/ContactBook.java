package cn.qing.demo.contactbook;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
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
        linearLayout.addView(getLayoutInflater().inflate(R.layout.row_item, null),LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
        linearLayout.addView(getLayoutInflater().inflate(R.layout.separate_line, null)
     		   ,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
        Cursor people = getContentResolver().query(Contacts.CONTENT_URI, null, null, null, null);

        
        
        
//        while(people.moveToNext()) {
//           int nameFieldColumnIndex = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//           String contact = people.getString(nameFieldColumnIndex);
//           int numberFieldColumnIndex = people.getColumnIndex(PhoneLookup.NUMBER);
//           String number = people.getString(nameFieldColumnIndex);
//           int imageColumnIndex = people.getColumnIndex(PhoneLookup.PHOTO_ID);
//           String photoString = people.getString(imageColumnIndex);
//           
//           
//           LinearLayout rowLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.row_item, null);
//           TextView tView = (TextView) rowLayout.findViewById(R.id.textView1);
//           tView.setText(contact + "\t" + photoString);
//           
//           linearLayout.setOrientation(LinearLayout.VERTICAL);
//           linearLayout.addView(rowLayout,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
//        }
//
//        people.close();
    }
}