package com.stejavu.konkanrailwayapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AbstractActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_status);


        Bundle bundle = getIntent().getExtras();
        String motto = bundle.getString("motto");

        WebView webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient());

        if(motto.equals("seat")){
            webView.loadUrl("http://www.indianrail.gov.in/enquiry/SEAT/SeatAvailability.html?locale=en");
        }else if(motto.equals("pnr")){
            webView.loadUrl("http://www.indianrail.gov.in/enquiry/PNR/PnrEnquiry.html?locale=en");
        }else if(motto.equals("press")){
            webView.loadUrl("http://konkanrailway.com/press/");
        }else if(motto.equals("gallery")){
            webView.loadUrl("http://konkanrailway.com/gallery");
        }

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
