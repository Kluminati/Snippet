package com.herokuapp.nodejsserverproject.snippet.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;
import com.herokuapp.nodejsserverproject.snippet.observers.OnCommentClick;
import com.herokuapp.nodejsserverproject.snippet.observers.OnLikePressed;
import com.herokuapp.nodejsserverproject.snippet.observers.OnPostCommentClick;
import com.herokuapp.nodejsserverproject.snippet.pojo.SnippetPojoObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class SnippetAdapter extends RecyclerView.Adapter<SnippetAdapter.SnippetViewHolder> implements View.OnClickListener {



    private List<SnippetPojoObject> dataList;
    private Context context;
    private LayoutInflater inflater;
    private MainActivity mainActivity;
    private String username;

    public SnippetAdapter(List<SnippetPojoObject> dataList, Context context, LayoutInflater inflater, MainActivity mainActivity, String username) {
        this.dataList = dataList;
        this.context = context;
        this.inflater = inflater;
        this.mainActivity = mainActivity;
        this.username = username;
    }


    public List<SnippetPojoObject> getDataList() {
        return dataList;
    }

    @Override
    public SnippetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.snippet_item, parent, false);


        return new SnippetViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(SnippetViewHolder holder, int position) {
        SnippetPojoObject snippet = dataList.get(position);
        holder.etTitle.setText(snippet.getEtTitle());
        holder.etUserName.setText(snippet.getEtUserName());
        holder.btnComment.setOnClickListener(new OnCommentClick("", mainActivity));
        holder.btnPost.setOnClickListener(new OnPostCommentClick(snippet.getId(), mainActivity,username));
        holder.btnLike.setOnClickListener(new OnLikePressed(snippet.getId(),mainActivity,username));
        holder.tvLikeCount.setText(snippet.getLikes());


        JSONArray array = null;
        try {
            array = new JSONArray(dataList.get(position).getComments());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        SnippetCommentAdapter snippetCommentAdapter =new SnippetCommentAdapter(array,inflater,context,mainActivity);
        RecyclerView rv = holder.rvComentList;

        rv.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(snippetCommentAdapter);


        Picasso.with(context).
                load(dataList.get(position).getIvPost()).
                placeholder(R.drawable.snippet_logo).
                error(R.drawable.ic_menu_share).
                into(holder.ivPost);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        v.findViewById(R.id.snippetCommentObject).setVisibility(View.VISIBLE);


    }



    class SnippetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button btnLike;
        private Button btnPost;
        private RecyclerView rvComentList;
        private Button btnComment;
        private ImageView ivPost;
        private TextView etTitle;
        private TextView etUserName;
        private TextView tvLikeCount;

        public SnippetViewHolder(View convertView) {
            super(convertView);
            ivPost = (ImageView) convertView.findViewById(R.id.imagePost);
            btnLike = (Button) convertView.findViewById(R.id.btnLike);
            etTitle = (TextView) convertView.findViewById(R.id.etTitle);
            etUserName = (TextView) convertView.findViewById(R.id.etUser);
            btnComment = (Button) convertView.findViewById(R.id.btnComment);
            btnPost = (Button) convertView.findViewById(R.id.btnPost);
            rvComentList = (RecyclerView) convertView.findViewById(R.id.recView);
            tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
        }

        @Override
        public void onClick(View v) {
            v.findViewById(R.id.snippetCommentObject).setVisibility(View.VISIBLE);
        }
    }

}
