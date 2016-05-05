package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.herokuapp.nodejsserverproject.snippet.LoginActivity;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterTask extends AsyncTask<Void,Void,Boolean> {
    String name;
    String email;
    String pass;
    LoginActivity loginActivity;
    ProgressDialog progressDialog;

    public RegisterTask(String name, String email, String pass, LoginActivity loginActivity) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.loginActivity = loginActivity;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
            progressDialog = ProgressDialog.show(loginActivity,"Please Wait, Registering","Be with you shortly",true);
        }catch (Exception ignored){}
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (result) {
            Toast.makeText(loginActivity, "Register was successful", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(loginActivity, "Register failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String result = WebUtills.postRequestToDb("username=" + name + "&password=" + pass + "&email=" + email, "registerUser");
        try {
            JSONObject dataObj = new JSONObject(result);
            return dataObj.get("success").toString().equals("true");

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

}
