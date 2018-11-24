package com.vlevieux.drumtab;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    DynamicTabView dtv;
    HorizontalScrollView hsw;
    ObjectAnimator animator;

    public static List<Shape> shapes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        dtv = findViewById(R.id.tab_vw_dynamic_tab);
        hsw = findViewById(R.id.tab_hsw_tab);

        //Testing tab class
        InputStream is = new ByteArrayInputStream( "<score-partwise><work><work-title /></work><identification><creator type=\"composer\" /></identification><part id=\"P1\"><measure number=\"1\"><attributes><divisions>960</divisions><time><beats>4</beats><beat-type>4</beat-type></time></attributes><direction placement=\"above\"><sound tempo=\"120\" /></direction><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>3</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>38</fret><string>5</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>3</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>3</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>5</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>49</fret><string>3</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note></measure></part></score-partwise>".getBytes() );
        Tab tab = new Tab(is);

        Log.d("TAB_ACTIVITY", tab.toString());

        int c = 0;
        int i = 0;
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

            Log.d("TAB_SHAPE_DEBUG", String.valueOf(horizontal_position));
            TabActivity.shapes.add(new Shape(vertical_position, horizontal_position, 100, 100, c));
            i++;
            horizontal_position += 2*note.getDuration()/tab.getDivisions();

        }



        tab.setSongName("Test_01");
        tab.setSongName("Test_02");
        tab.setArtistName("Drum Tab");
        tab.setId(1);

    }

    public void playTab(View view) {
        // TODO: Calculate duration
        int duration = 30000;
        this.animator = ObjectAnimator.ofInt(hsw, "scrollX",duration );
        Log.d("TAB_ANIMATION", "Starting Auto-Scrollig, total duration : " + String.valueOf(duration/1000));
        this.animator.setDuration(duration);
        this.animator.start();
    }

    public void previousTab(View view) {
        this.animator.cancel();
        hsw.scrollTo(0,0);
    }
}