package com.cscorner.feelit;

import static com.cscorner.feelit.zREDUCTANT_ACTIVITY_all_playlist_interface.PLAYLIST_POSTION_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
/*   THIS CLASS DOES NOT AFFECT THE APP AT ALL IT IS A USELESS FILE*/
public class zREDUCTANT_ACTIVITY_Recently_added_playlist_activity extends AppCompatActivity {
    private boolean was_intent_changed;
    public static final String MINIPLAYER_SONG_NAME_KEY="key_of_miniplayer_song_name";
    public static final String MINIPLAYER_ARTIST_NAME_KEY="key_of_miniplayer_artist_name";
    public static final String MINIPLAYER_ALBUM_ART_KEY="key_of_miniplayer_album_art";
    public static final String MINIPLAYER_ACTIVATE_KEY="key_of_miniplayer_activation";



    private TextView miniplayer_song_name_text_view;
    private TextView miniplayer_artist_name_text_view;
    private ImageView miniplayer_album_art_imageview;

    private  int count =0;
    private int duration;
    private int current_song_index=3;

    private RecyclerView recyclerView;
    private recently_added_adapter_class adapter;
    private RecyclerView.LayoutManager layoutManager;


    private MediaPlayer player;
    private String result="";
    private String filePath;
    private ArrayList<Recently_added_recyclerview_elements_item_class> arrayList;
    private static final int READ_EXTERNAL_STORAGE=1;
    private TextView recently_added_textview;
    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zreductant_activity_recently_added_playlist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        miniplayer_song_name_text_view=findViewById(R.id.song_name_text_view_recently_added_miniplayer);
        miniplayer_artist_name_text_view=findViewById(R.id.artist_name_text_view_recently_added_miniplayer);
        miniplayer_album_art_imageview=findViewById(R.id.album_art_image_view_recently_added_miniplayer);

        arrayList=new ArrayList<>();
        check_permission();




        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.Albums.ALBUM_ART

        };

        String selection = MediaStore.Audio.Media.DATA + " LIKE ? AND " +
                MediaStore.Audio.Media.MIME_TYPE + "=?";
        String[] selectionArgs = new String[]{"%.mp3", "audio/mpeg"};
        String sortorder=MediaStore.Audio.AudioColumns.DATE_ADDED+" DESC";

        Cursor cursor = this.getContentResolver().query(uri, projection, selection, selectionArgs,sortorder );

        if(cursor!=null){
            while (cursor.moveToNext()){
                  filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                int durationIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
                 duration= cursor.getInt(durationIndex);
                if(!isRingtone(filePath)){

                    arrayList.add(new Recently_added_recyclerview_elements_item_class(
                            cursor.getString(0),
                            filePath,
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getLong(5),false));

                }

            }
            cursor.close();


        }

        recyclerView=findViewById(R.id.recently_added_recyclerview__);
        recyclerView.setHasFixedSize(true);
        adapter=new recently_added_adapter_class(arrayList);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) {
                current_song_index=position;
                play(position);
            }

            @Override
            public void more_button_ITEM_Clicked(View view,int position) {
            }

            @Override
            public void on_ITEM_LONG_CLICKED(int Long_pressed_song) {

            }




        });
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        if(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)){
            current_song_index=preferences.getInt("key",0);
            config_miniplayer(preferences.getString(MINIPLAYER_SONG_NAME_KEY,""),preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,""),preferences.getLong(MINIPLAYER_ALBUM_ART_KEY,0));
        }
    }
    public void PAUSE_AND_PLAY(View view) throws IOException {
        pause_and_play();


    }
    public void pause_and_play(){

        count+=1;
        if(count==1){
            media_player.get_media_player().pause();
            make_a_toast("Music Is Paused");
        }
        else {
            count=0;
            media_player.get_media_player().start();
            make_a_toast("Music Is Resumed");
        }

    }
    public void play(int position) {
        try{
            Recently_added_recyclerview_elements_item_class item=arrayList.get(position);
            current_song_index=position;
            Uri uri=Uri.parse(item.getMpath());
//
//

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
                    config_miniplayer(item.getMsong_name(),item.getMartist(),item.getMalbum_art());
                    make_a_toast(String.format("NOW PLAYING\n%s",arrayList.get(position).getMsong_name()));

                    store_mini_player_data();


                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    if(current_song_index==arrayList.size()-1){
                        play(0);
                        store_mini_player_data();
                    }
                    else{
                        current_song_index+=1;
                        try {
                            make_a_toast("song_complete");
                            play(current_song_index);
                            store_miniplayer_permission_data();
                            if(was_intent_changed=true){
                                zREDUCTANT_ACTIVITY_all_playlist_interface d=new zREDUCTANT_ACTIVITY_all_playlist_interface();
                                d.uu();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });
        }catch (Exception e){}
    }
    public void config_miniplayer(String Song_NAME,String Artist_NAME,long Album_art){
        if(Song_NAME.length()>36){
            miniplayer_song_name_text_view.setText(Song_NAME.substring(0,36)+"...");
        }
        else {
            miniplayer_song_name_text_view.setText(Song_NAME);
        }
        miniplayer_artist_name_text_view.setText(Artist_NAME);
        Uri AlbumUri=Uri.parse("content://media/external/audio/albumart/" + Album_art);
        Picasso.get().load(AlbumUri).into(miniplayer_album_art_imageview);

    }
    public void back(View view){
        was_intent_changed=true;
        Intent intent=new Intent(this, zREDUCTANT_ACTIVITY_all_playlist_interface.class);
        startActivity(intent);


    }


    public void check_permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
        } else {
            // Request the permission

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }


    public boolean isRingtone(String filePath) {

        return filePath.toLowerCase().contains("ringtones");}
    public void test(){
        String playlist_name=getIntent().getStringExtra("playlist_name");
        int playlist_position=getIntent().getIntExtra(PLAYLIST_POSTION_ID,-1);
        if(playlist_position!=-1){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform your actions
            } else {
                while (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }
            }
        }
    }
    public void store_mini_player_data(){
        Recently_added_recyclerview_elements_item_class item=arrayList.get(current_song_index);
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();

        editor.putString(MINIPLAYER_SONG_NAME_KEY,item.getMsong_name());
        editor.putString(MINIPLAYER_ARTIST_NAME_KEY,item.getMartist());
        editor.putLong(MINIPLAYER_ALBUM_ART_KEY,item.getMalbum_art());
        editor.apply();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onStop() {
        super.onStop();

        was_intent_changed=true;
        store_miniplayer_permission_data();
        make_a_toast("onstop");

    }
    public void store_miniplayer_permission_data(){
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,was_intent_changed);
        editor.putInt("key",current_song_index);
        editor.apply();
        store_mini_player_data();
    }
    public void make_a_toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}