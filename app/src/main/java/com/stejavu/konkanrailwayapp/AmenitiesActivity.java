package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AmenitiesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView allStation, wifi, dietFood, shravanSeva, sarathiSeva, wakeUpCall, prs, townBook, janSadharan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);

        //allStation = findViewById(R.id.allStationAmenities);
        wifi = findViewById(R.id.wifi);
        dietFood = findViewById(R.id.dietFood);
        shravanSeva = findViewById(R.id.shravanSeva);
        sarathiSeva = findViewById(R.id.sarathiSeva);
        wakeUpCall = findViewById(R.id.wakeUpCall);
        prs = findViewById(R.id.prs);
        townBook = findViewById(R.id.townBooking);
        janSadharan = findViewById(R.id.janSadharan);

        //allStation.setOnClickListener(this);
        wifi.setOnClickListener(this);
        dietFood.setOnClickListener(this);
        shravanSeva.setOnClickListener(this);
        sarathiSeva.setOnClickListener(this);
        wakeUpCall.setOnClickListener(this);
        prs.setOnClickListener(this);
        townBook.setOnClickListener(this);
        janSadharan.setOnClickListener(this);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.allStationAmenities:
                break;*/
            case R.id.wifi:
                final Dialog dialogWifi = new Dialog(v.getContext());
                dialogWifi.setContentView(R.layout.wifi_dialog);
                Button button = dialogWifi.findViewById(R.id.wifiDialog);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogWifi.cancel();
                    }
                });
                dialogWifi.show();

                break;
            case R.id.dietFood:
                final Dialog dialogDietFood = new Dialog(v.getContext());
                dialogDietFood.setContentView(R.layout.diet_food_dialog);
                Button buttonDiet = dialogDietFood.findViewById(R.id.dietFoodDialog);
                buttonDiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDietFood.cancel();
                    }
                });
                dialogDietFood.show();

                break;
            case R.id.shravanSeva:
                final Dialog dialogShravan = new Dialog(v.getContext());
                dialogShravan.setContentView(R.layout.shravan_seva_dialog);
                Button shravanButton = dialogShravan.findViewById(R.id.shravanSevaDialog);
                shravanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogShravan.cancel();
                    }
                });
                dialogShravan.show();

                break;
            case R.id.sarathiSeva:
                final Dialog dialogSarathi = new Dialog(v.getContext());
                dialogSarathi.setContentView(R.layout.sarathi_seva_dialog);
                Button sarathiButton = dialogSarathi.findViewById(R.id.sarathiSevaDialog);
                sarathiButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSarathi.cancel();
                    }
                });
                dialogSarathi.show();

                break;
            case R.id.wakeUpCall:
                final Dialog dialogWake = new Dialog(v.getContext());
                dialogWake.setContentView(R.layout.wakeup_dialog);
                Button buttonWake = dialogWake.findViewById(R.id.wakeUpCallDialog);
                buttonWake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogWake.cancel();
                    }
                });
                dialogWake.show();

                break;
            case R.id.prs:
                final Dialog dialogPrs = new Dialog(v.getContext());
                dialogPrs.setContentView(R.layout.prs_dialog);
                Button buttonPrs = dialogPrs.findViewById(R.id.prsDialog);
                buttonPrs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPrs.cancel();
                    }
                });
                dialogPrs.show();

                break;
            case R.id.townBooking:
                final Dialog dialogTown = new Dialog(v.getContext());
                dialogTown.setContentView(R.layout.town_booking_dialog);
                Button buttonTown = dialogTown.findViewById(R.id.townDialog);
                buttonTown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogTown.cancel();
                    }
                });
                dialogTown.show();

                break;
            case R.id.janSadharan:
                final Dialog dialogJan = new Dialog(v.getContext());
                dialogJan.setContentView(R.layout.jan_dhan_dialog);
                Button buttonJan = dialogJan.findViewById(R.id.janSadharanDialog);
                buttonJan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogJan.cancel();
                    }
                });
                dialogJan.show();
                break;
        }
    }
}
