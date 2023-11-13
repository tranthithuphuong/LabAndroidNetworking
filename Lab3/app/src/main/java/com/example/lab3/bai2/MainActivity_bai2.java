package com.example.lab3.bai2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lab3.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity_bai2 extends AppCompatActivity {
    // json object response url
    private String urlJsonObj = "http://192.168.9.102/Thondph16247_object.json";
    // json array response url
    private String urlJsonArry = "http://192.168.9.102/Thondph16247_array.json";
    private static String TAG = MainActivity_bai2.class.getSimpleName();
    // progress dialog
    private ProgressDialog pDialog;
    private String jsonResponse;

    private Button btn1,btn2;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai2);
        btn1 = findViewById(R.id.btnObjRequest);
        btn2 = findViewById(R.id.btnArrRequest);
        tv = findViewById(R.id.txtResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeJsonObjectRequest();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeJsonArrayRequest();
            }
        });
    }
    private void makeJsonArrayRequest() {
        showDialog();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(TAG, jsonArray.toString());
                        try {
// Parsing json array response
// loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject person = (JSONObject) jsonArray.get(i);
                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");
                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n";
                            }
                            tv.setText(jsonResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG,"Error: "+ volleyError.getMessage());
                Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }
    private void makeJsonObjectRequest() {
        showDialog();
        JsonObjectRequest jsonObjReq = new
                JsonObjectRequest(Request.Method.GET, urlJsonObj,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, jsonObject.toString());
                try {
// Parsing json object response
// response will be a json object
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    JSONObject phone = jsonObject.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");
                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "Home: " + home + "\n\n";
                    jsonResponse += "Phone: " + mobile + "\n\n";
                    tv.setText(jsonResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " +
                            e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " +
                        volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }
    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}


