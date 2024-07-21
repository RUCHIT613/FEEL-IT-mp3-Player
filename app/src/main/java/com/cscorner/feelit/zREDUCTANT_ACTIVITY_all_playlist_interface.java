package com.cscorner.feelit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/*   THIS CLASS DOES NOT AFFECT THE APP AT ALL IT IS A USELESS FILE*/
public class zREDUCTANT_ACTIVITY_all_playlist_interface extends AppCompatActivity {
    public static TextView textView;
    public static final String PLAYLIST_POSTION_ID="POSITION_ID";
    private ArrayList<Playlists_recycler_item_class> arrayList_for_playlists;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Playlist_recycler_item_Adapter_class adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zreductant_activity_all_playlist_interface);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        arrayList_for_playlists=new ArrayList<>();
        load_array();
    }
    public void check_and_play() throws IOException {
//

        SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
        String path = preferences.getString("key2", "");

        Uri uri = Uri.parse(path);
        media_player.get_media_player().reset();
        MediaPlayer player=media_player.get_media_player();
        if(player.isPlaying()) {
            player.release();

        }
        player.setDataSource(getApplicationContext(),uri);
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                make_a_toast("song done");
            }
        });

    }
    public void load_array(){

        recyclerView=findViewById(R.id.All_playlist_reclclerview);
        adapter=new Playlist_recycler_item_Adapter_class(arrayList_for_playlists);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        arrayList_for_playlists.add(new Playlists_recycler_item_class(100,"Recently Added",false));
        adapter.notifyItemInserted(arrayList_for_playlists.size());
//        adapter.set_ON_CLICK_Listener(new Playlist_recycler_item_Adapter_class.onCLICK_Listener() {
//            @Override
//            public void on_ITEM_click(int Position) {
//                String PLAYLIST_NAME=arrayList_for_playlists.get(Position).getMPlaylist_name();
//                make_a_toast(String.format("PLAYLIST NAME : %s",PLAYLIST_NAME));
//                On_CLICK_PLAYLIST(PLAYLIST_NAME,Position);
//
//                try{
////                    check_and_play();
//
//                }catch (Exception e){}
//
//
//
//            }
//        });

    }
    public void On_CLICK_PLAYLIST(String playlist_name,int Position){
        if(playlist_name.equals("Recently Added")){
            Intent Recently_added_playlist_intent=new Intent(this, zREDUCTANT_ACTIVITY_Recently_added_playlist_activity.class);
            Recently_added_playlist_intent.putExtra("playlist_name",playlist_name);
            Recently_added_playlist_intent.putExtra(PLAYLIST_POSTION_ID,Position);
            Recently_added_playlist_intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(Recently_added_playlist_intent);

        }else{
            Intent USER_added_playlist_intent=new Intent(this, zREDUCTANT_ACTIVITY_User_created_playlist_activity.class);
            USER_added_playlist_intent.putExtra("playlist_name",playlist_name);
            USER_added_playlist_intent.putExtra(PLAYLIST_POSTION_ID,Position);
            startActivity(USER_added_playlist_intent);
        }
    }

    public void new_playlist_click(View view){


        arrayList_for_playlists.add(new Playlists_recycler_item_class(100,String.format("Playlist : %d",arrayList_for_playlists.size()),false));
        adapter.notifyItemInserted(arrayList_for_playlists.size());
    }
    public void make_a_toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void uu(){
        make_a_toast("I CAN ACCESS THIS");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}