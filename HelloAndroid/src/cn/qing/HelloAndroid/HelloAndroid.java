package cn.qing.HelloAndroid;

import android.R.layout;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HelloAndroid extends Activity implements OnClickListener {

	private MediaController mc;
	BottomBar bar;
	private Display display;
	private TopBar topBar;
	private BottomBar bottomBar;
	private AlphaAnimation fadeOut;
	private AlphaAnimation fadeIn;
	private boolean BarsShows = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		display = getWindowManager().getDefaultDisplay();
		RelativeLayout layout = new RelativeLayout(this);
		setContentView(layout);

		fadeOut = new AlphaAnimation(1.0f, 0.1f);
		fadeOut.setDuration(300);
		fadeIn = new AlphaAnimation(0.1f, 1.0f);
		fadeIn.setDuration(300);

		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.frontpage);
		imageView.setBackgroundColor(Color.WHITE);
		layout.addView(imageView);

		bottomBar = new BottomBar(this);
		RelativeLayout.LayoutParams bottomLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 120);
		bottomLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bottomBar.setLayoutParams(bottomLayoutParams);
		layout.addView(bottomBar);

		topBar = new TopBar(this);
		LayoutParams topLayoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, 90);
		topBar.setLayoutParams(topLayoutParams);
		layout.addView(topBar);

		layout.setOnClickListener(this);
	}

	private void hideBars() {
		topBar.startAnimation(fadeOut);
		bottomBar.startAnimation(fadeOut);
		topBar.setVisibility(View.INVISIBLE);
		bottomBar.setVisibility(View.INVISIBLE);
	}

	private void showBars() {
		topBar.setVisibility(View.VISIBLE);
		bottomBar.setVisibility(View.VISIBLE);
		topBar.startAnimation(fadeIn);
		bottomBar.startAnimation(fadeIn);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (BarsShows) {
			hideBars();
		} else {
			showBars();
		}
		BarsShows = !BarsShows;
	}

	// @Override
	// public void onConfigurationChanged(Configuration newConfig) {
	// super.onConfigurationChanged(newConfig);
	// // Checks the orientation of the screen
	// if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	// Toast.makeText(this, "landscape", Toast.LENGTH_LONG).show();
	// } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
	// Toast.makeText(this, "portrait", Toast.LENGTH_LONG).show();
	// }
	//
	// }

}