package com.analizate.database;

import android.util.Log;

public class Doctor {
	//private variables
	int _id;
	int _id_especialty;
	String _name;
	String _specialty_name;
	String _mail;
	String _phone;
	String _cellphone;
	String _address;
	String _obs;
	String _image;
	
  	//Empty constructor
	public Doctor(){ }
  // constructor
	public Doctor(int id, int id_especialty, String specialty_name, String name, String mail, String phone, String cellphone, String address, String obs, String image){
		this._id = id;
		this._id_especialty = id_especialty;
		this._name = name;
		this._specialty_name = specialty_name;
		this._mail = mail;
		this._phone = phone;
		this._cellphone = cellphone;
		this._address = address;
		this._obs = obs;
		this._image = image;
	}
  // constructor
  public Doctor(int id_especialty, String specialty_name, String name, String mail, String phone, String cellphone, String address, String obs, String image){
    this._id_especialty = id_especialty;
    this._name = name;
    this._specialty_name = specialty_name;
    this._mail = mail;
    this._phone = phone;
    this._cellphone = cellphone;
    this._address = address;
    this._obs = obs;
    this._image = image;
  }
  // getting ID
  public int getID(){
    return this._id;
  }
  // setting ID
  public void setID(int id){
    this._id = id;
  }
  // getting ID_especiality
  public int getIdEspeciality(){
    return this._id_especialty;
  }
  // setting ID_especiality
  public void setIdEspeciality(int id_especiality){
    this._id_especialty = id_especiality;
  } 
  // getting name
  public String getName(){
    return this._name;
  }
  // setting name
  public void setName(String name){
    this._name = name;
  }
  //getting name
  public String getSpecialtyName(){
	  return this._specialty_name;
  }
  // setting name
  public void setSpecialtyName(String specialtyName){
	  this._specialty_name = specialtyName;
  }
  // getting mail
  public String getMail(){
    return this._mail;
  }
  // setting mail
  public void setMail(String mail){
    this._mail = mail;
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
  public String getCellPhone(){
    return this._cellphone;
  }
  // setting cellphone
  public void setCellPhone(String cellphone){
    this._cellphone = cellphone;
  }
  // getting address
  public String getAddress(){
    return this._address;
  }
  // setting address
  public void setAddress(String address){
    this._address = address;
  }
  // getting obs
  public String getObs(){
    return this._obs;
  }
  // setting obs
  public void setObs(String obs){
    this._obs = obs;
  }
  // getting image
  public String getImage(){
	  //Log.d("CordovaLog", "QUOOOOOOOOOOOOOOOOOOOo");
	  //Log.d("CordovaLog", "UUUUUUUUUUUUUUUU " + this._image);
	  return this._image;
  }
  // setting image
  public void setImage(String image){
    this._image = image;
  }
}