package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_ALBUM_ART_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_ALBUM_NAME_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_ARTIST_NAME_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_PATH_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_SONG_NAME_KEY;

import static java.io.File.createTempFile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MUSIC_PLAYER_BOTTOM_CLASS extends BottomSheetDialogFragment {
    private int  count=0;
    private int  custom_timer_count=0;
    private LinearLayout add_song_to_playlist_linear_layout;
    private LinearLayout song_info_linear_layout;
    private ImageButton add_song_to_multiple_playlist_add_button;
    private ArrayList<Playlists_recycler_item_class> marrayList;
    private boolean mPERMISSION_FOR_ADD_TO_PLAYLIST_AND_SONG_INFO;
    private int mLAYOUT;
    private boolean mpermission_for_add_song_to_playlist;
    private RecyclerView recyclerView;
    private adapter_class_for_add_song_to_playlists adapter;
    private RecyclerView.LayoutManager layoutManager;


    //SONG INFO
    private String SONG_NAME;
    private String ARTIST_NAME;
    private String ALBUM_NAME;
    private long ALBUM_ART;

    private ImageView song_info_image_view;
    private TextView info_song_name;
    private TextView info_artist_name;

    private TextView go_to_artist_textview;
    private TextView go_to_album_textview;
    private TextView sleep_timer_textview;
    private TextView five_sleep_timer_textview;
    private TextView ten_sleep_timer_textview;
    private TextView fifteen_sleep_timer_textview;
    private TextView end_of_track_sleep_timer_textview;
    private TextView custom_sleep_timer_textview;



    private ConstraintLayout custom_timer_constraint_layout;
    private LinearLayout sleep_timer_linear_layout;
    private ImageView set_custom_sleep_timer;
    private NumberPicker hour_numberPicker;
    private NumberPicker min_numberPicker;

    private Set_ON_CLICKED_LISTENER mlistener;
    public interface Set_ON_CLICKED_LISTENER {
        void BOTTOM_CLASS_SENDS_POSITION_FROM_RECYCLER_VIEW(int POSITION);
        void BOTTOM_CLASS_ADD_SONG_TO_MULTIPLE_PLAYLIST_BUTTON_FUNCTION();
        void BOTTOM_CLASS_GO_TO_ARTIST_OR_ALBUM(long ALBUM_ART, String ARTIST_NAME_OR_ALBUM_NAME, boolean PERMISSION_FOR_ALBUM) throws IOException;
        void BOTTOM_CLASS_ACTIVATE_SLEEP_TIMER_BOTTOM_FRAGMENT();

        void BOTTOM_CLASS_SLEEP_TIMER(int TIME);



    }
    public MUSIC_PLAYER_BOTTOM_CLASS(int LAYOUT,boolean PERMISSION_FOR_ADD_TO_PLAYLIST_AND_SONG_INFO,ArrayList<Playlists_recycler_item_class> arrayList,Boolean permission_for_add_song_to_playlist){
        mLAYOUT=LAYOUT;
        mPERMISSION_FOR_ADD_TO_PLAYLIST_AND_SONG_INFO=PERMISSION_FOR_ADD_TO_PLAYLIST_AND_SONG_INFO;
        marrayList=arrayList;
        mpermission_for_add_song_to_playlist=permission_for_add_song_to_playlist;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(mLAYOUT,container,false);
        return view;
    }

    @SuppressLint("WrongThread")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getContext().getSharedPreferences("preff",Context.MODE_PRIVATE);

        SONG_NAME=preferences.getString(MINIPLAYER_SONG_NAME_KEY,"");
        ARTIST_NAME=preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,"");
        ALBUM_NAME=preferences.getString(MINIPLAYER_ALBUM_NAME_KEY,"");
        ALBUM_ART=preferences.getLong(MINIPLAYER_ALBUM_ART_KEY,613);
        if(mPERMISSION_FOR_ADD_TO_PLAYLIST_AND_SONG_INFO){
            go_to_artist_textview=view.findViewById(R.id.go_to_artist);
            go_to_album_textview=view.findViewById(R.id.go_to_album);
            sleep_timer_textview=view.findViewById(R.id.sleep_timer_TEXTVIEW);

            add_song_to_playlist_linear_layout=view.findViewById(R.id.add_song_to_playlist);
            add_song_to_multiple_playlist_add_button=view.findViewById(R.id.bottom_add_song_to_multiple_playlist_ADD_BUTTON);
            recyclerView=view.findViewById(R.id.bottom_add_song_to_multiple_playlist);
            recyclerView.setHasFixedSize(true);

            adapter=new adapter_class_for_add_song_to_playlists(marrayList);
            layoutManager=new LinearLayoutManager(getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            adapter.set_ON_CLICK_Listener(new Playlist_recycler_item_Adapter_class.onCLICK_Listener() {
                @Override
                public void on_ITEM_click(int Position) {

                }

                @Override
                public void more_on_ITEM_click(View view, int Position) {
                    mlistener.BOTTOM_CLASS_SENDS_POSITION_FROM_RECYCLER_VIEW(Position);
                }
            });
            add_song_to_multiple_playlist_add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_ADD_SONG_TO_MULTIPLE_PLAYLIST_BUTTON_FUNCTION();
                    dismiss();
                }
            });
            info_song_name=view.findViewById(R.id.song_info_song_name);
            info_artist_name=view.findViewById(R.id.song_info_artist_name);
            song_info_image_view=view.findViewById(R.id.song_info_imageview);
            song_info_linear_layout=view.findViewById(R.id.song_info);
            info_song_name.setText(SONG_NAME);
            info_artist_name.setText(ARTIST_NAME);

            Uri AlbumUri = Uri.parse("content://media/external/audio/albumart/" + ALBUM_ART);
            if(!preferences.getString(MINIPLAYER_PATH_KEY,"").equals("")){
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();

                retriever.setDataSource(preferences.getString(MINIPLAYER_PATH_KEY,""));

                //THE ERROR IS CAUSING WHEN APP IS INITIALLY START THE  PATH WILL BE "" AND RETRIEVER CANNOT FIND THE ""
                //AND ALBUM ART IS ALSO 613 IS ALSO CANNOT FIND IT

                byte[] albumArtBytes = retriever.getEmbeddedPicture();
                if (albumArtBytes != null) {
                    Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


                    File tempFile = null;
                    try {
                        tempFile = createTempFile("album_art", ".jpg");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        albumArtBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

// Get the Uri of the temporary file
                    Uri uri = Uri.fromFile(tempFile);
                    //THIS LINE MAY CAUSE ERROR DUE TO ALBUM ART BEING 613;
                    Picasso.get().load(uri).into(song_info_image_view);



//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

                    // Now you have the album art bitmap, you can display it or process it further
                } else {
                    // No album art available
                }
                try {
                    retriever.release();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

//        song_info_image_view.setImageURI(AlbumUri);

            go_to_artist_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mlistener.BOTTOM_CLASS_GO_TO_ARTIST_OR_ALBUM(ALBUM_ART,ARTIST_NAME,false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    dismiss();
                }
            });
            go_to_album_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mlistener.BOTTOM_CLASS_GO_TO_ARTIST_OR_ALBUM(ALBUM_ART,ALBUM_NAME,true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    dismiss();
                }
            });

            sleep_timer_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_ACTIVATE_SLEEP_TIMER_BOTTOM_FRAGMENT();
                    //this will give a func call to activate next bottom fragment

//                    if(count==1){
//                        sleep_timer_linear_layout.setVisibility(View.VISIBLE);
//                    }else{
//                        sleep_timer_linear_layout.setVisibility(View.GONE);
//                        custom_timer_constraint_layout.setVisibility(View.GONE);
//                        custom_timer_count=0;
//                        count=0;
//                    }
                }
            });
            activate_views(mpermission_for_add_song_to_playlist);

        }
        else{
//            sleep_timer_linear_layout =view.findViewById(R.id.timer_layout);
            five_sleep_timer_textview=view.findViewById(R.id.sleep_timer_five_mins_TEXTVIEW);
            ten_sleep_timer_textview=view.findViewById(R.id.sleep_timer_ten_mins_TEXTVIEW);
            fifteen_sleep_timer_textview=view.findViewById(R.id.sleep_timer_fifteen_mins_TEXTVIEW);
            end_of_track_sleep_timer_textview=view.findViewById(R.id.end_of_the_track_timer_TEXTVIEW);
            custom_sleep_timer_textview=view.findViewById(R.id.custom_timer_TEXTVIEW);
            custom_timer_constraint_layout =view.findViewById(R.id.sleep_timer_constriant_layout);
            set_custom_sleep_timer=view.findViewById(R.id.custom_timer_from_num_pickers);

            hour_numberPicker=view.findViewById(R.id.numberPicker_for_song_info_hour);
            min_numberPicker=view.findViewById(R.id.numberPicker_for_song_info_min);
            set_num_picker_attributes(hour_numberPicker,24);
            set_num_picker_attributes(min_numberPicker,59);






            five_sleep_timer_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_SLEEP_TIMER(5*60);
                    dismiss();
                }
            });
            ten_sleep_timer_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_SLEEP_TIMER(10*60);
                    dismiss();
                }
            });
            fifteen_sleep_timer_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_SLEEP_TIMER(15*60);
                    dismiss();
                }
            });
            end_of_track_sleep_timer_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_SLEEP_TIMER(613);
                    dismiss();
                }
            });
            set_custom_sleep_timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.BOTTOM_CLASS_SLEEP_TIMER(hour_numberPicker.getValue()*3600+min_numberPicker.getValue()*60);
                    dismiss();
                }
            });



            custom_sleep_timer_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    custom_timer_count+=1;
                    if(custom_timer_count==1){
                        custom_timer_constraint_layout.setVisibility(View.VISIBLE);
                    }else{
                        custom_timer_constraint_layout.setVisibility(View.GONE);
                        custom_timer_count=0;
                    }
                }
            });
        }









    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mlistener=(Set_ON_CLICKED_LISTENER) context;

        }catch (Exception e){}
    }
    public void activate_views(Boolean permission_for_add_song_to_playlist){
        if(permission_for_add_song_to_playlist){
            add_song_to_playlist_linear_layout.setVisibility(View.VISIBLE);
            song_info_linear_layout.setVisibility(View.GONE);

        }else {
            song_info_linear_layout.setVisibility(View.VISIBLE);
            add_song_to_playlist_linear_layout.setVisibility(View.GONE);
        }
    }
    public void set_num_picker_attributes(NumberPicker numberPicker ,int max){
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(max);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d",value);
            }
        });
    }


}


