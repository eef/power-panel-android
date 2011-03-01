package com.wellbaked.powerpanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class Transport extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected Void doInBackground(Void... unused) {
		DefaultHttpClient client = getClient();
		String url = "http://192.168.0.100";
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			System.out.println(parseResponse(response));
		} catch (ClientProtocolException e) {
			System.out.println("=================== ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("=================== IOException");
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String parseResponse(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }
	
	public DefaultHttpClient getClient() {
	    DefaultHttpClient ret = null;

	    //sets up parameters
	    HttpParams params = new BasicHttpParams();
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(params, "utf-8");
	    params.setBooleanParameter("http.protocol.expect-continue", false);

	    //registers schemes for both http and https
	    SchemeRegistry registry = new SchemeRegistry();
	    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 3000));
	    final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
	    sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	    registry.register(new Scheme("https", sslSocketFactory, 443));

	    ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
	    ret = new DefaultHttpClient(manager, params);
	    return ret;
	}
	
}