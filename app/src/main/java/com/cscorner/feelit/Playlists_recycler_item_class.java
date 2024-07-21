package com.cscorner.feelit;

public class Playlists_recycler_item_class {
    private String MPlaylist_name;
    private long MPlaylist_image_album_art;
    private boolean Mis_selected;
    public Playlists_recycler_item_class(long Album_art,String Playlist_name,boolean is_selected){
        MPlaylist_name=Playlist_name;
        MPlaylist_image_album_art=Album_art;
        Mis_selected=is_selected;
    }

    public String getMPlaylist_name() {
        return MPlaylist_name;
    }

    public long getMPlaylist_image_album_art() {
        return MPlaylist_image_album_art;
    }

    public boolean isMis_selected() {
        return Mis_selected;
    }

    public void setMis_selected(boolean mis_selected) {
        Mis_selected = mis_selected;
    }
}
