package com.example.lab3.bai1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetContact extends AsyncTask<Void,Void,Void> {
 private String TAG = MainActivity.class.getSimpleName();

 public static String url = "http://192.168.9.102/Thondph16247.php";

 ArrayList<Contact> contactsList;

 private ProgressDialog pDialog;
 private ListView lv;
 Context context;
 ContactAdapter adapter;

    public GetContact(ListView lv, Context context) {
        this.lv = lv;
        this.context = context;
        contactsList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait.........");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();

        String jsonStr = handler.makeServicaCall(url);
        Log.e(TAG,"response from url :"+ jsonStr);
        if (jsonStr != null){
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray contacts = jsonObject.getJSONArray("contacts");

                for (int i = 0;i< contacts.length();i++){
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");

                    Contact contact = new Contact();
                    contact.setId(id);
                    contact.setName(name);
                    contact.setEmail(email);
                    contact.setMobile(mobile);
                    contactsList.add(contact);

                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG ,"json parsing error : "+e.getMessage());
            }
        }else {
            Log.e(TAG, "Couldn't get json from server .");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
        adapter = new ContactAdapter(context, contactsList);
        lv.setAdapter(adapter);
        }
    }

