package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.stejavu.konkanrailwayapp.com.konkanrail.database.AppDatabase;
import com.stejavu.konkanrailwayapp.com.konkanrail.entities.RecyclerDataModel;
import com.stejavu.konkanrailwayapp.com.konkanrail.entities.Train;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    AlertDialog progressBar;
    final String PREFS_NAME = "CURR_DATE";
    List<Train> trainList;
    AppDatabase db;


    ScheduledExecutorService executorService;
    ViewGroup.MarginLayoutParams marginLayoutParams;
    ViewGroup.LayoutParams params;
    private InterstitialAd mInterstitialAd;
    boolean isActivityEnded = true;
    private AdView mAdView;

    RecyclerView recyclerView;
    RecyclerCustomAdapter adapter;
    ArrayList<RecyclerDataModel> recyclerDataModel = new ArrayList<>();
    int[] images = {R.drawable.calendar, R.drawable.loc1, R.drawable.chair, R.drawable.helpline, R.drawable.news, R.drawable.aboutus};
    String[] contentArray = {"Trains Timetable", "Live Train Status On KR Route", "Seat Availability / PNR Status", "Passenger Amenities",
            "Press Release", "Know More About Us"};
    String[] descriptionArray = {"11:20 AM", "Reached / Running / Delay", "Check Seat Availability", "Helpline Numbers",
            "Latest Information Released", "About Us"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < images.length; i++) {
            recyclerDataModel.add(new RecyclerDataModel(
                    contentArray[i],
                    images[i],
                    descriptionArray[i]
            ));
        }

        trainList = new ArrayList<>();
        db = AppDatabase.getDatabase(getApplicationContext());

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerCustomAdapter(recyclerDataModel);
        recyclerView.setAdapter(adapter);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        params = recyclerView.getLayoutParams();

        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(params);
        }


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("yyyy.MM.dd", Locale.UK);
        //Toast.makeText(this,ft.format(dNow),Toast.LENGTH_SHORT).show();
        String date = ft.format(dNow);
        if (!settings.getString("date", "").contains(date)) {

            if (isNetworkAvailable(this.getApplication())) {
                // record the fact that the app has been started at least once
                settings.edit().putString("date", date).apply();
                //Toast.makeText(this,"Date changed",Toast.LENGTH_SHORT).show();
                db.trainDao().deleteAll();
                doDBReady();
            }

        }

        if (db.trainDao().getAlltrain("NORM").size() == 0) {
            //db.trainDao().deleteAll();
            doDBReady();
        }


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        prepareAd();
        scheduleAd();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                marginLayoutParams.setMargins(0, 0, 0, 90);
                recyclerView.setLayoutParams(marginLayoutParams);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                marginLayoutParams.setMargins(0, 0, 0, 0);
                recyclerView.setLayoutParams(marginLayoutParams);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        /*
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "5";
        runner.execute(sleepTime);
        */
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;

        builder.setMessage("Exit the application ? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialog = builder.create();
        dialog.show();

        //super.onBackPressed();


    }

    public void doDBReady() {
        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();
        String url = "http://konkanrailway.com/TrainSchedule/trainschedule.action";
        String url1 = "http://konkanrailway.com/TrainSchedule/sptrainschedule.action";
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        Document doc = Jsoup.parse(response);
                        Element sel = doc.select("select").first();
                        String mainText = sel.wholeText();
                        String[] arr = mainText.split("\n");
                        int index = trainList.size();
                        for (String ar : arr) {
                            ar = ar.trim();

                            if (!ar.isEmpty())
                                if (!ar.contains("Select Category")) {
                                    Train train = new Train();
                                    train.name = ar;
                                    train.type = "NORM";
                                    train.id = index++;
                                    trainList.add(train);
                                    db.trainDao().insertAll(train);
                                }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        Document doc = Jsoup.parse(response);
                        Element sel = doc.select("select").first();
                        String mainText = sel.wholeText();
                        String[] arr = mainText.split("\n");
                        int index = trainList.size();
                        for (String ar : arr) {
                            ar = ar.trim();

                            if (!ar.isEmpty())
                                if (!ar.contains("Select Category")) {
                                    Train train = new Train();
                                    train.name = ar;
                                    train.type = "SPEC";
                                    train.id = index++;
                                    trainList.add(train);
                                    db.trainDao().insertAll(train);
                                }
                        }

                        //Toast.makeText(getApplicationContext(),"Second request "+arr[2],Toast.LENGTH_LONG).show();

                        //addToRoom();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });


        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest1);

    }

    private Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android.net.Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }


    @Override
    protected void onPause() {

        stopad();

        super.onPause();
    }



    @Override
    protected void onResume() {

        if (executorService.isShutdown()) {
            scheduleAd();
        }

        super.onResume();
    }

    public void scheduleAd() {
        executorService =
                Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("ad", "Not loaded");
                        }

                        prepareAd();
                    }
                });
            }
        }, 30, 30, TimeUnit.SECONDS);

    }

    public void prepareAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7385091305730363/1637616816");//original
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//dummy
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void stopad() {
        executorService.shutdown();
    }

}
