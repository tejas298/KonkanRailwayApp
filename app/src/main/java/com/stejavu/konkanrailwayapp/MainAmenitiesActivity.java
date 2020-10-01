package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainAmenitiesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_amenities);

        card1 = findViewById(R.id.helpline);
        card2 = findViewById(R.id.amenities);
        card3 = findViewById(R.id.placesToVisit);

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
        if(v.getId() == R.id.helpline){
            Intent intent = new Intent(v.getContext(),HelplineActivity.class);
            v.getContext().startActivity(intent);
        }else if(v.getId() == R.id.amenities){
            Intent intent = new Intent(v.getContext(),AmenitiesActivity.class);
            v.getContext().startActivity(intent);
        }else if(v.getId() == R.id.placesToVisit){
            Intent intent = new Intent(v.getContext(),PlacesToVisit.class);
            v.getContext().startActivity(intent);
        }
    }
}
