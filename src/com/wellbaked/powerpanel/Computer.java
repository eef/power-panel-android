package com.wellbaked.powerpanel;

import android.content.Context;

public class Computer extends StoreBase {
	
	public static String[] COLUMNS = {"private_key, mac_address, host_name, os_info, display_name, last_ip, id"};
	public static String TABLE = "computers";
	
	public Computer(Context context) {
		super(context, TABLE, COLUMNS);
	}

}
