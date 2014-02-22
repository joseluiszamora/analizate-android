package com.analizate.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
        // declare buttons 
        final Button btnHospitals = (Button)findViewById(R.id.btnViewHistory);
		final Button btnMedical = (Button)findViewById(R.id.btnLogout);
		final Button btnPharms = (Button)findViewById(R.id.btnUpdate);
		final Button btnAboutUs = (Button)findViewById(R.id.btnMap);
		final Button btnOurServices = (Button)findViewById(R.id.btnViewProjects);
		
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																													

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.txtInfo:
			// custom dialog
			final Dialog dialog = new Dialog(DashboardActivity.this, R.style.cust_dialog);
			dialog.setContentView(R.layout.dialog_template_2);
		    // get this
		    String name = "Acerca de: ";
		    String desc = "<h2>Laboratorios Analizate version 1.0 </h2> La Paz, Bolivia 2014  <br><br> Desarrollado por: <h4>hanadevel.com</h4>";
			
			dialog.setTitle(name);
			final String[] title = { name };
	  		final String[] info = { desc };
	  		Integer[] imageId = { R.drawable.btn_moreinfo };
	  		TextView textview = (TextView) dialog.findViewById(R.id.textViewDialog2);
	  		textview.setText(Html.fromHtml(desc));
	  		dialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}	
	}
}