package cn.qing.DisplayHtml;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
/**
 * Demonstrates how to embed a WebView in your activity. Also demonstrates how
 * to have javascript in the WebView call into the activity, and how the activity 
 * can invoke javascript.
 * <p>
 * In this example, clicking on the android in the WebView will result in a call into
 * the activities code in {@link DemoJavaScriptInterface#clickOnAndroid()}. This code
 * will turn around and invoke javascript using the {@link WebView#loadUrl(String)}
 * method.
 * <p>
 * Obviously all of this could have been accomplished without calling into the activity
 * and then back into javascript, but this code is intended to show how to set up the 
 * code paths for this sort of communication.
 *
 */
public class DisplayHtml extends Activity {
	
	private static final String LOG_TAG = "WebViewDemo";
	Display display;
    private Handler mHandler = new Handler();
    private AWebView webView;
	Context context;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        display = getWindowManager().getDefaultDisplay();
        
        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        
        RelativeLayout relativeLayout = new RelativeLayout(this);
        webView = new AWebView(this,200);
        webView.setBackgroundColor(Color.YELLOW);
        
        WebSettings webSettings = webView.getSettings();
//        webSettings.setSavePassword(false);
//        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(false);
//        webSettings.setDefaultZoom(ZoomDensity.FAR);
//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
//        webSettings.setBuiltInZoomControls(true);
        
//        webView.setWebChromeClient(new MyWebChromeClient());
//        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
        webView.setInitialScale(getScale());
        webView.loadUrl("file:///android_asset/2_landscape.html");//asset folder
        //file:///sdcard/xxx.html  sdcard file
        //file:///data/data   internal storage
        relativeLayout.setMinimumHeight(display.getHeight());
        relativeLayout.setMinimumWidth(display.getWidth());
        RelativeLayout.LayoutParams center = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        center.addRule(RelativeLayout.CENTER_IN_PARENT);
        webView.setLayoutParams(center);
        relativeLayout.addView(webView);
        layout.addView(relativeLayout);
    }
    private int getScale(){
        int width = display.getWidth(); 
        Double wval = new Double(width)/new Double(1024);
        wval = wval * 100d;
        Double hval	= new Double(display.getHeight())/new Double(768);
        hval = hval * 100d;
        if (wval < hval) {
			return wval.intValue();
		}else {
			return hval.intValue();
		}
    }

    
    final class DemoJavaScriptInterface {


		DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void clickOnAndroid() {
            mHandler.post(new Runnable() {

				public void run() {
                	webView.loadUrl("javascript:wave()");
                }
            });

        }
    }
    
    
    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * debugging your javascript.
     */
    final class MyWebChromeClient extends WebChromeClient {

		@Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(LOG_TAG, message);
            result.confirm();
            Toast.makeText(context, url + " says:" + message, Toast.LENGTH_LONG);
            return true;
        }
    }
}