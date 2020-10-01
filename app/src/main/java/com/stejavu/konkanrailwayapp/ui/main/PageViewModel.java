package com.stejavu.konkanrailwayapp.ui.main;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class PageViewModel extends AndroidViewModel {

    String[] array = null;
    AppDatabase db;
    List<Train> trainsNorm ;
    Train[] normArr = null;
    List<Train> trainsSpec;
    Train[] specArr = null;


    public PageViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
        trainsNorm = db.trainDao().getAlltrain("NORM");
        normArr = (Train[]) trainsNorm.toArray(new Train[trainsNorm.size()]);
        trainsSpec = db.trainDao().getAlltrain("SPEC");
        specArr = (Train[]) trainsSpec.toArray(new Train[trainsSpec.size()]);
    }

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String[]> mText = Transformations.map(mIndex, new Function<Integer, String[]>() {
        @Override
        public String[] apply(Integer input) {

            if(input == 1){

                String[] trainArray = new String[normArr.length];
                for(int i=0; i<trainArray.length; i++){
                    trainArray[i] = normArr[i].name;
                }
                return trainArray;
                /*return new String[]{"06551-KAWR-VSG UNRESERVED","06552-VSG-KAWR-UNRESERVED","06585-YPR-KAWR SPL DAILY","06586-KAWR-YPR SPL DAILY",
                                    "10103-MANDOVI EXPRESS","10104-MANDOVI EXPRESS","10111-KONKANKANYA EXPRESS","10112-KONKANKANYA EXPRESS","10215-MAO-ERS SUPFAST",
                                    "10216-ERS-MAO SUPFAST","11003-TUTARI EXPRESS","11004-TUTARI EXPRESS","11085-LTT MAO DOUBLE DECKE","11086-MAO LTT DOUBLE DECKE",
                                    "11097-PUNE-ERNAKULAM EXP","11098-ERNAKULAM-PUNE EXP","12051-JAN SHATABDI EXPRESS","12052-JAN SHATABDI EXPRESS","12133-MANGALORE EXP",
                                    "12134-MUMBAI EXP.","12201-LTT-KCVL GARIB RATH","12202-KCVL-LTT GARIB RATH","12217-KCVL-CDG SAMPARK KRA","12218-CDG-KCVL SAMPARKK",
                                    "12223-LTT ERS DURANTO EXP","12224-ERS LTT DURANTO EXP","12283-ERS NZM DURANTO EXP","12284-NZM ERS DURANTO EXP","12431-RAJDHANI EXPRESS",
                                    "12449-GOA SMPRK KRANTI EXP","12450-GOA SMPRK KRANTI EXP","12483-KCVL-ASR EXPRESS","12484-ASR-KCVL EXPRESS","12617-MANGALA EXP","12618-MANGALA EXP",
                                    "12619-MATSYAGANDHA EXPRESS","12620-MATSYAGANDHA EXPRESS","12741-VSG-PNBE EXPRESS","12742-PNBE-VSG EXPRESS","12779-VSG-NZM GOA SUPFAST",
                                    "12780-NZM-VSG GOA EXPRESS","12977-ERS-AIIMARUSAGAR EXP","12978-AII-ERS EXPRESS","16311-SGNR-KCVL EXPRESS","16312-KCVL-SGNR EXPRESS","16333-VRL-TVC EXPRESS",
                                    "16334-TVC-VRL EXPRESS","16335-GANDHIDHAM NAGARCOIL","16336-NAGARCOIL GANDHIDHAM","16337-OKHA - ERS EXPRESS","16338-ERS - OKHA EXPRESS",
                                    "16345-NETRAVATI EXPRESS","16346-NETRAVATI EXPRESS","16515-YPR-KAWR  INTERCITY","16516-KAWR-YPR INTERCITY","16552-VSG-KAWR-UNRESERVED",
                                    "16595-YPR-KAWR EXP DAILY","16596-KAWR-YPR EXP DAILY","17316-VLNK-VSG EXP","19259-KCVL-BVC  EXPRESS","19260-BVC-KCVL EXPRESS","19261-KCVL-PBR EXPRESS",
                                    "19262-PBR-KCVL EXPRESS","19331-KCVL INDB EXP","19332-INDB-KCVL EXP","19423-TEN-GIMB HAMSAFAR EX","19424-GIMB-TEN-HAMSAFAR EX","19577-TEN-JAM EXP",
                                    "19578-JAM-TEN EXP","22113-LTT KCVL SUPERFAST E","22114-KCVL LTT SUPERFAST E","22115-LTT KRMI AC SUPERFAS","22116-KRMI LTT AC SUPERFAS",
                                    "22119-CSTM-KRMI TEJAS SPL","22120-KRMI-CSTM TEJAS SPL","22149-ERS-PUNE SUP EXP","22150-PUNE-ERS SUP","22413-NZM RAJDHANI","22414-NZM-MAO RAJDHANI",
                                    "22475-HISAR-CBE-AC EXP","22476-CBE-HSR AC SUP","22629-DR TEN EXP","22630-TEN DR EXP","22633-NIZAMUDDIN EXP.","22634-NZM TVC SF EXP","22635-MAQ INTERCITY EXP",
                                    "22636-MAO INTERCITY EXP","22653-TVC-NZM SUPER FAST","22654-NZM-TVC SUPER FAST","22655-TVC NZM AC SUPERFAST","22656-NZM TVC AC SUPERFAST","22659-KCVL DDN EXP",
                                    "22660-DDN KCVL EXP","22907-MAO-HAPA SUPERFAST","22908-HAPA-MAO SUP","50101-RN MAO PASSENGER","50102-MAO RN PASSENGER","50103-DADAR-RATNAGIRI PASS",
                                    "50104-RN-DR PASSENGER","50105-DIVA-SAWANTWADI PASS","50106-SAWANTWADI-DIVA PASS","50107-SWV-MAO PASSENGER","50108-MAO-SWV PASSENGER","56640-MAQ-MAO PASSENGER",
                                    "56641-MAO-MAQ PASSENGER","56665-KGQ BYNR PASSENGER","56666-BYNR KGQ PASSENGER","56961-KULEM-VASCO PASSENGE","56962-VASCO-KULEM PASSENGE","56963-KULEM-VASCO PASSENGE",
                                    "56964-VASCO-KULEM PASSENGE","56965-KULEM-VASCO PASSENGE","56966-VSG-KULEM PASSENGER","70101-PERN-KAWR PASSENGER","70102-KAWR-PERN PASSENGER","70103-PERN-KAWR PASSENGER",
                                    "70104-KAWR-PERN PASSENGER","70105-MAO MAQ DEMU","70106-MAQ MAO DEMU","17419-TPTY-VSG EXPRESS","17420-VSG-TPTY EXPRESS","11099-LTT-MAO DOUBLE DECKE",
                                    "11100-MAO-LTT DOUBLE DECKE","01136-SWV-CSMT SPL"};
*/
            }else if(input == 2){

                String[] trainArray = new String[specArr.length];
                for(int i=0; i<trainArray.length; i++){
                    trainArray[i] = specArr[i].name;
                }
                return trainArray;
                /*return new String[]{"01136-SWV-CSMT SPL","11099-LTT-MAO DOUBLE DECKE","11100-MAO-LTT DOUBLE DECKE","17419-TPTY-VSG EXPRESS",
                                    "17420-VSG-TPTY EXPRESS"};*/
            }
            return null;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String[]> getText() {
        return mText;
    }

    public void setStringArray(String[] arr){
        array = null;
        array = arr;
    }
}