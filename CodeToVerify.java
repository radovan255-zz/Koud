package com.example.ubuntu.whyyes;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zakovacr on 23.6.2017.
 */

public class CodeToVerify {
    public String codeNumber;
    public String time;
    public String name;
    public String store;

    public String imei;
    public String age;
    public String status;
    public double latitude;
    public double longitude;

    public static ArrayList<CodeToVerify> getRecipesFromFile(String filename, Context context) {

        final ArrayList<CodeToVerify> codeList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("codes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray codes = json.getJSONArray("codes");

            // Get Recipe objects from data
            for (int i = 0; i < codes.length(); i++) {
                CodeToVerify code = new CodeToVerify();

                code.codeNumber = codes.getJSONObject(i).getString("code");
                code.imei = codes.getJSONObject(i).getString("imei");
                code.time = codes.getJSONObject(i).getString("time");
                code.status = codes.getJSONObject(i).getString("status");
                code.name = codes.getJSONObject(i).getString("name");
                code.store = codes.getJSONObject(i).getString("store");

                codeList.add(code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return codeList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
