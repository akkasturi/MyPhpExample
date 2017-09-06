package com.example.amkastur.myphpexample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText userNameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameET = (EditText) findViewById(R.id.etUserName);
        passwordET = (EditText) findViewById(R.id.etPassword);
    }


    public void onLogin(View view){
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        String type = "login";

        BackgroundWorker  backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,userName,password);
    }

    public void openRegister(View view){
        /* Start register activity */
        startActivity(new Intent(this,Register.class));
    }

    public void openFetch(View view){
        /* start fetch activity */
      startActivity(new Intent(this, Fetch.class));
    }

    private class RegisterBgWorker extends AsyncTask<String, Object, String> {
        private Context mContext ;
        private AlertDialog mAlerDialog;


        RegisterBgWorker(Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... voids) {
            String username =  voids[1];
            String password =  voids[2];
            String postData = null;
            String result="";

            String loginUrl = "http://helpingapps.in/php/login.php";

            try {
                postData = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            result = Utils.executePhp(loginUrl, postData);
            return result;
        }

        @Override
        protected void onPreExecute() {
            mAlerDialog = new AlertDialog.Builder(mContext).create();
            mAlerDialog.setTitle("Login Status");
            //super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            mAlerDialog.setMessage(aVoid);
            mAlerDialog.show();
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }
}
