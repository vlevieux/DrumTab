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

    public DrumTab(String artistName, String songName, String xml, String tab) {
        this.artistName = artistName;
        this.songName = songName;
        this.xml = xml;
        this.tab = tab;
    }

    @Override
    public String toString() {
        return "DrumTab [id=" + drumTabId + ", artist=" + artistName + ", song=" + songName
                + ", xml=" + xml + ", tab=" + tab + "]\n";
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

    public void setDrumTabId(String drumTabId) {
        this.drumTabId = drumTabId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}