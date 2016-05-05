package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.herokuapp.nodejsserverproject.snippet.LoginActivity;
import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginTask extends AsyncTask<Void,Void,Boolean> {
    private ProgressDialog progressDialog;
    private LoginActivity loginActivity;
    private String username;
    private String password;


    public LoginTask(LoginActivity loginActivity, String username, String password) {
        this.loginActivity = loginActivity;
        this.username = username;
        this.password = password;
    }
    /**
     *
     * JavaDoc
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
            progressDialog = ProgressDialog.show(loginActivity, "Please Wait, Loging in", "Checking your credentials", true);
        }catch (Exception ignored){}
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (result) {
            Toast.makeText(loginActivity, "Loged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(loginActivity, MainActivity.class);
            intent.putExtra("username",username);
            loginActivity.startActivity(intent);
        }
        else
            Toast.makeText(loginActivity, "User or password is incorrect", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String result = WebUtills.postRequestToDb("user=" + username + "&pass=" + password, "login");
        try {
            JSONObject dataObj = new JSONObject(result);
            return dataObj.get("success").toString().equals("true");

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
