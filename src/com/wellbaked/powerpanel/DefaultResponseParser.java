package com.wellbaked.powerpanel;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: chris
 * Date: 01/03/11
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class DefaultResponseParser implements ResponseParser {
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
}
