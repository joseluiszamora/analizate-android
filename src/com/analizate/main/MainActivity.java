package com.analizate.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.analizate.database.DatabaseHandlerDoctor;
import com.analizate.database.DatabaseHandlerInstitution;
import com.analizate.database.DatabaseHandlerService;
import com.analizate.database.DatabaseHandlerSpecialty;

public class MainActivity extends Activity {
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 4000;
	
	public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StartAnimations();
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				DatabaseHandlerInstitution db = new DatabaseHandlerInstitution(MainActivity.this, "", null, '1');
				db.setTable();
				DatabaseHandlerDoctor db2 = new DatabaseHandlerDoctor(MainActivity.this, "", null, '1');
				db2.setTable();
				DatabaseHandlerService db3 = new DatabaseHandlerService(MainActivity.this, "", null, '1');
				db3.setTable();
				DatabaseHandlerSpecialty db4 = new DatabaseHandlerSpecialty(MainActivity.this, "", null, '1');
				db4.setTable();
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(MainActivity.this, DashboardActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

	private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
 
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        iv.clearAnimation();
        iv.startAnimation(anim);
 
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}