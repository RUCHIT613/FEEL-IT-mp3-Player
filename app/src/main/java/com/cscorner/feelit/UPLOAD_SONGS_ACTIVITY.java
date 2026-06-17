package com.cscorner.feelit;

import static android.content.ContentValues.TAG;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.FIRST_CHECK_PLAYLIST_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_playlists;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_recently_added_playlist;

import static java.io.File.createTempFile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UPLOAD_SONGS_ACTIVITY extends AppCompatActivity {
    RecyclerView recyclerView;
    Playlist_recycler_item_Adapter_class Adapter_For_All_Playlist;
    LinearLayoutManager layoutManager;
    public static ArrayList<Recently_added_recyclerview_elements_item_class> Upload_arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_songs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ArrayList<Playlists_recycler_item_class> arrayList=new ArrayList<>();
        for(Playlists_recycler_item_class i : arrayList_for_all_playlists){
            arrayList.add(i);
        }
        recyclerView=findViewById(R.id.recyclerview_for_upload_songs);
        recyclerView.setHasFixedSize(true);
        Adapter_For_All_Playlist=new Playlist_recycler_item_Adapter_class(arrayList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(Adapter_For_All_Playlist);

        recyclerView.setLayoutManager(layoutManager);
        Context context=getApplicationContext();
        Adapter_For_All_Playlist.set_ON_CLICK_Listener(new Playlist_recycler_item_Adapter_class.onCLICK_Listener() {
            @Override
            public void on_ITEM_click(int Position) throws IOException {
                String PLAYLIST_NAME= arrayList.get(Position).getMPlaylist_name();
                SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                boolean should_i_load_user_created_playlist = preferences.getBoolean(PLAYLIST_NAME, false);
                if(PLAYLIST_NAME.equals("Recently Added")){
                    Upload_arrayList=arrayList_for_recently_added_playlist;
                }else{
                    if (should_i_load_user_created_playlist) {
//            arrayList_for_user_created_playlist=
                        if(preferences.getBoolean(FIRST_CHECK_PLAYLIST_KEY+PLAYLIST_NAME,false)){
                            Upload_arrayList = Update_User_Created_Playlist.check_by_song_name(arrayList_for_recently_added_playlist, save_and_load_array.load_array_for_user_created_playlist(getApplicationContext(), PLAYLIST_NAME),PLAYLIST_NAME,getApplicationContext());
                            editor.putBoolean(FIRST_CHECK_PLAYLIST_KEY+PLAYLIST_NAME,false);
                            editor.apply();
                            Log.d("FIRST_OR_NOT", "FIRST TIME");
                        }else{
                            Upload_arrayList=Update_User_Created_Playlist.get_updated_user_created_array_list(arrayList_for_recently_added_playlist, save_and_load_array.load_array_for_user_created_playlist(getApplicationContext(), PLAYLIST_NAME));
                            Log.d("FIRST_OR_NOT", "NOT FIRST TIME");
                        }


                    } else {
                        Toast.makeText(UPLOAD_SONGS_ACTIVITY.this, "PLAYLIST IS EMPTY", Toast.LENGTH_SHORT).show();
                        Upload_arrayList = new ArrayList<>();




                    }
                }

                startActivity(new Intent(getApplicationContext(), UPLOAD_USER_CREATED_PLAYLIST.class));

            }

            @Override
            public void more_on_ITEM_click(View view, int Position) throws IOException {

            }
        });



    }

}