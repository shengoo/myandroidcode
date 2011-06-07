package qing.demos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityPassParam extends Activity {
    private Button button;
	private Context context;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        this.button = (Button) this.findViewById(R.id.Button01);
		this.button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIUtil.wait("loading", new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(ActivityPassParam.this, SndActivity.class);
						Bundle bundle = new Bundle();
		                bundle.putString("param", "test param");
		                intent.putExtras(bundle);
						startActivity(intent);
					}
				}, context);
				
			}
		});
    }
}