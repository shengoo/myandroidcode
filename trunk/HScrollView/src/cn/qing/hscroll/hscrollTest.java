package cn.qing.hscroll;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class hscrollTest extends Activity implements OnClickListener {
	
	private HorizontalScrollView hscroll;
	private FrameLayout linearLayout;
	private Display display;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		hscroll = new HorizontalScrollView(this);
		linearLayout = new FrameLayout(this);
		linearLayout.setBackgroundColor(Color.BLUE);
//		linearLayout.setMinimumWidth(display.getWidth()*3);
		display = this.getWindowManager().getDefaultDisplay();
		hscroll.setHorizontalScrollBarEnabled(false);
		hscroll.setHorizontalFadingEdgeEnabled(false);
		hscroll.setBackgroundColor(Color.WHITE);
		hscroll.setMinimumWidth(display.getWidth() * 2);
		MyImageView view0 = new MyImageView(this,300);
		view0.setImageResource(R.drawable.sample_0);
		ImageView view1 = new ImageView(this);
		view1.setImageResource(R.drawable.sample_1);
		ImageView view2 = new ImageView(this);
		view2.setImageResource(R.drawable.sample_2);
//		view0.setPadding(200, 0, 0, 0);
		view1.setPadding(600, 0, 0, 0);
		Gravity gravity = new Gravity();
		linearLayout.addView(view1);
		linearLayout.addView(view0);
//		linearLayout.addView(view1,display.getWidth(),display.getHeight());
//		linearLayout.addView(view2,display.getWidth(),display.getHeight());
		Button button = new Button(this);
		button.setOnClickListener(this);
		button.setLayoutParams(params);
		button.setText("aaaaaaaa");
		hscroll.addView(linearLayout);
		layout.addView(hscroll);
		layout.addView(button);
		setContentView(layout);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		new CountDownTimer(1000, 20) { 
//
//	        public void onTick(long millisUntilFinished) { 
//	        	int pos = (int)((double)(1000 - millisUntilFinished)/1000 * display.getWidth());
//	        	String posString = Integer.toString(pos);
//	        	Log.i("INFO", posString);
//	            hscroll.smoothScrollBy( display.getWidth()/50, 0); 
//	        } 
//
//	        public void onFinish() { 
//
//	        } 
//	     }.start();
		TranslateAnimation translateAnimation = new TranslateAnimation(
				0, -600, 0, 0);
		translateAnimation.setDuration(5000);
		hscroll.startAnimation(translateAnimation);
	}
	
}
