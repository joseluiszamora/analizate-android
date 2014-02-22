package com.analizate.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerInstitution extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Analizate";
	// Table name
	private static final String TABLE_NAME = "Institutions";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_MAIL = "mail";
	private static final String KEY_WEB = "web";
	private static final String KEY_DESC = "desc";
	private static final String KEY_IMAGE = "image";
	
	public DatabaseHandlerInstitution(Context context, String name, CursorFactory factory, int version) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_ADDRESS + " TEXT," 
				+ KEY_PHONE + " TEXT," + KEY_MAIL + " TEXT," + KEY_WEB + " TEXT," + KEY_DESC + " TEXT," + KEY_IMAGE + " TEXT" + ")";
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
	public void add(Institution institution) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, institution.getName());
		values.put(KEY_CATEGORY, institution.getCategory());
		values.put(KEY_ADDRESS, institution.getAddress());
		values.put(KEY_PHONE, institution.getPhone());
		values.put(KEY_MAIL, institution.getMail());
		values.put(KEY_WEB, institution.getWeb());
		values.put(KEY_DESC, institution.getDesc());
		values.put(KEY_IMAGE, institution.getImage());
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single product
	public Institution get(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_NAME, KEY_CATEGORY, KEY_ADDRESS, KEY_PHONE, KEY_MAIL, KEY_WEB, KEY_DESC, KEY_IMAGE }, KEY_ID + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			Institution institution = new Institution(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), 
					cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
			return institution;
		}else{
			return null;
		}
	}
	
	public String getImage(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_IMAGE }, KEY_ID + "=?", new String[] { id }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			return cursor.getString(0);
		}else{
			return null;
		}
	}
	
	// Getting All
	public List<Institution> getAll(String category) {
		List<Institution> list = new ArrayList<Institution>();
		//Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_CATEGORY + " = '"+ category + "' ORDER BY " + KEY_NAME;
		Log.d("log_tag", selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Institution institution = new Institution();
				institution.setID(Integer.parseInt(cursor.getString(0)));
				institution.setName(cursor.getString(1));
				institution.setCategory(cursor.getString(2));
				institution.setAddress(cursor.getString(3));
				institution.setPhone(cursor.getString(4));
				institution.setMail(cursor.getString(5));
				institution.setWeb(cursor.getString(6));
				institution.setDesc(cursor.getString(7));
				institution.setImage(cursor.getString(8));
				// Adding contact to list
				list.add(institution);
			} while (cursor.moveToNext());
		}
		return list;
	}

	
	public String[] getAllNames(String category) {
		int i = 0;
		//Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_CATEGORY + " = '"+ category + "' ORDER BY " + KEY_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String [] list;  
		list = new String[cursor.getCount()];
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {				
				list[i] = new String(cursor.getString(1));
				i++;
			} while (cursor.moveToNext());
		}		
		return list;
	}
	
	// Getting All For Search
	public List<Institution> getAllSearch(String like, String category) {
		//Select All Query
		List<Institution> list = new ArrayList<Institution>();
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " LIKE '%"+like +"%' AND " + KEY_CATEGORY + " = '"+ category + "' ORDER BY " + KEY_NAME;
		
		Cursor cursor = db.rawQuery(selectQuery, null); 
		
		if (cursor.moveToFirst()) {
			do {
				Institution institution = new Institution();
				institution.setID(Integer.parseInt(cursor.getString(0)));
				institution.setName(cursor.getString(1));
				institution.setCategory(cursor.getString(2));
				institution.setAddress(cursor.getString(3));
				institution.setPhone(cursor.getString(4));
				institution.setMail(cursor.getString(5));
				institution.setWeb(cursor.getString(6));
				institution.setDesc(cursor.getString(7));
				institution.setImage(cursor.getString(8));
				// Adding contact to list
				list.add(institution);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	// Deleting single
	public void delete(Institution institution) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(institution.getID()) });
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