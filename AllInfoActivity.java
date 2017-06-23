package com.example.ubuntu.whyyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.example.ubuntu.whyyes.R.id.buttonScan;

public class AllInfoActivity extends AppCompatActivity {

    private TextView age, name, store;
    private RadioButton male, female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_info);

        age = (TextView) findViewById(R.id.editText_age);
        name = (TextView) findViewById(R.id.editText_name);
        store = (TextView) findViewById(R.id.editText_store);
        male = (RadioButton) findViewById(R.id.radioButton_male);
        female = (RadioButton) findViewById(R.id.radioButton_fem);

        Button buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.age = age.getText().toString();
                MainActivity.name = name.getText().toString();
                MainActivity.store = store.getText().toString();

                if (male.isChecked()){
                    MainActivity.gender = "male";
                }
                else if (female.isChecked()){
                    MainActivity.gender = "female";
                }
                else MainActivity.gender = null;

                finish();
            }
        });
    }
}
