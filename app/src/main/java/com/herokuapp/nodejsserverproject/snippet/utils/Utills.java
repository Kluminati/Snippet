package com.herokuapp.nodejsserverproject.snippet.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.herokuapp.nodejsserverproject.snippet.MainActivity;
import com.herokuapp.nodejsserverproject.snippet.R;


public class Utills {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideListAndDisplayUploadItem(MainActivity main) {
        View recyclerView = main.findViewById(R.id.recyclerViewList);
        View uploadView = main.findViewById(R.id.cvPhotoUpload);

        if (recyclerView != null) {
            recyclerView.setVisibility(View.GONE);
        }
        if (uploadView != null) {
            uploadView.setVisibility(View.VISIBLE);
        }
    }
    public static void hideUploadItemAndDisplayList(MainActivity main) {
        View recyclerView = main.findViewById(R.id.recyclerViewList);
        View uploadView = main.findViewById(R.id.cvPhotoUpload);

        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (uploadView != null) {
            uploadView.setVisibility(View.GONE);
        }
    }

    public static void openPickChooser(MainActivity mainActivity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mainActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
}
