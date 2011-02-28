package com.wellbaked.powerpanel;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Java SDK Imports
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public class PowerPanel extends ListActivity {
	
	// Creation of variable which will be used
	private Dispatcher dispatcher;
	TextView text_view;
	HashMap<String, Integer> tvList = new HashMap<String, Integer>();
	public static final int SCAN = Menu.FIRST + 1; // Scan For Computers Menu Item
	public static final int PURGE_DB = Menu.FIRST + 2; // Purge Database Menu Item
	public static final int ADD_COMP = Menu.FIRST + 3; // Add Computer Menu Item
	public String[] mockItems = {"Computer 1", "Computer 2", "Computer 3"};
	public HashMap<String, HashMap<String, String>> allComputers;
	public String[] listItems;
	public Computer computers;
	
	// Override the oncreate method of the extended Activity
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		// The creation of the dispatcher, all UI events will call this
		createDispatcher();
		
		// Create a list of text view objects which will be interacted with
		tvs();
		
		// create a Computer class instance
		this.computers = new Computer(this);
		computers.deleteAll();
		// update the computer count
		computerCount();
		
		// Make the call to display the list
		 listSetup();
		
    }
	
	public void onListItemClick(ListView parent, View v, final int position, long id) {
		// Computer computer = allComputers.get(this.listItems[position]);
		// makeToast(computer.getField("hostname"), true);
	}
	
	// Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SCAN, 0, "Scan");
		menu.add(0, PURGE_DB, 0, "Purge DB");
		menu.add(0, ADD_COMP, 0, "Add Comp");
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
			//computerCount();
			listSetup();
			updateTextView("status", "Purged Database");
			return true;
		case ADD_COMP:
			// load the add computer class
			// showForm();
			Random randomGenerator = new Random();
			String t = "object" + Integer.toString(randomGenerator.nextInt(100));
			computers.insert(new String[] {t,t,t,t,t,t});
			listSetup();
			computerCount();
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
	protected void updateTextView(String tv, String messege) {
		text_view = (TextView)findViewById(tvList.get(tv));
		text_view.setText(messege);
	}
	
	// Get the number of computers stored and display it
	private void computerCount() {
		String cc = computers.count();
		updateTextView("computer_count", "Stored Computers: " + cc);
	}
	
	// Class to create the list of computers on screen
	class IconicAdapter extends ArrayAdapter {
		@SuppressWarnings("unchecked")
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
		this.listItems = listItems();
		setListAdapter(new IconicAdapter());
		registerForContextMenu(getListView());
	}
	
	// Map the returned hash map from selecting all the computers into a array of keys
	// We can the reference the allcomputers hash went we perform actions
	private String[] listItems() {
		// allComputers = dispatcher.getComputerList();
		allComputers = this.computers.all();
		System.out.println("SIZE =====");
		System.out.println(computers.count());
		String[] items = new String[Integer.parseInt(computers.count())];
		Set<?> comps = allComputers.entrySet();
		Iterator<?> compsIterator = comps.iterator();
		int i = 0;
		while(compsIterator.hasNext()){
			Map.Entry<String, HashMap<String, String>> mapping = (Map.Entry<String, HashMap<String, String>>) compsIterator.next();
			String name = mapping.getKey().toString();
			System.out.println(name);
		    items[i] = name;
		    i++;
		}
		return items;
	}
	
	private void makeToast(String msg, Boolean len) {
		int length;
		if (len) {
			length = Toast.LENGTH_LONG;
		} else {
			length = Toast.LENGTH_SHORT;
		}
		Toast.makeText(this, msg, length).show();
	}
	
	private void showForm() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View addView = inflater.inflate(R.layout.computer_form, null);
		final ComputerForm wrapper = new ComputerForm(addView);
		new AlertDialog.Builder(this).setTitle("Add Computer").setView(
				addView).setPositiveButton("save",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						computers.insert(new String[] {wrapper.getField("private_key"), wrapper.getField("mac_address"), wrapper.getField("hostname"), "OSX", wrapper.getField("display_name"), wrapper.getField("last_ip")});
						listSetup();
						// computerCount();
					}
				}).setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// ignore, just dismiss
					}
				}).show();
	}
	
	class ComputerForm {

		View base = null;
		TextView label;
		EditText input;
		HashMap<String, Integer> form_fields = new HashMap<String, Integer>();
		ComputerForm(View base) {
			this.base = base;
			form_fields.put("display_name", R.id.display_name_input);
			form_fields.put("hostname", R.id.hostname_input);
			form_fields.put("last_ip", R.id.last_ip);
			form_fields.put("mac_address", R.id.mac_address_input);
			form_fields.put("private_key", R.id.private_key_input);
		}
		
		public String getField(String field) {
			input = (EditText) this.base.findViewById(form_fields.get(field));
			return input.getText().toString();
		}
	}
}