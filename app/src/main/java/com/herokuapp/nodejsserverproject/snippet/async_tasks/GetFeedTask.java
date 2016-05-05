package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

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


public class GetFeedTask extends AsyncTask<Void,Void,JSONObject> {
    private ProgressDialog progressDialog;
    private MainActivity mainActivity;
    private FragmentManager supportFragmentManager;
    private String username;
    private SnippetFragment fragment;
    private List<SnippetPojoObject> list = new ArrayList<>();

    public GetFeedTask(MainActivity mainActivity, FragmentManager supportFragmentManager, String username) {
        this.mainActivity = mainActivity;
        this.supportFragmentManager = supportFragmentManager;
        this.username = username;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{//start the loadin dialog
            progressDialog = ProgressDialog.show(mainActivity, "Please Wait While We Load The Data", "Gettin You The Good Stuff!", true);
        }catch (Exception ignored){}
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        try {
            if(result != null && result.get("success").toString().equals("true")) {
                list = extractResults(result);

                fragment = new SnippetFragment();
                fragment.setData(list);
                fragment.setUsername(username);
                fragment.setMain(mainActivity);

                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

            }
            else
                Toast.makeText(mainActivity, "Failed To load Data", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(mainActivity, "Data returned from db the wrong way :" + result.toString(), Toast.LENGTH_LONG).show();
        }
        //finish loading dialog
        progressDialog.dismiss();
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
            Log.e("GetFeedTask", e.getMessage());
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
            Log.e("GetFeedTask ","doInBackground:" + e.getMessage());
            return null;
        }
    }
}
