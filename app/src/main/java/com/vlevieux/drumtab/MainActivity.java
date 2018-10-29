package com.vlevieux.drumtab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ListView tabList;

    public class ListAdapter extends ArrayAdapter<Tab> {

        int resourceLayout;
        Context context;
        ListAdapter(Context context, int resource, List<Tab> tabs) {
            super(context, resource, tabs);
            this.resourceLayout = resource;
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(context);
                v = vi.inflate(resourceLayout, null);
            }
            Tab p = getItem(position);
            TextView name = v.findViewById(R.id.main_tv_name);
            TextView info = v.findViewById(R.id.main_tv_more_info);
            name.setText("Sum 41");
            info.setText("BPM 150");
            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Tab();

        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);

        generateToolBar();
        generateTabListView();
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

    protected void generateTabListView(){
        this.tabList = findViewById(R.id.main_lv_tabs);
        ListAdapter customAdapter = new ListAdapter(this, R.layout.row, Arrays.asList(new Tab(), new Tab(),new Tab()));
        tabList.setAdapter(customAdapter);
    }

    public void startSettingActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
