package com.vlevieux.drumtab;

/**
 * @author thomas THEBAUD, 11/6/2018
 *
 */
public class DrumTab {

    private String drumTabId;
    private String artistName;
    private String songName;
    private String xml;
    private String tab;

    public DrumTab(){
    }

    public DrumTab(String drumTabId, String artistName, String songName, String xml, String tab) {
        this.drumTabId = drumTabId;
        this.artistName = artistName;
        this.songName = songName;
        this.xml = xml;
        this.tab = tab;
    }

    public String getDrumTabId() {
        return drumTabId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongName() {
        return songName;
    }

    public String getXml() {
        return xml;
    }

    public String getTab() {
        return tab;
    }
}