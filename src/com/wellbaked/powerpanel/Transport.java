package com.wellbaked.powerpanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.security.sasl.ClientFactoryImpl;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

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
		String url = "http://192.168.0.100";
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			ret = responseParser.parseResponse(response);
		} catch (ClientProtocolException e) {
			System.out.println("=================== ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("=================== IOException");
			e.printStackTrace();
		}
		return ret;
	}
	
	 protected void onPostExecute(final String response) {
		 transport_listener.onTransportComplete(response);
	 }



	
}