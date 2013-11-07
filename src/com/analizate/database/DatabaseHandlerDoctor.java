package com.analizate.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerDoctor extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "analizate";

	// Customers table name
	private static final String TABLE_NAME = "Doctors";
	
	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "Nombre";
	private static final String KEY_ESPEC = "Especificaciones";
	private static final String KEY_DESC = "Descripcion";
	private static final String KEY_IMG = "Imagen";
	private static final String KEY_PHONE = "Telefono";
	private static final String KEY_CELLPHONE = "TelCelular";
	private static final String KEY_EMAIL = "Email";
	
	public DatabaseHandlerDoctor(Context context, String name, CursorFactory factory, int version) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			+ KEY_ESPEC + " TEXT," + KEY_DESC + " TEXT," + KEY_IMG + " TEXT," + KEY_PHONE + " TEXT," + KEY_CELLPHONE + " TEXT," + KEY_EMAIL + " TEXT" + ")";
		db.execSQL(CREATE_USERS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
		
	//Remake all the table, Risk!!!!
	public void clearTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// Create tables again
		onCreate(db);
	}
	
	// Adding new Doctor
	public void addCustomer(Doctor doctor) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();		
		//values.put(KEY_ID, doctor.getID()); // Doctor code
		values.put(KEY_NAME, doctor.getName()); // Doctor Nombre
		values.put(KEY_ESPEC, doctor.getEspec()); // Doctor Especificaciones
		values.put(KEY_DESC, doctor.getDesc()); // Doctor Descripcion
		values.put(KEY_IMG, doctor.getImage()); // Doctor Imagen
		values.put(KEY_PHONE, doctor.getPhone()); // Doctor Telefono
		values.put(KEY_CELLPHONE, doctor.getCellphone()); // Doctor TelCelular
		values.put(KEY_EMAIL, doctor.getEmail()); // Doctor Email

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}	
	
	// Getting single Doctor
	Doctor get(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_NAME, KEY_ESPEC, KEY_DESC, KEY_IMG, KEY_PHONE, KEY_CELLPHONE, KEY_EMAIL }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			Doctor doctor = new Doctor(Integer.parseInt(cursor.getString(0)) , cursor.getString(1), cursor.getString(2), cursor.getString(3), 
					cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
			return doctor;
		}else{
			return null;
		}
	}
	
	// Getting All Doctors
	public List<Doctor> getAll() {
		List<Doctor> doctorList = new ArrayList<Doctor>();
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Doctor doctor = new Doctor();
				doctor.setID(Integer.parseInt(cursor.getString(0)));
				doctor.setName(cursor.getString(1));
				doctor.setEspec(cursor.getString(2));
				doctor.setDesc(cursor.getString(3));										
				doctor.setImage(cursor.getString(4));
				doctor.setPhone(cursor.getString(5));
				doctor.setCellphone(cursor.getString(6));
				doctor.setEmail(cursor.getString(7));
				doctorList.add(doctor);
			} while (cursor.moveToNext());
		}		
		return doctorList;
	}
	
	// Getting All Doctors by search
	public List<Doctor> getSearch(String like) {
		List<Doctor> doctorList = new ArrayList<Doctor>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_NAME, KEY_ESPEC, KEY_DESC, KEY_IMG, KEY_PHONE, KEY_CELLPHONE, KEY_EMAIL }, 
				KEY_NAME + " LIKE '%"+like +"%'", null, null, null, KEY_NAME);
		if (cursor.moveToFirst()) {
			do {
				Doctor doctor = new Doctor();
				doctor.setID(Integer.parseInt(cursor.getString(0)));
				doctor.setName(cursor.getString(1));
				doctor.setEspec(cursor.getString(2));
				doctor.setDesc(cursor.getString(3));										
				doctor.setImage(cursor.getString(4));
				doctor.setPhone(cursor.getString(5));
				doctor.setCellphone(cursor.getString(6));
				doctor.setEmail(cursor.getString(7));
				doctorList.add(doctor);
			} while (cursor.moveToNext());
		}
		return doctorList;
	}
}