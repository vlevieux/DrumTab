package com.vlevieux.drumtab;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TabActivity extends AppCompatActivity {

    DynamicTabView dtv;
    HorizontalScrollView hsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        dtv = findViewById(R.id.tab_vw_dynamic_tab);
        hsw = findViewById(R.id.tab_hsw_tab);

        //Testing tab class
        InputStream is = new ByteArrayInputStream( "<score-partwise><work><work-title>Test 01</work-title></work><identification><creator type=\"composer\">DrumTab</creator></identification><part id=\"P1\"><measure number=\"1\"><attributes><divisions>960</divisions><time><beats>4</beats><beat-type>4</beat-type></time></attributes><direction placement=\"above\"><sound tempo=\"120\" /></direction><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><rest /><duration>960</duration><type>quarter</type></note></measure><measure number=\"2\"><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>43</fret><string>5</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>41</fret><string>5</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure></part></score-partwise>".getBytes() );
        Tab tab = new Tab(is);
        tab.setSongName("Test_01");
        tab.setSongName("Test_02");
        tab.setArtistName("Drum Tab");
        tab.setId(1);
    }

    public void playTab(View view) {
        // TODO: Calculate duration
        int duration = 30000;
        ObjectAnimator animator=ObjectAnimator.ofInt(hsw, "scrollX",duration );
        Log.d("TAB_ANIMATION", "Starting Auto-Scrollig, total duration : " + String.valueOf(duration/1000));
        animator.setDuration(duration);
        animator.start();
    }

    public void previousTab(View view) {
        hsw.scrollTo(0,0);
    }
}