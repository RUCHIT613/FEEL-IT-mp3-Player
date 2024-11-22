package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_ACTIVATE_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.PLAY_NEXT_AND_ADD_TO_QUEUE_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.PLAY_NEXT_INDEX;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.PLAY_NEXT_SONG;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_song_interface;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.current_song_index;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.is_add_to_queue_active;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.is_play_next_active;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.temp_array_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;


public class All_Song_Fragment extends Fragment {

    public RecyclerView recyclerView;
    public recently_added_adapter_class Adapter_For_All_Song_Interface;
    public RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all_song_fragment, container, false);
        recyclerView=view.findViewById(R.id.all_songs_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        Adapter_For_All_Song_Interface=new recently_added_adapter_class(arrayList_for_all_song_interface);
        recyclerView.setAdapter(Adapter_For_All_Song_Interface);
        recyclerView.setLayoutManager(layoutManager);
        Adapter_For_All_Song_Interface.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) throws Exception {
                SharedPreferences preferences= getContext().getSharedPreferences("preff", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("ACTION","ALL_SONG_INTERFACE");
                editor.putInt("ALL_SONG_INTERFACE",position);
                editor.apply();

            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                SharedPreferences preferences = getContext().getSharedPreferences("preff",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.pop_menu_for_recently_added_songs_artist_album);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_item_1) {
                            SharedPreferences preferences = getContext().getSharedPreferences("preff",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("ACTION","ADD_SONG_FROM_ALL_SONG_INTERFACE_MORE");
                            editor.putInt("ADD_SONG_FROM_ALL_SONG_INTERFACE_MORE",position);
                            editor.apply();
//
                            return true;
                        }else if(item.getItemId()==R.id.add_to_queue&&preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)){

                            Recently_added_recyclerview_elements_item_class CURRENT=arrayList_for_all_song_interface.get(position);
                            temp_array_list.add(new Recently_added_recyclerview_elements_item_class(
                                    CURRENT.getMsong_name(),
                                    CURRENT.getMpath(),
                                    CURRENT.getMartist(),
                                    CURRENT.getMalbum_name(),
                                    CURRENT.getMduration(),
                                    CURRENT.getMalbum_art(),
                                            false)
                                    );
                            is_add_to_queue_active=true;
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
                            save_and_load_array.save_array_for_user_created_playlist(getContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            return true;
                        }else if((item.getItemId()==R.id.play_next)&&(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false))){

                            is_add_to_queue_active=true;

                            Recently_added_recyclerview_elements_item_class Current=arrayList_for_all_song_interface.get(position);
//                            make_a_toast(String.format("PNSong Name : %s",Current.getMsong_name()),true);
                            if(is_play_next_active){
                                if(PLAY_NEXT_SONG.equals(temp_array_list.get(current_song_index).getMsong_name())){
                                    PLAY_NEXT_INDEX+=1;
//                                    make_a_toast("SONG IS SAME",true);
                                    Toast.makeText(getContext(), "SONG is Same", Toast.LENGTH_SHORT).show();

                                }else{
                                    PLAY_NEXT_INDEX=current_song_index+1;
                                    PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
//                                    make_a_toast("SONG Changed",true);
                                    Toast.makeText(getContext(), "song changed", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                PLAY_NEXT_INDEX=current_song_index+1;
                                is_play_next_active=true;
                            }
                            temp_array_list.add(PLAY_NEXT_INDEX,new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
                            save_and_load_array.save_array_for_user_created_playlist(getContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
//                            load_data_into_array_list_for_recently_added();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

            @Override
            public void on_ITEM_LONG_CLICKED(int Long_pressed_song) {

            }
        });

        return view;
    }

}