package com.wellbaked.powerpanel;

import java.util.HashMap;

import android.content.Context;

public class Dispatcher extends PowerPanel {
	
	private Store store;
	private Context context;
	
	public Dispatcher(Context context) {
		this.context = context;
		store = new Store(context);
	}
	
	
	public String computerCount() {
		String count = store.computerCount();
		return count;
	}
	
	public void addComputer(String private_key, String mac_address, String host_name, String os_info, String display_name, String last_ip) {
		Long result = store.insert(private_key, mac_address, host_name, os_info, display_name, last_ip);
	}
	
	public void purgeDB() {
		store.deleteAll();
	}
	
	public void callSetTest() {
		setTest("test");
	}
	
	public void setTest(String text) {
		super.updateTextView("status", text);
	}
	
	public HashMap<String, Computer> getComputerList() {
		return store.selectAll();
	}

}
