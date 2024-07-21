package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_song_interface;

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
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.all_songs_interface_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_item_1_for_all_songs_interface) {
                            SharedPreferences preferences = getContext().getSharedPreferences("preff",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("ACTION","ADD_SONG_FROM_ALL_SONG_INTERFACE_MORE");
                            editor.putInt("ADD_SONG_FROM_ALL_SONG_INTERFACE_MORE",position);
                            editor.apply();
//
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