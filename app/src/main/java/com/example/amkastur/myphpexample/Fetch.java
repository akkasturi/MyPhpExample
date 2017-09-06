package com.example.amkastur.myphpexample;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        FetchBgWorker backgroundWorker = new FetchBgWorker();
        backgroundWorker.execute("fetch");

    }


    public void onFetch(View view) {
        mFetchText.setText("okay");
    }

    private class FetchBgWorker extends AsyncTask<String, Object, String> {

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
            
            try {
                JSONArray jArray = new JSONArray(aVoid);

                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    rows = "name : " + json_data.getString("name") +
                            ", owner : " + json_data.getString("owner") +
                            ", password: " + json_data.getString("password");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mFetchText.setText(rows);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }
}
