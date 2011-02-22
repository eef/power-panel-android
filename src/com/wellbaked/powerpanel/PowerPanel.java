package com.wellbaked.powerpanel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PowerPanel extends Activity {
	
	private Dispatcher dispatcher;
	TextView computer_count;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        createDispatcher();
        dispatcher.testMethod();
    }
    
	// Method to create an instance of the dispatcher class
	private void createDispatcher() {
		dispatcher = new Dispatcher(this);
	}
	
}