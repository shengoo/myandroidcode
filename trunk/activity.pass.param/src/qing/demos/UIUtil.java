/**
 * 
 */
package qing.demos;

import java.util.LinkedList;
import java.util.Queue;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author Qing
 * @date 2011-6-7
 * 
 */
public class UIUtil {
	private static final Object ourMonitor = new Object();
	private static ProgressDialog ourProgress;
	private static class Pair {
		final Runnable Action;
		final String Message;

		Pair(Runnable action, String message) {
			Action = action;
			Message = message;
		}
	};
	private static final Queue<Pair> ourTaskQueue = new LinkedList<Pair>();
	private static final Handler ourProgressHandler = new Handler() {
		public void handleMessage(Message message) {
			try {
				synchronized (ourMonitor) {
					if (ourTaskQueue.isEmpty()) {
						ourProgress.dismiss();
						ourProgress = null;
					} else {
						ourProgress.setMessage(ourTaskQueue.peek().Message);
					}
					ourMonitor.notify();
				}
			} catch (Exception e) {
			}
		}
	};
	public static void wait(String msg, Runnable action, Context context) {
		synchronized (ourMonitor) {
			final String message = msg;
			ourTaskQueue.offer(new Pair(action, message));
			if (ourProgress == null) {
				ourProgress = ProgressDialog.show(context, null, message, true, false);
			} else {
				return;
			}
		}
		final ProgressDialog currentProgress = ourProgress;
		new Thread(new Runnable() {
			public void run() {
				while ((ourProgress == currentProgress) && !ourTaskQueue.isEmpty()) {
					Pair p = ourTaskQueue.poll();
					p.Action.run();
					synchronized (ourMonitor) {
						ourProgressHandler.sendEmptyMessage(0);
						try {
							ourMonitor.wait();
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}).start();
	}
}
