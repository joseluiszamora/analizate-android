package com.analizate.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHandlerSpecialty extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Analizate";
	// Table name
	private static final String TABLE_NAME = "specialties";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DESC = "desc";
	
	public DatabaseHandlerSpecialty(Context context, String name, CursorFactory factory, int version) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_DESC + " TEXT" + ")";
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
	// Adding new
	public void add(Specialty specialty) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, specialty.getName());
		values.put(KEY_DESC, specialty.getDesc());
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single
	public Specialty get(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_NAME, KEY_DESC }, KEY_ID + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			Specialty specialty = new Specialty(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
			return specialty;
		}else{
			return null;
		}							
	}
	
	// Getting All
	public List<Specialty> getAll() {
		List<Specialty> list = new ArrayList<Specialty>();
		//Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Specialty specialty = new Specialty();
				specialty.setID(Integer.parseInt(cursor.getString(0)));
				specialty.setName(cursor.getString(1));
				specialty.setDesc(cursor.getString(2));
				// Adding contact to list
				list.add(specialty);
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
				list[i] = new String(cursor.getString(1));
				i++;
			} while (cursor.moveToNext());
		}		
		return list;
	}
	
	// Getting All For Search
	public List<Specialty> getAllSearch(String like) {
		List<Specialty> list = new ArrayList<Specialty>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_NAME, KEY_DESC }, 
				KEY_NAME + " LIKE '%"+like +"%'", null, null, null, KEY_NAME);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Specialty specialty = new Specialty();
				specialty.setID(Integer.parseInt(cursor.getString(0)));
				specialty.setName(cursor.getString(1));
				specialty.setDesc(cursor.getString(2));
				// Adding contact to list
				list.add(specialty);
			} while (cursor.moveToNext());
		}	
		return list;
	}
	// Deleting single
	public void delete(Specialty specialty) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(specialty.getID()) });
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