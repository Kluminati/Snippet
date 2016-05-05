package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;
import com.herokuapp.nodejsserverproject.snippet.fragments.SnippetFragment;
import com.herokuapp.nodejsserverproject.snippet.pojo.SnippetPojoObject;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UpdateFeedTask extends AsyncTask<Void,Void,JSONObject> {
    private ProgressDialog progressDialog;
    private MainActivity mainActivity;
    private FragmentManager supportFragmentManager;
    private String username;
    private SnippetFragment fragment;
    private List<SnippetPojoObject> list = new ArrayList<>();

    public UpdateFeedTask(MainActivity mainActivity, FragmentManager supportFragmentManager, String username) {
        this.mainActivity = mainActivity;
        this.supportFragmentManager = supportFragmentManager;
        this.username = username;
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        try {
            if(result !=null && result.has("success") && result.get("success").toString().equals("true")) {
                list = extractResults(result);
                fragment = new SnippetFragment();
                fragment.setData(list);
                fragment.setUsername(username);
                fragment.setMain(mainActivity);
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private List<SnippetPojoObject> extractResults(JSONObject result) {
        try {
            List<SnippetPojoObject> data = new ArrayList<>();
            SnippetPojoObject pojoObject;
            JSONArray dataObj = new JSONArray(result.get("result").toString());
            JSONObject snippitObj;
            for (int i = 0; i < dataObj.length(); i++) {
                snippitObj = new JSONObject(dataObj.get(i).toString());
                pojoObject = new SnippetPojoObject(
                        snippitObj.get("id").toString(),
                        snippitObj.get("input").toString(),
                        snippitObj.get("title").toString(),
                        snippitObj.get("comments").toString(),
                        snippitObj.get("username").toString(),
                        snippitObj.get("likes").toString());

                data.add(pojoObject);
            }

            return data;
        } catch (JSONException e) {
            Log.e("UpdateFeedTask", e.getMessage());
            e.printStackTrace();

        }

        return null;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        String result = WebUtills.postRequestToDb("No parameters", "selectSnippetObject");//TODO : need to implement this in the db/server
        try {

            return new JSONObject(result);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("UpdateFeedTask", "doInBackground" + e.getMessage());
            return null;
        }
    }
}
