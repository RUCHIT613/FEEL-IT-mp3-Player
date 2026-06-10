package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.CHILD_PLAYLIST_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MISSING_SONGS_PLAYLIST_KEY;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MISSING_SONG_INTERFACE extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public ADAPTER_FOR_EDIT_SONG_POSITION adapter;
    public ArrayList<Recently_added_recyclerview_elements_item_class> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_missing_song_interface);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrayList=new ArrayList<>();
        arrayList=save_and_load_array.load_array_for_user_created_playlist(this,MISSING_SONGS_PLAYLIST_KEY+CHILD_PLAYLIST_KEY);
        recyclerView=findViewById(R.id.recyclerview_of_missing_songs_interface);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        adapter=new ADAPTER_FOR_EDIT_SONG_POSITION(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void BACK_BUTTON_OF_MISSING_SONG_INTERFACE(View view){
        finish();
    }
}