package com.wellbaked.powerpanel;

import java.util.HashMap;

import android.content.Context;

public class Computer {
	
	private HashMap<String, String> fields = new HashMap<String, String>();
	private Store store;
	
	public Computer(Context context) {
		this.store = new Store(context);
		fields.put("display_name", "");
		fields.put("hostname", "");
		fields.put("mac_address", "");
		fields.put("last_ip", "");
		fields.put("os_info", "");
		fields.put("private_key", "");
	}
	
	public Computer(String display_name, String hostname, String mac_address, String last_ip, String os_info, String private_key, Context context) {
		this.store = new Store(context);
		fields.put("display_name", display_name);
		fields.put("hostname", hostname);
		fields.put("mac_address", mac_address);
		fields.put("last_ip", last_ip);
		fields.put("os_info", os_info);
		fields.put("private_key", private_key);
	}
	
	public Computer(String display_name, String hostname, String mac_address, String last_ip, String os_info, String private_key) {
		fields.put("display_name", display_name);
		fields.put("hostname", hostname);
		fields.put("mac_address", mac_address);
		fields.put("last_ip", last_ip);
		fields.put("os_info", os_info);
		fields.put("private_key", private_key);
	}
	
	public void setField(String field, String value) {
		fields.put(field, value);
	}
	
	public String getField(String field) {
		return fields.get(field);
	}
	
	public void save() {
		System.out.println(fields.get("hostname"));
	}

}
