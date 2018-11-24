package com.vlevieux.drumtab;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    protected ListView tabList;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText querySearch;

    SqlHelper db = new SqlHelper(MainActivity.this);

    //a list to store all the artist from firebase database
    private List<DrumTab> drumTabs;
    private DrumTabList drumTabAdapter;
    private boolean listIsEmpty;

    //our database reference object
    DatabaseReference databaseDrumTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(MainActivity.this);
        //For tab activity test
        //Intent i = new Intent(getBaseContext(), TabActivity.class);
        //startActivity(i);

        drumTabs = new ArrayList<>();
        tabList = findViewById(R.id.main_lv_tabs);

        //getting the reference of artists node
        databaseDrumTabs = FirebaseDatabase.getInstance().getReference("drumTabs");

        //Intent intent = new Intent(this, TabActivity.class);
        //startActivity(intent);

        generateToolBar();

        querySearch = findViewById(R.id.main_et_search);
        ImageButton searchButton = findViewById(R.id.main_ib_search);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(db.getAllDrumTab().isEmpty()){
            databaseDrumTabs.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    drumTabs.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        DrumTab drumTab = postSnapshot.getValue(DrumTab.class);
                        //adding artist to the list
                        drumTabs.add(drumTab);
                    }

                    //creating adapter
                    drumTabAdapter = new DrumTabList(MainActivity.this, R.layout.row, drumTabs);
                    //attaching adapter to the listview
                    tabList.setAdapter(drumTabAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{

            drumTabs.clear();

            for(DrumTab drumTab : db.getAllDrumTab()){
                drumTabs.add(drumTab);
            }

            //creating adapter
            drumTabAdapter = new DrumTabList(MainActivity.this, R.layout.row, drumTabs);
            //attaching adapter to the listview
            tabList.setAdapter(drumTabAdapter);
        }
    }

    protected void generateToolBar() {
        Toolbar toolBar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {getSupportActionBar().setTitle("DrumTab");}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void startSettingActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void searchInSqlLiteDatabase(){

        Log.d("LastTest", "Before For Loop 1");
        for(DrumTab drumTab : db.getDrumTab(querySearch.getText().toString().toLowerCase())){
            drumTabs.add(drumTab);
            listIsEmpty = false;
            Log.d("LastTest", drumTab.toString());
        }
    }

    public void search(View v){

        //clear list
        drumTabs.clear();
        listIsEmpty = true;

        searchInSqlLiteDatabase();
        searchInFirebaseDatabase(querySearch.getText().toString().toLowerCase());
    }

    public void searchInFirebaseDatabase(final String querySearch){

        databaseDrumTabs = FirebaseDatabase.getInstance().getReference("drumTabs");

        //attaching value event listener
        databaseDrumTabs.orderByChild("artistName").startAt(querySearch)
                .endAt(querySearch + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("LastTest", "Before For Loop 2.1");
                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            DrumTab drumTab = postSnapshot.getValue(DrumTab.class);
                            //adding artist to the list
                            drumTabs.add(drumTab);
                            listIsEmpty = false;

                            Log.d("LastTest", drumTab.toString());
                        }

                        //attaching value event listener
                        databaseDrumTabs.orderByChild("songName").startAt(querySearch)
                                .endAt(querySearch + "\uf8ff")
                                .addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Log.d("LastTest", "Before For Loop 2.2");
                                        //iterating through all the nodes
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            //getting artist
                                            DrumTab drumTab = postSnapshot.getValue(DrumTab.class);
                                            //adding artist to the list
                                            drumTabs.add(drumTab);
                                            listIsEmpty = false;

                                            Log.d("LastTest", drumTab.toString());
                                        }

                                        updateTabList();
                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }

                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void updateTabList() {

        if (listIsEmpty) {

            //get all favorites from SqlLite Database
            for (DrumTab drumTab : db.getAllDrumTab()) {
                drumTabs.add(drumTab);
            }

            Log.d("LastTest", "Empty");
            //updateAdapter
            Toast.makeText(MainActivity.this, "No result", Toast.LENGTH_SHORT).show();

        } else {
            Log.d("LastTest", "Not Empty");

            Log.d("LastTest", "With duplicates");
            for(DrumTab drumTab : drumTabs){
                Log.d("LastTest", drumTab.toString());
            }
            //Removing Duplicates;
            Set<DrumTab> s = new LinkedHashSet<>();
            s.addAll(drumTabs);

            Log.d("LastTest", "Without Duplicates");
            for(DrumTab drumTab : s){
                Log.d("LastTest", drumTab.toString());
            }

            drumTabs = new ArrayList<>();
            drumTabs.addAll(s);
            //Now the List has only the identical Elements

            Log.d("LastTest", "Without Duplicates");
            for(DrumTab drumTab : drumTabs){
                Log.d("LastTest", drumTab.toString());
            }

            Toast.makeText(MainActivity.this, drumTabAdapter.getCount() + " results", Toast.LENGTH_SHORT).show();
        }

        Log.d("LastTest", "Final List");
        for(DrumTab drumTab : drumTabs){
            Log.d("LastTest", drumTab.toString());
        }

        Log.d("LastTest", "tabList" + String.valueOf(tabList.getAdapter().getCount()));
        Log.d("LastTest", "adapter" + String.valueOf(drumTabAdapter.getCount()));
        drumTabAdapter.notifyDataSetChanged();
        tabList.invalidateViews();


    }

}