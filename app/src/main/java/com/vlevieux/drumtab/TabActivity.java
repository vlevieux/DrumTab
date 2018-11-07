package com.vlevieux.drumtab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        //Testing tab class
        InputStream is = new ByteArrayInputStream( "<score-partwise><work><work-title>Test 01</work-title></work><identification><creator type=\"composer\">DrumTab</creator></identification><part id=\"P1\"><measure number=\"1\"><attributes><divisions>960</divisions><time><beats>4</beats><beat-type>4</beat-type></time></attributes><direction placement=\"above\"><sound tempo=\"120\" /></direction><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><rest /><duration>960</duration><type>quarter</type></note></measure><measure number=\"2\"><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>960</duration><type>quarter</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>960</duration><type>quarter</type><chord /></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>36</fret><string>6</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>38</fret><string>4</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note><note><notations><technical><fret>43</fret><string>5</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>42</fret><string>2</string></technical></notations><duration>480</duration><type>eighth</type></note><note><notations><technical><fret>41</fret><string>5</string></technical></notations><duration>480</duration><type>eighth</type><chord /></note></measure></part></score-partwise>".getBytes() );
        Tab tab = new Tab(is);
        tab.setSongName("Test_01");
        tab.setArtistName("Drum Tab");
        tab.setId(1);
    }
}
