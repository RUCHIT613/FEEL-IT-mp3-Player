package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_artist_interface;

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

import java.io.IOException;


public class All_Artist_Fragment extends Fragment {

    public RecyclerView recyclerView;
    public all_album_interface_and_all_artist_interface_recyclerview_adapter Adapter_for_All_Artist_Interface;
    public RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all__artist_, container, false);
        // Inflate the layout for this fragment
        recyclerView=view.findViewById(R.id.Recyclerview_of_all_artist_interface);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        Adapter_for_All_Artist_Interface=new all_album_interface_and_all_artist_interface_recyclerview_adapter(arrayList_for_all_artist_interface);
        recyclerView.setAdapter(Adapter_for_All_Artist_Interface);
        recyclerView.setLayoutManager(layoutManager);
        Adapter_for_All_Artist_Interface.SET_ON_CLICKED_LISTENER(new all_album_interface_and_all_artist_interface_recyclerview_adapter.ON_CLICKED_LISTENER() {
            @Override
            public void ON_ITEM_CLICKED(long ALBUM_ART, String ALBUM_NAME, int TOTAL_ALBUM_SONGS, int ALBUM_POSITION) throws IOException {
                SharedPreferences preferences= getActivity().getSharedPreferences("preff", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("ACTION","ARTIST_PLAYLIST_ON_CLICKED");
                editor.putLong("ARTIST_PLAYLIST_ON_CLICKED_ALBUM_ART",ALBUM_ART);
                editor.putString("ARTIST_PLAYLIST_ON_CLICKED_ARTIST_NAME",ALBUM_NAME);
                editor.putInt("ARTIST_PLAYLIST_ON_CLICKED_TOTAL_SONGS",TOTAL_ALBUM_SONGS);
                editor.putInt("ARTIST_PLAYLIST_ON_CLICKED_POSITION",ALBUM_POSITION);
                editor.apply();
            }
        });
        return view;
    }
}