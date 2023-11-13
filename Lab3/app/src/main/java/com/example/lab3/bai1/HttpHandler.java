package com.example.lab3.bai1;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServicaCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToSring(in);

        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG , "Exception : "+ e.getMessage());
        }
        return response;
    }

    private String convertStreamToSring(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
