package com.cscorner.feelit;

import java.util.ArrayList;

public class load_songs_of_given_album_or_artist {
    public static ArrayList<Recently_added_recyclerview_elements_item_class> LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(ArrayList<Recently_added_recyclerview_elements_item_class> RECENTLY_ADDED_PLAYLIST, String ALBUM_NAME_OR_ARTIST_NAME, boolean permission_for_album){
        ArrayList<Recently_added_recyclerview_elements_item_class> specific_album_playlist=new ArrayList<>();
        for(int i=0;i<RECENTLY_ADDED_PLAYLIST.size();i++){
            Recently_added_recyclerview_elements_item_class CURRENT_ITEM=RECENTLY_ADDED_PLAYLIST.get(i);
            if(permission_for_album){
                if(ALBUM_NAME_OR_ARTIST_NAME.equals(CURRENT_ITEM.getMalbum_name())){
                    specific_album_playlist.add(new Recently_added_recyclerview_elements_item_class(
                            CURRENT_ITEM.getMsong_name(),
                            CURRENT_ITEM.getMpath(),
                            CURRENT_ITEM.getMartist(),
                            CURRENT_ITEM.getMalbum_name(),
                            CURRENT_ITEM.getMduration(),
                            CURRENT_ITEM.getMalbum_art(),false));
                }
            }
            else {
                if(ALBUM_NAME_OR_ARTIST_NAME.equals(CURRENT_ITEM.getMartist())){
                    specific_album_playlist.add(new Recently_added_recyclerview_elements_item_class(
                            CURRENT_ITEM.getMsong_name(),
                            CURRENT_ITEM.getMpath(),
                            CURRENT_ITEM.getMartist(),
                            CURRENT_ITEM.getMalbum_name(),
                            CURRENT_ITEM.getMduration(),
                            CURRENT_ITEM.getMalbum_art(),false));
                }
            }

        }
        return specific_album_playlist;
    }

}
