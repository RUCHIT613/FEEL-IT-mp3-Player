package com.cscorner.feelit;

public class all_album_interface_all_all_artist_interface_recyclerview_item_class {
    private long malbum_art;
    private String malbum_name;
    private int mtotal_songs;



    public all_album_interface_all_all_artist_interface_recyclerview_item_class(long ALBUM_ART, String ALBUM_NAME, int TOTAL_SONGS){
        malbum_art=ALBUM_ART;
        malbum_name=ALBUM_NAME;
        mtotal_songs=TOTAL_SONGS;


    }
    public long getMalbum_art() {
        return malbum_art;
    }

    public String getMalbum_name() {
        return malbum_name;
    }

    public int getMtotal_songs() {
        return mtotal_songs;
    }
}
