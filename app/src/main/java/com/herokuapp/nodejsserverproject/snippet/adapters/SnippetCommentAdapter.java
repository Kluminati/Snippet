package com.herokuapp.nodejsserverproject.snippet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class SnippetCommentAdapter extends RecyclerView.Adapter<SnippetCommentAdapter.SnippetCommentViewHolder>{



    private JSONArray data;
    private LayoutInflater inflater;
    private Context context;
    private MainActivity mainActivity;

    public SnippetCommentAdapter(JSONArray data, LayoutInflater inflater, Context context, MainActivity mainActivity) {

        this.data = data;
        this.inflater = inflater;
        this.context = context;
        this.mainActivity = mainActivity;
    }

    public JSONArray getData() {
        return data;
    }
    @Override
    public SnippetCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SnippetCommentViewHolder(inflater.inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SnippetCommentViewHolder holder, int position) {

        JSONObject comment;
        try {
            comment = new JSONObject(data.get(position).toString());
            holder.tvComment.setText(comment.get("comment").toString());
            holder.tvUser.setText(comment.get("user").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return data.length();
    }

    class SnippetCommentViewHolder extends RecyclerView.ViewHolder {

        TextView tvUser;
        TextView tvComment;

        public SnippetCommentViewHolder(View itemView) {
            super(itemView);
            tvUser = (TextView)itemView.findViewById(R.id.tvCommentUserName);
            tvComment = (TextView)itemView.findViewById(R.id.tvCommentObject);
        }
    }
}
