package com.wellbaked.powerpanel;

import org.apache.http.HttpResponse;

/**
 * Created by IntelliJ IDEA.
 * User: chris
 * Date: 01/03/11
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
public interface ResponseParser {
    String parseResponse(HttpResponse response);
}
