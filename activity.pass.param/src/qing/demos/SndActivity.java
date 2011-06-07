/**
 * 
 */
package qing.demos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Qing
 * @date 2011-5-16
 * 
 */
public class SndActivity extends Activity {
	private TextView tv;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);
        Bundle bundle = this.getIntent().getExtras();
        tv = (TextView) findViewById(R.id.TextView01);
        tv.setText(bundle.getString("param"));
    }
}
