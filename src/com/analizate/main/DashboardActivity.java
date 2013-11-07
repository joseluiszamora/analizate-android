package com.analizate.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashboardActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		// declare buttons 
		final Button btnDoctors = (Button)findViewById(R.id.btnDoctors);
		final Button btnHospitals = (Button)findViewById(R.id.btnHospitals);
		
		// click btn Doctors
		btnDoctors.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, DoctorActivity.class);
				startActivity(intent);
			}		
		});
		
		// click btn Doctors
		btnHospitals.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, HospitalActivity.class);
				startActivity(intent);
			}		
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}