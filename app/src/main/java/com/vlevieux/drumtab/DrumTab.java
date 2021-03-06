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
    private String isFavorite;

    public DrumTab(){
    }

    public DrumTab(String artistName, String songName, String xml, String tab, String isFavorite) {
        this.artistName = artistName;
        this.songName = songName;
        this.xml = xml;
        this.tab = tab;
        this.isFavorite = isFavorite;
    }


    @Override
    public int hashCode() {
        return (this.artistName.hashCode() + this.songName.hashCode()
                + this.tab.hashCode() + this.xml.hashCode() + this.drumTabId.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DrumTab){
            DrumTab temp = (DrumTab) obj;
            if(this.songName.equals(temp.songName) && this.artistName.equals(temp.artistName))
                return true;

        }
        return false;
    }

    @Override
    public String toString() {
        return "DrumTab [id=" + drumTabId + ", artist=" + artistName + ", song=" + songName
                + ", xml=" + xml + ", tab=" + tab + ",favorite=" + isFavorite + "]\n";
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
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