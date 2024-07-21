package com.cscorner.feelit;

import static com.cscorner.feelit.zREDUCTANT_ACTIVITY_all_playlist_interface.PLAYLIST_POSTION_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
/*   THIS CLASS DOES NOT AFFECT THE APP AT ALL IT IS A USELESS FILE*/
public class zREDUCTANT_ACTIVITY_User_created_playlist_activity extends AppCompatActivity {
    private TextView user_added_playlist_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zreductant_activity_user_created_playlist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        user_added_playlist_textview=findViewById(R.id.user_created_playlist_textview);
        String playlist_name=getIntent().getStringExtra("playlist_name");
        int playlist_position=getIntent().getIntExtra(PLAYLIST_POSTION_ID,-1);
        if(playlist_position!=-1){
            user_added_playlist_textview.setText(String.format("Playlist NAME :%s\nPLAYLIST POSTION : %d",playlist_name,playlist_position));
        }
    }
}