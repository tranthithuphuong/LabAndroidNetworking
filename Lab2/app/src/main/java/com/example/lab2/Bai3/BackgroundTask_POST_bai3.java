package com.example.lab2.Bai3;

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


public class BackgroundTask_POST_bai3 extends AsyncTask<String,Void,Void> {
    String duongdan = "http://10.24.21.142/api_android/api_lab2_bai3.php";
    Context context;
    TextView tvKq;
    String strKq;
    ProgressDialog dialog ;

    public BackgroundTask_POST_bai3(Context context, TextView tvKq) {
        this.context = context;
        this.tvKq = tvKq;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(duongdan);
            String param = "canh=" + URLEncoder.encode(strings[0].toString(), "utf-8");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setFixedLengthStreamingMode(param.getBytes().length);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
            printWriter.print(param);
            printWriter.close();

            String line="";
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            strKq = sb.toString();
            urlConnection.disconnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Calculating...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if ((dialog.isShowing())){
            dialog.dismiss();
        }
        tvKq.setText(strKq);
    }
}
