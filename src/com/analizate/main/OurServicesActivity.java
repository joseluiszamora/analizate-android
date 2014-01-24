package com.analizate.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.analizate.database.DatabaseHandlerService;
import com.analizate.database.Service;
import com.analizate.webservice.InternetDetector;
import com.analizate.webservice.JSONParser;

public class OurServicesActivity extends Activity implements OnItemClickListener{
	// Progress Dialog
    private ProgressDialog pDialog;
    
    // Creating JSON Parser object
 	JSONParser jsonParser = new JSONParser();
 	
 	// internet object
	InternetDetector internet;
	
	DatabaseHandlerService db;
	
	
	ListView listView;
	List<Service> rowItems;
	
	
	// search functionality
	EditText edittext;
	ListView listview;
	
	String[] text = null;
	int textlength = 0;
	
	ArrayList<String> text_sort = new ArrayList<String>();
	ArrayList<Integer> image_sort = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_our_services);
		
		db = new DatabaseHandlerService(getApplicationContext(), "", null, 1);
		
		internet = new InternetDetector(getApplicationContext());
		if (internet.isConnectingToInternet()) {
			pDialog = new ProgressDialog(OurServicesActivity.this);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setMessage("Actualizando...");
			pDialog.setCancelable(false);
			pDialog.setMax(100);
			
			UpdateInfoAsyncDialog updateWork = new UpdateInfoAsyncDialog();
			updateWork.execute();
        }else{
			Toast toast = Toast.makeText(OurServicesActivity.this, "Conexión de datos no disponible", Toast.LENGTH_SHORT);
			toast.show();
        }
		
		listView = (ListView) findViewById(R.id.contentlistclient);
	    edittext = (EditText) findViewById(R.id.textSearch);
	    
	    // set all Customers List
	    text = db.getAllNames();
	    final List<Service> rowItemsProd = db.getAll();
	    //listview.setAdapter(new CustomAdapterProducts(this, text, rowItems));
	    CustomAdapterProducts adapter = new CustomAdapterProducts(this, text, rowItemsProd);
	    //ProductsAdapter adapter = new ProductsAdapter(this, rowItems);
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
			    
			    List<Service> SearchRowItems = db.getAllSearch(edittext.getText().toString());
			    CustomAdapterProducts adapter = new CustomAdapterProducts(OurServicesActivity.this, text_sort, SearchRowItems);
			    listView.setAdapter(adapter);
			   }
		  });
	}

	private class UpdateInfoAsyncDialog extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			
    		List<NameValuePair> paramsx = new ArrayList<NameValuePair>();
			
			// Products JSONArray
			JSONArray results = null;
			// getting JSON string from URL
    		String returnJson = jsonParser.makeHttpRequest("http://www.analizate.com.bo/api/v1/services.json", 
    						"GET", paramsx);
    		
    		try {
    			Log.d("log_tag", "> " + returnJson.trim());
    			if (!returnJson.trim().toString().equals(null)){
    				Log.d("log_tag", "Transaccion creada");
    				
    				
    				results = new JSONArray(returnJson);
        			if (results != null) {
        				db.clearTable();
        				// looping through All albums
        				for (int i = 0; i < results.length(); i++) {					
        					JSONObject c = results.getJSONObject(i);
        				 	String name = c.getString("name");
        				 	String desc = c.getString("desc");
        				 	db.add(new Service(name, desc));
        				 	Log.d("log_tag", "SAVED !!!!!!");
        				}
        			}
    			}else{
    				Log.d("log_tag", "Fallo!!!!!!");
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
			    final List<Service> rowItemsProd = db.getAll();
			    //listview.setAdapter(new CustomAdapterProducts(this, text, rowItems));
			    CustomAdapterProducts adapter = new CustomAdapterProducts(OurServicesActivity.this, text, rowItemsProd);
			    //ProductsAdapter adapter = new ProductsAdapter(this, rowItems);
			    listView.setAdapter(adapter);
			    listView.setOnItemClickListener(OurServicesActivity.this);
				pDialog.dismiss();
			}
		}
		
		@Override
		protected void onCancelled() {}
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String obj_id = (String) ((TextView) arg1.findViewById(R.id.customer_id)).getText();
		/*
        Intent intentNewProduct = new Intent(this, OurServicesInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("obj_id", custom_name);		
		intentNewProduct.putExtras(bundle);
		startActivity(intentNewProduct);
		finish();*/		
		
		
		
		// custom dialog
		final Dialog dialog = new Dialog(OurServicesActivity.this);
		dialog.setContentView(R.layout.dialog_template);

	    // get this
		Log.d("CordovaLog", "======");
		Log.d("CordovaLog", obj_id);
		
	    Service service = db.get(obj_id);
	    Log.d("CordovaLog", service.getName());
	    // Define TextViews
	    
	    String name = "";
	    String desc = "";
		try {
			name = URLDecoder.decode(service.getName(), "UTF-8");
			desc = URLDecoder.decode(service.getDesc(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.setTitle(name);
		final String[] title = { "" };
  		final String[] info = { desc };
	    
  		Integer[] imageId = { R.drawable.exit };
	    
  		CustomList adapter = new CustomList(OurServicesActivity.this, title,  info, imageId);
  		ListView list = (ListView) dialog.findViewById(R.id.listinfo222);
        list.setAdapter(adapter);
        
        
		/*// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("Android custom dialog example!");
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);
 
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});*/
 
		dialog.show();
	}
	
    
	class CustomAdapterProducts extends BaseAdapter {
	
		  String[] data_text;
		  Context context;
		  List<Service> rowCustomers;
	
		CustomAdapterProducts() {}
	
		CustomAdapterProducts(Context context, String[] text, List<Service> items) {
			this.context = context;
			data_text = text;
			this.rowCustomers = items;
		}
	
		CustomAdapterProducts(Context context, ArrayList<String> text, List<Service> items) {
			this.context = context;
			this.rowCustomers = items;
			data_text = new String[text.size()];
			for (int i = 0; i < text.size(); i++) {
				data_text[i] = text.get(i);
			}
		 }
		  
		@Override
		public int getCount() {
			//return data_text.length;
	      return rowCustomers.size();
	  }
	
	  @Override
	  	public Object getItem(int position) {
	      return rowCustomers.get(position);
	  }
	
	  @Override
	  	public long getItemId(int position) {
	      return rowCustomers.indexOf(getItem(position));
	  }
		  
		public Object getItemCustom(int position) {
			return rowCustomers.get(position);
		}
	
		  /* private view holder class */
	    private class ViewHolder {
	        TextView customerId;
	        TextView txtName;
	        //TextView txtAddress;
	    }
	    
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(R.layout.row_services, null);
				
		        holder = new ViewHolder();
		        holder.customerId = (TextView) convertView.findViewById(R.id.customer_id);
		        holder.txtName = (TextView) convertView.findViewById(R.id.customerName);
		        //holder.txtAddress = (TextView) convertView.findViewById(R.id.customerAddress);
		        convertView.setTag(holder);
		     }
		     else {
		         holder = (ViewHolder) convertView.getTag();
		     }
			
			Service rowItem = (Service) getItem(position);
		      holder.customerId.setText(String.valueOf(rowItem.getID()));
		      holder.txtName.setText(rowItem.getName());
		      //holder.txtAddress.setText(""); 
			
			return convertView;
		  }
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