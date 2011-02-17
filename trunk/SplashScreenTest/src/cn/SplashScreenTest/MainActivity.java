package cn.SplashScreenTest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText("Main Activity");
		setContentView(textView);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 按下键盘上返回按钮

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)

			// .setIcon(R.drawable.services)

					.setTitle(R.string.prompt)

					.setMessage(R.string.quit_desc)

					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}

							})

					.setPositiveButton(R.string.confirm,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int whichButton) {

									finish();

								}

							}).show();

			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		System.exit(0);
	}
	
}
