package com.paragallo.camerademo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Preview mPreview;
	private View overlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		RelativeLayout main = (RelativeLayout) findViewById(R.id.mainview);

		mPreview = new Preview(this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		main.addView(mPreview, layoutParams);// add preview in main view

		LayoutInflater controlInflater = getLayoutInflater();
		overlay = controlInflater.inflate(R.layout.overlay, null);

		main.addView(overlay, layoutParams);// add overlay in main view, above
											// preview

		ImageButton takeButton = (ImageButton) findViewById(R.id.takepic);
		takeButton.setOnClickListener(new OnClickListener() {// add listener

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mPreview != null)
							mPreview.takeAPicture();
					}
				});
	}

}

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;
	private Context mContext;

	Preview(Context context) {
		super(context);
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where to
		// draw.
		System.out.println("[surfaceCreated]width:" + this.getWidth()
				+ ",height:" + this.getHeight());
		try {
			mCamera = Camera.open();
			// mCamera.setDisplayOrientation(90);//working on apis higher than
			// 2.2
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		// or other apps cannot use camera
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(h, w);
//		List<Size> previewSizes = parameters.getSupportedPreviewSizes();
//		for (Size size : previewSizes) {
//			System.out.println("preview size:width:" + size.width + ",height:" + size.height);
//		}
//		List<Camera.Size> picSizes = parameters.getSupportedPictureSizes();
//		for (Size size : picSizes) {
//			System.out.println("size:width:" + size.width + ",height:" + size.height);
//		}
		//Camera.Size picSize = picSizes.get(0);
		// parameters.setPictureSize(picSize.width,picSize.height);
		// System.out.println("[picSize]width:" + picSize.width + ",height:" +
		// picSize.height);
		// parameters.setPreviewFrameRate(3);//
		// parameters.setPictureFormat(PixelFormat.JPEG);//
		// parameters.set("jpeg-quality", 85);//
		// parameters.setPictureSize(h, w);

		//parameters.set("jpeg-quality", 72);
		//parameters.set("orientation", "portrait");
		// parameters.set("picture-size", "320X430");
		// parameters.set("rotation", 0);
		//parameters.setPictureFormat(PixelFormat.JPEG);
		// parameters.setPictureSize(1232, 2048);
		// mCamera.setDisplayOrientation(90);

		//parameters.set("orientation", "portrait");
		parameters.set("rotation", 90);

		mCamera.setParameters(parameters);
		mCamera.setDisplayOrientation(90);//working on apis higher than 2.2
		mCamera.startPreview();

		//System.out.println("[surfaceChanged]width:" + w + ",height:" + h);

	}

	public void takeAPicture() {

		mCamera.takePicture(myShutterCallback, myPictureCallback_RAW,
				myPictureCallback_JPG);
	}

	ShutterCallback myShutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			//Camera.Parameters p = mCamera.getParameters();
			//p.set("rotation", "90");//rotate picture
			//mCamera.setParameters(p);
		}
	};

	PictureCallback myPictureCallback_RAW = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback myPictureCallback_JPG = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
			/*
			 * Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0,
			 * arg0.length);
			 */

			Uri uriTarget = mContext.getContentResolver().insert(
					Media.EXTERNAL_CONTENT_URI, new ContentValues());

			OutputStream imageFileOS;
			try {
				imageFileOS = mContext.getContentResolver().openOutputStream(
						uriTarget);
				imageFileOS.write(arg0);
				imageFileOS.flush();
				imageFileOS.close();

				Toast.makeText(mContext,
						"Image saved: " + uriTarget.toString(),
						Toast.LENGTH_LONG).show();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mCamera.startPreview();
		}
	};

}
