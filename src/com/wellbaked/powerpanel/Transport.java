	package com.wellbaked.powerpanel;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

public class Transport extends AsyncTask<String, Void, String> {
	
	String ret;
	
	TransportListener transport_listener = null;
    private ResponseParser responseParser;
    private ClientFactory clientFactory;

    public Transport(TransportListener listener, ResponseParser responseParser, ClientFactory clientFactory) {
		this.transport_listener = listener;
        this.responseParser = responseParser;
        this.clientFactory = clientFactory;
    }
	
	@Override
	protected String doInBackground(String... args) {
		DefaultHttpClient client = clientFactory.buildClient();
		String url = "http://10.254.254.134";
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			ret = responseParser.parseResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	 protected void onPostExecute(final String response) {
		 transport_listener.onTransportComplete(response);
	 }



	
}