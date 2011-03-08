package cn.qing.hscroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.content.*;
import android.graphics.*;
import android.content.res.*;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HScrollViewTest extends Activity implements OnGestureListener {
	
	
	private static final int X_MAX = 800;
	private static final int Y_MAX = 600;
	private int scrollX = 0;
    private int scrollY = 0;
	
    MyView main;   
    Bitmap bmp;
    Bitmap adapt;
    Resources res;
    Paint paint;
    GestureDetector gestureScanner;
    Display display;
    
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		gestureScanner = new GestureDetector(this);
        paint = new Paint();
        display = getWindowManager().getDefaultDisplay();
        res = getResources();
        bmp = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.sample_0);
        adapt = Bitmap.createBitmap(bmp);
              
        main = new MyView(this);        
        setContentView(main,new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent me)
    {
    	return gestureScanner.onTouchEvent(me);
    }
    
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
    	main.handleScroll(distanceX,distanceY);
    	return true;
    }
    
    @Override
    public boolean onDown(MotionEvent e)
    {
    	return true;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
    	return true;
    }
    
    @Override
    public void onLongPress(MotionEvent e){    }
    
    @Override
    public void onShowPress(MotionEvent e) {   }    
    
    @Override
    public boolean onSingleTapUp(MotionEvent e)    
    {
    	return true;
    }
    
    class MyView extends View
    {
    	public MyView(Context context)
    	{
    		super(context);
    	}
    	
    	@Override
    	protected void onDraw(Canvas canvas)
    	{
    		canvas.drawBitmap(adapt, 0, 0, paint);
    	}
    	
    	public void handleScroll(float distX, float distY)
    	{
    		// X-Axis ////////////////////////////////
    		
    		if(distX > 6.0)
    		{
    			if(scrollX < 460)
    			{
    				scrollX += 15;
    			}
    		}
    		else if(distX < -6.0)
    		{
    			if(scrollX >= 15)
    			{
    				scrollX -= 15;
    			}
    		}
    		////////////////////////////////////////////
   			
    		// Y-AXIS //////////////////////////////////
    		if(distY > 6.0)
    		{
    			if(scrollY < 100)
    			{
    				scrollY += 15;
    			}
    		}
    		else if(distY < -6.0)
    		{
    			if(scrollY >= 15)
    			{
    				scrollY -= 15;
    			}
    		}    		
    		////////////////////////////////////////////
    		
    		if((scrollX <= 480) && (scrollY <= 120))
    		{
    			adapt = Bitmap.createBitmap(bmp, scrollX, scrollY, 320, 480);    		
    			invalidate();
    		}
    	}
    }
    
}