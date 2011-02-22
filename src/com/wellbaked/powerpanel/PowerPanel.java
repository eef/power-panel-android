package com.wellbaked.powerpanel;

// Android SDK Imports
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

// Java SDK Imports
import java.util.HashMap;

public class PowerPanel extends Activity {
	
	// Creation of variable which will be used
	private Dispatcher dispatcher;
	TextView text_view;
	HashMap<String, Integer> tvList = new HashMap<String, Integer>();
	
	// Override the oncreate method of the extended Activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// The creation of the dispatcher, all UI events will call this
		createDispatcher();
		
		// Create a list of text view objects which will be interacted with
		tvs();
		
		// Add mock data
		// mockComputers(); // Uncomment this to add more mock computers
		
		// Testing get computer count
		computerCount();
		
    }
    
	// Method to create an instance of the dispatcher class
	private void createDispatcher() {
		dispatcher = new Dispatcher(this);
	}
	
	// We created a hash map of view objects.  String => Integer
	private void tvs() {
		tvList.put("computer_count", R.id.computer_count);
	}
	
	// Update the choosen object, accepts a string which is used to pull the correct value from the actionList<String, Integer>
	private void updateTextView(String tv, String messege) {
		text_view = (TextView)findViewById(tvList.get(tv));
		text_view.setText(messege);
	}
	
	// Get the number of computers stored and display it
	private void computerCount() {
		String cc = dispatcher.computerCount();
		updateTextView("computer_count", "Stored Computers: " + cc);
	}
	
	// Add some mock data, this will generally be called
	private void mockComputers() {
		dispatcher.addComputer("98sdua9s8du", "aosdahsodad", "aosdhaosd", "asudahsd", "asodasdd", "asdasd");
		dispatcher.addComputer("98sds8du", "ahsodad", "aosd", "ahsd", "asdd", "ad");
	}
}