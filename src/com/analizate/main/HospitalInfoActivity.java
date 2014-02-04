package com.analizate.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.analizate.database.DatabaseHandlerInstitution;
import com.analizate.database.Institution;

public class HospitalInfoActivity extends Activity {
	/** Called when the activity is first created. */
	DatabaseHandlerInstitution db = new DatabaseHandlerInstitution(this, "", null, '1');
	ListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_hospital_info);
	    
	    Bundle bundle = getIntent().getExtras();
	    String obj_id = bundle.getString("obj_id");
	    // get this
	    Institution institution = db.get(obj_id);
	    
	    // Define TextViews
  		TextView name = (TextView)findViewById(R.id.textView2xxy);
  				
  		name.setText(institution.getName());
  		
		final String[] title = {
		    "Dirección",
		    "Telefono",
		    "Mail",
		    "Web",
		    "Desc",
		    "Imagen"
		};

  		final String[] info = {
	        institution.getAddress(),
	        institution.getPhone(),
	        institution.getMail(),
	        institution.getWeb(),
	        institution.getDesc(),
	        institution.getImage()
	    };
  	  	  
  		    Integer[] imageId = {
  		            R.drawable.exit,
  		            R.drawable.exit,
  		            R.drawable.exit,
  		            R.drawable.exit,
  		            R.drawable.exit,
  		            R.drawable.exit
  		    };
  		    
  	  		CustomList adapter = new CustomList(HospitalInfoActivity.this, title,  info, imageId);
  	        list=(ListView)findViewById(R.id.listinfo);
  	        list.setAdapter(adapter);
	}
	
	public class CustomList extends ArrayAdapter<String>{
		private final Activity context;
		private final String[] web;
		private final String[] infoStr;
		private final Integer[] imageId;
		public CustomList(Activity context, String[] web, String[] info, Integer[] imageId) {
			super(context, R.layout.row_info_hosp, web);
			this.context = context;
			this.web = web;
			this.infoStr = info;
			this.imageId = imageId;
		}
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView= inflater.inflate(R.layout.row_info_hosp, null, true);
			TextView txtTitle = (TextView) rowView.findViewById(R.id.current_title);
			TextView txtInfo = (TextView) rowView.findViewById(R.id.current_desc);
			//ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
			txtTitle.setText(web[position]);
			txtInfo.setText(infoStr[position]);
			
			Log.d("CordovaLog", "------------->>>> " + position);
			Log.d("CordovaLog", "------------->>>> " + web[position]);
			//Log.d("CordovaLog", "------------->>>> " + infoStr[position]);
			
			//imageView.setImageResource(imageId[position]);
			return rowView;
		}
	}
}
