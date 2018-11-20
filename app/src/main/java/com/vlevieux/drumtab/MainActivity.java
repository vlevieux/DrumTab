package com.vlevieux.drumtab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ListView tabList;

    //a list to store all the artist from firebase database
    List<DrumTab> drumTabs;

    //our database reference object
    DatabaseReference databaseDrumTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    @Override
    protected void onStart() {
        super.onStart();


        //attaching value event listener
        databaseDrumTabs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clear list to avoid duplicates whenever the activity start
                drumTabs.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    DrumTab artist = postSnapshot.getValue(DrumTab.class);
                    //adding artist to the list
                    drumTabs.add(artist);
                }

                //creating adapter
                DrumTabList drumTabAdapter = new DrumTabList(MainActivity.this, R.layout.row, drumTabs);
                //attaching adapter to the listview
                tabList.setAdapter(drumTabAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

}