package com.mfrockola.android;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Angel C on 04/11/2016.
 *
 * Esta clase permitirá realizar peticiones POST al servidor donde se almacenan algunos datos de
 * MFRockola.
 */
public class PostConnection {

    public static void send() throws IOException {
        // Esta es la URL a la que enviareamos nuestras peticiones POST.
        URL url = new URL("http://www.tutormatematico.com/task_manager/v1/login");

        // Objeto Map donde incluiremos todos nuestros parametros de la peticion POST.
        Map<String, Object> params = new LinkedHashMap<>();

        // Agregamos nuestros parametros
        params.put("email","alm93alm@gmail.com");
        params.put("password","123456");

        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length()!=0){
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(),"UTF-8"
        ));

        StringBuilder responseStringBuilder = new StringBuilder();

        for (int c = in.read(); c != -1; c=in.read()) {
            responseStringBuilder.append((char) c);
        }

        System.out.println(responseStringBuilder.toString());

        Object obj = JSONValue.parse(responseStringBuilder.toString());

        JSONObject jsonObject = (JSONObject) obj;

        System.out.println("Hola Sr(a) " + jsonObject.get("name"));
    }

    public static void main (String [] args) {
        try {
            send();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
