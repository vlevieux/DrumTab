package com.vlevieux.drumtab;

import android.support.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class Tab {

    private int id = 1;
    private String songName = "";
    private String artistName = "";
    private int bpm = 120;
    private int measures = 0;
    private int divisions = 960;
    private ArrayList<Note> partition = new ArrayList<>();

    Tab(InputStream is) {
        readXml(is);
    }

    /**
     * Read XML file and store info in Tab's field and all notes in partition
     * @param is InputSteam of the XML File
     */
    private void readXml(InputStream is){
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();
            parser.setInput(is, null);

            //Read all the document
            while (parser.next() != XmlPullParser.END_DOCUMENT){
                if (parser.getEventType() == XmlPullParser.START_TAG){
                    switch (parser.getName()) {
                        case "work-title":
                            setSongName(parser.nextText());
                            break;
                        case "creator":
                            setArtistName(parser.nextText());
                            break;
                        case "divisions":
                            setDivisions(Integer.valueOf(parser.nextText()));
                            break;
                        case "measure":
                            setMeasures(Integer.valueOf(parser.getAttributeValue(null, "number")));
                            break;
                        case "sound":
                            setBpm(Integer.valueOf(parser.getAttributeValue(null, "tempo")));
                            break;
                        case "note":
                            partition.add(readNote(parser));
                            break;
                    }
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read note from XML parser
     * @param parser XML parser reading on <note> START TAG
     * @throws IOException parser.next() could raise IOException
     * @throws XmlPullParserException parser is not on <note> START TAG.
     */
    private Note readNote(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "note");
        Note note = new Note();
        while (parser.next()!=XmlPullParser.END_DOCUMENT){
            //Break loop when </note> is met
            if (parser.getEventType()==XmlPullParser.END_TAG){
                if (parser.getName()!=null){
                    if (parser.getName().equals("note")){
                        break;
                    }
                }
            }
            if (parser.getEventType()==XmlPullParser.START_TAG) {
                switch (parser.getName()) {
                    case "fret":
                        note.setValue(Integer.valueOf(parser.nextText()));
                        break;
                    case "duration":
                        note.setDuration(Integer.valueOf(parser.nextText()));
                        break;
                    case "chord":
                        note.setChord();
                        break;
                }
            }
        }
        return note;
    }

    /**
     * Return the ID of the tab
     * @return ID of the tab
     */
    public int getId(){
        return this.id;
    }

    /**
     * Return the name of the song
     * @return name of the song
     */
    public String getSongName(){return this.songName;}

    /**
     * Return the artist's name
     * @return name of the artist
     */
    public String getArtistName(){return this.artistName;}

    /**
     * Return BPM
     * @return beats per minute
     */
    public int getBpm(){return this.bpm;}

    /**
     * Return note at position
     * @param pos position of the note in partition
     * @return return Note at position in partition
     */
    public Note getNote(int pos){return this.partition.get(pos);}

    /**
     * Set Id of tab
     * @param id id of the tab (must be positive)
     */
    public void setId(int id){
        if (id>0)
            this.id = id;
    }

    /**
     * Set song's name
     * @param songName song's name (must be non null)
     */
    void setSongName(String songName){
        if (songName.length()>0)
            this.songName = songName;
    }

    /**
     * Set the artist's name
     * @param artistName artist's name (must be non null)
     */
    void setArtistName(String artistName){
        if (artistName.length()>0)
            this.artistName = artistName;
    }

    /**
     * Set BPM
     * @param bpm BPM (must be positive)
     */
    public void setBpm(int bpm){
        if (bpm>0)
            this.bpm = bpm;
    }

    /**
     * Set # of measure
     * @param number # of measure (must be positive)
     */
    public void setMeasures(int number) {
        if (number > 0){
            this.measures = number;
        }
    }

    /**
     * Get # of measure
     * @return # of measure
     */
    public int getMeasures(){
        return this.measures;
    }

    /**
     * Set Divisions Size
     * @param divisions Divisions
     */
    public void setDivisions(int divisions){
        this.divisions = divisions;
    }

    /**
     * Get Devisions Size
     * @return Divisions Size
     */
    public int getDivisions(){
        return this.divisions;
    }

    /**
     * Get Partition
     * @return partition
     */
    public ArrayList<Note> getPartition(){
        return this.partition;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tab("+this.getId()+","+this.getSongName()+","+this.getArtistName()+","+this.getBpm()+","+this.getMeasures()+","+this.getDivisions()+")";
    }
}

class Note{

    private int value;
    private int duration;
    private boolean chord;

    private final int[] possibleValue = {36,38,42,46,49};

    Note(){}

    Note(int value, int duration, boolean chord){
        this.value = value;
        this.duration = duration;
        this.chord = chord;
    }

    /**
     * Set note's value
     * @param value note's value (must be in possibleValue list)
     */
    public void setValue(int value){
        if (ArrayUtils.contains(possibleValue, value))
            this.value = value;
    }

    /**
     * Set note's duration
     * @param duration note's duration (must be positive)
     */
    void setDuration(int duration){
        if (duration>0)
            this.duration = duration;
    }

    /**
     * Set in a chord
     */
    void setChord(){
        this.chord = true;
    }

    /**
     * Return note's value
     * @return note's value
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Return note's duration
     * @return note's duration
     */
    public int getDuration(){
        return this.duration;
    }

    /**
     * Return if is note is in a chord
     * @return true if in chord else false
     */
    public boolean isChord() {
        return this.chord;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note("+this.getValue()+","+this.getDuration()+","+this.isChord()+")";
    }
}