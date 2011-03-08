package cn.qing.HelloAndroid;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;


public class BottomBar extends RelativeLayout implements OnClickListener{

	Context mContext;
	ImageButton playButton;
	Display display;
	ImageButton pauseButton;
	
	boolean isPlaying;
	public BottomBar(Context context){
		super(context);
		mContext = context;
		setBackgroundColor(Color.TRANSPARENT);
		
		
		

		
		
		playButton = new ImageButton(mContext);
		playButton.setBackgroundColor(Color.TRANSPARENT);
		playButton.setImageResource(R.drawable.play);
		playButton.setScaleType(ScaleType.FIT_CENTER);
		playButton.setOnClickListener(this);
		RelativeLayout.LayoutParams centerParams = 
			new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, 
					RelativeLayout.LayoutParams.WRAP_CONTENT);
		centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		playButton.setLayoutParams(centerParams);
		addView(playButton);
		
		
		

		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		v.setBackgroundColor(Colo)
		if(isPlaying){
			playButton.setImageResource(R.drawable.play);
			isPlaying = !isPlaying;
		}
		else {
			playButton.setImageResource(R.drawable.pause);
			isPlaying = !isPlaying;
		}
	}
	
	
	
}
