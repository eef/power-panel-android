package com.wellbaked.powerpanel;

public interface TransportListener {
	public void onTransportComplete(String response);
	
	public void onTransportCancel();
}
