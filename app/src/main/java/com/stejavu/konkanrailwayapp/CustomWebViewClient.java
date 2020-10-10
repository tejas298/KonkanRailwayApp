package com.stejavu.konkanrailwayapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;

public class CustomWebViewClient extends WebViewClient {

    AlertDialog.Builder alertDialog;
    AlertDialog dialog;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("konkanrailway.com") || Uri.parse(url).getHost().endsWith("indianrail.gov.in")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        alertDialog = new AlertDialog.Builder(view.getContext());
        //LayoutInflater inflater = ;
        alertDialog.setView(R.layout.loading_dialog);

        //alertDialog.setView(inflater.inflate(R.layout.loading_dialog, null));
        alertDialog.setCancelable(true);

        dialog = alertDialog.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        dialog.cancel();
    }
}
