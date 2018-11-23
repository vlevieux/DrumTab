package com.vlevieux.drumtab;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        DynamicTabView dtv = findViewById(R.id.tab_vw_dynamic_tab);
        HorizontalScrollView sw = findViewById(R.id.tab_hsw_tab);
        //DynamicTabView draw = new DynamicTabView(this);
        //final HorizontalScrollView scrollView = new HorizontalScrollView(this);
        //scrollView.addView(draw);
        //setContentView(scrollView);
        ObjectAnimator animator=ObjectAnimator.ofInt(sw, "scrollX",30000 );
        Log.d("TAB_DEBUG", String.valueOf(dtv.getMeasuredWidth()));
        animator.setDuration(30000);
        animator.start();

        //Testing tab class
        InputStream is = new ByteArrayInputStream( "<score-partwise><work><work-title>Test 01</work-title></work><identification><creator type=\"composer\">DrumTab</creator></identification><part id=\"P1\"><measure number=\"1\"><attributes><divisions>960</divisions><time><beats>4</beats><beat-type>4</beat-type></time></attributes><direction placement=\"above\"><sound tempo=\"120\" /></direction><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><rest /><duration>960</duration><type>quarter</type></note></measure><measure number=\"2\"><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>43</fret><string>5</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>41</fret><string>5</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure></part></score-partwise>".getBytes() );
        Tab tab = new Tab(is);
        tab.setSongName("Test_01");
        tab.setSongName("Test_02");
        tab.setArtistName("Drum Tab");
        tab.setId(1);
    }
}