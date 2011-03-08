package cn.qing.TestTimer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class TestTimer extends Activity {
	Timer timer = new Timer();
	Handler handler = new Handler(){

		public void handleMessage(Message msg) {
			switch (msg.what) {    
			case 1:    
//				setTitle("hear me?");
				testmmm();
				break;    
			}    
			super.handleMessage(msg);
		}
		
	};
	TimerTask task = new TimerTask(){

		public void run() {
			Message message = new Message();    
			message.what = 1;    
			handler.sendMessage(message);  
		}
		
	};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        timer.schedule(task, 10000);
    }
    
    private void testmmm() {
		setTitle("test method set title");
	}
    
}