package com.example.lab6_nwk;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class ConvertTemperatureTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    private MainActivity activity;
    private String soapAction;
    private String methodName;
    private String paramsName;
    public ConvertTemperatureTask(MainActivity activity, String soapAction,
                                  String methodName, String paramsName) {
        this.soapAction = soapAction;
        this.methodName = methodName;
        this.paramsName = paramsName;
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... params) {
//create a new soap request object
        SoapObject request = new SoapObject(Constants.NAME_SPACE,
                methodName);
//add properties for soap object
        request.addProperty(paramsName, params[0]);
//request to server and get Soap Primitive response
        return WebserviceCall.callWSThreadSoapPrimitive(Constants.URL,
                soapAction, request);
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (result == null) {
            Log.i("check", "cannot get result");
        } else {
            Log.i("check", result);
//invoke call back method of Activity
            activity.callBackDataFromAsyncTask(result);
        }
    }
}
