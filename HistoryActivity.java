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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = (ListView) findViewById(R.id.listViewHistory);
        final ArrayList<CodeToVerify> codeList = CodeToVerify.getRecipesFromFile("recipes.json", this);

        CodesAdapter adapter = new CodesAdapter(this, codeList);
        historyListView.setAdapter(adapter);
        String[] listItems = new String[codeList.size()];



        for(int i = 0; i < codeList.size(); i++){
            CodeToVerify codeToVerify = codeList.get(i);
            listItems[i] = codeToVerify.codeNumber;
        }

    }
}
