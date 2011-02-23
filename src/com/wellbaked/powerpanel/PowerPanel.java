package com.wellbaked.powerpanel;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Java SDK Imports
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PowerPanel extends ListActivity {
	
	// Creation of variable which will be used
	private Dispatcher dispatcher;
	TextView text_view;
	HashMap<String, Integer> tvList = new HashMap<String, Integer>();
	public static final int SCAN = Menu.FIRST + 1; // Scan For Computers Menu Item
	public static final int PURGE_DB = Menu.FIRST + 2; // Purge Database Menu Item
	public String[] mockItems = {"Computer 1", "Computer 2", "Computer 3"};
	public HashMap<String, String> allComputers;
	public String[] listItems;
	
	// Override the oncreate method of the extended Activity
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		// The creation of the dispatcher, all UI events will call this
		createDispatcher();
		
		// Create the string array of computers
		listItems = listItems();
		
		// Create a list of text view objects which will be interacted with
		tvs();
		
		// Add mock data
		// mockComputers(); // Uncomment this to add more mock computers
		
		// Testing get computer count
		computerCount();
		
		// Make the call to display the list
		listSetup();
		
    }
	
	// Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SCAN, 0, "Scan");
		menu.add(0, PURGE_DB, 0, "Purge DB");
		return true;
	}
	
	// Menu select actions
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SCAN:
			updateTextView("status", "Scan started");
			return true;
		case PURGE_DB:
			dispatcher.purgeDB();
			computerCount();
			updateTextView("status", "Purged Database");
			return true;
		}
		return false;
	}
    
	// Method to create an instance of the dispatcher class
	private void createDispatcher() {
		dispatcher = new Dispatcher(this);
	}
	
	// We created a hash map of view objects.  String => Integer
	private void tvs() {
		tvList.put("computer_count", R.id.computer_count);
		tvList.put("status", R.id.status);
	}
	
	// Update the choosen object, accepts a string which is used to pull the correct value from the tvList<String, Integer>
	private void updateTextView(String tv, String messege) {
		text_view = (TextView)findViewById(tvList.get(tv));
		text_view.setText(messege);
	}
	
	// Get the number of computers stored and display it
	private void computerCount() {
		String cc = dispatcher.computerCount();
		updateTextView("computer_count", "Stored Computers: " + cc);
	}
	/*
	Add some mock data, this will generally be called
	This is the structure we want to insert into the database.
	The network class would ideally check for computers in the database, update the computer if found or create a new computer if its not
	*/
	private void mockComputers() {
		dispatcher.addComputer("a", "aosdahsodad", "aosdhaosd", "asudahsd", "asodasdd", "asdasd");
		dispatcher.addComputer("b", "ahsodad", "aosd", "ahsd", "asdd", "ad");
		dispatcher.addComputer("c", "ahsodad", "aosd", "ahsd", "asdd", "ad");
		dispatcher.addComputer("d", "ahsodad", "aosd", "ahsd", "asdd", "ad");
	}
	
	// Class to create the list of computers on screen
	class IconicAdapter extends ArrayAdapter {
		IconicAdapter() {
			super(PowerPanel.this, R.layout.row, listItems);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, null);
			TextView label = (TextView) row.findViewById(R.id.label);
			TextView status_label = (TextView) row.findViewById(R.id.status_label);
			label.setText(listItems[position]);
			status_label.setText("Paired");
			return (row);
		}
	}
	
	// This is called to setup the list
	private void listSetup() {
		setListAdapter(new IconicAdapter());
		registerForContextMenu(getListView());
	}
	
	// Map the returned hash map from selecting all the computers into a array of keys
	// We can the reference the allcomputers hash went we perform actions
	private String[] listItems() {
		allComputers = dispatcher.getComputerList();
		String[] items = new String[allComputers.size()];
		Set comps = allComputers.entrySet();
		Iterator compsIterator = comps.iterator();
		int i = 0;
		while(compsIterator.hasNext()){
		    Map.Entry<String, String> mapping = (Map.Entry) compsIterator.next();
		    items[i] = mapping.getKey().toString();
		    i++;
		}
		return items;
	}
}