package com.example.amkastur.myphpexample;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Fetch extends AppCompatActivity {

    TextView mFetchText;
    ListView mPetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        mFetchText = (TextView) findViewById(R.id.tvFetch);
        mPetsList = (ListView) findViewById(R.id.lvShowPetRows);

        String petRows;

        FetchBgWorker backgroundWorker = new FetchBgWorker(this);
        backgroundWorker.execute("fetch");

    }


    public void onFetch(View view) {
        mFetchText.setText("okay");
    }

    private class FetchBgWorker extends AsyncTask<String, Object, String> {
        Context mContext;

        FetchBgWorker(Context context) {mContext = context;}

        @Override
        protected String doInBackground(String... voids) {
            String postData = null;
            String fetchPetUrl = "http://helpingapps.in/php/fetchPet.php";
            String result = "";

            try {
                postData = URLEncoder.encode("table_name", "UTF-8") + "=" + URLEncoder.encode("pet", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result = Utils.executePhp(fetchPetUrl, postData);
            return result;
        }

        @Override
        protected void onPreExecute() {
         }

        @Override
        protected void onPostExecute(String aVoid) {
            //mFetchText.setText(aVoid);
            String rows="";
            ArrayList<String> items = new ArrayList<String>();

            try {
                JSONArray jArray = new JSONArray(aVoid);


                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);

                    /*rows = "name : " + json_data.getString("name") +
                            ", owner : " + json_data.getString("owner") +
                            ", password: " + json_data.getString("password");*/
                    rows =  json_data.getString("name") +
                            ", " + json_data.getString("owner") +
                            ", " + json_data.getString("password");
                    items.add(rows);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(mContext,
                    android.R.layout.simple_list_item_1, items);

            mPetsList.setAdapter(mArrayAdapter);
            //mFetchText.setText(items.toString());
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }
}
