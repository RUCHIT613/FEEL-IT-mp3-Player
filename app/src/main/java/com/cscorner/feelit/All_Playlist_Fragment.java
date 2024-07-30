package com.cscorner.feelit;


import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_playlists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;


public class All_Playlist_Fragment extends Fragment {

//    public SharedPreferences.Editor editor=preferences.edit();

    public RecyclerView recyclerView;
    public Playlist_recycler_item_Adapter_class Adapter_For_All_Playlist;
    public RecyclerView.LayoutManager layoutManager;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all__playlist_,container,false);
        recyclerView=view.findViewById(R.id.All_playlist_recyclerview_all);
        recyclerView.setHasFixedSize(true);
        Adapter_For_All_Playlist=new Playlist_recycler_item_Adapter_class(arrayList_for_all_playlists);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setAdapter(Adapter_For_All_Playlist);

        recyclerView.setLayoutManager(layoutManager);
        Adapter_For_All_Playlist.set_ON_CLICK_Listener(new Playlist_recycler_item_Adapter_class.onCLICK_Listener() {
            @Override
            public void on_ITEM_click(int Position) throws IOException {
//                String PLAYLIST_NAME = arrayList_for_all_playlists.get(Position).getMPlaylist_name();
//                if(!PLAYLIST_NAME.equals("Favourite")){
////                    musicPlayerActivity.make_a_toast(String.format("PLAYLIST NAME : %s", PLAYLIST_NAME));
//                }
//                MUSIC_PLAYER_ACTIVITY musicPlayerActivity=new MUSIC_PLAYER_ACTIVITY();

//                musicPlayerActivity.On_CLICK_PLAYLIST(Position);
                SharedPreferences preferences= getContext().getSharedPreferences("preff",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString("ACTION","PLAYLIST_ON_CLICKED");
                editor.putInt("PLAYLIST_ON_CLICKED",Position);
                editor.apply();
            }

            @Override
            public void more_on_ITEM_click(View View, int Position) throws IOException {


                PopupMenu popupMenu = new PopupMenu(View.getContext(), View, Gravity.END);
                if(Position==0 || Position==arrayList_for_all_playlists.size()-1){
                    popupMenu.inflate(R.menu.pop_up_menu_for_recently_added_and_favourite);

                }else{
                    popupMenu.inflate(R.menu.pop_all_playlist_menu);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        SharedPreferences preferences = getContext().getSharedPreferences("preff",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= preferences.edit();
                        if (item.getItemId() == R.id.delete_playlist_pop_up_menu_of_all_playlists_interface) {
                            editor.putString("ACTION","DELETE_PLAYLIST");
                            editor.putInt("DELETE_PLAYLIST",Position);
                            editor.apply();
                            return true;
                        } else if (item.getItemId()==R.id.play_the_playlist_pop_up_menu_of_all_playlist_interface) {
                            editor.putString("ACTION","PLAY_PLAYLIST");
                            editor.putInt("PLAY_PLAYLIST",Position);
                            editor.apply();
                        }


                        return false;
                    }
                });
                popupMenu.show();
//

            }
        });

        return view;
    }
    public void NOTIFY_PLAYLIST_INSERTED(int position){
        Adapter_For_All_Playlist.notifyItemInserted(position);
    }
    public void NOTIFY_PLAYLIST_REMOVED(int position){
        Adapter_For_All_Playlist.notifyItemRemoved(position);
    }

}