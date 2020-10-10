package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView train, dateText, runsOn;
    AlertDialog.Builder alertDialog;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_details);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("train");
        name = name.replaceAll(" ","+");

        train = findViewById(R.id.realTrainText);
        dateText = findViewById(R.id.realDateText);
        runsOn = findViewById(R.id.realRunsText);

        alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        //alertDialog.setView(R.layout.loading_dialog);

        alertDialog.setView(inflater.inflate(R.layout.loading_dialog, null));
        alertDialog.setCancelable(false);

        dialog = alertDialog.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        //String url ="http://konkanrailway.com/TrainSchedule/onsubmit.action?trainDetail=02414-NZM-MAO+SUPERFAST+SP&objvo.jspFlag=0&objvo.selTrain=02414-NZM-MAO+SUPERFAST+SP";

        String url = "http://konkanrailway.com/TrainSchedule/onsubmit.action?trainDetail="+name+"&objvo.jspFlag=0&objvo.selTrain="+name;
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        String labelDate = response.substring(response.indexOf("dateToday"), response.indexOf("</label>"));//doc.select("dateToday").first();
                        String temp1 = response.substring(response.indexOf("trainCredNo"));
                        String labelNum = temp1.substring(temp1.indexOf("#000000"), temp1.indexOf("</label>"));//doc.selectFirst("trainCredNo");
                        String temp2 = response.substring(response.indexOf("trainCredName"));
                        String labelTrain = temp2.substring(temp2.indexOf("#000000"), temp2.indexOf("</label>"));//doc.selectFirst("trainCredName");
                        String temp3 = response.substring(response.indexOf("trRemarks"));
                        String labelRemark = temp3.substring(temp3.indexOf("#000000"), temp3.indexOf("</label>"));//doc.selectFirst("trRemarks");
                        String trainText = labelNum.substring(labelNum.indexOf(">")+1)+" - "+labelTrain.substring(labelTrain.indexOf(">")+1);
                        train.setText(trainText);
                        dateText.setText(labelDate.substring(labelDate.indexOf(">")+1));
                        runsOn.setText(labelRemark.substring(labelRemark.indexOf(">")+1));

                        String newText = response.substring(response.indexOf("<tbody>"), response.indexOf("</tbody"));
                        String[] arr = newText.split("\n");
                        List<String> list = new ArrayList<>();
                        int index = 0;
                        StringBuilder stringBuilder = new StringBuilder();
                        for(String str: arr){
                            if(str.contains("<tr>")){
                                index++;
                            }
                            if(str.contains("center")){
                                stringBuilder.append(str.substring(str.indexOf("\">")+2,str.indexOf("</")));
                                stringBuilder.append("_");
                            }
                            if(str.contains("</tr>")){
                                list.add(stringBuilder.toString());
                                stringBuilder.delete(0,stringBuilder.length());
                            }
                        }


                        /*  table building start  */


                        int paddingDp = 5;
                        float density = getResources().getDisplayMetrics().density;
                        int paddingPixel = (int)(paddingDp * density);

                        /* Find Tablelayout defined in main.xml */
                        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);

                        for(String s: list) {

                            String[] data = s.split("_");

                            /* Create a new row to be added. */
                            TableRow tr = new TableRow(getApplicationContext());
                            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            TextView textView = new TextView(getApplicationContext());
                            textView.setText(data[2]);
                            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView.setBackgroundResource(R.drawable.td_shape);
                            textView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextColor(Color.BLACK);
                            //Typeface typeface = Typeface.createFromAsset(getResources().getFont(R.font.segoeui);
                            textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.segoeui));
                            tr.addView(textView);

                            TextView textView1 = new TextView(getApplicationContext());
                            textView1.setText(data[3]);
                            textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView1.setBackgroundResource(R.drawable.td_shape);
                            textView1.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                            textView1.setGravity(Gravity.CENTER);
                            textView1.setTextColor(Color.BLACK);
                            //Typeface typeface = Typeface.createFromAsset(getResources().getFont(R.font.segoeui);
                            textView1.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.segoeui));
                            tr.addView(textView1);


                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setText(data[4]);
                            textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView2.setBackgroundResource(R.drawable.td_shape);
                            textView2.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                            textView2.setGravity(Gravity.CENTER);
                            textView2.setTextColor(Color.BLACK);
                            //Typeface typeface = Typeface.createFromAsset(getResources().getFont(R.font.segoeui);
                            textView2.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.segoeui));
                            tr.addView(textView2);


                            TextView textView3 = new TextView(getApplicationContext());
                            textView3.setText(data[5]);
                            textView3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView3.setBackgroundResource(R.drawable.td_shape);
                            textView3.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                            textView3.setGravity(Gravity.CENTER);
                            textView3.setTextColor(Color.BLACK);
                            //Typeface typeface = Typeface.createFromAsset(getResources().getFont(R.font.segoeui);
                            textView3.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.segoeui));
                            tr.addView(textView3);


                            /* Add row to TableLayout. */
                            //tr.setBackgroundResource(R.drawable.sf_gradient_03);
                            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        }
                        /* table building end  */

                        dialog.cancel();
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


    }
}
