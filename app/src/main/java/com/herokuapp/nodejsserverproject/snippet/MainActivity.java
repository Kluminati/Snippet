package com.herokuapp.nodejsserverproject.snippet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.herokuapp.nodejsserverproject.snippet.async_tasks.GetFeedTask;
import com.herokuapp.nodejsserverproject.snippet.fragments.AboutFragment;
import com.herokuapp.nodejsserverproject.snippet.utils.Utills;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    File input;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = getIntent().getExtras().getString("username");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        GetFeedTask feedTask = new GetFeedTask(this, getSupportFragmentManager(),username);
        feedTask.execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            AboutFragment fragment = new AboutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        } else if (id == R.id.nav_camera) {
            GetFeedTask feedTask = new GetFeedTask(this, getSupportFragmentManager(),username);
            feedTask.execute();
        }  else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                input = WebUtills.createFileOutOfInputStream(inputStream);

                if (findViewById(R.id.tvPhotofrag) != null) {
                    String name = input.getName().substring(0,5)+ "..."+ input.getName().substring(input.getName().length()-10, input.getName().length());
                    ((EditText)findViewById(R.id.tvPhotofrag)).setText(name);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick(R.id.fab)
    public void onClick() { Utills.hideListAndDisplayUploadItem(this); }

    public void hideKeyboardInFragment(View view) {
        Utills.hideKeyboard(this);
    }

    public void photoUpload(View view) {
        Utills.openPickChooser(this);
    }

    public void CancelPhotoUpload(View view) {
        Utills.hideUploadItemAndDisplayList(this);
    }

    public void SaveSnippetObject(View view) {
        String description = ((EditText)findViewById(R.id.etPhotoDescriptionfrag)).getText().toString();
        WebUtills.uploadFile(input,description,username, getSupportFragmentManager(),this);
        Utills.hideUploadItemAndDisplayList(this);
    }

}
