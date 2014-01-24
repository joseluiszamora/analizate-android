package com.analizate.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseHandlerDoctor extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Analizate";
	// Table name
	private static final String TABLE_NAME = "Doctors";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_ID_ESPECIALTY = "id_especialty";
	private static final String KEY_SPECIALTY_NAME = "specialtyname";
	private static final String KEY_NAME = "name";
	private static final String KEY_MAIL = "mail";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_CELL_PHONE = "cellphone";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_OBS = "obs";
	private static final String KEY_IMAGE = "image";
	
	public DatabaseHandlerDoctor(Context context, String name, CursorFactory factory, int version) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_ESPECIALTY + " INTEGER," + KEY_SPECIALTY_NAME + " TEXT," + KEY_NAME + " TEXT," + KEY_MAIL + " TEXT," 
				+ KEY_PHONE + " TEXT," + KEY_CELL_PHONE + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_OBS + " TEXT," + KEY_IMAGE + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	/**
	* All CRUD(Create, Read, Update, Delete) Operations
	*/
	//Remake all the table
	public void clearTable() {
		SQLiteDatabase db = this.getReadableDatabase();
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// Create tables again
		onCreate(db);
	}
	public void setTable() {
		SQLiteDatabase db = this.getReadableDatabase();
		onCreate(db);
	}
	// Adding new product
	public void add(Doctor doctor) {
		Log.d("CordovaLog", "ADDDDDDDDDDDDDDDDDDDDDDDDDDD STARTTTTTT");
		Log.d("CordovaLog", doctor.getName());
		Log.d("CordovaLog", doctor.getImage());
		Log.d("CordovaLog", "ADDDDDDDDDDDDDDDDDDDDDDDDDDD FINISHHHH");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID_ESPECIALTY, doctor.getIdEspeciality());
		values.put(KEY_SPECIALTY_NAME, doctor.getSpecialtyName());
		values.put(KEY_NAME, doctor.getName());
		values.put(KEY_MAIL, doctor.getMail());
		values.put(KEY_PHONE, doctor.getPhone());
		values.put(KEY_CELL_PHONE, doctor.getCellPhone());
		values.put(KEY_ADDRESS, doctor.getAddress());
		values.put(KEY_OBS, doctor.getObs());
		values.put(KEY_IMAGE, doctor.getImage());
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single product
	public Doctor get(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_ID_ESPECIALTY,
				KEY_SPECIALTY_NAME, KEY_NAME, KEY_MAIL, KEY_PHONE, KEY_CELL_PHONE, KEY_ADDRESS, KEY_OBS, KEY_IMAGE }, KEY_ID + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			Doctor doctor = new Doctor(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), 
					cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
			return doctor;
		}else{ return null; }
	}
	
	// Getting All
	public List<Doctor> getAll() {
		List<Doctor> list = new ArrayList<Doctor>();
		//Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Doctor doctor = new Doctor();
				doctor.setID(Integer.parseInt(cursor.getString(0)));
				doctor.setIdEspeciality(Integer.parseInt(cursor.getString(1)));
				doctor.setSpecialtyName(cursor.getString(2));
				doctor.setName(cursor.getString(3));
				doctor.setMail(cursor.getString(4));
				doctor.setPhone(cursor.getString(5));
				doctor.setCellPhone(cursor.getString(6));
				doctor.setAddress(cursor.getString(7));
				doctor.setObs(cursor.getString(8));
				//doctor.setImage(cursor.getString(9));
				// Adding contact to list
				list.add(doctor);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public String[] getAllNames() {
		int i = 0;
		//Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String [] list;  
		list = new String[cursor.getCount()];
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {				
				list[i] = new String(cursor.getString(2));
				i++;
			} while (cursor.moveToNext());
		}		
		return list;
	}
	
	// Getting All For Search
	public List<Doctor> getAllSearch(String like) {
		List<Doctor> list = new ArrayList<Doctor>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_ID_ESPECIALTY,
				KEY_SPECIALTY_NAME, KEY_NAME, KEY_MAIL, KEY_PHONE, KEY_CELL_PHONE, KEY_ADDRESS, KEY_OBS, KEY_IMAGE }, 
				KEY_NAME + " LIKE '%"+like +"%'", null, null, null, KEY_NAME);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Doctor doctor = new Doctor();
				doctor.setID(Integer.parseInt(cursor.getString(0)));
				doctor.setIdEspeciality(Integer.parseInt(cursor.getString(1)));
				doctor.setSpecialtyName(cursor.getString(2));
				doctor.setName(cursor.getString(3));
				doctor.setMail(cursor.getString(4));
				doctor.setPhone(cursor.getString(5));
				doctor.setCellPhone(cursor.getString(6));
				doctor.setAddress(cursor.getString(7));
				doctor.setObs(cursor.getString(8));
				//doctor.setImage(cursor.getString(9));
				// Adding contact to list
				list.add(doctor);
			} while (cursor.moveToNext());
		}	
		return list;
	}
	// Deleting single
	public void delete(Doctor doctor) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(doctor.getID()) });
		db.close();
	}
	// Getting Count
	public int count() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		// return count
		return cursor.getCount();
	}
}