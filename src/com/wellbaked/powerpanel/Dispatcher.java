package com.wellbaked.powerpanel;

import android.content.Context;

public class Dispatcher extends PowerPanel {
	
	private Store store;
	
	public Dispatcher(Context context) {
		store = new Store(context);
	}
	
	public String computerCount() {
		String count = store.computerCount();
		return count;
	}
	
	public void addComputer(String private_key, String mac_address, String host_name, String os_info, String display_name, String last_ip) {
		Long result = store.insert(private_key, mac_address, host_name, os_info, display_name, last_ip);
		System.out.println("Result of adding computer");
		System.out.println(Long.toString(result));
	}

}
