package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SeatPNRActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    CardView card1, card2;
    String[] listItems = {"Seat Availabilty","PNR status"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_pnr);

        //listView = findViewById(R.id.seat_pnr_listview);
        card1 = findViewById(R.id.seat);
        card2 = findViewById(R.id.pnr);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(v.getContext(),AbstractActivity.class);
        if(v.getId() == R.id.seat){
            bundle.putString("motto","seat");
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(v.getId() == R.id.pnr){
            bundle.putString("motto","pnr");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
