package com.herokuapp.nodejsserverproject.snippet.async_tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import org.json.JSONException;
import org.json.JSONObject;


public class IncrementLikeTask extends AsyncTask<Void,Void,JSONObject> {
    private MainActivity mainActivity;
    private String id;

    public IncrementLikeTask(MainActivity mainActivity, String id) {
        this.mainActivity = mainActivity;
        this.id = id;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject data = null;
        String result;
        result = WebUtills.postRequestToDb("objectId=" + id,
                "incrementLikes");
        try {
            data = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        try {
            if (result !=null && result.has("success") && result.get("success").toString().equals("true")) {
                Toast.makeText(mainActivity, "Like added", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(mainActivity, result.get("massage").toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.e("IncrementLikeTask","onPostExecute:" + result );
            //e.printStackTrace();
        }
    }
}
