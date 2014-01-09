package com.analizate.database;

public class Institution {
  //private variables
  int _id;
  String _name;
  String _category;
  String _address;
  String _phone;
  String _mail;
  String _web;
  String _desc;
  String _image;
  
  // Empty constructor
  public Institution(){
  }
  // constructor
  public Institution(int id, String name, String category, String address, String phone, String mail, String web, String desc, String image){
    this._id = id;
    this._name = name;
    this._category = category;
    this._address = address;
    this._phone = phone;
    this._mail = mail;
    this._web  = web;
    this._desc = desc;
    this._image = image;
  }
  
  // constructor
  public Institution(String name, String category, String address, String phone, String mail, String web, String desc, String image){
    this._name = name;
    this._category = category;
    this._address = address;
    this._phone = phone;
    this._mail = mail;
    this._web  = web;
    this._desc = desc;
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
  // getting name
  public String getName(){
    return this._name;
  }
  // setting name
  public void setName(String name){
    this._name = name;
  }
  // getting category
  public String getCategory(){
    return this._category;
  }
  // setting category
  public void setCategory(String category){
    this._category = category;
  }
  // getting address
  public String getAddress(){
    return this._address;
  }
  // setting address
  public void setAddress(String address){
    this._address = address;
  }
  // getting phone
  public String getPhone(){
    return this._phone;
  }
  // setting phone
  public void setPhone(String phone){
    this._phone = phone;
  }
  // getting mail
  public String getMail(){
    return this._mail;
  }
  // setting mail
  public void setMail(String mail){
    this._mail = mail;
  }
  // getting web
  public String getWeb(){
    return this._web;
  }
  // setting web
  public void setWeb(String web){
    this._web = web;
  }
  // getting desc
  public String getDesc(){
    return this._desc;
  }
  // setting desc
  public void setDesc(String desc){
    this._desc = desc;
  }
  // getting image
  public String getImage(){
    return this._image;
  }
  // setting image
  public void setImage(String image){
    this._image = image;
  }
}