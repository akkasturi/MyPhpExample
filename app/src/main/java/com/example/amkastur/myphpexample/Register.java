package com.example.amkastur.myphpexample;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {

    EditText mNameET;
    EditText mOwnerET;
    EditText mPasswordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameET = (EditText) findViewById(R.id.etName);
        mOwnerET = (EditText) findViewById(R.id.etOwner);
        mPasswordET = (EditText) findViewById(R.id.etPassword);
    }

    private class RegisterBgWorker extends AsyncTask<String, Object, String> {
        private Context mContext ;
        private AlertDialog mAlerDialog;


        RegisterBgWorker(Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... voids) {
            String postData = null;
            String registerUrl = "http://helpingapps.in/php/register.php";
            String result = "";
            String username = voids[1];
            String owner = voids[2];
            String password = voids[3];

            try {
                postData = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(owner, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            result = Utils.executePhp(registerUrl, postData);
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


    public void onRegister(View view){
        String name = mNameET.getText().toString();
        String owner = mOwnerET.getText().toString();
        String password = mPasswordET.getText().toString();
        String type = "register";

        RegisterBgWorker  backgroundWorker = new RegisterBgWorker(this);
        backgroundWorker.execute(type,name,owner,password);
    }
}
