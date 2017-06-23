package com.example.ubuntu.whyyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;

    ArrayAdapter<String> adapter;

    ArrayList<HashMap<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        String products[] = {"Code1", "Code1", "Code1", "Code1", "Code1"};

        historyListView = (ListView) findViewById(R.id.listViewHistory);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
        historyListView.setAdapter(adapter);




    }
}
