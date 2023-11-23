package com.example.lab6_nwk.bai2;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CurrencyConverter {

    private static final String NAMESPACE = "http://www.webserviceX.NET/";
    private static final String URL = "http://www.webservicex.net/CurrencyConvertor.asmx";
    private static final String SOAP_ACTION = "http://www.webserviceX.NET/ConversionRate";
    private static final String METHOD_NAME = "ConversionRate";

    public static double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo fromProp = new PropertyInfo();
            fromProp.setName("FromCurrency");
            fromProp.setValue(fromCurrency);
            fromProp.setType(String.class);
            request.addProperty(fromProp);

            PropertyInfo toProp = new PropertyInfo();
            toProp.setName("ToCurrency");
            toProp.setValue(toCurrency);
            toProp.setType(String.class);
            request.addProperty(toProp);

            PropertyInfo amountProp = new PropertyInfo();
            amountProp.setName("Amount");
            amountProp.setValue(amount);
            amountProp.setType(Double.class);
            request.addProperty(amountProp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return Double.parseDouble(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Đánh dấu lỗi
        }
    }
}
