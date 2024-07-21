package com.cscorner.feelit;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_albums;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_artist_interface;

import java.util.ArrayList;


public class Check_Whether_Album_or_Artist_Playlist_Exists {

    public static boolean Check_Album_or_Artist_Playlist(String ALBUM_OR_ARTIST_PLAYLIST_NAME,boolean Permission_For_Album){
        ArrayList<all_album_interface_all_all_artist_interface_recyclerview_item_class> arrayList=new ArrayList<>();
        if(Permission_For_Album){
            arrayList=arrayList_for_all_albums;
        }else{
            arrayList=arrayList_for_all_artist_interface;
        }
        boolean permission=false;
        for(int i=0;i<arrayList.size();i++){
            all_album_interface_all_all_artist_interface_recyclerview_item_class CURRENT_ITEM=arrayList.get(i);
            if(CURRENT_ITEM.getMalbum_name().equals(ALBUM_OR_ARTIST_PLAYLIST_NAME)){
                permission=true;
                break;
            }
        }
        return permission;
    }
}
