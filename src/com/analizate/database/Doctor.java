package com.analizate.database;

public class Doctor {
	//private variables
	int _id;
	String _name;
	String _espec;
	String _desc;
	String _image;
	String _phone;
	String _cellphone;
	String _email;
	
	// Empty constructor
	public Doctor(){
	}
	
	// constructor
	public Doctor(int id, String name, String espec, String desc, String image, String phone, String cellphone, String email){
		this._id = id;
		this._name = name;
		this._espec = espec;
		this._desc = desc;
		this._image = image;
		this._phone = phone;
		this._cellphone = cellphone;
		this._email = email;
	}
	
	// constructor
	public Doctor(String name, String espec, String desc, String image, String phone, String cellphone, String email){
		this._name = name;
		this._espec = espec;
		this._desc = desc;
		this._image = image;
		this._phone = phone;
		this._cellphone = cellphone;
		this._email = email;
	}
	
	// getting ID
		public int getID(){
			return this._id;
		}
		
		// setting ID
		public void setID(int id){
			this._id = id;
		}
		
		// getting Nombre
		public String getName(){
			return this._name;
		}
		
		// setting Nombre
		public void setName(String name){
			this._name = name;
		}
		
		// getting Especialidades
		public String getEspec(){
			return this._espec;
		}
		
		// setting Especialidades
		public void setEspec(String espec){
			this._espec = espec;
		}
		
		// getting descripcion
		public String getDesc(){
			return this._desc;
		}
		
		// setting descripcion
		public void setDesc(String desc){
			this._desc = desc;
		}
		
		// getting imagen
		public String getImage(){
			return this._image;
		}
		
		// setting imagen
		public void setImage(String image){
			this._image = image;
		}
		
		// getting phone
		public String getPhone(){
			return this._phone;
		}
		
		// setting phone
		public void setPhone(String phone){
			this._phone = phone;
		}

		// getting cellphone
		public String getCellphone(){
			return this._cellphone;
		}
		
		// setting cellphone
		public void setCellphone(String cellphone){
			this._cellphone = cellphone;
		}
		
		// getting email
		public String getEmail(){
			return this._email;
		}
		
		// setting email
		public void setEmail(String email){
			this._email = email;
		}
}
