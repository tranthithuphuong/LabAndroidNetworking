package com.example.lab6_nwk.bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab6_nwk.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class tiente extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText edtCurrency;
    private Button btnVNDToUSD, btnUSDToVND;
    private TextView tvResult;
    private String currencyValue; // Thêm dòng này

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiente);

        edtCurrency = findViewById(R.id.edtF_C);
        btnVNDToUSD = findViewById(R.id.btnF);
        btnUSDToVND = findViewById(R.id.btnC);
        tvResult = findViewById(R.id.tvResult);

        btnVNDToUSD.setOnClickListener(this);
        btnUSDToVND.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        currencyValue = edtCurrency.getText().toString().trim();

        if (view.getId() == R.id.btnF) {
            double vndToUsdResult = CurrencyConverter.convertCurrency("VND", "USD", Double.parseDouble(currencyValue));
            displayResult(vndToUsdResult, "USD");
        } else if (view.getId() == R.id.btnC) {
            double usdToVndResult = CurrencyConverter.convertCurrency("USD", "VND", Double.parseDouble(currencyValue));
            displayResult(usdToVndResult, "VND");
        }

    }

    private void displayResult(double result, String toCurrency) {
        if (result != -1) {
            tvResult.setText(String.format("%s %s", currencyValue, toCurrency));
        } else {
            tvResult.setText("Error");
        }
    }
}