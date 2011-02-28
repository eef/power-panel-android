package com.wellbaked.powerpanel;

import android.content.ContentValues;
import android.content.Context;

public class Computer extends StoreBase {
	
	
	public static String[] COLUMNS = {"id, private_key, mac_address, host_name, os_info, display_name, last_ip"};
	public static String TABLE = "computers";
	public ContentValues cv = new ContentValues();
	
	public Computer(Context context) {
		super(context, TABLE, COLUMNS);
		this.cv.put("private_key", "");
		this.cv.put("mac_address", "");
		this.cv.put("host_name", "");
		this.cv.put("os_info", "");
		this.cv.put("display_name", "");
		this.cv.put("last_ip", "");
	}
	
	public Computer(Context context, String private_key, String mac_address, String host_name, String os_info, String display_name, String last_ip) {
		super(context, TABLE, COLUMNS);
		this.cv.put("private_key", private_key);
		this.cv.put("mac_address", mac_address);
		this.cv.put("host_name", host_name);
		this.cv.put("os_info", os_info);
		this.cv.put("display_name", display_name);
		this.cv.put("last_ip", last_ip);
	}
	
	public Computer(Context context, String id, String private_key, String mac_address, String host_name, String os_info, String display_name, String last_ip) {
		super(context, TABLE, COLUMNS);
		this.cv.put("private_key", private_key);
		this.cv.put("mac_address", mac_address);
		this.cv.put("host_name", host_name);
		this.cv.put("os_info", os_info);
		this.cv.put("display_name", display_name);
		this.cv.put("last_ip", last_ip);
	}
	
	public boolean save() {
		insert(this.cv);
		return false;
	}
	
	public boolean update() {
		update(this.cv.getAsString("id"), this.cv);
		return false;
	}
	
	public String getField(String field) {
		return this.cv.getAsString(field);
	}
	
	public void setField(String field, String value) {
		this.cv.put(field, value);
	}
	

}
