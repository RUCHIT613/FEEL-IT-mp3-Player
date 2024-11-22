package com.cscorner.feelit;

import java.util.ArrayList;

public class Update_User_Created_Playlist {

    public static ArrayList<Recently_added_recyclerview_elements_item_class> get_updated_user_created_array_list(ArrayList<Recently_added_recyclerview_elements_item_class> recently_added_playlist,ArrayList<Recently_added_recyclerview_elements_item_class> old_user_created_playlist){
        for(int  i=0;i<old_user_created_playlist.size();i++){

            boolean should_the_song_path_be_checked=true;

            Recently_added_recyclerview_elements_item_class Current_Item_of_old_user_created_playlist=old_user_created_playlist.get(i);
            for(int j=0;j<recently_added_playlist.size();j++){
                Recently_added_recyclerview_elements_item_class Current_item_of_recently_added_playlist=recently_added_playlist.get(j);

                if(Current_Item_of_old_user_created_playlist.getMpath()
                        .equals(Current_item_of_recently_added_playlist.getMpath()) ){

                        old_user_created_playlist.set(i,new Recently_added_recyclerview_elements_item_class(
                                Current_item_of_recently_added_playlist.getMsong_name(),
                                Current_item_of_recently_added_playlist.getMpath(),
                                Current_item_of_recently_added_playlist.getMartist(),
                                Current_item_of_recently_added_playlist.getMalbum_name(),
                                Current_item_of_recently_added_playlist.getMduration(),
                                Current_item_of_recently_added_playlist.getMalbum_art(),false));
                        should_the_song_path_be_checked=false;
                        break;


//

                }


            }
            if(should_the_song_path_be_checked){
                old_user_created_playlist.remove(i);

//                boolean should_song_be_remove=false;
//                for(int k=0;k<recently_added_playlist.size();k++){
//                    Recently_added_recyclerview_elements_item_class Current_item_of_recently_added_playlist=recently_added_playlist.get(k);
//                    should_song_be_remove=true;
//                    if(Current_Item_of_old_user_created_playlist.getMpath().equals(Current_item_of_recently_added_playlist.getMpath())){
//                        old_user_created_playlist.set(i,new Recently_added_recyclerview_elements_item_class(
//                                Current_item_of_recently_added_playlist.getMsong_name(),
//                                Current_item_of_recently_added_playlist.getMpath(),
//                                Current_item_of_recently_added_playlist.getMartist(),
//                                Current_item_of_recently_added_playlist.getMalbum_name(),
//                                Current_item_of_recently_added_playlist.getMduration(),
//                                Current_item_of_recently_added_playlist.getMalbum_art(),false));
//                        should_song_be_remove=false;
//                        break;
//                    }
//
//                }
//                if(should_song_be_remove){
//
//
//                }

            }

        }

        return old_user_created_playlist;
    }
}
