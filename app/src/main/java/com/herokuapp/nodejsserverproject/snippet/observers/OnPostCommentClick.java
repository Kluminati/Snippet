package com.herokuapp.nodejsserverproject.snippet.observers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;
import com.herokuapp.nodejsserverproject.snippet.adapters.SnippetCommentAdapter;
import com.herokuapp.nodejsserverproject.snippet.async_tasks.AddCommentTask;

import org.json.JSONArray;
import org.json.JSONObject;


public class OnPostCommentClick implements View.OnClickListener {

    private String id;
    private MainActivity mainActivity;
    private String username;

    public OnPostCommentClick(String id, MainActivity mainActivity,String username) {

        this.id = id;
        this.mainActivity = mainActivity;
        this.username = username;
    }

    @Override
    public void onClick(View v) {
        try {
            JSONObject comment = new JSONObject();
            EditText etCommentRow = ((EditText)((View)v.getParent()).findViewById(R.id.etSnippetObjectNewComment));
            String c = etCommentRow.getText().toString();

            comment.put("user",username);
            comment.put("comment", c);

            AddCommentTask task = new AddCommentTask(mainActivity,id,comment.toString());
            task.execute();

            etCommentRow.setText("");
            RecyclerView recyclerView = (RecyclerView) ((View) v.getParent().getParent()).findViewById(R.id.recView);
            JSONArray data = ((SnippetCommentAdapter) recyclerView.getAdapter()).getData();
            data.put(comment);
            SnippetCommentAdapter snippetCommentAdapter =new SnippetCommentAdapter(data,mainActivity.getLayoutInflater(),mainActivity.getApplicationContext(),mainActivity);

            recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(snippetCommentAdapter);

        }catch (Exception e) {
            Log.e("OnPostCommentClick","FAIL in op post comment observer");
        }
    }
}
