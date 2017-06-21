package com.example.ubuntu.whyyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity {

    private Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnGoBack = (Button) findViewById(R.id.bt_go_back);

        btnGoBack.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                    finish();
            }
        });
    }
}
