package com.analizate.database;

public class Service {
	//private variables
	int _id;
	String _name;
	String _desc;
	
  	//Empty constructor
	public Service(){
	}
	// constructor
	public Service(int id, String name, String desc){
		this._id = id;
		this._name = name;
		this._desc = desc;
	}
	// constructor
	public Service(String name, String desc){
		this._name = name;
		this._desc = desc;
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
	// getting desc
	public String getDesc(){
	  return this._desc;
	}
    //setting desc
    public void setDesc(String desc){
      this._desc = desc;
    }
}