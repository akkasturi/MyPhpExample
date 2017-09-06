package com.example.amkastur.myphpexample;

/**
 *  THIS CLASS IS NOT USED ANY MORE. JUST HERE FOR REFERENCE.
 *  Real code in respect classes, extended from AsyncTask<>
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by amkastur on 05/09/2017.
 */

public class BackgroundWorker extends AsyncTask<String, Object, String> {

    private Context mContext ;
    private AlertDialog mAlerDialog;
    private boolean mIsFetch = false;

    BackgroundWorker(Context context){
        mContext = context;
    }

    private String executePhp(String urlStr, String postData) {

        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result="";
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        }  catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "a1";
    }

    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        String loginUrl = "http://helpingapps.in/php/login.php";
        String registerUrl = "http://helpingapps.in/php/register.php";
        String fetchPetUrl = "http://helpingapps.in/php/fetchPet.php";
        String result;

        if (type.equals("login")) {
            String username =  voids[1];
            String password =  voids[2];
            String postData = null;

            try {
                postData = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            result = executePhp(loginUrl, postData);

            return result;

        } else if (type.equals("register")) {

            String username =  voids[1];
            String owner =   voids[2];
            String password =  voids[3];
            String postData = null;

            try {
                postData = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(owner, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            result = executePhp(registerUrl, postData);

            return result;
        } else if (type.equals("fetch")) {

            String postData = null;

            try {
                postData = URLEncoder.encode("table_name", "UTF-8") + "=" + URLEncoder.encode("pet", "UTF-8");
                mIsFetch = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            result = executePhp(fetchPetUrl, postData);

            return result;
        }

        return "a2";
    }

    @Override
    protected void onPreExecute() {
        mAlerDialog = new AlertDialog.Builder(mContext).create();
        mAlerDialog.setTitle("Login Status");
        //super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String aVoid) {
        if(mIsFetch) {
            mAlerDialog.setMessage("Fetched : " + aVoid);
            mAlerDialog.show();
        }else {
            mAlerDialog.setMessage(aVoid);
            mAlerDialog.show();
        }

        //super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }
}
