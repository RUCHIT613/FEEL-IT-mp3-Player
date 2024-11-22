package com.cscorner.feelit;

import java.util.ArrayList;

public class check_whether_song_or_playlist_already_exists {

    public static boolean check_the_playlist(ArrayList<Playlists_recycler_item_class> arrayList_for_all_playlist,String PLAYLIST_NAME){
        boolean permission=true;
        for(int i=0;i<arrayList_for_all_playlist.size();i++){

            Playlists_recycler_item_class current_item_of_All_playlist=arrayList_for_all_playlist.get(i);
            if(PLAYLIST_NAME.equals(current_item_of_All_playlist.getMPlaylist_name())){
                permission=false;
                break;
            }
        }
        return permission;

    }
    public static boolean check_the_song(ArrayList<Recently_added_recyclerview_elements_item_class>arrayList_for_adding_song,String SONG_NAME,String SONG_PATH){
        boolean permission=true;
        for(int i=0;i<arrayList_for_adding_song.size();i++){
            Recently_added_recyclerview_elements_item_class current_item=arrayList_for_adding_song.get(i);
            if(SONG_NAME.equals(current_item.getMsong_name()) && SONG_PATH.equals(current_item.getMpath())){
                permission=false;
                break;
            }
        }

        return permission;

    }
}
