package com.wellbaked.powerpanel;

import android.content.ContentValues;
import android.content.Context;

public class Transport {

	private Context context;
	
	//  I have put some ideas in here
	// Anything can be changed except when creating a new computer it will need an instance of ContentValues passed to the save method
	public Transport(Context context) {
		this.context = context;
	}
	
	public void discover() {
		// Scan for all computer on the network
		// using the private key, or which ever value check for the computer in the database using ComputerInstance.recordExists(String field, String value);
		// if it returns false then parse the results using parseValues then call ComputerInstance.save(ContentValues values);
	}
	
	public void send() {
		// send a command
	}
	
	private ContentValues parseValues(Object values) {
		// ContentValues class is pretty much the same as a HashMap.
		// put and get methods exist
		// if the desktop server has returned a JSON string then loop each key => value and add it to the ContentValue like ContentValuesInstance.put("key", "value");
		return null;
	}
	
}
