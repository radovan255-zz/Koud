package com.example.ubuntu.whyyes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static String result = "";
    private static String text = "";


    private Button buttonScan, buttonSend, buttonHistory;
    private TextView textViewCode, textViewStatus, textViewLatitude, textViewLongitude, textViewTime;
    private IntentIntegrator qrScan;
    private GPSTracker gps;
    private Calendar calendar;
    private String code, time;
    private Double latitude, longitude;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonHistory = (Button) findViewById(R.id.buttonHistory);
        textViewCode = (TextView) findViewById(R.id.textViewCode);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

        /*
        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mngr.getDeviceId();
        */

        CheckOrSetPermisson();

        qrScan = new IntentIntegrator(this);
        gps = new GPSTracker(this);
        calendar = Calendar.getInstance();

        //Button Scan action
        buttonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (calendar == null) {

                } else {

                    time = calendar.getTime().toString();
                }

                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                }

                result = "";

                qrScan.initiateScan();
            }
        });

        //Button Send action
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                SendCodeOverHttp();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            launchActivity();
            }
        });
    }

    private void launchActivity(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void CheckOrSetPermisson() {
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (permissionGranted) {
            // {Some Code}
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Some info Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {

                    code = result.getContents();
                    textViewCode.setText("Scanned code: " + result.getContents());
                    textViewLatitude.setText("Latitude is: " + latitude);
                    textViewLongitude.setText("Longitude is: " + longitude);
                    textViewTime.setText("Time is: " + time);
                    textViewStatus.setText("Status");

                } catch (Exception ex) {
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void SendCodeOverHttp() throws UnsupportedEncodingException {

        String data = "{\"verificationrequest\":{\"verificationcode\":\"" + code + "\"," + "\"latitude\":\"" + latitude + "\"," + "\"longitude\":\"" + longitude + "\"}}";

        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("http://www.dontshush.me:42633");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();

        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        if (text.contains("true")) {
            result = "Status - valid";
        } else if (text.contains("false")) {
            result = "Status - invalid";
        } else {
            result = "No response";
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (text.contains("true")) {
                    result = "Status - valid";
                    textViewStatus.setTextColor(Color.parseColor("green"));
                    textViewStatus.setText(result);
                } else if (text.contains("false")) {
                    result = "Status - invalid";
                    textViewStatus.setTextColor(Color.parseColor("red"));
                    textViewStatus.setText(result);
                } else {
                    result = "No response";
                    textViewStatus.setText(result);
                }
            }
        });
    }
}


