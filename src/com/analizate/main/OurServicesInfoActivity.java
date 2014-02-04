package com.analizate.main;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.analizate.database.DatabaseHandlerService;
import com.analizate.database.Service;

public class OurServicesInfoActivity extends Activity {
	/** Called when the activity is first created. */
	DatabaseHandlerService db = new DatabaseHandlerService(this, "", null, '1');
	ListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_services_info);
	    
	    Bundle bundle = getIntent().getExtras();
	    String obj_id = bundle.getString("obj_id");

	    // get this
	    Service service = db.get(obj_id);
	    // Define TextViews
  		TextView name = (TextView)findViewById(R.id.textView2xxy);
  		name.setText(service.getName());
  		
		final String[] title = { "" };
  		final String[] info = { service.getDesc() };
	    Integer[] imageId = { R.drawable.exit };
	    
  		CustomList adapter = new CustomList(OurServicesInfoActivity.this, title,  info, imageId);
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
			Log.d("CordovaLog", "------------->>>> " + position);
			txtTitle.setText(web[position]);
			//txtInfo.setText(infoStr[position]);
			
			Spanned marked_up = Html.fromHtml(infoStr[position]);
			txtInfo.setText(marked_up.toString(),BufferType.SPANNABLE);
			
			//imageView.setImageResource(imageId[position]);
			return rowView;
		}
	}
}
