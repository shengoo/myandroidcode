package cn.qing.hscroll;

import android.content.Context;
import android.widget.ImageView;

public class MyImageView extends ImageView {


	private int loffset;//left offset
	
	public MyImageView(Context context,int offset) {
		super(context);
		loffset = offset;
	}
	
	@Override
	protected void onLayout (boolean changed, int left, int top, int right, int bottom){
		setFrame(loffset, 0, loffset+100, 200);
	}

}
