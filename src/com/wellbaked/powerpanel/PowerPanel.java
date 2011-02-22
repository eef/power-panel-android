package com.wellbaked.powerpanel;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class PowerPanel extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// Doing some testing on super classes
    	System.out.println("=======================");
    	System.out.println(event.getDisplayLabel());
    	return true;
    }
}