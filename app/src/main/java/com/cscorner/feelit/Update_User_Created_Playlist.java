package com.cscorner.feelit;

import static android.content.Context.MODE_PRIVATE;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MISSING_SONGS_PLAYLIST_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.PERMISSION_FOR_MISSING_SONGS_PLAYLIST_KEY;
import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class Update_User_Created_Playlist {

    public static ArrayList<Recently_added_recyclerview_elements_item_class> get_updated_user_created_array_list(ArrayList<Recently_added_recyclerview_elements_item_class> recently_added_playlist,ArrayList<Recently_added_recyclerview_elements_item_class> old_user_created_playlist){

        Log.d("OLD PLAYLIST",String.format("%d",old_user_created_playlist.size()));
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
        Log.d("SIZE OF UPDATED ARRAY",String.format("%d",old_user_created_playlist.size())  );

        return old_user_created_playlist;
    }
    public static boolean permission_to_display_song_in_log=true;
    public static String MISSING_SONG="";
    public static ArrayList<Recently_added_recyclerview_elements_item_class> check_by_song_name(ArrayList<Recently_added_recyclerview_elements_item_class> recently_added,ArrayList<Recently_added_recyclerview_elements_item_class> user_arraylist,String PLAYLIST_NAME,Context context){
        ArrayList<Recently_added_recyclerview_elements_item_class> arrayList=new ArrayList<>();
        ArrayList<Recently_added_recyclerview_elements_item_class> arrayList1=new ArrayList<>();

        for(int i=0;i<user_arraylist.size();i++){
            Recently_added_recyclerview_elements_item_class CURRENT1=user_arraylist.get(i);
            for(int j=0;j<recently_added.size();j++){
                Recently_added_recyclerview_elements_item_class CURRENT2=recently_added.get(j);
                permission_to_display_song_in_log=true;
                MISSING_SONG= CURRENT1.getMsong_name();
                if(CURRENT1.getMsong_name().equals(CURRENT2.getMsong_name())){
                    arrayList.add(CURRENT2);
                    permission_to_display_song_in_log=false;
                    break;

                }
            }
            if(permission_to_display_song_in_log){
                arrayList1.add(CURRENT1);
//                Log.d("MISSING SONG",String.format("%s - %s",MISSING_SONG,CURRENT1.getMartist()));
            }
        }
        arrayList1=load_all_songs_of_all_songs_interface_in_ascending.load_songs_artist_in_ascending(arrayList1);
        for(int i=0; i<arrayList1.size();i++){
            Recently_added_recyclerview_elements_item_class MISSING_ARTIST=arrayList1.get(i);
            Log.d("MISSING SONG",String.format("%s - %s",MISSING_ARTIST.getMsong_name(),MISSING_ARTIST.getMartist()));
        }
        SharedPreferences sharedPreferences=context.getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        if (arrayList1.size() != 0){
            save_and_load_array.save_array_for_user_created_playlist(context,arrayList1,MISSING_SONGS_PLAYLIST_KEY+PLAYLIST_NAME);
            editor.putBoolean(PERMISSION_FOR_MISSING_SONGS_PLAYLIST_KEY+PLAYLIST_NAME,true);
            Log.d("MISSING_SONGS_DETECTED","MISSING_SONGS_DETECTED");
        }else{
            editor.putBoolean(PERMISSION_FOR_MISSING_SONGS_PLAYLIST_KEY+PLAYLIST_NAME,false);
        }
        editor.apply();

        return arrayList;
    }



}
