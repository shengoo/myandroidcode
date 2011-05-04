/**
 * 
 */
package com.sheng00.BattaryStatus;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * @author Qing
 * @date 2011-5-4
 * 
 */
public class BatteryWatcher extends Service {

	private NotificationManager mNM;
	private int level;
	private int scale;
	private Notification notification;
	private PendingIntent contentIntent;
	
	private static final int NOTIFY_ID = 1;
	private int num = 1;


	/**
	 * 
	 */
	public BatteryWatcher() {
		// TODO Auto-generated constructor stub
//		Toast.makeText(this, "BatteryWatcher", Toast.LENGTH_LONG).show();
	}
	
	@Override
    public void onCreate() {
		Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.
//        Thread notifyingThread = new Thread(null, mTask, "NotifyingService");
//        mCondition = new ConditionVariable(false);
//        notifyingThread.start();
        
        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            int voltage = -1;
            int temp = -1;
            @Override
            public void onReceive(Context context, Intent intent) {
                level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//                showTxt(
//                			"level is "+level+"/"+scale+", " +
//                			"temp is "+temp+", " +
//                			"voltage is "+voltage +
//                			"plugged is " + plugged);
                
                showNotification(R.drawable.icon, level * 100 / scale);
//                showNotification2();
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }
	
	private void showTxt(String string){
    	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
	
	private void showNotification2(){
		// Instead of the normal constructor, we're going to use the one with no args and fill
        // in all of the data ourselves.  The normal one uses the default layout for notifications.
        // You probably want that in most cases, but if you want to do something custom, you
        // can set the contentView field to your own RemoteViews object.
        notification = new Notification();
        contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, BattaryStatus.class), 0);

        // This is who should be launched if the user selects our notification.
        notification.contentIntent = contentIntent;

        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "222";
        notification.tickerText = text;

        // the icon for the status bar
        notification.icon = R.drawable.icon;
        
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.status_bar_balloon);
        contentView.setTextViewText(R.id.text, text);
        contentView.setImageViewResource(R.id.icon, R.id.icon);
        notification.contentView = contentView;
        notification.flags = notification.FLAG_NO_CLEAR;
        notification.number = num++;
//        notification.defaults= defaults;
        mNM.notify(NOTIFY_ID, notification);
	}
	
	private void showNotification(int icon,int l) {
        // In this sample, we'll use the same text for the ticker and the expanded notification
//        CharSequence text = getText(textId);

        // Set the icon, scrolling text and timestamp.
        // Note that in this example, we pass null for tickerText.  We update the icon enough that
        // it is distracting to show the ticker text every time it changes.  We strongly suggest
        // that you do this as well.  (Think of of the "New hardware found" or "Network connection
        // changed" messages that always pop up)
        notification = new Notification(icon, Integer.toString(l), System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, BattaryStatus.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, "battery left " + Integer.toString(l) + "%",
                       null, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        notification.flags = notification.FLAG_NO_CLEAR;
        notification.number = num++;
        mNM.notify(NOTIFY_ID, notification);
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
    }

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {Toast.makeText(this, "service binded", Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub
		return null;
	}

}
