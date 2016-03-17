package com.webarch.srmobile.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Manoj Khanna
 */

public class HttpUtils {

    public static HttpURLConnection sendHttpRequest(String urlString) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Linux) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.166 Mobile Safari/537.36");
        httpURLConnection.setConnectTimeout(10000);
        return httpURLConnection;
    }

    public static HttpURLConnection sendHttpRequest(String urlString, String cookie) throws IOException {
        if (cookie == null) {
            throw new IOException("cookie is null!");
        }

        HttpURLConnection httpURLConnection = sendHttpRequest(urlString);
        httpURLConnection.addRequestProperty("Cookie", cookie);
        return httpURLConnection;
    }

    public static HttpURLConnection sendPostRequest(String urlString, HashMap<String, String> postDataMap) throws IOException {
        HttpURLConnection httpURLConnection = sendHttpRequest(urlString);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
        httpURLConnection.setDoOutput(true);

        String postData = "";
        for (Map.Entry<String, String> postDataEntry : postDataMap.entrySet()) {
            postData += postDataEntry.getKey() + "=" + postDataEntry.getValue() + "&";
        }
        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(postData);
        dataOutputStream.close();

        return httpURLConnection;
    }

    public static String sendJsonRequest(String urlString) throws IOException {
        HttpURLConnection httpURLConnection = sendHttpRequest(urlString);
        httpURLConnection.addRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }

}
