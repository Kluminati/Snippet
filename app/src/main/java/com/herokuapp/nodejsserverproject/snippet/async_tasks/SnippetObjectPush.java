package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;


public class SnippetObjectPush extends AsyncTask<Void,Void,Void>{

    private ProgressDialog progressDialog;
    private MainActivity mainActivity;
    private String description;
    private String username;
    private String input;


    public SnippetObjectPush(MainActivity mainActivity, String description, String username, String input) {
        this.mainActivity = mainActivity;
        this.description = description;
        this.username = username;
        this.input = input;

    }


    @Override
    protected Void doInBackground(Void... params) {

        String res = WebUtills.postRequestToDb("title=" + description + "&likes=0&comments=nocomments&username=" + username + "&input=" + "http://event.toyberman.com/images/" + input,
                "insertSnippetObject");
        Log.e("asdasdasd", res);

        return null;
    }

}
