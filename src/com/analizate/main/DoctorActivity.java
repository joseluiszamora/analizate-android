package com.analizate.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.analizate.database.DatabaseHandlerDoctor;
import com.analizate.database.Doctor;
import com.analizate.database.Institution;
import com.analizate.webservice.InternetDetector;
import com.analizate.webservice.JSONParser;

public class DoctorActivity extends Activity implements OnItemClickListener{
	// Progress Dialog
    private ProgressDialog pDialog;
    
    // Creating JSON Parser object
 	JSONParser jsonParser = new JSONParser();
 	
 	// internet object
	InternetDetector internet;
	
	DatabaseHandlerDoctor db;
	ListView listView;
	List<Institution> rowItems;
	
	// search functionality
	EditText edittext;
	ListView listview;
	String[] text = null;
	int textlength = 0;

	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<Integer> image_sort = new ArrayList<Integer>();
	
	/** Called when the activity is first created. */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor);
		
		Log.d("CordovaLog", "DOCTOR ACIVITY !!!!!");
		
		db = new DatabaseHandlerDoctor(this, "", null, '1');
		
		/*Doctor doc1 = new Doctor(1, "J Zamora", "jzamora@hotmail.com", "7373737", "588302", "av 1 # 54", "Ninguna Observacion", "image");
		Doctor doc2 = new Doctor(2, "Jose Luis Zamora", "jzamora@hotmail.com", "7373737", "588302", "av 1 # 54", "Ninguna Observacion", "image");
		
		db.add(doc1);
		db.add(doc2);
		db.add(doc3);*/
		
		
		
		
		internet = new InternetDetector(getApplicationContext());
		if (internet.isConnectingToInternet()) {
			/*pDialog = new ProgressDialog(DoctorActivity.this);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setMessage("Actualizando...");
			pDialog.setCancelable(false);
			pDialog.setMax(100);
			
			UpdateInfoAsyncDialog updateWork = new UpdateInfoAsyncDialog();
			updateWork.execute();*/
        }else{
			Toast toast = Toast.makeText(DoctorActivity.this, "Conexión de datos no disponible", Toast.LENGTH_SHORT);
			toast.show();
        }
		
		
	    listView = (ListView) findViewById(R.id.listHospitals);
	    edittext = (EditText) findViewById(R.id.textSearchHospitals);
	    
	    // set all Hospitals List
	    text = db.getAllNames();
	    final List<Doctor> rowItemsH = db.getAll();
	    CustomAdapterDoctors adapter = new CustomAdapterDoctors(this, text, rowItemsH);
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
				List<Doctor> SearchRowItems = db.getAllSearch(edittext.getText().toString());
				CustomAdapterDoctors adapter = new CustomAdapterDoctors(DoctorActivity.this, text_sort, SearchRowItems);
				listView.setAdapter(adapter);
				
				Log.d("log_tag", "Text Changed");
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	

	private class UpdateInfoAsyncDialog extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			
    		List<NameValuePair> paramsx = new ArrayList<NameValuePair>();
			
			// Products JSONArray
			JSONArray results = null;
			// getting JSON string from URL
    		String returnJson = jsonParser.makeHttpRequest("http://www.analizate.com.bo/api/v1/users/doctors.json", 
    						"GET", paramsx);
    		
    		try {
    			Log.d("CordovaLog", "> " + returnJson.trim());
    			if (!returnJson.trim().toString().equals(null)){
    				Log.d("CordovaLog", "Transaccion creada");
    				
    				
    				results = new JSONArray(returnJson);
        			if (results != null) {
        				db.clearTable();
        				// looping through All albums
        				for (int i = 0; i < results.length(); i++) {					
        					JSONObject c = results.getJSONObject(i);
        				    	
        				 	String name = c.getString("full_name");
        					String email = c.getString("email");
        					String phone = c.getString("phone");
        					String cellular = c.getString("cellular");
        					String address = c.getString("address");
        					String specialty_name = c.getString("specialty_name");
        					String observations = c.getString("observations");
        					String avatar_base64 = c.getString("avatar_base64");
        				 	String desc = c.getString("desc");
        				 	//db.add(new Doctor(1, specialty_name, email, phone, cellular, address, observations, avatar_base64));
        				 	//db.add(new Service(name, desc));
        				 	Log.d("CordovaLog", "SAVED !!!!!!");
        				}
        			}
    			}else{
    				Log.d("CordovaLog", "Fallo!!!!!!");
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
				// set all Customers List
			    text = db.getAllNames();
			    final List<Doctor> rowItemsProd = db.getAll();
			    //listview.setAdapter(new CustomAdapterProducts(this, text, rowItems));
			    CustomAdapterDoctors adapter = new CustomAdapterDoctors(DoctorActivity.this, text, rowItemsProd);
			    //ProductsAdapter adapter = new ProductsAdapter(this, rowItems);
			    listView.setAdapter(adapter);
			    listView.setOnItemClickListener(DoctorActivity.this);
				pDialog.dismiss();
			}
		}
		
		@Override
		protected void onCancelled() {}
	}
	


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.d("log_tag", "Clicking");
		String obj_id = (String) ((TextView) arg1.findViewById(R.id.customer_id)).getText();
		Log.d("log_tag", obj_id);
        Intent intentNewProduct = new Intent(this, HospitalInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("obj_id", obj_id);
		intentNewProduct.putExtras(bundle);
		startActivity(intentNewProduct);
	}
	
	
	class CustomAdapterDoctors extends BaseAdapter {

		String[] data_text;
		Context context;
		List<Doctor> rowDoctors;
	
	  	CustomAdapterDoctors() {}
	
		CustomAdapterDoctors(Context context, String[] text, List<Doctor> items) {
			this.context = context;
			data_text = text;
			this.rowDoctors = items;
		}
	
		CustomAdapterDoctors(Context context, ArrayList<String> text, List<Doctor> items) {
			this.context = context;
			this.rowDoctors = items;
			data_text = new String[text.size()];
			for (int i = 0; i < text.size(); i++) {
				data_text[i] = text.get(i);
			}
		}
	  	  
		@Override
		public int getCount() {
			return rowDoctors.size();
		}
	    @Override
	    public Object getItem(int position) {
	        return rowDoctors.get(position);
	    }
	    @Override
	    public long getItemId(int position) {
	    	return rowDoctors.indexOf(getItem(position));
	    }
		public Object getItemCustom(int position) {
			return rowDoctors.get(position);
		}
	
		
		/* private view holder class */
		private class ViewHolder {
			TextView customerId;
			TextView txtName;
			TextView txtAddress;
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
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}	
			Institution rowItem = (Institution) getItem(position);
			holder.customerId.setText(String.valueOf(rowItem.getID()));
			holder.txtName.setText(rowItem.getName());
			holder.txtAddress.setText(String.valueOf(rowItem.getAddress())); 
				
			return convertView;
		}
	
	}   
}