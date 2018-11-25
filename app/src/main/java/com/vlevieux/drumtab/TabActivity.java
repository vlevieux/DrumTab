package com.vlevieux.drumtab;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    DynamicTabView dtv;
    HorizontalScrollView hsw;
    TextView infoTv;
    ObjectAnimator animator;

    private Tab tab;

    public static List<Shape> shapes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        dtv = findViewById(R.id.tab_vw_dynamic_tab);
        hsw = findViewById(R.id.tab_hsw_tab);
        infoTv = findViewById(R.id.tab_tv_song_info);

        //Testing tab class
        InputStream is = new ByteArrayInputStream( "<score-partwise><work><work-title>HighWay To Hell</work-title></work><identification><creator type=\"composer\">AC/DC</creator></identification><part id=\"P1\"><measure number=\"1\"><attributes><divisions>960</divisions><time><beats>4</beats><beat-type>4</beat-type></time></attributes><direction placement=\"above\"><sound tempo=\"116\" /></direction><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type></note></measure><measure number=\"2\"><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note></measure><measure number=\"3\"><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note></measure><measure number=\"4\"><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure><measure number=\"5\"><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure><measure number=\"6\"><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure><measure number=\"7\"><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>7</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure></part></score-partwise>".getBytes() );


        tab = new Tab(is);
        Log.d("TAB_TAB", "Creating tab : " + tab.toString());

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

    public void addToFavorite(View view) {
    }
}