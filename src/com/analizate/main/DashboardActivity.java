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
        final Button btnHospitals = (Button)findViewById(R.id.btnHospitals);
		final Button btnMedical = (Button)findViewById(R.id.btnMedical);
		final Button btnPharms = (Button)findViewById(R.id.btnPharms);
		final Button btnAboutUs = (Button)findViewById(R.id.btnAboutUs);
		final Button btnOurServices = (Button)findViewById(R.id.btnOurServices);
		
		// click btn Hospitals
		btnHospitals.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, HospitalActivity.class);
				startActivity(intent);
			}		
		});
		// click btn Medical
		btnMedical.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, MedicalActivity.class);
				startActivity(intent);
			}
		});
		// click btn Pharms
		btnPharms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, PharmsActivity.class);
				startActivity(intent);
			}		
		});
		// click btn About Us
		btnAboutUs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, AboutUsActivity.class);
				startActivity(intent);
			}		
		});
		// click btn Our Services
		btnOurServices.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DashboardActivity.this, OurServicesActivity.class);
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