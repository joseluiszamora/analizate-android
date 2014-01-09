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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class PharmsActivity extends Activity implements OnItemClickListener {
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

	/** Called when the activity is first created. */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pharms);

		listView = (ListView) findViewById(R.id.listHospitals);
		edittext = (EditText) findViewById(R.id.textSearchHospitals);
		db = new DatabaseHandlerInstitution(this, "", null, '1');

		internet = new InternetDetector(getApplicationContext());

		// set all Hospitals List
		text = db.getAllNames("Farmacias");
		final List<Institution> rowItemsH = db.getAll("Farmacias");
		CustomAdapterHospitals adapter = new CustomAdapterHospitals(this, text,
				rowItemsH);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		edittext.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = edittext.getText().length();
				text_sort.clear();
				image_sort.clear();
				for (int i = 0; i < text.length; i++) {
					if (textlength <= text[i].length()) {
						if (edittext
								.getText()
								.toString()
								.equalsIgnoreCase(
										(String) text[i].subSequence(0,
												textlength))) {
							text_sort.add(text[i]);
							Log.d("log_tag", "Edit text "
									+ edittext.getText().toString());
						}
					}
				}
				List<Institution> SearchRowItems = db.getAllSearch(edittext
						.getText().toString(), "Farmacias");
				CustomAdapterHospitals adapter = new CustomAdapterHospitals(
						PharmsActivity.this, text_sort, SearchRowItems);
				listView.setAdapter(adapter);

				Log.d("log_tag", "Text Changed");
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
		Log.d("log_tag", "Clicking");
		String obj_id = (String) ((TextView) arg1
				.findViewById(R.id.customer_id)).getText();
		Log.d("log_tag", obj_id);
		Intent intentNewProduct = new Intent(this, HospitalInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("obj_id", obj_id);
		intentNewProduct.putExtras(bundle);
		startActivity(intentNewProduct);
	}

	class CustomAdapterHospitals extends BaseAdapter {

		String[] data_text;
		Context context;
		List<Institution> rowHospitals;

		CustomAdapterHospitals() {
		}

		CustomAdapterHospitals(Context context, String[] text,
				List<Institution> items) {
			this.context = context;
			data_text = text;
			this.rowHospitals = items;
		}

		CustomAdapterHospitals(Context context, ArrayList<String> text,
				List<Institution> items) {
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
				holder.customerId = (TextView) convertView
						.findViewById(R.id.customer_id);
				holder.txtName = (TextView) convertView
						.findViewById(R.id.customerName);
				holder.txtAddress = (TextView) convertView
						.findViewById(R.id.customerAddress);
				holder.img = (ImageView) convertView
						.findViewById(R.id.list_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Institution rowItem = (Institution) getItem(position);
			holder.customerId.setText(String.valueOf(rowItem.getID()));
			holder.txtName.setText(rowItem.getName());
			holder.txtAddress.setText(String.valueOf(rowItem.getAddress()));
			byte[] decodedString = Base64.decode(rowItem.getImage(),
					Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
					0, decodedString.length);

			BitmapDrawable ob = new BitmapDrawable(decodedByte);
			holder.img.setBackgroundDrawable(ob);
			return convertView;
		}

	}
}