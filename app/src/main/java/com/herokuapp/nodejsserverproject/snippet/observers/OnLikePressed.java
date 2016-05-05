package com.herokuapp.nodejsserverproject.snippet.observers;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;
import com.herokuapp.nodejsserverproject.snippet.async_tasks.IncrementLikeTask;


public class OnLikePressed implements View.OnClickListener {
    private String id;
    private MainActivity mainActivity;
    private String username;

    public OnLikePressed(String id, MainActivity mainActivity, String username) {
        this.id = id;
        this.mainActivity = mainActivity;
        this.username = username;
    }

    @Override
    public void onClick(View v) {
        try {

            Log.e("id+username",id + "  " + username + "  " );
            int likeCount;
            IncrementLikeTask task = new IncrementLikeTask(mainActivity,id);
            task.execute();

            v.setPressed(true);
            TextView tvLikeCount = (TextView) ((View) v.getParent().getParent()).findViewById(R.id.tvLikeCount);
            likeCount = Integer.valueOf(tvLikeCount.getText().toString());
            tvLikeCount.setText(String.valueOf(++likeCount));



        }catch (Exception e) {
            Log.e("OnLikePressed", "FAILEDd");
        }

    }
}
