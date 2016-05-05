package com.herokuapp.nodejsserverproject.snippet.observers;

import android.util.Log;
import android.view.View;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;


public class OnCommentClick implements View.OnClickListener {

    private String comments;
    private MainActivity mainActivity;

    public OnCommentClick(String comments, MainActivity mainActivity) {
        this.comments = comments;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {

        //((View)v.getParent()).findViewById(R.id.snippetCommentObject).setVisibility(View.INV);
        try {
            //((View)v.getParent()).findViewById(R.id.btnShare).setVisibility(View.INVISIBLE);
            View view = ((View) v.getParent().getParent().getParent()).findViewById(R.id.snippetCommentObject);

            int state = view.getVisibility();
            if (state == View.GONE || state == View.INVISIBLE)
                view.setVisibility(View.VISIBLE);
            else
                view.setVisibility(View.GONE);

        } catch (Exception ignore) {
            Log.e("OnCommentClick", ignore.getMessage());
        }


    }
}
