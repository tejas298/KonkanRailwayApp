package com.stejavu.konkanrailwayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
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
import com.stejavu.konkanrailwayapp.com.konkanrail.database.AppDatabase;
import com.stejavu.konkanrailwayapp.com.konkanrail.entities.Train;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    WebView textView;
    List<Train> trainList;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.testtext);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "TejasDB").allowMainThreadQueries().build();

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        trainList = new ArrayList<>();

        String url ="http://konkanrailway.com/TrainSchedule/onsubmit.action?trainDetail=02414-NZM-MAO+SUPERFAST+SP&objvo.jspFlag=0&objvo.selTrain=02414-NZM-MAO+SUPERFAST+SP";
        //String url = "http://konkanrailway.com/TrainSchedule/trainschedule.action";
        String url1 = "http://konkanrailway.com/TrainSchedule/sptrainschedule.action";
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        /*Document doc = Jsoup.parse(response);
                        Element sel = doc.select("select").first();
                        String mainText = sel.wholeText();
                        StringBuilder builder = new StringBuilder();
                        String[] arr = mainText.split("\n");
                        int index = trainList.size();
*/StringBuilder builder = new StringBuilder();
                        String labelDate = response.substring(response.indexOf("dateToday"), response.indexOf("</label>"));//doc.select("dateToday").first();
                        String temp1 = response.substring(response.indexOf("trainCredNo"));
                        String labelNum = temp1.substring(temp1.indexOf("#000000"), temp1.indexOf("</label>"));//doc.selectFirst("trainCredNo");
                        String temp2 = response.substring(response.indexOf("trainCredName"));
                        String labelTrain = temp2.substring(temp2.indexOf("#000000"), temp2.indexOf("</label>"));//doc.selectFirst("trainCredName");
                        String temp3 = response.substring(response.indexOf("trRemarks"));
                        String labelRemark = temp3.substring(temp3.indexOf("#000000"), temp3.indexOf("</label>"));//doc.selectFirst("trRemarks");

                        builder.append(labelDate.substring(labelDate.indexOf(">")+1));
                        builder.append("_");
                        builder.append(labelNum.substring(labelNum.indexOf(">")+1));
                        builder.append("_");
                        builder.append(labelTrain.substring(labelTrain.indexOf(">")+1));
                        builder.append("_");
                        builder.append(labelRemark.substring(labelRemark.indexOf(">")+1));
                        builder.append("_");

                        /*for(String ar : arr){

                            if(!ar.contains("Select Category")) {
                                Train train = new Train();
                                train.name = ar;
                                train.type = "NORM";
                                train.id = index++;
                                trainList.add(train);
                                db.trainDao().insertAll(train);
                            }
                        }*/
                        /*
                        for(String ar: arr){

                            ar = ar.trim();

                            if(!ar.isEmpty())
                                if(!ar.contains("Select Category")) {
                                    builder.append("_");
                                    builder.append(ar);
                                    builder.append("\n");
                                }
                        }
                        */
                        //Toast.makeText(getApplicationContext(),"First request "+arr[2],Toast.LENGTH_LONG).show();

                        textView.loadDataWithBaseURL(null, builder.toString(),"text/html", "UTF-8",null);


                        /*
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
                            }
                            if(str.contains("</tr>")){
                                list.add(stringBuilder.toString());
                                stringBuilder.delete(0,stringBuilder.length());
                            }
                        }

                        Log.i("test",list.get(1));
                        textView.loadDataWithBaseURL(null, list.get(1),"text/html", "UTF-8",null);

                        Toast.makeText(getApplicationContext(),list.get(1),Toast.LENGTH_LONG).show();
                        */

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
                        /*for(String ar : arr){
                            ar = ar.trim();

                            if(!ar.isEmpty())
                            if(!ar.contains("Select Category")) {
                                Train train = new Train();
                                train.name = ar;
                                train.type = "SPEC";
                                train.id = index++;
                                trainList.add(train);
                                db.trainDao().insertAll(train);
                            }
                        }*/

                        Toast.makeText(getApplicationContext(),"Second request "+arr[2],Toast.LENGTH_LONG).show();

                        addToRoom();
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

    public void addToRoom(){
        //db.trainDao().insertAll(trainList);
    }
}
