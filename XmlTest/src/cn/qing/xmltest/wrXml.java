package cn.qing.xmltest;

import java.io.*;

import org.xmlpull.v1.*;

import android.app.Activity;
import android.os.*;
import android.util.*;
import android.widget.TextView;

public class wrXml extends Activity {
	/** Called when the activity is first created. */
	private String appDir;
	private String xmlFileName;
	StringBuilder sb;
	TextView tv;

	public wrXml() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		appDir = this.getApplicationContext().getFilesDir().getAbsolutePath();
		xmlFileName = appDir + "/test.xml";
		tv = new TextView(this);
		sb = new StringBuilder();
//		writeXml();
		readFile();
		readXml();
		tv.setText(sb.toString());
		setContentView(tv);
	}

	private void readXml() {
		XmlPullParser parser = Xml.newPullParser();
		FileInputStream fileis = null;
		try {
			fileis = new FileInputStream(xmlFileName);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't find file:" + xmlFileName);
		}
		try {
			parser.setInput(fileis, null);
			int eventType = parser.getEventType();
			boolean intag = false;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					sb.append(parser.getName());
					if (parser.getName() != "root")
						intag = true;
					else
						sb.append("\n");
					if (parser.getAttributeCount() > 0)
						for (int i = 0; i < parser.getAttributeCount(); i++)
							sb.append(" Attr:(" + parser.getAttributeName(i)
									+ "," + parser.getAttributeValue(i) + ")");
					break;
				case XmlPullParser.TEXT:
					if (parser.getText() != null && intag)
						sb.append(" Text:" + parser.getText());
					break;
				case XmlPullParser.END_TAG:
					sb.append("\n");
					intag = false;
					break;
				}
				eventType = parser.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readFile() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(xmlFileName);
			br = new BufferedReader(fr);
			while (br.ready()) {
				String myreadline = br.readLine();
				sb.append(myreadline + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// fw.close();
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void writeXml() {

		// create a new file
		File xmlFile = new File(xmlFileName);
		try {
			xmlFile.createNewFile();
		} catch (IOException e) {
			Log.e("IOException", "exception in createNewFile() method");
		}

		// we have to bind the new file with a FileOutputStream
		FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(xmlFile);
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", "can't create FileOutputStream");
		}

		// we create a XmlSerializer in order to write xml data
		XmlSerializer serializer = Xml.newSerializer();
		try {
			// we set the FileOutputStream as output for the serializer, using
			// UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			// Write <?xml declaration with encoding (if encoding not null) and
			// standalone flag (if standalone not null)
			serializer.startDocument(null, Boolean.valueOf(true));
			// set indentation option
			serializer.setFeature(
					"http://xmlpull.org/v1/doc/features.html#indent-output",
					true);
			// start a tag called "root"
			serializer.startTag(null, "root");
			// i indent code just to have a view similar to xml-tree
			serializer.startTag(null, "child1");
			serializer.endTag(null, "child1");

			serializer.startTag(null, "child2");
			// set an attribute called "attribute" with a "value" for <child2>
			serializer.attribute(null, "attribute", "value");
			serializer.endTag(null, "child2");

			serializer.startTag(null, "child3");
			// write some text inside <child3>
			serializer.text("some text inside child3");
			serializer.endTag(null, "child3");

			serializer.startTag(null, "First");
			serializer.startTag(null, "2");
			serializer.startTag(null, "3");
			serializer.attribute(null, "attr3", "attr3");
			serializer.text("3");
			serializer.endTag(null, "3");
			serializer.endTag(null, "2");
			serializer.endTag(null, "First");
			
			serializer.endTag(null, "root");
			serializer.endDocument();
			// write xml data into the FileOutputStream
			serializer.flush();
			// finally we close the file stream
			fileos.close();

			sb.append("file has been created");
		} catch (Exception e) {
			Log.e("Exception", "error occurred while creating xml file");
			sb.append("Create file error");
		}

	}
}