package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_albums;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;


public class All_Album_Fragment extends Fragment {


    public RecyclerView recyclerView;
    public all_album_interface_and_all_artist_interface_recyclerview_adapter Adapter_For_Album_Interface;
    public RecyclerView.LayoutManager layoutManager;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all__album_, container, false);
        recyclerView=view.findViewById(R.id.all_album_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        Adapter_For_Album_Interface=new all_album_interface_and_all_artist_interface_recyclerview_adapter(arrayList_for_all_albums);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setAdapter(Adapter_For_Album_Interface);
        recyclerView.setLayoutManager(layoutManager);
        Adapter_For_Album_Interface.SET_ON_CLICKED_LISTENER(new all_album_interface_and_all_artist_interface_recyclerview_adapter.ON_CLICKED_LISTENER() {
            @Override
            public void ON_ITEM_CLICKED(long ALBUM_ART, String ALBUM_NAME, int TOTAL_ALBUM_SONGS, int ALBUM_POSITION) {
                SharedPreferences preferences = getContext().getSharedPreferences("preff", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=preferences.edit();

                editor.putString("ACTION","ALBUM_PLAYLIST_ON_CLICKED");
                editor.putLong("ALBUM_PLAYLIST_ON_CLICKED_ALBUM_ART",ALBUM_ART);
                editor.putString("ALBUM_PLAYLIST_ON_CLICKED_ALBUM_NAME",ALBUM_NAME);
                editor.putInt("ALBUM_PLAYLIST_ON_CLICKED_TOTAL_SONGS",TOTAL_ALBUM_SONGS);
                editor.putInt("ALBUM_PLAYLIST_ON_CLICKED_ALBUM_POSITION",ALBUM_POSITION);
                editor.apply();
//                Toast.makeText(getContext(), String.format("%d",preferences.getLong("ALBUM_PLAYLIST_ON_CLICKED_LONG",10)), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), String.format("%s",ALBUM_NAME), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    public void UPDATE_ALL_ALBUM_INTERFACE_PLAYLIST(){  //UPDATE THE ALL ALBUM INTERFACE ARRAYLIST WHEN func onResume GET CALLED

    }
}