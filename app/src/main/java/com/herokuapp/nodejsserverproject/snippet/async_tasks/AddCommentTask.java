package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import org.json.JSONException;
import org.json.JSONObject;


public class AddCommentTask extends AsyncTask<Void,Void,JSONObject> {
    private ProgressDialog progressDialog;
    private MainActivity mainActivity;
    private String id;
    private String comment;

    public AddCommentTask(MainActivity mainActivity, String id, String comment) {
        this.mainActivity = mainActivity;
        this.id = id;
        this.comment = comment;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject data = null;
        String result,yes;
        result = WebUtills.postRequestToDb("id=" + id + "&comment=" + comment,
                "updateComments");
        try {
            data = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
            progressDialog = ProgressDialog.show(mainActivity, "Please Wait, Loging in", "Checking your credentials", true);


        }catch (Exception ignored){}
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        try {
            if (result.get("success").toString().equals("true")) {
                Toast.makeText(mainActivity, "Comment added", Toast.LENGTH_SHORT).show();

            } else
                 Toast.makeText(mainActivity, result.get("massage").toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
