package com.wellbaked.powerpanel;

import android.content.Context;

public class Dispatcher extends PowerPanel {
	
	public Dispatcher(Context context) {
		System.out.println("*******************");
		System.out.println("Dispatcher Called");
		System.out.println("Context Package Name: " + context.getPackageName());
	}
	
	public void testMethod() {
		System.out.println("*******************");
		System.out.println("Test Method Was called");	
	}

}
