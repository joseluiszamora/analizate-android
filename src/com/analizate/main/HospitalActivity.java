package com.analizate.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.analizate.database.DatabaseHandlerInstitution;
import com.analizate.database.Institution;
import com.analizate.webservice.InternetDetector;
import com.analizate.webservice.JSONParser;

public class HospitalActivity extends Activity implements OnItemClickListener {
	// Progress Dialog
    private ProgressDialog pDialog;
    
    // Creating JSON Parser object
 	JSONParser jsonParser = new JSONParser();
 	
 	// internet object
	InternetDetector internet;
	
	/** Called when the activity is first created. */
	DatabaseHandlerInstitution db;

	ListView listView;
	List<Institution> rowItems;
	
	// search functionality
	EditText edittext;
	ListView listview;
	String[] text = null;
	int textlength = 0;

	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<Integer> image_sort = new ArrayList<Integer>();
	CustomAdapterHospitals adapter;
	
	/** Called when the activity is first created. */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital);
		
		listView = (ListView) findViewById(R.id.listHospitals);
		listView.setDivider(getResources().getDrawable(android.R.color.transparent));
		edittext = (EditText) findViewById(R.id.textSearchHospitals);
		edittext.clearFocus();
		db = new DatabaseHandlerInstitution(this, "", null, '1');
	   
	    internet = new InternetDetector(getApplicationContext());
	    
		if (internet.isConnectingToInternet()) {
			pDialog = new ProgressDialog(HospitalActivity.this);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setMessage("Actualizando...");
			pDialog.setCancelable(false);
			pDialog.setMax(100);
			
			UpdateInfoAsyncDialog updateWork = new UpdateInfoAsyncDialog();
			updateWork.execute();
        }else{
			Toast toast = Toast.makeText(HospitalActivity.this, "Conexión de datos no disponible", Toast.LENGTH_SHORT);
			toast.show();
        }
	    
	    // set all Hospitals List
	    text = db.getAllNames("Hospitales");
	    final List<Institution> rowItemsH = db.getAll("Hospitales");
	    adapter = new CustomAdapterHospitals(this, text, rowItemsH);
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(this);

		edittext.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				textlength = edittext.getText().length();
				text_sort.clear();
				image_sort.clear();
				for (int i = 0; i < text.length; i++) {
					if (textlength <= text[i].length()) {
						if (edittext.getText().toString().equalsIgnoreCase ( (String) text[i].subSequence(0,textlength))) {
							text_sort.add(text[i]);
							Log.d("log_tag", "Edit text " + edittext.getText().toString());
						}
					}
				}
				List<Institution> SearchRowItems = db.getAllSearch(edittext.getText().toString(), "Hospitales");
				adapter = new CustomAdapterHospitals(HospitalActivity.this, text_sort, SearchRowItems);
				listView.setAdapter(adapter);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String obj_id = (String) ((TextView) arg1.findViewById(R.id.customer_id)).getText();
		
		// custom dialog
		final Dialog dialog = new Dialog(HospitalActivity.this, R.style.cust_dialog);
		dialog.setContentView(R.layout.dialog_template);

	    // get this
		Log.d("CordovaLog", obj_id);
		
		Institution institution = db.get(obj_id);
	    dialog.setTitle(institution.getName());
	    
	    final String[] title = {
			    "",
			    "Dirección",
			    "Telefono",
			    "Mail",
			    "Web"
			};

	  		final String[] info = {
	  			institution.getDesc(),
		        institution.getAddress(),
		        institution.getPhone(),
		        institution.getMail(),
		        institution.getWeb()
		    };
	  	  	  
		    Integer[] imageId = {
		            R.drawable.exit,
		            R.drawable.exit,
		            R.drawable.exit,
		            R.drawable.exit,
		            R.drawable.exit
		    };
	    
  		CustomList adapter2 = new CustomList(HospitalActivity.this, title,  info, imageId);
  		ListView list = (ListView) dialog.findViewById(R.id.listinfo222);
        list.setAdapter(adapter2);
        
        String imgx = db.getImage(obj_id);
        ImageView image = (ImageView) dialog.findViewById(R.id.imageView11x1);
		
		if ( !imgx.equals(null) ) {
			byte[] decodedString = Base64.decode(imgx, Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);																								
			BitmapDrawable ob = new BitmapDrawable(decodedByte);
			image.setBackgroundDrawable(ob);
		}
		
        dialog.show();
	}

	private class UpdateInfoAsyncDialog extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
    		List<NameValuePair> paramsx = new ArrayList<NameValuePair>();
			// Products JSONArray
			JSONArray results = null;
    		String returnJson = jsonParser.makeHttpRequest("http://www.analizate.com.bo/api/v1/institutions.json", "GET", paramsx);
    		try {
				if (!returnJson.trim().toString().equals(null)){
					results = new JSONArray(returnJson);
	    			if (results != null) {
	    				db.clearTable();
	    				for (int i = 0; i < results.length(); i++) {					
	    					JSONObject c = results.getJSONObject(i);
	    				 	db.add(new Institution(c.getString("name"), c.getString("category"), c.getString("address"), c.getString("phone"), 
	    				 			c.getString("mail"), c.getString("web"), c.getString("desc"), c.getString("logo_base64")));
	    				}
	    			}
				}
    		}
    		catch (Exception e) {}
    		
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();
			pDialog.setProgress(progreso);
		}
		@Override
		protected void onPreExecute() {
			pDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					UpdateInfoAsyncDialog.this.cancel(true);
				}
			});
			pDialog.setProgress(0);
			pDialog.show();
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(result) {
			    text = db.getAllNames("Hospitales");
			    final List<Institution> rowItemsH = db.getAll("Hospitales");
			    adapter = new CustomAdapterHospitals(HospitalActivity.this, text, rowItemsH);
			    listView.setAdapter(adapter);
			    listView.setOnItemClickListener(HospitalActivity.this);
				pDialog.dismiss();
			}
		}
		
		@Override
		protected void onCancelled() {}
	}

	class CustomAdapterHospitals extends BaseAdapter {

		String[] data_text;
		Context context;
		List<Institution> rowHospitals;
	
	  	CustomAdapterHospitals() {}
	
		CustomAdapterHospitals(Context context, String[] text, List<Institution> items) {
			this.context = context;
			data_text = text;
			this.rowHospitals = items;
		}
	
		CustomAdapterHospitals(Context context, ArrayList<String> text, List<Institution> items) {
			this.context = context;
			this.rowHospitals = items;
			data_text = new String[text.size()];
			for (int i = 0; i < text.size(); i++) {
				data_text[i] = text.get(i);
			}
		}
	  	  
		@Override
		public int getCount() {
			return rowHospitals.size();
		}
	    @Override
	    public Object getItem(int position) {
	        return rowHospitals.get(position);
	    }
	    @Override
	    public long getItemId(int position) {
	    	return rowHospitals.indexOf(getItem(position));
	    }
		public Object getItemCustom(int position) {
			return rowHospitals.get(position);
		}
		
		/* private view holder class */
		private class ViewHolder {
			TextView customerId;
			TextView txtName;
			TextView txtAddress;
			ImageView img;
		}
	      
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.row_hospital, null);

				holder = new ViewHolder();
				holder.customerId = (TextView) convertView.findViewById(R.id.customer_id);
				holder.txtName = (TextView) convertView.findViewById(R.id.customerName);
				holder.txtAddress = (TextView) convertView.findViewById(R.id.customerAddress);
				holder.img = (ImageView) convertView.findViewById(R.id.list_image);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}	
			Institution rowItem = (Institution) getItem(position);
			holder.customerId.setText(String.valueOf(rowItem.getID()));
			holder.txtName.setText(rowItem.getName());
			holder.txtAddress.setText(String.valueOf(rowItem.getAddress()));
			
			holder.img.setBackgroundDrawable(null);
			holder.img.setImageResource(0);
			if (rowItem.getImage().toString().length() > 4) {
				byte[] decodedString = Base64.decode(rowItem.getImage(), Base64.DEFAULT);
				Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);																								
				BitmapDrawable ob = new BitmapDrawable(decodedByte);
				holder.img.setBackgroundDrawable(ob);
			} else {
				holder.img.setImageResource(R.drawable.analizatelogo);
			}
			
			return convertView;
		}
	}

	public class CustomList extends ArrayAdapter<String>{
		private final Activity context;
		private final String[] web;
		private final String[] infoStr;
		//private final Integer[] imageId;
		public CustomList(Activity context, String[] web, String[] info, Integer[] imageId) {
			super(context, R.layout.row_info_hosp, web);
			this.context = context;
			this.web = web;
			this.infoStr = info;
			//this.imageId = imageId;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView= inflater.inflate(R.layout.row_info_hosp, null, true);
			TextView txtTitle = (TextView) rowView.findViewById(R.id.current_title);
			TextView txtInfo = (TextView) rowView.findViewById(R.id.current_desc);
			txtTitle.setText(Html.fromHtml(web[position]));
			txtInfo.setText(Html.fromHtml(infoStr[position]));
			return rowView;
		}
	}
}