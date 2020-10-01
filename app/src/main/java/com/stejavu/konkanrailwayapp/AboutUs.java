package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {

    CardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        card1 = findViewById(R.id.website);
        card2 = findViewById(R.id.photoGallery);
        card3 = findViewById(R.id.map);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.website){
            String url = "http://konkanrailway.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            v.getContext().startActivity(intent);
        }else if(v.getId() == R.id.photoGallery){
            Intent intent = new Intent(v.getContext(),AbstractActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("motto","gallery");
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        }else if(v.getId() == R.id.map){
            Intent intent = new Intent(v.getContext(),RailMap.class);
            v.getContext().startActivity(intent);
        }
    }
}
