package com.vlevieux.drumtab;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

class Tab {

    private int id = 1;
    private String songName = "Hell Song";
    private String artistName = "Sum 41";
    private int bpm = 120;
    private List<List<String>> partition;
    private JSONObject jTab;

    Tab() {
        {
            try {
                jTab = new JSONObject("{\"songName\":\"Hell Song\",\"artistName\":\"Sum 41\",\"bpm\":120,\"partition\":[{\"s\":\"|--x--x--x--x|\"},{\"k\":\"|x--x--x--x--|\" }]}");
                songName = jTab.getString("songName");
                artistName = jTab.getString("artistName");
                bpm = jTab.getInt("bpm");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("TAB_INFOS", "Song Name : " + songName + " "
            + "ArtistName : " + artistName + " "
            + "BPM : " + bpm);
    }
}
