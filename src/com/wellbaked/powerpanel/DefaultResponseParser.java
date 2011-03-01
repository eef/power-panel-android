package com.wellbaked.powerpanel;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class DefaultResponseParser implements ResponseParser {
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
