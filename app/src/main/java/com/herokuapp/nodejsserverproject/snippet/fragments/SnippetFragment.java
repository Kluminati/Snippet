package com.herokuapp.nodejsserverproject.snippet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;
import com.herokuapp.nodejsserverproject.snippet.adapters.SnippetAdapter;
import com.herokuapp.nodejsserverproject.snippet.pojo.SnippetPojoObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SnippetFragment extends Fragment {

    private List<SnippetPojoObject> data;
    private MainActivity main;
    private String username;

    public SnippetFragment() {
        // Required empty public constructor
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setData(List<SnippetPojoObject> data) {
        this.data = data;
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View obj = inflater.inflate(R.layout.fragment_snippet, container, false);
        SnippetAdapter snippetAdapter =new SnippetAdapter(data,main.getApplicationContext(),main.getLayoutInflater(),main,username);
        RecyclerView rv = (RecyclerView)obj.findViewById(R.id.recyclerViewList);
        rv.setLayoutManager(new LinearLayoutManager(main, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(snippetAdapter);
        return obj;
    }

}
