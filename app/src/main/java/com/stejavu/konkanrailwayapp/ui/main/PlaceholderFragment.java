package com.stejavu.konkanrailwayapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.stejavu.konkanrailwayapp.R;
import com.stejavu.konkanrailwayapp.ScheduleDetailsActivity;
import com.stejavu.konkanrailwayapp.TrainStatusActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_train_schedule, container, false);
        final Context context = root.getContext();
        //final TextView textView = root.findViewById(R.id.section_label);
        final ListView listView = root.findViewById(R.id.list_view_train);
        pageViewModel.getText().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] s) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, s);
                final String[] newArray = s;
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();

                        bundle.putString("train",newArray[position]);
                        //Toast.makeText(view.getContext(),newArray[position],Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(view.getContext(), ScheduleDetailsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        });
        return root;
    }
}