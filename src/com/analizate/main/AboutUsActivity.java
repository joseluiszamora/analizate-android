package com.analizate.main;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AboutUsActivity extends TabActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		TabHost tabHost = getTabHost();
		
		// Tab for Project Sobre Nosotros
        TabSpec infospec = tabHost.newTabSpec("Nosotros");
        // setting Title and Icon for the Tab
        infospec.setIndicator("Nosotros", getResources().getDrawable(android.R.drawable.ic_menu_info_details));
        Intent infoIntent = new Intent(this, aboutAdapter.class);
		infospec.setContent(infoIntent);
		
        // Tab for Project Mision
        TabSpec mapspec = tabHost.newTabSpec("Misión");
        mapspec.setIndicator("Misión", getResources().getDrawable(android.R.drawable.ic_menu_mapmode));
        Intent mapIntentNew = new Intent(this, AboutMision.class);
        mapspec.setContent(mapIntentNew);
        
        // Tab for Project Vision
        TabSpec visspec = tabHost.newTabSpec("Visión");
        visspec.setIndicator("Visión", getResources().getDrawable(android.R.drawable.ic_dialog_info));
        Intent visIntentNew = new Intent(this, AboutVision.class);
        visspec.setContent(visIntentNew);
        
        // Tab for Project Valores
        TabSpec valspec = tabHost.newTabSpec("Valores");
        valspec.setIndicator("Valores", getResources().getDrawable(android.R.drawable.ic_menu_help));
        Intent valIntentNew = new Intent(this, AboutValues.class);
        valspec.setContent(valIntentNew);

        // Adding all TabSpec to TabHost
        tabHost.addTab(infospec);
        tabHost.addTab(mapspec);
        tabHost.addTab(visspec);
        tabHost.addTab(valspec);
	}
}