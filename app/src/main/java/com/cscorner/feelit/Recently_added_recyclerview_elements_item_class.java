package com.cscorner.feelit;

public class Recently_added_recyclerview_elements_item_class {
    private String msong_name;
    private String mpath;
    private String martist;
    private String malbum_name;
    private int mduration;
    private long malbum_art;
    private boolean mis_selected;


    public Recently_added_recyclerview_elements_item_class(String song_name,String path,String artist,String album_name,int duration,long album_art,boolean is_selected){

        msong_name=song_name;
        mpath=path;
        martist=artist;
        malbum_name=album_name;
        mduration=duration;
        malbum_art=album_art;
        mis_selected=is_selected;
    }

    public String getMsong_name() {
        return msong_name;
    }

    public String getMpath() {
        return mpath;
    }

    public String getMartist() {
        return martist;
    }

    public String getMalbum_name() {
        return malbum_name;
    }

    public int getMduration() {
        return mduration;
    }

    public long getMalbum_art() {
        return malbum_art;
    }

    public boolean isMis_selected() {
        return mis_selected;
    }

    public void setMis_selected(boolean mis_selected) {
        this.mis_selected = mis_selected;
    }
}
