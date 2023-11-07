package com.example.lab2.bai4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask_POST extends AsyncTask<String, Void, Void> {
    String duongdan = bai4.SERVER_NAME;
    Context context;
    TextView tvketqua;
    String strketqua;
    ProgressDialog pDialog;

    public BackgroundTask_POST(Context context, TextView tvketqua) {
        this.context = context;
        this.tvketqua = tvketqua;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Caculating...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL (duongdan);
            String param = "a=" + URLEncoder.encode(strings[0].toString(),"utf-8") + "&b="
                    + URLEncoder.encode(strings[1].toString(),"utf-8") + "&c= " +
                    URLEncoder.encode(strings[2].toString(),"utf-8");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(param.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter pritnt = new PrintWriter(urlConnection.getOutputStream());
            pritnt.print(param);
            pritnt.close();

            String line = "";
            BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }
            strketqua = sb.toString();
            urlConnection.disconnect();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void ketqua) {
        super.onPostExecute(ketqua);
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
        tvketqua.setText(strketqua);
    }
}
