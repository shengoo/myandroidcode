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
	private static BatteryWatcher sInstance;


	/**
	 * 
	 */
	public BatteryWatcher() {
		// TODO Auto-generated constructor stub
//		Toast.makeText(this, "BatteryWatcher", Toast.LENGTH_LONG).show();
	}
	
	public static BatteryWatcher getInstance() {
		return sInstance;
	}
	
	@Override
    public void onCreate() {
		if (sInstance != null) {
			return;
		}
		Toast.makeText(this, "on create", Toast.LENGTH_SHORT).show();
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sInstance = this;

        
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
                System.out.println("level:" + level +"scale:" + scale);
                showNotification(level * 100 / scale);
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }
	
//	private void showTxt(String string){
//    	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
//    }
	
//	private void showNotification2(){
//		// Instead of the normal constructor, we're going to use the one with no args and fill
//        // in all of the data ourselves.  The normal one uses the default layout for notifications.
//        // You probably want that in most cases, but if you want to do something custom, you
//        // can set the contentView field to your own RemoteViews object.
//        notification = new Notification();
//        contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, BattaryStatus.class), 0);
//
//        // This is who should be launched if the user selects our notification.
//        notification.contentIntent = contentIntent;
//
//        // In this sample, we'll use the same text for the ticker and the expanded notification
//        CharSequence text = "222";
//        notification.tickerText = text;
//
//        // the icon for the status bar
//        notification.icon = R.drawable.icon;
//        
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.status_bar_balloon);
//        contentView.setTextViewText(R.id.text, text);
//        contentView.setImageViewResource(R.id.icon, R.id.icon);
//        notification.contentView = contentView;
//        notification.flags = notification.FLAG_ONGOING_EVENT;
////        notification.number = num++;
////        notification.defaults= defaults;
//        mNM.notify(NOTIFY_ID, notification);
//	}
	
	private void showNotification(int l) {
        // In this sample, we'll use the same text for the ticker and the expanded notification
//        CharSequence text = getText(textId);

        // Set the icon, scrolling text and timestamp.
        // Note that in this example, we pass null for tickerText.  We update the icon enough that
        // it is distracting to show the ticker text every time it changes.  We strongly suggest
        // that you do this as well.  (Think of of the "New hardware found" or "Network connection
        // changed" messages that always pop up)
		if (notification == null) {
			notification = new Notification(getNotiIcon(l),this.getString(R.string.start_message),System.currentTimeMillis());
		}
			notification.icon = getNotiIcon(l);
			notification.when = System.currentTimeMillis();

        // The PendingIntent to launch our activity if the user selects this notification
        contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, BattaryStatus.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, this.getString(R.string.notify_title) + "£º" + Integer.toString(l) + "%",
                       null, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        notification.flags = notification.FLAG_ONGOING_EVENT;
//        notification.number = num++;
        mNM.notify(NOTIFY_ID, notification);
        Toast.makeText(this, "showNotification", Toast.LENGTH_SHORT).show();
    }
	
	private int getNotiIcon(int l){
		int result = 0;
		switch (l) {
		case 0:
			result = R.drawable.r0;
			break;
		case 1:
			result = R.drawable.r1;
			break;
		case 2:
			result = R.drawable.r2;
			break;
		case 3:
			result = R.drawable.r3;
			break;
		case 4:
			result = R.drawable.r4;
			break;
		case 5:
			result = R.drawable.r5;
			break;
		case 6:
			result = R.drawable.r6;
			break;
		case 7:
			result = R.drawable.r7;
			break;
		case 8:
			result = R.drawable.r8;
			break;
		case 9:
			result = R.drawable.r9;
			break;
		case 10:
			result = R.drawable.r10;
			break;
		case 11:
			result = R.drawable.r11;
			break;
		case 12:
			result = R.drawable.r12;
			break;
		case 13:
			result = R.drawable.r13;
			break;
		case 14:
			result = R.drawable.r14;
			break;
		case 15:
			result = R.drawable.r15;
			break;
		case 16:
			result = R.drawable.r16;
			break;
		case 17:
			result = R.drawable.r17;
			break;
		case 18:
			result = R.drawable.r18;
			break;
		case 19:
			result = R.drawable.r19;
			break;
		case 20:
			result = R.drawable.a20;
			break;
		case 21:
			result = R.drawable.a21;
			break;
		case 22:
			result = R.drawable.a22;
			break;
		case 23:
			result = R.drawable.a23;
			break;
		case 24:
			result = R.drawable.a24;
			break;
		case 25:
			result = R.drawable.a25;
			break;
		case 26:
			result = R.drawable.a26;
			break;
		case 27:
			result = R.drawable.a27;
			break;
		case 28:
			result = R.drawable.a28;
			break;
		case 29:
			result = R.drawable.a29;
			break;
		case 30:
			result = R.drawable.a30;
			break;
		case 31:
			result = R.drawable.a31;
			break;
		case 32:
			result = R.drawable.a32;
			break;
		case 33:
			result = R.drawable.a33;
			break;
		case 34:
			result = R.drawable.a34;
			break;
		case 35:
			result = R.drawable.a35;
			break;
		case 36:
			result = R.drawable.a36;
			break;
		case 37:
			result = R.drawable.a37;
			break;
		case 38:
			result = R.drawable.a38;
			break;
		case 39:
			result = R.drawable.a39;
			break;
		case 40:
			result = R.drawable.g40;
			break;
		case 41:
			result = R.drawable.g41;
			break;
		case 42:
			result = R.drawable.g42;
			break;
		case 43:
			result = R.drawable.g43;
			break;
		case 44:
			result = R.drawable.g44;
			break;
		case 45:
			result = R.drawable.g45;
			break;
		case 46:
			result = R.drawable.g46;
			break;
		case 47:
			result = R.drawable.g47;
			break;
		case 48:
			result = R.drawable.g48;
			break;
		case 49:
			result = R.drawable.g49;
			break;
		case 50:
			result = R.drawable.g50;
			break;
		case 51:
			result = R.drawable.g51;
			break;
		case 52:
			result = R.drawable.g52;
			break;
		case 53:
			result = R.drawable.g53;
			break;
		case 54:
			result = R.drawable.g54;
			break;
		case 55:
			result = R.drawable.g55;
			break;
		case 56:
			result = R.drawable.g56;
			break;
		case 57:
			result = R.drawable.g57;
			break;
		case 58:
			result = R.drawable.g58;
			break;
		case 59:
			result = R.drawable.g59;
			break;
		case 60:
			result = R.drawable.g60;
			break;
		case 61:
			result = R.drawable.g61;
			break;
		case 62:
			result = R.drawable.g62;
			break;
		case 63:
			result = R.drawable.g63;
			break;
		case 64:
			result = R.drawable.g64;
			break;
		case 65:
			result = R.drawable.g65;
			break;
		case 66:
			result = R.drawable.g66;
			break;
		case 67:
			result = R.drawable.g67;
			break;
		case 68:
			result = R.drawable.g68;
			break;
		case 69:
			result = R.drawable.g69;
			break;
		case 70:
			result = R.drawable.g70;
			break;
		case 71:
			result = R.drawable.g71;
			break;
		case 72:
			result = R.drawable.g72;
			break;
		case 73:
			result = R.drawable.g73;
			break;
		case 74:
			result = R.drawable.g74;
			break;
		case 75:
			result = R.drawable.g75;
			break;
		case 76:
			result = R.drawable.g76;
			break;
		case 77:
			result = R.drawable.g77;
			break;
		case 78:
			result = R.drawable.g78;
			break;
		case 79:
			result = R.drawable.g79;
			break;
		case 80:
			result = R.drawable.g80;
			break;
		case 81:
			result = R.drawable.g81;
			break;
		case 82:
			result = R.drawable.g82;
			break;
		case 83:
			result = R.drawable.g83;
			break;
		case 84:
			result = R.drawable.g84;
			break;
		case 85:
			result = R.drawable.g85;
			break;
		case 86:
			result = R.drawable.g86;
			break;
		case 87:
			result = R.drawable.g87;
			break;
		case 88:
			result = R.drawable.g88;
			break;
		case 89:
			result = R.drawable.g89;
			break;
		case 90:
			result = R.drawable.g90;
			break;
		case 91:
			result = R.drawable.g91;
			break;
		case 92:
			result = R.drawable.g92;
			break;
		case 93:
			result = R.drawable.g93;
			break;
		case 94:
			result = R.drawable.g94;
			break;
		case 95:
			result = R.drawable.g95;
			break;
		case 96:
			result = R.drawable.g96;
			break;
		case 97:
			result = R.drawable.g97;
			break;
		case 98:
			result = R.drawable.g98;
			break;
		case 99:
			result = R.drawable.g99;
			break;
		case 100:
			result = R.drawable.g100;
			break;

		default:
			break;
		}
		return result;
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
