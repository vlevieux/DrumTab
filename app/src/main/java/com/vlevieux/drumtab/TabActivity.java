package com.vlevieux.drumtab;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    DynamicTabView dtv;
    HorizontalScrollView hsw;
    TextView infoTv;
    ImageButton addToFavoriteBtn;

    ObjectAnimator animator;

    private Tab tab;
    private String tabPath;

    public static List<Shape> shapes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        dtv = findViewById(R.id.tab_vw_dynamic_tab);
        hsw = findViewById(R.id.tab_hsw_tab);
        infoTv = findViewById(R.id.tab_tv_song_info);
        addToFavoriteBtn = findViewById(R.id.tab_btn_add_to_favorite);


        tabPath= getIntent().getStringExtra("PATH");

        //Testing Path
        tabPath = "/mnt/sdcard/drumTabs/drumTabtest_01drum tab.xml";

        File tabFile = new File(tabPath);
        Log.d("TAB_TAB", "Tab Path : " + tabPath);

        updateFavoriteBtn();

        InputStream is = null;
        try {
            is = new FileInputStream(tabFile);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Tab not found",
                    Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }

        tab = new Tab(is);
        Log.d("TAB_TAB", "Creating tab : " + tab.toString());

        dtv.setTabWidth(tab.getMeasures());
        infoTv.setText(String.format("%s - %s", tab.getArtistName(), tab.getSongName()));

        generateShapes();
    }

    private void generateShapes(){
        int c = 0;
        int vertical_position = 0;
        int horizontal_position = 5;
        for(Note note : tab.getPartition()){
            if (note.isChord()){
                horizontal_position -= 2*note.getDuration()/tab.getDivisions();
            }
            switch(note.getValue()){
                case 36:
                    c = Color.BLUE;
                    vertical_position = 3;
                    break;
                case 38:
                    c = Color.RED;
                    vertical_position = 2;
                    break;
                case 42:
                    c = Color.YELLOW;
                    vertical_position = 1;
                    break;
                case 46:
                    c = Color.GREEN;
                    vertical_position = 1;
                    break;
                case 49:
                    c = Color.MAGENTA;
                    vertical_position = 0;
                    break;
            }
            TabActivity.shapes.add(new Shape(vertical_position, horizontal_position, 100, 100, c));
            horizontal_position += 2*note.getDuration()/tab.getDivisions();
        }
    }

    public void playTab(View view) {
        int duration = (int)(((float)(1000*60*tab.getMeasures()*4+(10+16))) / (float)tab.getBpm());
        Log.d("TAB_ANIMATION", "Total Duration : " + String.valueOf(duration));
        this.animator = ObjectAnimator.ofInt(hsw, "scrollX",dtv.getWidth()-hsw.getWidth());
        this.animator.setInterpolator(new LinearInterpolator());
        Log.d("TAB_ANIMATION", "Starting Auto-Scrolling, total duration : " + String.valueOf(duration/1000));
        this.animator.setDuration(duration);
        this.animator.start();
    }

    public void previousTab(View view) {
        if (animator != null)
            this.animator.cancel();
        hsw.scrollTo(0,0);
    }

    public void finishTab(View view) {
        if (animator != null)
            this.animator.cancel();
        Log.d("TAB_ACTIVITY", "Tab is finished");
        this.finish();
    }

    public void updateFavoriteBtn(){
        if(tabPath.charAt(0)=='/') {
            addToFavoriteBtn.setImageResource(R.drawable.button_pressed);
        }else{
            addToFavoriteBtn.setImageResource(R.drawable.button_normal);
        }
    }

    public void addToFavorite(View view) {
    }
}