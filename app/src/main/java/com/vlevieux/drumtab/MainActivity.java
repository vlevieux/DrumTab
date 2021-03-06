package com.vlevieux.drumtab;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    protected ListView tabList;
    private EditText querySearch;
    private ProgressBar progressBarHorizontal;
    private ImageButton searchButton;

    SqlHelper db = new SqlHelper(MainActivity.this);
    DatabaseReference databaseDrumTabs;

    private ArrayList<DrumTab> drumTabs;
    private DrumTabList drumTabAdapter;
    private boolean listIsEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(MainActivity.this);

        drumTabs = new ArrayList<>();
        tabList = findViewById(R.id.main_lv_tabs);
        progressBarHorizontal = findViewById(R.id.main_pb_download_horizontal);
        progressBarHorizontal.setVisibility(View.INVISIBLE);
        querySearch = findViewById(R.id.main_et_search);
        searchButton = findViewById(R.id.main_ib_search);
        generateToolBar();

        //getting the reference of artists node
        databaseDrumTabs = FirebaseDatabase.getInstance().getReference("drumTabs");

    }

    /**
     * At the start of the activity, display all the favorites.
     * If there are not, display all the firebase database.
     */
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
                    drumTabAdapter = new DrumTabList(MainActivity.this, R.layout.row, drumTabs, progressBarHorizontal);
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
            drumTabAdapter = new DrumTabList(MainActivity.this, R.layout.row, drumTabs, progressBarHorizontal);
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

    /**
     * SQL query to find drumTab
     */
    public void searchInSqlLiteDatabase(String querySearch){

        Log.d("LastTest", "Before For Loop 1");
        for(DrumTab drumTab : db.getDrumTab(querySearch)){
            drumTabs.add(drumTab);
            listIsEmpty = false;
            Log.d("LastTest", drumTab.toString());
        }
    }

    /**
     * OnClickListener for the searchButton
     * @param v, view
     */
    public void search(View v){

        //clear list
        drumTabs.clear();
        listIsEmpty = true;
        searchInSqlLiteDatabase(querySearch.getText().toString().toLowerCase());
        searchInFirebaseDatabase(querySearch.getText().toString().toLowerCase());
    }

    /**
     * Firbase query to find drumTab
     * @param querySearch, string from the editText used for the search
     */
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

    /**
     * Delete duplicates and update listView with the result from the queries
     * If the queries return null, then display favorites
     */
    public void updateTabList() {

        if (listIsEmpty) {

            //get all favorites from SqlLite Database
            for (DrumTab drumTab : db.getAllDrumTab()) {
                drumTabs.add(drumTab);
            }

            Log.d("LastTest", "Empty");
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
            drumTabs.clear();
            drumTabs.addAll(s);

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

        //update listView
        drumTabAdapter.notifyDataSetChanged();
        tabList.invalidateViews();
    }
}