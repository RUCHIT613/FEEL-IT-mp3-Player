package com.cscorner.feelit;
import static com.cscorner.feelit.App.CHANNEL_1_ID;

import static com.cscorner.feelit.MainActivity.Permission_For_External_Storage;
import static com.cscorner.feelit.MainActivity.Permission_For_Telephone;

import static java.io.File.createTempFile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.content.Intent;
//import android.support.v4.media.session.MediaSessionCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v4.media.session.MediaSessionCompat;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

//                                             엄마!!! 니 아들는 이 앱가 만들었어요
public class MUSIC_PLAYER_ACTIVITY extends AppCompatActivity implements MUSIC_PLAYER_BOTTOM_CLASS.Set_ON_CLICKED_LISTENER {
    private static final String TAG = "Bluetooth";
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    // UUID of the Serial Port Profile (SPP)
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String DEVICE_ADDRESS = "CC:DB:A7:68:A7:E6"; // Replace with your ESP32 Bluetooth address
//    private EditText editText;
    private volatile boolean stopThread = false;



    public Boolean PERMISSION_TO_DISPLAY_TOAST=false;
    public static boolean is_app_active=false;

    public static boolean PERMISSION_TO_COPY_ARRAYLIST=true;

    public int TOTAL_SONGS_OF_RECENTLY_ADDED;
    public static ArrayList<ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT> arrayList_for_fragments;
    private All_Song_Fragment ALL_SONG_FRAGMENT=new All_Song_Fragment();
    private All_Playlist_Fragment ALL_PLAYLIST_FRAGMENT=new All_Playlist_Fragment();
    private All_Album_Fragment ALL_ALBUM_FRAGMENT=new All_Album_Fragment();
    private All_Artist_Fragment ALL_ARTIST_FRAGMENT=new All_Artist_Fragment();
    public VIEW_PAGER_ADAPTER adapter_for_view_pager;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    public String CURRENT_PLAYING_PLAYLIST;
    public boolean Permission_To_Proceed=false;




    //TRANSITION
    public boolean permission_for_flicking=true;
    private Animation SLIDE_RIGHT_ANIMATION;
    private Animation SLIDE_LEFT_ANIMATION;
    private Animation FADE_IN;
    private Animation FADE_OUT;

    private Animation SLIDE_RIGH;

    public static boolean permission_for_miniplayer_widget_favorite=false;
    public static boolean permission_for_miniplayer_widget_shuffle=false;
    private boolean IS_FAVOURITE_PLAYLIST_ACTIVE =false;
    private boolean permission_to_resume_the_song_from_telephony=false;
    private int sleep_timer_time_in_secs;
    private boolean is_end_of_the_track_sleep_timer=false;
    private boolean is_sleep_timer_active=false;
    private boolean is_song_info_active=false;

    private boolean is_any_timer_running=false;
    private int single_song_selected_playlist_position;
    private boolean was_service_started_before=false;
    private boolean permission_for_app_inactivity=true;
    private NotificationManager notificationManager;
    private RemoteViews expanded;
    private boolean is_activity_minimize=false;
    private NotificationManagerCompat notificationManagerCompat;
    private NotificationCompat.Builder notification_builder;
    public static MediaSessionCompat mediaSession;
    private boolean is_bluetooth_enabled = false;
    private boolean was_music_player_played = false;


    private ArrayList<Playlists_recycler_item_class> arrayList_for_add_playlist_without_recently_added;
    private boolean is_single_song_selected;
    private int[] playlist_position_array;
    private ArrayList<Integer> selected_playlist_from_bottom_fragment_position_array_list;
    private int[] final_playlist_position_array;
    private int CURRENT_SONG_POSITION_FOR_MORE_BUTTON;
    private int CURRENT_PLAYLIST_POSITION_FOR_MORE_BUTTON;
    private int index = 0;
    public static ArrayList<Recently_added_recyclerview_elements_item_class> temp_array_list;
    private Handler handler = new Handler();
    private int USER_CREATED_PLAYLIST_POSITION;

    //ARRAY LOADER PERMISSION KEY

    public static final String LOAD_ARRAY_PERMISSION_KEY_FOR_ALL_PLAYLIST = "key_of_load_all_playlist_array";
    public static final String LOAD_ARRAY_PERMISSION_KEY_FOR_USER_CREATED_PLAYLIST = "key_of_load_user_created_playlist_array";

    public static final String TOTAL_SONGS_OF_RECENTLY_ADDED_KEY = "key_of_total_songs_of_recently_added";

    public static final String SHORTCUT_PLAYLIST_ACTIVATION="key_of_shortcut_playlist_activation";
    public static final String MINIPLAYER_SONG_NAME_KEY = "key_of_miniplayer_song_name";
    public static final String MINIPLAYER_PATH_KEY = "key_of_miniplayer_path";
    public static final String MINIPLAYER_ARTIST_NAME_KEY = "key_of_miniplayer_artist_name";
    public static final String MINIPLAYER_ALBUM_NAME_KEY = "key_of_miniplayer_album_name";
    public static final String MINIPLAYER_DURATION_KEY = "key_of_miniplayer_duration_name";
    public static final String MINIPLAYER_ALBUM_ART_KEY = "key_of_miniplayer_album_art";
    public static final String MINIPLAYER_ACTIVATE_KEY = "key_of_miniplayer_activation";

    public static final String CURRENT_INTERFACE_POSITION_KEY = "key_of_current_interface_position_key";
    public static final String CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME_KEY = "key_of_current_album_playlist_key";

    public static final String AUDIO_CONNECTIVITY_MODE_KEY="key_of_audio_connectivity";

    public static final String EXTERNAL_AUDIO_CONTROL_MODE_KEY="key_for_external_control_mode";
    public static final String EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_NEXT_MODE="key_for_external_control_event_key_for_next_mode";
    public static final String EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_PREVIOUS_MODE="key_for_external_control_event_key_for_previous_mode";

    public static final String BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME="key_of_backup_playlist_plus_playlist_name";
    public static final String BACKUP_PLAYLIST_LIST_PERMISSION_KEY ="key_of_backup_playlist_plus_playlist_list_name";

    public static final String PAUSE_TIMER_ACTIVATION_KEY="key_for_activation_of_pause_timer";
    public static final String PAUSE_TIMER_TIME_KEY="key_for_pause_timer_time";
    public static final String PLAY_TIMER_ACTIVATION_KEY="key_for_activation_of_play_timer";
    public static final String PLAY_TIMER_TIME_KEY="key_for_play_timer_time";
    public static final String REPEAT_MODE_KEY="key_for_repeat_mode";
    public static final String SHUFFLE_MODE_KEY="key_for_shuffle_mode";

    public static final String PLAY_NEXT_AND_ADD_TO_QUEUE_KEY ="key_for_play_next";


    //HOME ATTRIBUTES

    public static boolean is_add_to_queue_active=false;
    public static boolean is_play_next_active=false;
    public static int PLAY_NEXT_INDEX=613;
    public static String PLAY_NEXT_SONG="";
    private int CURRENT_INTERFACE_POSITION=613;

    private ImageView ALL_SONGS_INTERFACE_IMAGEVIEW, ALL_PLAYLIST_INTERFACE_IMAGEVIEW, ALL_ALBUMS_INTERFACE_IMAGEVIEW, ALL_ARTIST_INTERFACE_IMAGEVIEW;
    private ConstraintLayout ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT;
    public static boolean is_media_player_paused = true;
    private ConstraintLayout miniplayer;
    private TextView miniplayer_song_name_text_view;
    private TextView miniplayer_artist_name_text_view;
    private ImageView miniplayer_album_art_imageview;
    private boolean intent_changed = false;
    private boolean new_media_player_permission;
    private ImageView miniplayer_pause_play_image_view;
    private ImageView miniplayer_previous_song_image_view;
    private ImageView miniplayer_next_song_image_view;

    private ConstraintLayout music_player;
    private TextView music_player_Album_Name_text_view;
    private ImageView music_player_album_art_image_view;
    private TextView music_player_Song_Name_text_view;
    private TextView music_player_Artist_Name_text_view;
    private TextView music_player_total_duration_text_view;
    private SeekBar music_player_seek_bar;
    private TextView music_player_Current_duration;
    private ImageView music_player_play_and_pause_image_view;
    private ImageView music_player_previous_song_image_view;
    private ImageView music_player_next_song_image_view;
    private ImageView Repeat_Button;
    public static int Count_for_Repeat_Button=0;
    private ImageView Shuffle_Button;
    public static int Count_for_Shuffle_Button=0;
    private ImageView Favorite_Button;
    private int Count_for_Favorite_Button=0;
    private ArrayList<Integer> Shuffled_Index_Arraylist;
    private int Shuffled_Array_Index_POSITION=0;
    private boolean IS_MUSIC_PLAYER_ACTIVE=false;



    //FOR ALL PLAYLIST INTERFACE
    private ConstraintLayout all_playlist_interface;
    private boolean IS_ALL_PLAYLIST_INTERFACE_ACTIVE = false;
    public MediaPlayer player;

    public static final String PLAYLIST_POSTION_ID = "POSITION_ID";
    public static ArrayList<Playlists_recycler_item_class> arrayList_for_all_playlists;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Playlist_recycler_item_Adapter_class adapter_for_all_playlist;
    private int position;


    //USER CREATED PLAYLIST
    private ConstraintLayout user_created_playlist_contrain_layout;
    private ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_user_created_playlist;
    private recently_added_adapter_class adapter_for_user_created_playlist;
    private ImageView user_created_image_view;
    private TextView user_created_playlist_name_text_view;
    private TextView user_created_total_songs_text_view;
    private boolean IS_USER_CREATED_PLAYLIST_ACTIVE = false;


    //RECENTLY ADDED PLAYLIST
    private ConstraintLayout recently_added_playlist_constrain_layout;
    private boolean IS_RECENTLY_ADDED_PLAYLIST_ACTIVE = false;
    public static ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_recently_added_playlist;
    private recently_added_adapter_class adapter_for_recently_added_playist;
    private static final int READ_EXTERNAL_STORAGE = 1;
    private String filePath;
    private ImageView recently_added_image_view;
    private TextView recently_added_playlist_name_text_view;
    private TextView recently_added_total_songs_text_view;
    private int duration;


    // ADD NEW PLAYLIST
    private ConstraintLayout add_new_playlist_interface;
    private EditText add_new_playlist_edit_text;
    private ActivityResultLauncher<Intent> resultLauncher;
    private ImageView im;


    //ADD PLAYLIST INTERFACE
    private String ALBUM_OR_ARTIST_NAME;
    private ArrayList<Playlists_recycler_item_class> arrayList_for_add_playlist_interface;
    private adapter_class_for_add_song_to_playlists adapter_for_add_playlist_interface;
    private ConstraintLayout add_song_to_playlist_interface;

    //ADD MULTIPLE SONGS TO MULTIPLE PLAYLIST
    private ConstraintLayout add_multiple_songs_to_multiple_playlist;
    private ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_add_multiple_songs_to_multiple_playlist;
    private adapter_for_add_multiple_songs_to_multiple_playlist adapter_for_add_multiple_songs_to_multiple_playlist;
    private ArrayList<Integer> arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist;
    private boolean is_add_multiple_songs_selected_interface_is_long_pressed = false;

    public static int current_song_index = 0;

    //ALL ALBUMS INTERFACE
    private boolean IS_ALL_ALBUM_INTERFACE_ACTIVE = false;
    private ConstraintLayout all_album_interface_constraint_layout;

    public static ArrayList<all_album_interface_all_all_artist_interface_recyclerview_item_class> arrayList_for_all_albums;
    private all_album_interface_and_all_artist_interface_recyclerview_adapter adapter_for_all_album_interface;

    //ALBUM PLAYLIST
    private boolean IS_ALBUM_PLAYLIST_ACTIVE = false;
    private ConstraintLayout album_playlist_constrain_layout;
    private ImageView album_art_imageview_for_album_playlist;
    private TextView album_name_text_view_for_album_playlist;
    private TextView total_songs_text_view_for_album_playlist;

    private ArrayList<Recently_added_recyclerview_elements_item_class> array_list_for_album_playlist;
    private recently_added_adapter_class adapter_for_album_playlist;
    private String CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME;
    public int temp_ount = 0;

    //ALL ARTIST PLAYLIST
    private boolean IS_ALL_ARTIST_INTERFACE_ACTIVE = false;
    private ConstraintLayout all_artist_interface_constrain_layout;
    public static ArrayList<all_album_interface_all_all_artist_interface_recyclerview_item_class> arrayList_for_all_artist_interface;
    private all_album_interface_and_all_artist_interface_recyclerview_adapter adapter_for_all_artist_interface;

    //ARTIST PLAYLIST
    private boolean IS_ARTIST_PLAYLIST_ACTIVE = false;
    private ConstraintLayout artist_playlist_constrain_layout;
    private ImageView album_art_imageview_for_artist_playlist;
    private TextView album_name_text_view_for_artist_playlist;
    private TextView total_songs_text_view_for_artist_playlist;
    private ArrayList<Recently_added_recyclerview_elements_item_class> array_list_for_artist_playlist;
    private recently_added_adapter_class adapter_for_artist_playlist;


    //ALL SONGS INTERFACE
    public static ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_all_song_interface;
    private ConstraintLayout all_song_interface_constrain_layout;
    private recently_added_adapter_class adapter_for_all_songs_interface;
    private boolean IS_ALL_SONGS_INTERFACE_ACTIVE = false;


    //EDIT SONG POSITION OF USER CREATED PLAYLIST INTERFACE
    private ArrayList<Recently_added_recyclerview_elements_item_class> array_for_edit_song_position;
    private ConstraintLayout edit_song_position_of_user_created_playlist_interface;
    private recently_added_adapter_class adapter_for_edit_song_position_of_user_created;
    private String Activate_User_Playlist_From_Edit_Song_Position_Playlist_Name;
    private int Activate_User_Playlist_From_Edit_Song_Position_Playlist_Position;
    private boolean is_edit_song_position_is_long_pressed=false;
    private Call_Listener callListener;
    private TelephonyManager telephonyManager;

    //QUEUE INTERFACE
    private ConstraintLayout constraintLayout_of_queue_interface;
    private RecyclerView RecyclerView_of_queue_interface;
    private recently_added_adapter_class adapter_for_queue_interface;
    private LinearLayoutManager linearLayoutManager;
    private boolean IS_QUEUE_INTERFACE_ACTIVE=false;


    //APP SETTING PANEL
    private ImageView setting_button;
    private boolean IS_SETTING_INTERFACE_ACTIVE=false;
    private ConstraintLayout setting_interface_constraint_layout;

        //AUDIO CONNECTIVITY MODE
    private ImageView radioButton_of_Bluetooth_device;
    private ImageView radioButton_of_Wired_device;
    private boolean is_radioButton_of_Bluetooth_device_selected;



    private TextView external_audio_text_view;
    private ConstraintLayout external_audio_constrain_layout;
    private ConstraintLayout external_audio_constrain_layout2;
    private ConstraintLayout external_audio_control_expanded_layout;
    private ConstraintLayout external_audio_control_play_next_mode_expanded_constraint_layout;
    private TextView external_audio_control_activate_play_next_mode_options_text_view;
    private TextView external_audio_control_activate_play_previous_mode_options_text_view;


    private TextView external_audio_control_play_next_mode_play_next_song;
    private TextView external_audio_control_play_next_mode_play_previous_song;
    private TextView external_audio_control_play_next_mode_play_previous_v2_song;


    private ConstraintLayout info_message_constrain_layout_of_next_song_mode_play_next_song;
    private TextView info_message_of_Play_next_mode_of_play_next_song_textview;

    private ConstraintLayout info_message_constrain_layout_of_next_song_mode_play_previous_song;
    private TextView info_message_of_Play_next_mode_of_play_previous_song_textview;

    private ConstraintLayout info_message_constrain_layout_of_next_song_mode_play_previous_song_v2;
    private TextView info_message_of_Play_next_mode_of_play_previous_song_textview_v2;

    ////RADIO BUTTONS OF PLAY NEXT MODE
    private ImageView radio_button_of_play_next_mode_play_next_song;
    private ImageView radio_button_of_play_next_mode_play_previous_song;
    private ImageView radio_button_of_play_next_mode_play_previous_song_v2;




    private ConstraintLayout info_message_constrain_layout_of_previous_song_mode_play_next_song;
    private TextView info_message_of_Play_previous_mode_of_play_next_song_textview;

    private ConstraintLayout info_message_constrain_layout_of_previous_song_mode_play_previous_song;
    private TextView info_message_of_Play_previous_mode_of_play_previous_song_textview;

    private ConstraintLayout info_message_constrain_layout_of_previous_song_mode_play_previous_song_v2;
    private TextView info_message_of_Play_previous_mode_of_play_previous_song_textview_v2;

    ////RADIO BUTTONS OF PLAY PREVIOUS MODE
    private ImageView radio_button_of_play_previous_mode_play_next_song;
    private ImageView radio_button_of_play_previous_mode_play_previous_song;
    private ImageView radio_button_of_play_previous_mode_play_previous_song_v2;



    //BACKUP PLAYLIST
    private ConstraintLayout backup_Playlist_extended_layout;
    private adapter_for_backup_playlist adapter_for_backup_playlist;
    private TextView info_message_of_backup_playlist;
    private ImageButton down_button_for_backup_playlist;


    //BACKUP PLAYLIST LIST
    private ConstraintLayout available_backup_playlist_list_extended_layout;
    private ArrayList<String> arrayList_for_backup_playlist_list;
    private TextView info_message_of_backup_playlist_list;

    private adapter_for_BACKUP_PLAYLIST_LIST adapter_for_backup_playlist_list;


    //CUSTOMIZE WIDGET ATTRIBUTES
    private ConstraintLayout constraintLayout_which_contains_all_customize_widget_controls;
    private ConstraintLayout constraintLayout_which_contains_slot1_controls;
    private ConstraintLayout constraintLayout_which_contains_slot2_controls;
    private ImageView imageView_for_slot1;
    private ImageView imageView_for_slot2;

    ////MINIPLAYER WIDGET SLOT 1
    private ImageView radio_button_for_favourite_slot_1;
    private ImageView radio_button_for_repeat_slot_1;
    private ImageView radio_button_for_shuffle_slot_1;
    ////MINIPLAYER WIDGET SLOT 2
    private ImageView radio_button_for_favourite_slot_2;
    private ImageView radio_button_for_repeat_slot_2;
    private ImageView radio_button_for_shuffle_slot_2;


    //PAUSE ACTIVITY TIMER ATTRIBUTES
    private ConstraintLayout contrain_layout_of_pause_timer_attributes;
    private ConstraintLayout pause_timer_extended_layout;
    private NumberPicker hours_numberPicker_for_pause_timer;
    private NumberPicker min_numberPicker_for_pause_timer;
    private Switch switch_of_pause_timer;
    private TextView hour_textview_of_pause_timer;
    private TextView min_textview_of_pause_timer;
    private CardView cardView_of_set_timer_of_pause_timer;


    //PLAY ACTIVITY TIMER ATTRIBUTES
    private ConstraintLayout constraint_layout_of_play_timer_attributes;
    private ConstraintLayout play_timer_extended_layout;
    private NumberPicker hours_numberPicker_for_play_timer;
    private NumberPicker min_numberPicker_for_play_timer;
    private Switch switch_of_play_timer;
    private TextView hour_textview_of_play_timer;
    private TextView min_textview_of_play_timer;
    private CardView cardView_of_set_timer_of_play_timer;

    private boolean was_play_timer_activated_before=false;
    private boolean does_user_respond=false;
    private PhoneStateListener phoneStateListener;

    private ConstraintLayout STOP_SLEEP_TIMER_CONSTAINT_LAYOUT;
    private TextView Remaining_Sleep_Timer_Textview;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    public GestureDetector gestureDetector;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connectToDevice(DEVICE_ADDRESS);
//
//
        gestureDetector=new GestureDetector(this,new SWIPE_LISTENER());

        SLIDE_RIGHT_ANIMATION =AnimationUtils.loadAnimation(this,R.anim.slide_right_animation);
        SLIDE_LEFT_ANIMATION =AnimationUtils.loadAnimation(this,R.anim.slide_left_animation);
        FADE_IN=AnimationUtils.loadAnimation(this,R.anim.fade_in_animation);
        FADE_OUT=AnimationUtils.loadAnimation(this,R.anim.fade_out_animation);

        is_activity_minimize=false;
        setContentView(R.layout.music_player_activity_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        notificationManager=getSystemService(NotificationManager.class);
        preferences=getSharedPreferences("preff",MODE_PRIVATE);
        editor=preferences.edit();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            setupPhoneStateListener();
        }
        UNPLUGGED_RECEIVER UNPLUGGEDRECIEVER =new UNPLUGGED_RECEIVER();
        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(UNPLUGGEDRECIEVER, filter);
        is_app_active=true;








        hours_numberPicker_for_pause_timer=findViewById(R.id.hour_num_picker_for_pause_timer);
        min_numberPicker_for_pause_timer=findViewById(R.id.min_numpicker_for_pause_timer);
        hours_numberPicker_for_play_timer=findViewById(R.id.hour_num_picker_for_play_timer);
        min_numberPicker_for_play_timer=findViewById(R.id.min_numpicker_for_play_timer);

        set_num_picker_attributes(hours_numberPicker_for_pause_timer,24);
        set_num_picker_attributes(min_numberPicker_for_pause_timer,59);
        set_num_picker_attributes(hours_numberPicker_for_play_timer,24);
        set_num_picker_attributes(min_numberPicker_for_play_timer,59);





        


        //ALL ARRAYLISTS
        arrayList_for_all_playlists = new ArrayList<>();
        arrayList_for_add_playlist_interface = new ArrayList<>();
        arrayList_for_recently_added_playlist = new ArrayList<>();
        arrayList_for_user_created_playlist = new ArrayList<>();
        arrayList_for_all_song_interface = new ArrayList<>();
        temp_array_list = new ArrayList<>();
//        NotificationManagerCompat.from(this);
//        activate_notification();
        check_notification_action_commands();


        //ALL CONSTRAIN LAYOUTS OF INTERFACES
        all_playlist_interface = findViewById(R.id.all_playlist_interface_single_screen);
        recently_added_playlist_constrain_layout = findViewById(R.id.recently_added_playlist_recyclerview_constrain_layout);
        user_created_playlist_contrain_layout = findViewById(R.id.user_created_playlist_recyclerview_constrain_layout);
        add_new_playlist_interface = findViewById(R.id.add_new_playlist_interface);
        add_song_to_playlist_interface = findViewById(R.id.constraintLayout_of_add_song_to_playlist);
        add_multiple_songs_to_multiple_playlist = findViewById(R.id.add_multiple_songs_to_multiple_playlist_interface);
        all_album_interface_constraint_layout = findViewById(R.id.album_interface_playlist);
        all_artist_interface_constrain_layout = findViewById(R.id.artist_interface_playlist);
        all_song_interface_constrain_layout = findViewById(R.id.song_interface_playlist);
        edit_song_position_of_user_created_playlist_interface=findViewById(R.id.edit_song_position_of_user_created_playlist_interface);

        //ALL SYSTEM AND USER CREATED PLAYLIST ATTRIBUTES
        recently_added_image_view = findViewById(R.id.recently_playlist_image_view);
        recently_added_playlist_name_text_view = findViewById(R.id.playlist_name_of_recently_added_playlist_TEXTVIEW);
        recently_added_total_songs_text_view = findViewById(R.id.total_songs_in_recently_added_playlist_text_view);
        user_created_image_view = findViewById(R.id.user_created_image_view);
        user_created_total_songs_text_view = findViewById(R.id.total_songs_in_user_created_playlist_text_view);
        user_created_playlist_name_text_view = findViewById(R.id.playlist_name_of_user_created_playlist_TEXTVIEW);

        //ALL INTERFACES  IMAGEVIEW AKA BUTTON
        ALL_SONGS_INTERFACE_IMAGEVIEW = findViewById(R.id.all_songs_interface);
        ALL_PLAYLIST_INTERFACE_IMAGEVIEW = findViewById(R.id.all_playlist_interface);
        ALL_ALBUMS_INTERFACE_IMAGEVIEW = findViewById(R.id.all_album_interface);
        ALL_ARTIST_INTERFACE_IMAGEVIEW = findViewById(R.id.all_artist_interface);
        ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT=findViewById(R.id.all_interface_button_parent_constaint_layout);

        //ALBUM PLAYLIST ATTRIBUTES
        album_playlist_constrain_layout = findViewById(R.id.album_playlist_constrain_layout);
        album_art_imageview_for_album_playlist = findViewById(R.id.album_playlist_image_view);
        album_name_text_view_for_album_playlist = findViewById(R.id.playlist_name_of_album_playlist_TEXTVIEW);
        total_songs_text_view_for_album_playlist = findViewById(R.id.total_songs_in_album_playlist_text_view);

        //ARTIST PLAYLIST ATTRIBUTES
        artist_playlist_constrain_layout = findViewById(R.id.artist_playlist_constrain_layout);
        album_art_imageview_for_artist_playlist = findViewById(R.id.artist_playlist_image_view);
        album_name_text_view_for_artist_playlist = findViewById(R.id.playlist_name_of_artist_playlist_TEXTVIEW);
        total_songs_text_view_for_artist_playlist = findViewById(R.id.total_songs_in_artist_playlist_text_view);

        //QUEUE INTERFACE
        constraintLayout_of_queue_interface=findViewById(R.id.queue_interface);



        //SETTING OPTION ATTRIBUTES
        setting_button=findViewById(R.id.setting_option_button_);
        setting_interface_constraint_layout=findViewById(R.id.setting_constrain_layout);

        ////AUDIO CONNECTIVITY OPTIONS
        radioButton_of_Bluetooth_device=findViewById(R.id.radio_button_of_bluetooth_devices);
        radioButton_of_Wired_device=findViewById(R.id.radio_button_of_wired_devices);


        external_audio_text_view=findViewById(R.id.external_audio_control);//IT IS TEXTVIEW OF External Audio Controls onClick

        external_audio_constrain_layout=findViewById(R.id.external_audio_control_Play_Next_Mode_test); //IT IS EXPANDED CONSTRAIN LAYOUT OF External Audio Controls WHEN USER CLICKS IT
        external_audio_constrain_layout2 =findViewById(R.id.external_audio_control_Play_Previous_Mode_test);

        external_audio_control_expanded_layout=findViewById(R.id.EXTERNAL_AUDIO_CONTROLS_CONSTRAINT_LAYOUT_WHICH_CONTAINS_PLAYNEXT_AND_PLAYPREVIOUS_TEXTVIEW);//IT IS EXPANDED CONSTRAIN LAYOUT OF External Audio Controls WHICH CONTAINS ALL THE VIEWS

        external_audio_control_activate_play_next_mode_options_text_view=findViewById(R.id.external_audio_control_Play_Next_Mode_text_view); //IT IS PLAY NEXT MODE TEXT VIEW ON CLICKED IT GIVES CUSTOM EXTERNAL  CONTROLS
        external_audio_control_activate_play_previous_mode_options_text_view=findViewById(R.id.external_audio_control_Play_Previous_Mode_text_view);


        external_audio_control_play_next_mode_play_next_song=findViewById(R.id.play_next_song_mode_1);
        external_audio_control_play_next_mode_play_previous_song=findViewById(R.id.play_previous_song_mode_1);
//        external_audio_control_play_next_mode_play_previous_v2_song=findViewById(R.id.play_previous_song_mode_v2_1);

        //INFO MESSAGE OF PLAY NEXT MODE -> PLAY NEXT SONG
        info_message_constrain_layout_of_next_song_mode_play_next_song=findViewById(R.id.Play_next_Play_next_song_mode_constraint_layout_test);//THIS IS A CONSTRAINT LAYOUT WHICH CONTAINS PLAY NEXT SONG AND ITS INFO MESSAGE OF PLAY NEXT MODE WHEN CLICKED
        info_message_of_Play_next_mode_of_play_next_song_textview=findViewById(R.id.info_message_textview_for_play_next_mode_play_next_song_textview_test);//THIS IS INFO MESSAGE OF PLAY NEXT SONG OF PLAY NEXT MODE

        //INFO MESSAGE OF PLAY NEXT MODE -> PLAY PREVIOUS SONG
        info_message_constrain_layout_of_next_song_mode_play_previous_song=findViewById(R.id.Play_next_Play_previous_song_mode_constraint_layout_test);
        info_message_of_Play_next_mode_of_play_previous_song_textview=findViewById(R.id.info_message_textview_for_play_next_mode_play_previous_song_textview_test);

        //INFO MESSAGE OF PLAY NEXT MODE -> PLAY PREVIOUS SONG V2
        info_message_constrain_layout_of_next_song_mode_play_previous_song_v2=findViewById(R.id.Play_next_Play_previous_v2_song_mode_constraint_layout_test);
        info_message_of_Play_next_mode_of_play_previous_song_textview_v2=findViewById(R.id.info_message_textview_for_play_next_mode_play_previous_v2_song_textview_test);

        ////RADIO BUTTONS OF PLAY NEXT MODE
        radio_button_of_play_next_mode_play_next_song=findViewById(R.id.radio_button_of_play_next_mode_play_next_song_test);
        radio_button_of_play_next_mode_play_previous_song=findViewById(R.id.radio_button_of_play_next_mode_play_previous_song_test);
        radio_button_of_play_next_mode_play_previous_song_v2=findViewById(R.id.radio_button_of_play_next_mode_play_previous_song_v2_test);




        //INFO MESSAGE OF PLAY PREVIOUS MODE -> PLAY NEXT SONG
        info_message_constrain_layout_of_previous_song_mode_play_next_song=findViewById(R.id.Play_Previous_Play_next_song_mode_constraint_layout_test);
        info_message_of_Play_previous_mode_of_play_next_song_textview=findViewById(R.id.info_message_textview_for_play_previous_mode_play_next_song_textview_test);

        //INFO MESSAGE OF PLAY PREVIOUS MODE -> PLAY PREVIOUS SONG
        info_message_constrain_layout_of_previous_song_mode_play_previous_song=findViewById(R.id.Play_Previous_Play_previous_song_mode_constraint_layout_test);
        info_message_of_Play_previous_mode_of_play_previous_song_textview=findViewById(R.id.info_message_textview_for_play_previous_mode_play_previous_song_textview_test);

        //INFO MESSAGE OF PLAY PREVIOUS MODE -> PLAY PREVIOUS SONG V2
        info_message_constrain_layout_of_previous_song_mode_play_previous_song_v2=findViewById(R.id.Play_Previous_Play_previous_v2_song_mode_constraint_layout_test);
        info_message_of_Play_previous_mode_of_play_previous_song_textview_v2=findViewById(R.id.info_message_textview_for_play_previous_mode_play_previous_v2_song_textview_test);

        ////RADIO BUTTONS OF PLAY NEXT MODE
        radio_button_of_play_previous_mode_play_next_song=findViewById(R.id.radio_button_of_play_previous_mode_play_next_song_test);
        radio_button_of_play_previous_mode_play_previous_song=findViewById(R.id.radio_button_of_play_previous_mode_play_previous_song_test);
        radio_button_of_play_previous_mode_play_previous_song_v2=findViewById(R.id.radio_button_of_play_previous_mode_play_previous_song_v2_test);


        //BACKUP PLAYLIST
        backup_Playlist_extended_layout=findViewById(R.id.backup_playlist_extended_layout);
        info_message_of_backup_playlist=findViewById(R.id.backup_playlist_info_textview);
        down_button_for_backup_playlist =findViewById(R.id.down_button_of_backup_playlist);


        //BACKUP PLAYLIST LIST
        available_backup_playlist_list_extended_layout =findViewById(R.id.available_backup_playlist_extended_layout);
        info_message_of_backup_playlist_list=findViewById(R.id.backup_playlist_list_info_textview);


        //PAUSE TIMER
        pause_timer_extended_layout=findViewById(R.id.PAUSE_INACTIVITY_TIMER_extended_layout);
        contrain_layout_of_pause_timer_attributes=findViewById(R.id.pause_attributes_contraint_layout);
        switch_of_pause_timer=findViewById(R.id.switch_of_pause_activity_timer);
        hour_textview_of_pause_timer=findViewById(R.id.HOUR_TEXTVIEW_OF_PAUSE_TIMER);
        min_textview_of_pause_timer=findViewById(R.id.MIN_TEXTVIEW_OF_PAUSE_TIMER);
        cardView_of_set_timer_of_pause_timer=findViewById(R.id.set_timer_button_of_pause_timer);

        //PLAY TIMER
        play_timer_extended_layout=findViewById(R.id.PLAY_INACTIVITY_TIMER_extended_layout);
        constraint_layout_of_play_timer_attributes=findViewById(R.id.play_attributes_contraint_layout);
        switch_of_play_timer=findViewById(R.id.switch_of_play_activity_timer);
        hour_textview_of_play_timer=findViewById(R.id.HOUR_TEXTVIEW_OF_PLAY_TIMER);
        min_textview_of_play_timer=findViewById(R.id.MIN_TEXTVIEW_OF_PLAY_TIMER);
        cardView_of_set_timer_of_play_timer=findViewById(R.id.set_timer_button_of_play_timer);

        //CUSTOMIZE WIDGET
        constraintLayout_which_contains_all_customize_widget_controls=findViewById(R.id.this_constrain_layout_contains_all_customize_widget_controls);
        constraintLayout_which_contains_slot1_controls=findViewById(R.id.constraint_layout_slot1_controls);
        constraintLayout_which_contains_slot2_controls=findViewById(R.id.constraint_layout_slot2_controls);
        imageView_for_slot1=findViewById(R.id.SLOT_1_MINIPLAYER_WIDGET_IMAGEVIEW);
        imageView_for_slot2=findViewById(R.id.SLOT_2_MINIPLAYER_WIDGET_IMAGEVIEW);
        ////SLOT 1
        radio_button_for_favourite_slot_1=findViewById(R.id.radio_button_of_favourite_slot_1);
        radio_button_for_repeat_slot_1=findViewById(R.id.radio_button_of_repeat_slot_1);
        radio_button_for_shuffle_slot_1=findViewById(R.id.radio_button_of_shuffle_slot_1);

        ////SLOT 2
        radio_button_for_favourite_slot_2=findViewById(R.id.radio_button_of_favourite_slot_2);
        radio_button_for_repeat_slot_2=findViewById(R.id.radio_button_of_repeat_slot_2);
        radio_button_for_shuffle_slot_2=findViewById(R.id.radio_button_of_shuffle_slot_2);



        //SLEEP TIMER
        STOP_SLEEP_TIMER_CONSTAINT_LAYOUT=findViewById(R.id.STOP_SLEEP_TIMER_CONTRAINT_LAYOUT);
        Remaining_Sleep_Timer_Textview=findViewById(R.id.sleep_timer_remaining_time);
        load_array_for_all_playlists();
        check_permission();


//        add_playlist__Interface();
        im = findViewById(R.id.image__);

        //MINI-PLAYER ATTRIBUTES
        miniplayer = findViewById(R.id.miniplayer_ID);
        miniplayer_song_name_text_view = findViewById(R.id.song_name_text_view_recently_added_miniplayer);
        miniplayer_artist_name_text_view = findViewById(R.id.artist_name_text_view_recently_added_miniplayer);
        miniplayer_album_art_imageview = findViewById(R.id.album_art_image_view_recently_added_miniplayer);
        miniplayer_next_song_image_view = findViewById(R.id.next_song);
        miniplayer_pause_play_image_view = findViewById(R.id.play_pause);
        miniplayer_previous_song_image_view = findViewById(R.id.previous_song);

        //MUSIC-PLAYER ATTRIBUTES
        music_player = findViewById(R.id.MUSIC_PLAYER_INTERFACE);
        music_player_Album_Name_text_view = findViewById(R.id.musicplayer_Album_Name);
        music_player_album_art_image_view = findViewById(R.id.musicplayer_Album_Art);
        music_player_Song_Name_text_view = findViewById(R.id.musicplayer_Song_Name);
        music_player_Artist_Name_text_view = findViewById(R.id.musicplayer_Artist_Name);
        music_player_total_duration_text_view = findViewById(R.id.musicplayer_total_duration);
        music_player_seek_bar = findViewById(R.id.musicplayer_SeekBar);
        music_player_Current_duration = findViewById(R.id.musicplayer_current_duration);
        music_player_next_song_image_view = findViewById(R.id.next_song_musicplayer);
        music_player_previous_song_image_view = findViewById(R.id.previous_song_music_player);
        music_player_play_and_pause_image_view = findViewById(R.id.play_pause_music_player);
        Repeat_Button=findViewById(R.id.repeat_imageview);
        Count_for_Repeat_Button=preferences.getInt(REPEAT_MODE_KEY,0);
        Shuffle_Button=findViewById(R.id.shuffle_imageview);
        Count_for_Shuffle_Button=preferences.getInt(SHUFFLE_MODE_KEY,0);
        Favorite_Button=findViewById(R.id.favourite_button_imageview);


        //PERMISSION TO ACTIVATED MINI-PLAYER ON START OF THE APP
        preferences = getSharedPreferences("preff", MODE_PRIVATE);

        editor.putBoolean("app",true);
        editor.apply();

        was_music_player_played = preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY, false);
        if (preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY, false)) {

            //THIS CHECKS A POSSIBILITY THAT IF SONG IS PLAYED AND THEN APP IS CLOSED AND USER DELETE THE SONG FROM IT'S EXTERNAL STORAGE AND OPENS THE APP, IT SHOULD WORK PROPERLY
            if(!check_whether_song_or_playlist_already_exists.check_the_song(arrayList_for_recently_added_playlist,preferences.getString(MINIPLAYER_SONG_NAME_KEY,""),preferences.getString(MINIPLAYER_PATH_KEY,""))){

                make_a_toast("song_exists",false);
                new_media_player_permission = true;
                current_song_index = preferences.getInt("key", 0);

                temp_array_list=new ArrayList<>();
                CURRENT_INTERFACE_POSITION = preferences.getInt(CURRENT_INTERFACE_POSITION_KEY, 1);

                if (CURRENT_INTERFACE_POSITION == 1) { //FOR SYSTEM AND USER PLAYLIST
                    USER_CREATED_PLAYLIST_POSITION = preferences.getInt("key2", 0);
                    if (USER_CREATED_PLAYLIST_POSITION != 0) {
                        temp_array_list = copy_arraylist(save_and_load_array.load_array_for_user_created_playlist(this, arrayList_for_all_playlists.get(USER_CREATED_PLAYLIST_POSITION).getMPlaylist_name()));

                    } else {

                        temp_array_list = copy_arraylist(arrayList_for_recently_added_playlist);
                    }
                } else if (CURRENT_INTERFACE_POSITION == 2) { //FOR ALBUM INTERFACE
                    CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME = preferences.getString(CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME_KEY, "");

                    if(Check_Whether_Album_or_Artist_Playlist_Exists.Check_Album_or_Artist_Playlist(CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME,true)){
                        temp_array_list = copy_arraylist(load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist, CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME, true));
                    }
                    else {
                        editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,false);
                        editor.apply();
                    }

                } else if (CURRENT_INTERFACE_POSITION == 3) {//FOR ARTIST INTERFACE
                    CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME = preferences.getString(CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME_KEY, "");
                    if(Check_Whether_Album_or_Artist_Playlist_Exists.Check_Album_or_Artist_Playlist(CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME,false)){
                        temp_array_list = copy_arraylist(load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist, CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME, false));

                    }
                    else {
                        make_a_toast("artist_does_not_found",false);
                        editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,false);
                        editor.apply();
                    }
                } else if (CURRENT_INTERFACE_POSITION == 0) {//FOR ALL SONG INTERFACE
                    temp_array_list = copy_arraylist(load_all_songs_of_all_songs_interface_in_ascending.load_songs_in_ascending(arrayList_for_recently_added_playlist));
                }

                if(preferences.getBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false)){        //THIS CHECKS WHETHER PLAY NEXT OR ADD TO QUEUE FEATURE WAS ACTIVE OR NOT
                    make_a_toast("QUEUE",true);
                    temp_array_list=copy_arraylist(save_and_load_array.load_array_for_user_created_playlist(getApplicationContext(),"PLAY_NEXT_AND_ADD_TO_QUEUE"));
                    is_add_to_queue_active=true;
                    is_play_next_active=false;
                }
                make_a_toast(String.format("Temp Array Size : %d",temp_array_list.size()),false);



                try {
                    config_miniplayer(preferences.getString(MINIPLAYER_SONG_NAME_KEY, ""),
                            preferences.getString(MINIPLAYER_PATH_KEY, ""),
                            preferences.getString(MINIPLAYER_ARTIST_NAME_KEY, ""),
                            preferences.getString(MINIPLAYER_ALBUM_NAME_KEY, ""),
                            preferences.getInt(MINIPLAYER_DURATION_KEY, 0),
                            preferences.getLong(MINIPLAYER_ALBUM_ART_KEY, 0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)) {
                    miniplayer.setVisibility(View.VISIBLE);
                    SHUFFLE_SETUP();

                } else {
                    miniplayer.setVisibility(View.GONE);

                }

            }else {
                make_a_toast("song_does not exits",false);
                miniplayer.setVisibility(View.GONE);
                editor.putString(MINIPLAYER_PATH_KEY,"");
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,false);
                editor.apply();
            }







        }

        //HANDLES AND CONFIGS SEEKBAR OF THE MUSIC-PLAYER
        music_player_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null && fromUser) {
                    media_player.get_media_player().seekTo(progress * 1000);
                    music_player_seek_bar.setProgress(progress);
                    music_player_Current_duration.setText(formated_time(media_player.get_media_player().getCurrentPosition()));
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaSession = new MediaSessionCompat(this, "f");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        PlaybackStateCompat.Builder playback = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE|
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                );
        mediaSession.setPlaybackState(playback.build());
        mediaSession.setActive(true);
        mediaSession.setCallback(new MediaSessionCompat.Callback() {



            @Override
            public void onPlay() {
                PLAY_AND_PAUSE();
                updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
            }

            @Override
            public void onPause() {
                PLAY_AND_PAUSE();
                updatePlaybackState(PlaybackStateCompat.STATE_PAUSED);
            }

            @Override
            public void onSkipToNext() {
                updatePlaybackState(PlaybackStateCompat.STATE_SKIPPING_TO_NEXT);

                DeviceConnectionChecker connectionChecker =new DeviceConnectionChecker(getApplicationContext());

                SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
                boolean audio_connectivity_permission=preferences.getBoolean(AUDIO_CONNECTIVITY_MODE_KEY,false);
                if (connectionChecker.areEarphonesConnected()) {
                    current_song_index -= 1;
                    Shuffled_Array_Index_POSITION -=1;


                }

                    String EVENT_NAME=preferences.getString(EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_NEXT_MODE,"PLAY_NEXT_SONG");
//                    switch (EVENT_NAME) {
//                        case "PLAY_NEXT_SONG":
//                            NEXT_SONG();
//                            break;
//                        case "PLAY_PREVIOUS_SONG":
//                            if(current_song_index<=0){
//                                play(temp_array_list.size()-1);
//                            }
//                            else {
//                                play(current_song_index-1);
//                            }
//                            break;
//                        case "PLAY_PREVIOUS_SONG_V2":
//                            PREVIOUS_SONG();
//                            break;
//                    }

                ACTIVATE_EVENT(EVENT_NAME);
            }

            @Override
            public void onSkipToPrevious() {
                updatePlaybackState(PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS);
                DeviceConnectionChecker connectionChecker =new DeviceConnectionChecker(getApplicationContext());
                SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
                if (connectionChecker.areEarphonesConnected()) {
                    current_song_index -= 1;
                    Shuffled_Array_Index_POSITION-=1;

                }

                    String EVENT_NAME=preferences.getString(EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_PREVIOUS_MODE,"PLAY_PREVIOUS_SONG_V2");
//                    switch (EVENT_NAME) {
//                        case "PLAY_NEXT_SONG":
//                            NEXT_SONG();
//                            break;
//                        case "PLAY_PREVIOUS_SONG":
//                            if(current_song_index<=0){
//                                play(temp_array_list.size()-1);
//                            }
//                            else {
//                                play(current_song_index-1);
//                            }
//                            break;
//                        case "PLAY_PREVIOUS_SONG_V2":
//                            PREVIOUS_SONG();
//                            break;
//                    }
                ACTIVATE_EVENT(EVENT_NAME);

            }
        });
        expanded=new RemoteViews(getPackageName(),R.layout.custom_notification);
//
//        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        int result=audioManager.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
//        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
//            if(permission_to_resume_the_song_from_telephony){
//                PLAY_AND_PAUSE();
//            }
//        }else {
//            media_player.resume_media_player();
//            PLAY_AND_PAUSE();
//
//        }
//        AudioManager.OnAudioFocusChangeListener audioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
//            @Override
//            public void onAudioFocusChange(int focusChange) {
//                make_a_toast(String.format("%d",focusChange));
//                switch (focusChange) {
//
//                    case AudioManager.AUDIOFOCUS_LOSS:
//                        make_a_toast("lost premanently");
//                        // Audio focus lost permanently, stop playback
//                        break;
//                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
//                        make_a_toast("lost temporaily");
//                        // Audio focus lost temporarily, pause playback
//                        break;
//                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                        make_a_toast("lost small");
//                        // Audio focus lost temporarily, but your app can continue playing at a lower volume
//                        break;
//                    case AudioManager.AUDIOFOCUS_GAIN:
//                        make_a_toast("regain");
//                        // Audio focus regained, resume playback
//                        break;
//                }
//            }
//        };

        tabLayout=findViewById(R.id.TABLAYOUT);
        viewPager=findViewById(R.id.VIEWPAGER);

        tabLayout.setupWithViewPager(viewPager);

        arrayList_for_fragments=new ArrayList<>();
        arrayList_for_fragments.add(new ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT(ALL_SONG_FRAGMENT,"SONGS"));
        arrayList_for_fragments.add(new ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT(ALL_PLAYLIST_FRAGMENT,"PLAYLIST"));
        arrayList_for_fragments.add(new ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT(ALL_ALBUM_FRAGMENT,"ALBUM"));
        arrayList_for_fragments.add(new ITEM_CLASS_OF_VIEW_PAGER_FRAGMENT(ALL_ARTIST_FRAGMENT,"ARTIST"));
//        adapter_for_view_pager.ADD_FRAGMENT(ALL_SONG_FRAGMENT,"SONGS");
//        adapter_for_view_pager.ADD_FRAGMENT(ALL_PLAYLIST_FRAGMENT,"PLAYLIST");
//        adapter_for_view_pager.ADD_FRAGMENT(ALL_ALBUM_FRAGMENT,"ALBUM");
//        adapter_for_view_pager.ADD_FRAGMENT(ALL_ARTIST_FRAGMENT,"ARTIST");
        adapter_for_view_pager=new VIEW_PAGER_ADAPTER(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,arrayList_for_fragments);

        viewPager.setAdapter(adapter_for_view_pager);

        viewPager.setCurrentItem(1);


        try {
            updateWidget(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public class SWIPE_LISTENER extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        permission_for_flicking=false;
                        if(permission_for_flicking){
                            if (diffX > 0) {

                                if(IS_MUSIC_PLAYER_ACTIVE){
                                    PREVIOUS_SONG();
                                }
                                if(IS_SETTING_INTERFACE_ACTIVE){
                                    count_for_option_button=1;
                                    SETTING_OPTION_BUTTON_FUNC();
                                }
                                if (IS_ALL_PLAYLIST_INTERFACE_ACTIVE&&!IS_MUSIC_PLAYER_ACTIVE) {
                                    ALL_SONG_INTERFACE_FUNC();
                                }
                                else if (IS_ALL_ALBUM_INTERFACE_ACTIVE&&!IS_MUSIC_PLAYER_ACTIVE){
                                    ALL_PLAYLIST_INTERFACE_FUNC();
                                } else if (IS_ALL_ARTIST_INTERFACE_ACTIVE&&!IS_MUSIC_PLAYER_ACTIVE) {
                                    ALL_ALBUM_INTERFACE_FUNC();
                                }

//                            onSwipeRight();
                            } else {

                                if(IS_MUSIC_PLAYER_ACTIVE){
                                    NEXT_SONG();
                                }
                                if (IS_ALL_PLAYLIST_INTERFACE_ACTIVE&&!IS_MUSIC_PLAYER_ACTIVE) {
                                    ALL_ALBUM_INTERFACE_FUNC();
                                }
                                else if (IS_ALL_ALBUM_INTERFACE_ACTIVE&&!IS_MUSIC_PLAYER_ACTIVE){
                                    ALL_ARTIST_INTERFACE_FUNC();
                                } else if (IS_ALL_SONGS_INTERFACE_ACTIVE&&!IS_MUSIC_PLAYER_ACTIVE) {
                                    ALL_PLAYLIST_INTERFACE_FUNC();
                                }


//                            onSwipeLeft();
                            }
                        }

                        result = true;
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
//                            onSwipeBottom();
                        } else {
//                            onSwipeTop();
                        }
                        result = true;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }


    private int ss=0;
    private void setupPhoneStateListener() {
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, @NonNull String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);

                RemoteViews expanded=new RemoteViews(getPackageName(),R.layout.custom_notification);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        // Call answered or outgoing call
                        // Incoming call
//                        if(!is_media_player_paused){

                            expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.play_icon);
                            miniplayer_pause_play_image_view.setImageResource(R.drawable.play_icon);
                            music_player_play_and_pause_image_view.setImageResource(R.drawable.play_icon);
                            is_media_player_paused = true;
                            media_player.pause_media_player();
                            handler.removeCallbacks(runnable2);
                            was_play_timer_activated_before=false;
                            check_user_inactivity();
                        try {
                            updateWidget(getApplicationContext());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

//
                            configure_notification(preferences.getString(MINIPLAYER_SONG_NAME_KEY, ""),preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,""),preferences.getLong(MINIPLAYER_ALBUM_ART_KEY,100));



//                        }
//                        Toast.makeText(MainActivity.this, "Incoming call: " + phoneNumber, Toast.LENGTH_SHORT).show();
                        break;


                    case TelephonyManager.CALL_STATE_IDLE:
                        ss+=1;
                        //CALL HUNG UP OR ENDED


                        if(permission_to_resume_the_song_from_telephony && ss>1 ){
                            make_a_toast("CALL ENDED",false);
                            miniplayer_pause_play_image_view.setImageResource(R.drawable.pause);
                            music_player_play_and_pause_image_view.setImageResource(R.drawable.pause);
                            expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.pause);
                            if(new_media_player_permission){
                                new_media_player_permission = false;
                                play(current_song_index);
                            }
                            else{
                                media_player.resume_media_player();
                            }
                            was_play_timer_activated_before=true;
                            handler.removeCallbacks(runnable_for_play_timer);
                            check_user_inactivity_for_play_timer();
                            permission_to_resume_the_song_from_telephony=true;
                            is_media_player_paused = false;
                            configure_notification(preferences.getString(MINIPLAYER_SONG_NAME_KEY, ""),preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,""),preferences.getLong(MINIPLAYER_ALBUM_ART_KEY,100));
                            try {
                                updateWidget(getApplicationContext());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
//                        Toast.makeText(MainActivity.this, "Call ended: " + phoneNumber, Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public class UNPLUGGED_RECEIVER extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
            if (intent.getAction() != null && intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                // Headphones unplugged
                if(!is_media_player_paused){

                    expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.play_icon);
                    miniplayer_pause_play_image_view.setImageResource(R.drawable.play_icon);
                    music_player_play_and_pause_image_view.setImageResource(R.drawable.play_icon);
                    is_media_player_paused = true;

                    if(is_app_active){
                        media_player.pause_media_player();
                    }
                    handler.removeCallbacks(runnable2);
                    was_play_timer_activated_before=false;
                    check_user_inactivity();
                    configure_notification(preferences.getString(MINIPLAYER_SONG_NAME_KEY, ""),preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,""),preferences.getLong(MINIPLAYER_ALBUM_ART_KEY,100));
                    try {
                        updateWidget(getApplicationContext());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        }
    }

    public  void ACTIVATE_EVENT(String EVENT_NAME){
        was_play_timer_activated_before=false;

        switch (EVENT_NAME) {
            case "PLAY_NEXT_SONG":
                NEXT_SONG();
                break;
            case "PLAY_PREVIOUS_SONG":
                if (Count_for_Shuffle_Button==1 &&(Count_for_Repeat_Button==2 ||( Count_for_Repeat_Button<1||Count_for_Repeat_Button>2) ) ) { //IF SHUFFLE IS ACTIVE
//                Random random =new Random();
//                int shuffle_song_index=Math.abs(random.nextInt()%temp_array_list.size());
//                play(shuffle_song_index);

                    if(Shuffled_Array_Index_POSITION<=0){
                        Shuffled_Array_Index_POSITION=Shuffled_Index_Arraylist.size()-1;
//                    make_a_toast(String.format("%d %d",Shuffled_Array_Index_POSITION,Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION)));
                        play(Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION));

                    }
                    else{
                        Shuffled_Array_Index_POSITION-=1;
//                    make_a_toast(String.format("%d %d",Shuffled_Array_Index_POSITION,Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION)));
                        play(Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION));



                    }
//
                }else{
                    if(current_song_index<=0){
                        play(temp_array_list.size()-1);
                    }
                    else {
                        play(current_song_index-1);
                    }
                }

                break;
            case "PLAY_PREVIOUS_SONG_V2":

                PREVIOUS_SONG();
                make_a_toast(String.format("%d",Shuffled_Array_Index_POSITION),false);

                break;
        }
    }


    public void load_array_for_all_playlists() {//ALL PLAYLIST INTERFACE
        set_all_active_flags_to_false();
//        is_all_album_interface_active=false;
//        is_album_playlist_active=false;
//        is_user_created_playlist_active=false;
//        is_recently_added_playlist_active=false;
        IS_ALL_PLAYLIST_INTERFACE_ACTIVE = true;

        load_data_into_array_list_for_recently_added();
        editor.putInt(TOTAL_SONGS_OF_RECENTLY_ADDED_KEY,TOTAL_SONGS_OF_RECENTLY_ADDED);
        editor.apply();
        SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
        boolean should_i_load_array = preferences.getBoolean(LOAD_ARRAY_PERMISSION_KEY_FOR_ALL_PLAYLIST, false);
//        should_i_load_array=false;
        if (should_i_load_array) {
            arrayList_for_all_playlists = save_and_load_array.load_array_for_all_playlist(this);
            load_album_art_for_all_playlist();

        }



//        recyclerView = findViewById(R.id.All_playlist_recyclerview_all);
//
//        adapter_for_all_playlist = new Playlist_recycler_item_Adapter_class(arrayList_for_all_playlists);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter_for_all_playlist);
//        recyclerView.setHasFixedSize(true);
        if (!should_i_load_array) {
            arrayList_for_all_playlists.add(new Playlists_recycler_item_class(100, "Recently Added", false));
//            adapter_for_all_playlist.notifyItemInserted(arrayList_for_all_playlists.size());
            arrayList_for_all_playlists.add(new Playlists_recycler_item_class(913,"Favourite",false));
//            adapter_for_all_playlist.notifyItemInserted(arrayList_for_all_playlists.size());
        }

//        adapter_for_all_playlist.set_ON_CLICK_Listener(new Playlist_recycler_item_Adapter_class.onCLICK_Listener() {
//
//
//            @Override
//            public void on_ITEM_click(int Position) throws IOException {
//                String PLAYLIST_NAME = arrayList_for_all_playlists.get(Position).getMPlaylist_name();
//                if(!PLAYLIST_NAME.equals("Favourite")){
//                    make_a_toast(String.format("PLAYLIST NAME : %s", PLAYLIST_NAME));
//                }
//                On_CLICK_PLAYLIST(Position);
//            }
//
//            @Override
//            public void more_on_ITEM_click(View view, int Position) {
//                SharedPreferences.Editor editor = preferences.edit();
//                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
//                popupMenu.inflate(R.menu.pop_all_playlist_menu);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        if (item.getItemId() == R.id.delete_playlist_pop_up_menu_of_all_playlists_interface) {
////                            make_a_toast(String.format("Item 1 : %s",item.getTitle().toString()));
//
//
//                            String not_deletable_playlist = arrayList_for_all_playlists.get(Position).getMPlaylist_name();
//                            if (!(not_deletable_playlist.equals("Recently Added")|| not_deletable_playlist.equals("Favourite"))) {
//
//                                boolean permission_to_check_the_playlist_is_not_empty=preferences.getBoolean(not_deletable_playlist,false);
//                                if(permission_to_check_the_playlist_is_not_empty &&
//                                        preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+not_deletable_playlist,false)){
//
//                                    editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+not_deletable_playlist,true);
//
//                                }else{
//                                    editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+not_deletable_playlist,false);
//                                }
//
//                                arrayList_for_all_playlists.remove(Position);
//                                adapter_for_all_playlist.notifyItemRemoved(Position);
//                                save_the_all_playlist_array();
//                            } else {
//                                make_a_toast("You Can't Delete This Playlist");
//                            }
//                            save_the_all_playlist_array();
//                            return true;
//                        } else if (item.getItemId() == R.id.play_the_playlist_pop_up_menu_of_all_playlist_interface) {
////                            String PLAYLIST_NAME=arrayList_for_all_playlists.get(Position).getMPlaylist_name();
////                            boolean Permission_For_Getting_Path=preferences.getBoolean(PLAYLIST_NAME,false);
////
////                            if(Permission_For_Getting_Path){
////                                ArrayList<Recently_added_recyclerview_elements_item_class> ARRAYLIST_FOR_GETTING_FIRST_SONG_OF_THE_PLAYLIST=save_and_load_array.load_array_for_user_created_playlist(getApplicationContext(),PLAYLIST_NAME);
////                                make_a_toast(String.format("%d",ARRAYLIST_FOR_GETTING_FIRST_SONG_OF_THE_PLAYLIST.size()));
////                                Recently_added_recyclerview_elements_item_class CURRENT_ITEM=ARRAYLIST_FOR_GETTING_FIRST_SONG_OF_THE_PLAYLIST.get(0);
////                                ADD_HOME_SCREEN_SHORTCUT(CURRENT_ITEM.getMpath(),PLAYLIST_NAME);
////
//////                                                                ADD_HOME_SCREEN_SHORTCUT();
////                            }
//
////                            ADD_HOME_SCREEN_SHORTCUT();
////                            make_a_toast(arrayList_for_all_playlists.get(Position).getMPlaylist_name());
////                            make_a_toast(String.format("Item 2 : %s", item.getTitle().toString()));
//
//
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//
//
//            }
//        });

        add_new_playlist_edit_text = findViewById(R.id.add_new_playlist_edit_text);

    }

//    public void DELETE_PLAYLIST(int Position){
//////                            make_a_toast(String.format("Item 1 : %s",item.getTitle().toString()));
////
////
//                            String not_deletable_playlist = arrayList_for_all_playlists.get(Position).getMPlaylist_name();
//                            make_a_toast(not_deletable_playlist,false);
//                            if (!(not_deletable_playlist.equals("Recently Added")|| not_deletable_playlist.equals("Favourite"))) {
//
//                                boolean permission_to_check_the_playlist_is_not_empty=preferences.getBoolean(not_deletable_playlist,false);
//                                if(permission_to_check_the_playlist_is_not_empty &&
//                                        preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+not_deletable_playlist,false)){
//
//                                    editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+not_deletable_playlist,true);
//
//                                }else{
//                                    editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+not_deletable_playlist,false);
//                                }
//
//                                arrayList_for_all_playlists.remove(Position);
//                                ALL_PLAYLIST_FRAGMENT.NOTIFY_PLAYLIST_REMOVED(Position);
////                                adapter_for_all_playlist.notifyItemRemoved(Position);
//                                save_the_all_playlist_array();
//                            } else {
//                                make_a_toast("You Can't Delete This Playlist",true);
//                            }
//                            save_the_all_playlist_array();
//
////                            return true;
//    }

    public void PLAY_PLAYLIST(int PLAYLIST_POSITION) throws Exception {

        String PLAYLIST_NAME=arrayList_for_all_playlists.get(PLAYLIST_POSITION).getMPlaylist_name();
        if(PLAYLIST_NAME.equals("Recently Added")&& arrayList_for_recently_added_playlist.size()!=0){
            CURRENT_INTERFACE_POSITION = 1;
            USER_CREATED_PLAYLIST_POSITION = 0;
            current_song_index = 0;
            ON_CLICK_PLAYLIST(PLAYLIST_POSITION);
            ACTIVATE_MUSIC_PLAYER_INTERFACE();
            set_all_active_flags_to_false();
            PERMISSION_TO_COPY_ARRAYLIST=true;

            IS_RECENTLY_ADDED_PLAYLIST_ACTIVE = true;
            was_play_timer_activated_before=false;
            is_add_to_queue_active=false;
            is_play_next_active=false;
            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
            editor.apply();
            play(0);
            SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
            Count_for_Repeat_Button=preferences.getInt(REPEAT_MODE_KEY,3);
            SET_REPEAT_MODE();
            SHUFFLE_SETUP();
        }


        else if(preferences.getBoolean(PLAYLIST_NAME,false)){
            ArrayList<Recently_added_recyclerview_elements_item_class> OLD_ARRAYLIST=save_and_load_array.load_array_for_user_created_playlist(this,PLAYLIST_NAME);
            ArrayList<Recently_added_recyclerview_elements_item_class> NEW_ARRAYLIST=Update_User_Created_Playlist.get_updated_user_created_array_list(arrayList_for_recently_added_playlist,OLD_ARRAYLIST);
            if(NEW_ARRAYLIST.size()>0){
                CURRENT_INTERFACE_POSITION = PLAYLIST_POSITION;
                USER_CREATED_PLAYLIST_POSITION = PLAYLIST_POSITION;
                Permission_To_Proceed=true;
                current_song_index =0;
                ON_CLICK_PLAYLIST(PLAYLIST_POSITION);
                ACTIVATE_MUSIC_PLAYER_INTERFACE();
                set_all_active_flags_to_false();
                CURRENT_PLAYING_PLAYLIST=PLAYLIST_NAME;
                IS_USER_CREATED_PLAYLIST_ACTIVE = true;

                was_play_timer_activated_before=false;
                PERMISSION_TO_COPY_ARRAYLIST=true;
                is_add_to_queue_active=false;
                is_play_next_active=false;
                editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
                editor.apply();
                play(0);

                SET_REPEAT_MODE();
                SHUFFLE_SETUP();

            }else{
                editor.putBoolean(PLAYLIST_NAME,false);
                editor.apply();
                make_a_toast("PLAYLIST IS EMPTY",true);
            }




        }else{
            make_a_toast("PLAYLIST IS EMPTY",true);
        }
//        make_a_toast(PLAYLIST_NAME,true);

    }


    public void PLAY_SONG_FROM_ALL_SONG_INTERFACE(int position) throws Exception {
        CURRENT_INTERFACE_POSITION = 0;
        current_song_index = position;
        set_all_active_flags_to_false();
//                is_all_album_interface_active=false;
//                is_recently_added_playlist_active=false;
//                is_user_created_playlist_active=false;
//                is_all_playlist_interface_active=false;
        IS_ALL_SONGS_INTERFACE_ACTIVE = true;
        ACTIVATE_MUSIC_PLAYER_INTERFACE();

        was_play_timer_activated_before=false;

        is_add_to_queue_active=false;
        is_play_next_active=false;

        editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
        editor.apply();
//        load_data_into_array_list_for_recently_added();
        PERMISSION_TO_COPY_ARRAYLIST=true;
        play(current_song_index);
        SHUFFLE_SETUP();
        SET_REPEAT_MODE();

    }

    public void ADD_SINGLE_SONG_FROM_ALL_SONG_INTERFACE(int position){
        if (arrayList_for_all_playlists.size() > 2) {
            is_single_song_selected = true;
            selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
//                                arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//                                for (int i = 1; i < arrayList_for_all_playlists.size()-1; i++) {
//                                    arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(), arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));
//                                }
            add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
//                                add_playlist__Interface();
            CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
            single_song_selected_playlist_position=0;
            MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,
                                                                                                true,
                                                                                                arrayList_for_add_playlist_without_recently_added,
                                                                                                true);
            musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
        } else {
            make_a_toast("THERE ARE NO PLAYLiST",true);
        }
    }

    public void load_album_art_for_all_playlist() {
        ArrayList<Playlists_recycler_item_class> temp_arrayList_for_loading_Album_art_to_all_playlist = new ArrayList<>();

        for (int i = 0; i < arrayList_for_all_playlists.size(); i++) {
            Playlists_recycler_item_class current_item_for_all_playlist_album_image = arrayList_for_all_playlists.get(i); //THESE IS USED TO GET AND SET THE ALBUM IMAGE FOR THE PLAYLIST PICTURE
            String PLAYLIST_NAME = current_item_for_all_playlist_album_image.getMPlaylist_name();                      //IT CONTAIN SYSTEM AND USER PLAYLIST NAMES

            SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);

            boolean permission_for_total_elements_in_playlist = preferences.getBoolean(PLAYLIST_NAME, false); //THESE CHECKS WHETHER THE PLAYLIST IS EMPTY OR NOT

            if (permission_for_total_elements_in_playlist) {
                if (PLAYLIST_NAME.equals("Recently Added")) {
                    temp_arrayList_for_loading_Album_art_to_all_playlist.add(new Playlists_recycler_item_class(arrayList_for_recently_added_playlist.get(0).getMalbum_art(), PLAYLIST_NAME, false));
                } else if (PLAYLIST_NAME.equals("Favourite")) {
                    temp_arrayList_for_loading_Album_art_to_all_playlist.add(new Playlists_recycler_item_class(913, PLAYLIST_NAME, false));

                } else {
                    ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_accessing_first_element = new ArrayList<>();
                    arrayList_for_accessing_first_element = save_and_load_array.load_array_for_user_created_playlist(this, PLAYLIST_NAME);  //THIS LOAD THE DESIRE PLAYLIST TO ACCESS THE FIRST SONG OF PLAYLIST
                    temp_arrayList_for_loading_Album_art_to_all_playlist.add(new Playlists_recycler_item_class(arrayList_for_accessing_first_element.get(0).getMalbum_art(), PLAYLIST_NAME, false));
                }
            } else {
                if(PLAYLIST_NAME.equals("Favourite")){
                    temp_arrayList_for_loading_Album_art_to_all_playlist.add(new Playlists_recycler_item_class(913, PLAYLIST_NAME, false));
                }else{
                    temp_arrayList_for_loading_Album_art_to_all_playlist.add(new Playlists_recycler_item_class(100, PLAYLIST_NAME, false));
                }
            }


        }
        arrayList_for_all_playlists = temp_arrayList_for_loading_Album_art_to_all_playlist;
        save_and_load_array.save_array_for_all_playlist(this, arrayList_for_all_playlists);


    }

    public void save_the_all_playlist_array() {

        save_and_load_array.save_array_for_all_playlist(this, arrayList_for_all_playlists);  // THIS SAVE THE ARRAYLIST WHICH CONTAINS ALL USER AND SYSTEM PLAYLIST
//        if(arrayList_for_all_playlists.size()==1){
//
//        }
    }

    @SuppressLint("Range")
    public void load_data_into_array_list_for_recently_added() {
        arrayList_for_recently_added_playlist = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.Media.ALBUM_ID

        };

//        String selection = MediaStore.Audio.Media.DATA + " LIKE ? AND " +
//                MediaStore.Audio.Media.MIME_TYPE + "=?";

        String selection = "(" +
                MediaStore.Audio.Media.DATA + " LIKE ? OR " +
                MediaStore.Audio.Media.DATA + " LIKE ? OR " +
                MediaStore.Audio.Media.DATA + " LIKE ?" +
                ") AND (" +
                MediaStore.Audio.Media.MIME_TYPE + "=? OR " +
                MediaStore.Audio.Media.MIME_TYPE + "=? OR " +
                MediaStore.Audio.Media.MIME_TYPE + "=? )";


//        String[] selectionArgs = new String[]{"%.mp3", "audio/mpeg"};

        String[] selectionArgs = new String[]{
                "%.m4a", "%.mp3", "%.ogg",  // File extensions
                "audio/mpeg", "audio/mp3", "audio/ogg"  // MIME types
        };
        String sortorder = MediaStore.Audio.AudioColumns.DATE_ADDED + " DESC";

        Cursor cursor = this.getContentResolver().query(uri, projection, selection, selectionArgs, sortorder);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                int durationIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
                duration = cursor.getInt(durationIndex);
                if (!isRingtone(filePath)) {

                    arrayList_for_recently_added_playlist.add(new Recently_added_recyclerview_elements_item_class(
                            cursor.getString(0),
                            filePath,
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getLong(5), false));

                }

            }
            cursor.close();


        }
        TOTAL_SONGS_OF_RECENTLY_ADDED=arrayList_for_recently_added_playlist.size();

//        arrayList_for_all_song_interface = arrayList_for_recently_added_playlist;
        arrayList_for_all_song_interface = load_all_songs_of_all_songs_interface_in_ascending.load_songs_in_ascending(arrayList_for_recently_added_playlist);

        if(arrayList_for_recently_added_playlist.size()!=0){      //BASICALLY THIS IS USE TO ADD SONGS WITH SAME ALBUM NAME IN arrayList_for_all_albums
            arrayList_for_all_albums = new ArrayList<>();
            for (int i = 0; i < arrayList_for_recently_added_playlist.size(); i++) {
                boolean is_duplicate = false;
                Recently_added_recyclerview_elements_item_class current_item_of_Recently_Added = arrayList_for_recently_added_playlist.get(i);

                for (int j = 0; j < arrayList_for_all_albums.size(); j++) {
                    all_album_interface_all_all_artist_interface_recyclerview_item_class current_item_of_All_ALbums = arrayList_for_all_albums.get(j);
                    if (current_item_of_Recently_Added.getMalbum_name()
                            .equals(current_item_of_All_ALbums.getMalbum_name())) {
                        is_duplicate = true;
                        break;
                    }

                }
                if (!is_duplicate) {
                    arrayList_for_all_albums.add(new all_album_interface_all_all_artist_interface_recyclerview_item_class(
                            current_item_of_Recently_Added.getMalbum_art(),
                            current_item_of_Recently_Added.getMalbum_name(),
                            total_elements(current_item_of_Recently_Added.getMalbum_name(), true)));
                }
            }
        }


        if(arrayList_for_recently_added_playlist.size()!=0){    //BASICALLY THIS IS USE TO ADD SONGS WITH SAME ARTIST NAME IN arrayList_for_all_artist_interface
            arrayList_for_all_artist_interface=new ArrayList<>();
            for (int i = 0; i < arrayList_for_recently_added_playlist.size(); i++) {
                Recently_added_recyclerview_elements_item_class recently_added_current_item = arrayList_for_recently_added_playlist.get(i);

                boolean is_duplicate = false;
                for (int j = 0; j < arrayList_for_all_artist_interface.size(); j++) {
                    all_album_interface_all_all_artist_interface_recyclerview_item_class all_artist_playlist_current_item = arrayList_for_all_artist_interface.get(j);
                    if (recently_added_current_item.getMartist()
                            .equals(all_artist_playlist_current_item.getMalbum_name())) {
                        is_duplicate = true;
                        break;
                    }
                }
                if (!is_duplicate) {
                    arrayList_for_all_artist_interface.add(new all_album_interface_all_all_artist_interface_recyclerview_item_class(
                            recently_added_current_item.getMalbum_art(),
                            recently_added_current_item.getMartist(),
                            total_elements(recently_added_current_item.getMartist(), false)));
                }
            }
        }






    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            // Handle play/pause button click
            make_a_toast("onkey down",false);
//            play_and_pause_func();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ON_CLICK_PLAYLIST(int position) throws IOException {          //THIS FUNCTION IS GET CALLED WHEN USER CLICKS A PLAYLIST FROM ALL PLAYLISTS FRAGMENT
        Playlists_recycler_item_class current_item = arrayList_for_all_playlists.get(position);
//        set_all_interface_button_visibility(false);

        if (current_item.getMPlaylist_name().equals("Recently Added")) {
//            back_function_protocol(false);
//            all_playlist_interface.setVisibility(View.GONE);
            recently_added_playlist();
        } else {
            user_created_playlist(arrayList_for_all_playlists.get(position).getMPlaylist_name(), position);
        }

    }


    public void make_a_toast(String message,boolean Permission) {
        if(PERMISSION_TO_DISPLAY_TOAST||Permission){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }



    @SuppressLint("Range")
    public void recently_added_playlist() throws IOException {
        ScrollView scrollView=findViewById(R.id.scrollView_of_recently_added_playlist);
        permission_for_flicking=false;
        setting_button.setVisibility(View.GONE);
        set_all_interface_button_visibility(false);
        set_all_active_flags_to_false();
//        is_all_album_interface_active=false;
//        is_album_playlist_active=false;
//        is_user_created_playlist_active=false;
//        is_all_playlist_interface_active=false;
        IS_RECENTLY_ADDED_PLAYLIST_ACTIVE = true;
//        load_data_into_array_list_for_recently_added();

        Recently_added_recyclerview_elements_item_class current = arrayList_for_recently_added_playlist.get(0);
        long Album_ID = current.getMalbum_art();
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);



        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(current.getMpath());

        byte[] albumArtBytes = retriever.getEmbeddedPicture();
        if (albumArtBytes != null) {
            Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


            File tempFile = createTempFile("album_art", ".jpg");
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
            Picasso.get().load(uri).into(recently_added_image_view);


//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

            // Now you have the album art bitmap, you can display it or process it further
        } else {
            Picasso.get().load(R.drawable.logo).into(recently_added_image_view);


        }

        retriever.release();



        CURRENT_PLAYLIST_POSITION_FOR_MORE_BUTTON = 0;

//        is_all_playlist_interface_active=false;
//        is_user_created_playlist_active=false;

        recently_added_playlist_constrain_layout.startAnimation(FADE_IN);
        recently_added_playlist_constrain_layout.setVisibility(View.VISIBLE);

        recently_added_playlist_name_text_view.setText(arrayList_for_all_playlists.get(0).getMPlaylist_name());
        recently_added_total_songs_text_view.setText(String.format("SONGS : %d", arrayList_for_recently_added_playlist.size()));

        recyclerView = findViewById(R.id.recently_added_recyclerview___);
        adapter_for_recently_added_playist = new recently_added_adapter_class(arrayList_for_recently_added_playlist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_for_recently_added_playist);
        adapter_for_recently_added_playist.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) throws Exception {

//                is_all_album_interface_active=false;
//                is_album_playlist_active=false;
//                is_user_created_playlist_active=false;
//                is_all_playlist_interface_active=false;
                if (!is_add_multiple_songs_selected_interface_is_long_pressed) {
//                    load_data_into_array_list_for_recently_added();
                    CURRENT_INTERFACE_POSITION = 1;
                    USER_CREATED_PLAYLIST_POSITION = 0;
                    current_song_index = position;
                    ACTIVATE_MUSIC_PLAYER_INTERFACE();
                    set_all_active_flags_to_false();
                    IS_RECENTLY_ADDED_PLAYLIST_ACTIVE = true;
                    was_play_timer_activated_before=false;
                    is_add_to_queue_active=false;
                    is_play_next_active=false;
                    editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
                    editor.apply();
                    PERMISSION_TO_COPY_ARRAYLIST=true;
                    play(position);
                    SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
                    Count_for_Repeat_Button=preferences.getInt(REPEAT_MODE_KEY,3);
                    SET_REPEAT_MODE();
                    SHUFFLE_SETUP();

                }

            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {

                CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.pop_menu_for_recently_added_songs_artist_album);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_item_1) {

                            if (arrayList_for_all_playlists.size() > 2) {
                                is_single_song_selected = true;

                                selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
//                                arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//                                for (int i = 1; i < arrayList_for_all_playlists.size()-1; i++) {
//                                    arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(), arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));
//                                }
                                add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
                                single_song_selected_playlist_position=1; //ITS INDICATES THAT A SINGLE SONG IS SELECTED FROM RECENTLY ADDED PLAYLIST
//                                add_playlist__Interface();

                                MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,true,arrayList_for_add_playlist_without_recently_added,true);
                                musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
                            } else {
                                make_a_toast("THERE ARE NO PLAYLIST",true);
                            }
                            return true;
                        } else if (item.getItemId() == R.id.add_to_queue&&preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)) {
                            is_add_to_queue_active=true;
                            Recently_added_recyclerview_elements_item_class Current=arrayList_for_recently_added_playlist.get(position);
                            make_a_toast(String.format("Song Name : %s",Current.getMsong_name()),true);
                            temp_array_list.add(new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();




//


                            return true;
                        }else if((item.getItemId()==R.id.play_next)&&(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false))){

                            is_add_to_queue_active=true;

                            Recently_added_recyclerview_elements_item_class Current=arrayList_for_recently_added_playlist.get(position);
                            make_a_toast(String.format("PNSong Name : %s",Current.getMsong_name()),true);
                            if(is_play_next_active){
                                if(PLAY_NEXT_SONG.equals(temp_array_list.get(current_song_index).getMsong_name())){
                                    PLAY_NEXT_INDEX+=1;
                                    make_a_toast("SONG IS SAME",true);

                                }else{
                                    PLAY_NEXT_INDEX=current_song_index+1;
                                    PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                    make_a_toast("SONG Changed",true);
                                }

                            }else{
                                PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                PLAY_NEXT_INDEX=current_song_index+1;
                                is_play_next_active=true;
                            }
                            temp_array_list.add(PLAY_NEXT_INDEX,new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
//                            load_data_into_array_list_for_recently_added();
                            return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();

            }

            @Override
            public void on_ITEM_LONG_CLICKED(int Long_pressed_song) {
                is_add_multiple_songs_selected_interface_is_long_pressed = true;
                arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist = new ArrayList<>();
                arrayList_for_add_multiple_songs_to_multiple_playlist = new ArrayList<>();
                add_multiple_songs_to_multiple_playlist_interface(Long_pressed_song);

            }


        });
        scrollView.scrollTo(0,recently_added_image_view.getTop());


    }

    public void back_button_of_add_multiple_songs_to_multiple_playlists(View view) {
        is_add_multiple_songs_selected_interface_is_long_pressed = false;
        add_multiple_songs_to_multiple_playlist.setVisibility(View.GONE);
        recently_added_playlist_constrain_layout.setVisibility(View.VISIBLE);
        miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
    }

    public void add_multiple_songs_to_multiple_playlist_activate_add_to_playlist_bottom_class(View view) {
        is_single_song_selected = false;
//        is_single_song_selected_from_all_song_interface = false;

        if (arrayList_for_all_playlists.size() > 2) {
//            arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//            for (int i = 1; i < arrayList_for_all_playlists.size()-1; i++) {
//                arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(),
//                        arrayList_for_all_playlists.get(i).getMPlaylist_name(),
//                        false));
//            }
            add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
            single_song_selected_playlist_position=1;
            MUSIC_PLAYER_BOTTOM_CLASS add_multiple_songs_to_multiple_playlist_bottom_class = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,true,arrayList_for_add_playlist_without_recently_added,true);
            add_multiple_songs_to_multiple_playlist_bottom_class.show(getSupportFragmentManager(), "tag");

        }


    }

    public void add_multiple_songs_to_multiple_playlist_interface(int FOCUS_SONG_POSITION) {

        arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist = new ArrayList<>();
        arrayList_for_add_multiple_songs_to_multiple_playlist = new ArrayList<>();
        recently_added_playlist_constrain_layout.setVisibility(View.GONE);
        music_player.setVisibility(View.GONE);
        miniplayer.setVisibility(View.GONE);
        add_multiple_songs_to_multiple_playlist.setVisibility(View.VISIBLE);


        arrayList_for_add_multiple_songs_to_multiple_playlist = arrayList_for_recently_added_playlist;
        for (int i = 0; i < arrayList_for_recently_added_playlist.size(); i++) {
            Recently_added_recyclerview_elements_item_class current_item = arrayList_for_recently_added_playlist.get(i);
            arrayList_for_add_multiple_songs_to_multiple_playlist.set(i,
                    new Recently_added_recyclerview_elements_item_class(current_item.getMsong_name(),
                                                                        current_item.getMpath(),
                                                                        current_item.getMartist(),
                                                                        current_item.getMalbum_name(),
                                                                        current_item.getMduration(),
                                                                        current_item.getMalbum_art(),
                                                                        false));
        }


        recyclerView = findViewById(R.id.add_multiple_songs_to_multiple_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter_for_add_multiple_songs_to_multiple_playlist = new adapter_for_add_multiple_songs_to_multiple_playlist(arrayList_for_add_multiple_songs_to_multiple_playlist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter_for_add_multiple_songs_to_multiple_playlist);
        recyclerView.scrollToPosition(FOCUS_SONG_POSITION);

        adapter_for_add_multiple_songs_to_multiple_playlist.set_on_clicked_listener(new adapter_for_add_multiple_songs_to_multiple_playlist.SET_ON_ITEM_CLICKED_LISTENER() {
            @Override
            public void MORE_BUTTON_WHICH_IS_CHECKBOX_ON_CLICKED_LISTENER(int POSITION) {
//                make_a_toast(String.format("Clicked Song : %s",arrayList_for_recently_added_playlist.get(POSITION).getMsong_name()));
                arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist.add(POSITION);//IT is REDUCTANT!!!!
            }
        });


    }

    public void add_playlist__Interface() {
        selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
        make_a_toast(String.format("SELECTED SONG : %s", arrayList_for_recently_added_playlist.get(CURRENT_SONG_POSITION_FOR_MORE_BUTTON).getMsong_name()),true);
        miniplayer.setVisibility(View.GONE);
        recently_added_playlist_constrain_layout.setVisibility(View.GONE);
        add_song_to_playlist_interface.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView_add_song_to_playlist);
        arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
        for (int i = 1; i < arrayList_for_all_playlists.size(); i++) {
            arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(100, arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));

        }

        adapter_for_add_playlist_interface = new adapter_class_for_add_song_to_playlists(arrayList_for_add_playlist_without_recently_added);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter_for_add_playlist_interface);
        recyclerView.setLayoutManager(layoutManager);
        adapter_for_add_playlist_interface.set_ON_CLICK_Listener(new Playlist_recycler_item_Adapter_class.onCLICK_Listener() {
            @Override
            public void on_ITEM_click(int Position) {

            }

            @Override
            public void more_on_ITEM_click(View view, int Position) {
                selected_playlist_from_bottom_fragment_position_array_list.add(Position);
//                playlist_position_array[index]=Position;
//                index+=1;


            }
        });


    }

    public void on_add_playlist_button(View view) {
        add_song_to_playlist_interface.setVisibility(View.GONE);
        miniplayer.setVisibility(View.VISIBLE);
        recently_added_playlist_constrain_layout.setVisibility(View.VISIBLE);
        String result = "";
        for (int i = 0; i < index; i++) {
            result = result + String.format("%d ", playlist_position_array[i]);
        }
//        make_a_toast(String.format("Clicked Elements Are : %s",result));

//        check_the_element_which_are_meant_to_be_selected(playlist_position_array_list,playlist_position_array_list.size());
    }

    //    public void check_the_element_which_are_meant_to_be_selected(ArrayList<Integer> position_array,int len){
//        ArrayList<Integer> final_playlist_position_array=new ArrayList<>();
//        for(int i=0;i<len;i++){
//            int count_for_position=0;
//            for(int j=0;j<len;j++){
//                if(position_array.get(i)==position_array.get(j)){
//                    count_for_position+=1;
//                }
//            }
//            if(count_for_position%2!=0 ){
////                make_a_toast(String.format("ELEMENT : %d\nCOUNT : %d",position_array.get(i),count_for_position));
//                final_playlist_position_array.add(position_array.get(i));
//            }
//        }
//        make_unique_array(final_playlist_position_array,final_playlist_position_array.size());
//
//
//
//    }
//    public void make_unique_array(ArrayList<Integer> not_unique_array,int len){
//        ArrayList<Integer>  unique_selected_playlist_position_array=new ArrayList<>();
//
//
//        for(int i=0;i<len;i++){
//            boolean is_duplicate=false;
//            for(int j=0;j<unique_selected_playlist_position_array.size();j++){
//                if(not_unique_array.get(i)==unique_selected_playlist_position_array.get(j)){
//                    is_duplicate=true;
//                    break;
//                }
//            }
//            if(!is_duplicate){
//                unique_selected_playlist_position_array.add(not_unique_array.get(i));
//
//
//            }
//        }
//        String res="";
//        for(int m=0;m<unique_selected_playlist_position_array.size();m++){
//            res=res+" "+arrayList_for_add_multiple_songs_to_multiple_playlist.get(unique_selected_playlist_position_array.get(m)).getMsong_name();
//            make_a_toast(String.format("Selected Song : %s",arrayList_for_add_multiple_songs_to_multiple_playlist.get(unique_selected_playlist_position_array.get(m)).getMsong_name()));
//        }
////        make_a_toast(res);
//        make_a_toast(String.format("len of unique:%d",unique_selected_playlist_position_array.size()));
//
////        add_song_to_desired_playlist_using_unique_selected_playlist_position_array(unique_selected_playlist_position_array,unique_selected_playlist_position_array.size());
//        add_multiple_songs_to_desired_playlist_using_unique_selected_playlist_position_array(unique_selected_playlist_position_array,unique_selected_playlist_position_array.size(),
//                playlist_position_array_list,playlist_position_array_list.size());
//
//    }
    public void add_multiple_songs_to_desired_playlist_using_unique_selected_playlist_position_array(ArrayList<Integer> selected_song_position_arraylist, int selected_song_position_arraylist_len, ArrayList<Integer> selected_playlist_position_array, int len) {

        for (int i = 0; i < selected_song_position_arraylist_len; i++) {
            CURRENT_SONG_POSITION_FOR_MORE_BUTTON = selected_song_position_arraylist.get(i);  //IT STORES SONG POSITION WHICH IS SELECTED
            add_single_song_to_desired_playlist_using_unique_selected_playlist_position_array(selected_playlist_position_array, len);
        }
    }

    public void add_single_song_to_desired_playlist_using_unique_selected_playlist_position_array(ArrayList<Integer> selected_playlist_position_array, int len) {
        for (int i = 0; i < len; i++) {

            ArrayList<Recently_added_recyclerview_elements_item_class> temp_array_for_adding_song_to_playlist = new ArrayList<>();

            String SELECTED_PLAYLIST_NAME = arrayList_for_add_playlist_without_recently_added.get(selected_playlist_position_array.get(i)).getMPlaylist_name();
            SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
            boolean permission_to_load_array = preferences.getBoolean(SELECTED_PLAYLIST_NAME, false);   //CHECKS WHETHER PLAYLIST IS EMPTY OR NOT
            if (permission_to_load_array) {
                temp_array_for_adding_song_to_playlist = save_and_load_array.load_array_for_user_created_playlist(this, SELECTED_PLAYLIST_NAME);

            } else {
                temp_array_for_adding_song_to_playlist = new ArrayList<>();
            }
            Recently_added_recyclerview_elements_item_class CURRENT_ITEM=null;
            if (single_song_selected_playlist_position==0) {     //FROM ALL SONGS
                CURRENT_ITEM = arrayList_for_all_song_interface.get(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);
            }
            else if(single_song_selected_playlist_position==1) { //FROM RECENTLY ADDED PLAYLIST
                CURRENT_ITEM = arrayList_for_recently_added_playlist.get(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);
            }
            else if(single_song_selected_playlist_position==2){ //FROM ALBUM PLAYLIST
                CURRENT_ITEM=load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist,ALBUM_OR_ARTIST_NAME,true).get(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);
            }
            else if (single_song_selected_playlist_position==3){//FROM ARTIST PLAYLIST
                CURRENT_ITEM=load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist,ALBUM_OR_ARTIST_NAME,false).get(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);

            } else if (single_song_selected_playlist_position==613) {  //FROM MUSIC PLAYER'S ADD PLAYLIST BUTTON
                CURRENT_ITEM=temp_array_list.get(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);
            }
            boolean permission =check_whether_song_or_playlist_already_exists.check_the_song(temp_array_for_adding_song_to_playlist,//SELECTED PLAYLIST
                                                                                            CURRENT_ITEM.getMsong_name(),          //SELECTED SONG NAME
                                                                                            CURRENT_ITEM.getMpath());             //SELECTED SONG PATH

            if(permission){        //IF SONG DOES NOT EXISTS IN THE PLAYLIST
                temp_array_for_adding_song_to_playlist.add(new Recently_added_recyclerview_elements_item_class(
                        CURRENT_ITEM.getMsong_name(),
                        CURRENT_ITEM.getMpath(),
                        CURRENT_ITEM.getMartist(),
                        CURRENT_ITEM.getMalbum_name(),
                        CURRENT_ITEM.getMduration(),
                        CURRENT_ITEM.getMalbum_art(), false));

                save_and_load_array.save_array_for_user_created_playlist(this, temp_array_for_adding_song_to_playlist, SELECTED_PLAYLIST_NAME);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(SELECTED_PLAYLIST_NAME, true);

                editor.apply();
            }
            else {    //IF EXISTS
                make_a_toast(String.format("%s Already Exists",CURRENT_ITEM.getMsong_name()),true);
            }


        }


    }

    public void user_created_playlist(String PLAYLIST_NAME, int PLAYLIST_POSITION) throws IOException {
        ScrollView scrollView=findViewById(R.id.scrollview_of_user_created_playlist);
        permission_for_flicking=false;
        if(PLAYLIST_NAME.equals("Favourite")){
            IS_FAVOURITE_PLAYLIST_ACTIVE =true;

        }
        back_function_protocol(false);
        setting_button.setVisibility(View.GONE);
        arrayList_for_user_created_playlist = new ArrayList<>();
        CURRENT_PLAYLIST_POSITION_FOR_MORE_BUTTON = PLAYLIST_POSITION;
        set_all_active_flags_to_false();
//        is_all_album_interface_active=false;
//        is_album_playlist_active=false;
//        is_all_playlist_interface_active=false;
//        is_recently_added_playlist_active=false;
        IS_USER_CREATED_PLAYLIST_ACTIVE = true;


//        make_a_toast(arrayList_for_all_playlists.get(PLAYLIST_POSITION).getMPlaylist_name());
        SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
        boolean should_i_load_user_created_playlist = preferences.getBoolean(PLAYLIST_NAME, false);
        if (should_i_load_user_created_playlist) {
//            arrayList_for_user_created_playlist=

            arrayList_for_user_created_playlist = Update_User_Created_Playlist.get_updated_user_created_array_list(arrayList_for_recently_added_playlist, save_and_load_array.load_array_for_user_created_playlist(this, PLAYLIST_NAME));
            make_a_toast("should_i_load_user_created_playlist : TRUE",false);

            Recently_added_recyclerview_elements_item_class current = arrayList_for_user_created_playlist.get(0);
            long Album_ID = current.getMalbum_art();
            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_ID);



            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(current.getMpath());

//            ADD_HOME_SCREEN_SHORTCUT(current.getMpath(),PLAYLIST_NAME);

            byte[] albumArtBytes = retriever.getEmbeddedPicture();
            if (albumArtBytes != null) {
                Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


                File tempFile = createTempFile("album_art", ".jpg");
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
                Picasso.get().load(uri).into(user_created_image_view);



//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

                // Now you have the album art bitmap, you can display it or process it further
            } else {
                // No album art available
                Picasso.get().load(R.drawable.logo).into(user_created_image_view);

            }

            retriever.release();



        } else {
            make_a_toast("should_i_load_user_created_playlist : FALSE",false);
            arrayList_for_user_created_playlist = new ArrayList<>();
//            if(PLAYLIST_NAME.equals("K-POP")){
//                make_a_toast("temp",false);
//                put_temp_songs();
//            }

            Picasso.get().load(R.drawable.logo).into(user_created_image_view);

        }
        if(!PLAYLIST_NAME.equals("Favourite")){
            if(preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+PLAYLIST_NAME,false)){
                make_a_toast("Backup Found",true);
            }else {
                make_a_toast("Backup Didn't Found",true);
            }
        }
        user_created_playlist_contrain_layout.startAnimation(FADE_IN);
        user_created_playlist_contrain_layout.setVisibility(View.VISIBLE);

        user_created_playlist_name_text_view.setText(PLAYLIST_NAME);
        user_created_total_songs_text_view.setText(String.format("SONGS : %d", arrayList_for_user_created_playlist.size()));
        recyclerView = findViewById(R.id.user_created_recyclerview);
        recyclerView.setHasFixedSize(true);
//        load_data_into_array_list_for_recently_added();
//        arrayList_for_user_created_playlist=Update_User_Created_Playlist.get_updated_user_created_array_list(save_and_load_array.load_array_for_user_created_playlist(this,PLAYLIST_NAME),arrayList_for_recently_added_playlist);
        adapter_for_user_created_playlist = new recently_added_adapter_class(arrayList_for_user_created_playlist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_for_user_created_playlist);

//        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                Collections.swap(arrayList_for_user_created_playlist, target.getAbsoluteAdapterPosition(), viewHolder.getAbsoluteAdapterPosition());
//                adapter_for_user_created_playlist.notifyItemMoved(target.getAbsoluteAdapterPosition(), viewHolder.getAbsoluteAdapterPosition());
////                save_drag_n_drop(PLAYLIST_NAME);
//                return true;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//            }
//        };
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);
        adapter_for_user_created_playlist.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) throws Exception {

//                is_all_album_interface_active=false;
//                is_album_playlist_active=false;
//                is_all_playlist_interface_active=false;
//                is_recently_added_playlist_active=false;
                if(!is_edit_song_position_is_long_pressed){
                    is_add_to_queue_active=false;
                    CURRENT_INTERFACE_POSITION = 1;
                    USER_CREATED_PLAYLIST_POSITION = PLAYLIST_POSITION;
                    Permission_To_Proceed=true;
                    current_song_index = position;
                    ACTIVATE_MUSIC_PLAYER_INTERFACE();
                    set_all_active_flags_to_false();
                    CURRENT_PLAYING_PLAYLIST=PLAYLIST_NAME;
                    IS_USER_CREATED_PLAYLIST_ACTIVE = true;
                    was_play_timer_activated_before=false;

                    is_play_next_active=false;
                    is_add_to_queue_active=false;
                    editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
                    editor.apply();
                    PERMISSION_TO_COPY_ARRAYLIST=true;
                    play(position);

                    SET_REPEAT_MODE();
                    SHUFFLE_SETUP();

                }
            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                SharedPreferences.Editor editor= preferences.edit();
                CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.user_created_playlist_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_user_created_playlist_remove_song) {
                            arrayList_for_user_created_playlist.remove(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);
                            adapter_for_user_created_playlist.notifyItemRemoved(CURRENT_SONG_POSITION_FOR_MORE_BUTTON);
                            user_created_total_songs_text_view.setText(String.format("SONGS : %d", arrayList_for_user_created_playlist.size()));

                            if (arrayList_for_user_created_playlist.size() != 0 ) {
                                save_user_playlist(arrayList_for_user_created_playlist, PLAYLIST_NAME);
                                editor.putBoolean(PLAYLIST_NAME,true);


                            } else {
                                editor.putBoolean(PLAYLIST_NAME, false);
                                editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+PLAYLIST_NAME,false);
                            }
                            editor.apply();


                            return true;
                        } else if((item.getItemId()==R.id.play_next)&&(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false))){

                            is_add_to_queue_active=true;

                            Recently_added_recyclerview_elements_item_class Current=arrayList_for_user_created_playlist.get(position);
                            make_a_toast(String.format("PNSong Name : %s",Current.getMsong_name()),true);
                            if(is_play_next_active){
                                if(PLAY_NEXT_SONG.equals(temp_array_list.get(current_song_index).getMsong_name())){
                                    PLAY_NEXT_INDEX+=1;
                                    make_a_toast("SONG IS SAME",true);

                                }else{
                                    PLAY_NEXT_INDEX=current_song_index+1;
                                    PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                    make_a_toast("SONG Changed",true);
                                }

                            }else{
                                PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                PLAY_NEXT_INDEX=current_song_index+1;
                                is_play_next_active=true;
                            }
                            temp_array_list.add(PLAY_NEXT_INDEX,new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
//                            load_data_into_array_list_for_recently_added();
                            return true;
                        }
                        else if (item.getItemId()==R.id.add_to_queue) {
                            is_add_to_queue_active=true;



                            Recently_added_recyclerview_elements_item_class Current=arrayList_for_user_created_playlist.get(position);
                            make_a_toast(String.format("Song Name : %s",Current.getMsong_name()),true);
                            temp_array_list.add(new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
//                            load_data_into_array_list_for_recently_added();
                            arrayList_for_user_created_playlist=Update_User_Created_Playlist.get_updated_user_created_array_list(save_and_load_array.load_array_for_user_created_playlist(getApplicationContext(),PLAYLIST_NAME),arrayList_for_recently_added_playlist);

                            return true;

                        }
                        return false;
                    }

                });
                popupMenu.show();
            }

            @Override
            public void on_ITEM_LONG_CLICKED(int LONG_PRESSED_SONG_POSITION) {
//                make_a_toast(String.format("LONG PRESSED SONG : %s",arrayList_for_user_created_playlist.get(LONG_PRESSED_SONG_POSITION).getMsong_name()));
                make_a_toast("EDIT INTERFACE ACTIVATED",true);
                is_edit_song_position_is_long_pressed=true;
                EDIT_SONG_POSITION_INTERFACE_FOR_USER_CREATED_PLAYLIST(PLAYLIST_NAME,PLAYLIST_POSITION,arrayList_for_user_created_playlist,LONG_PRESSED_SONG_POSITION);

            }


        });
//


        if (arrayList_for_user_created_playlist.size() > 0) {
            make_a_toast("USER ARRAY IS SAVED SUCCESSFULLY",false);
            SharedPreferences preferences1 = getSharedPreferences("preff", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences1.edit();
            editor.putBoolean(PLAYLIST_NAME, true);
            editor.apply();
            save_and_load_array.save_array_for_user_created_playlist(this, arrayList_for_user_created_playlist, PLAYLIST_NAME);


        }else{
            make_a_toast("USER ARRAY IS SAVED SUCCESSFULLY",false);
            SharedPreferences preferences1 = getSharedPreferences("preff", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences1.edit();
            editor.putBoolean(PLAYLIST_NAME, false);
            editor.apply();
        }
        scrollView.scrollTo(0,user_created_image_view.getTop());


    }


    public void EDIT_SONG_POSITION_INTERFACE_FOR_USER_CREATED_PLAYLIST(String USER_CREATED_PLAYLIST_NAME,int USER_CREATED_PLAYLIST_POSITION,ArrayList<Recently_added_recyclerview_elements_item_class> arrayList,int SONG_FOCUS_POSITION){
        miniplayer.setVisibility(View.GONE);
        music_player.setVisibility(View.GONE);

        array_for_edit_song_position=arrayList;
        Activate_User_Playlist_From_Edit_Song_Position_Playlist_Name=USER_CREATED_PLAYLIST_NAME;
        Activate_User_Playlist_From_Edit_Song_Position_Playlist_Position=USER_CREATED_PLAYLIST_POSITION;
        set_all_visibility_off();
        set_all_interface_button_visibility(false);
        set_all_active_flags_to_false();
//        set_all_interface_image_off();
        edit_song_position_of_user_created_playlist_interface.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.edit_song_position_of_user_created_playlist_interface_recyclerview);
        recyclerView.setHasFixedSize(true);
        ADAPTER_FOR_EDIT_SONG_POSITION adapter=new ADAPTER_FOR_EDIT_SONG_POSITION(array_for_edit_song_position);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(SONG_FOCUS_POSITION);
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Collections.swap(array_for_edit_song_position, target.getAbsoluteAdapterPosition(), viewHolder.getAbsoluteAdapterPosition());
                adapter.notifyItemMoved(target.getAbsoluteAdapterPosition(), viewHolder.getAbsoluteAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


//        adapter_for_edit_song_position_of_user_created.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
//            @Override
//            public void on_ITEM_Clicked(int position) {
//            }
//
//            @Override
//            public void more_button_ITEM_Clicked(View view, int position) {
//            }
//
//            @Override
//            public void on_ITEM_LONG_CLICKED(int Long_pressed_song) {
//            }
//        });

    }
    public void BACK_BUTTON_OF_EDIT_SONG_POSITION(View view) throws IOException {

        is_edit_song_position_is_long_pressed=false;
        miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
        music_player.setVisibility(View.GONE);
        edit_song_position_of_user_created_playlist_interface.setVisibility(View.GONE);
        user_created_playlist_contrain_layout.setVisibility(View.VISIBLE);
        save_user_playlist(array_for_edit_song_position,Activate_User_Playlist_From_Edit_Song_Position_Playlist_Name);
        user_created_playlist(Activate_User_Playlist_From_Edit_Song_Position_Playlist_Name,Activate_User_Playlist_From_Edit_Song_Position_Playlist_Position);
    }
    private void save_user_playlist(ArrayList<Recently_added_recyclerview_elements_item_class> array_list_for_user_created_playlist, String PLAYLIST_NAME) {
        save_and_load_array.save_array_for_user_created_playlist(this, array_list_for_user_created_playlist, PLAYLIST_NAME);
    }

    private void save_drag_n_drop(String PLAYLIST_NAME) {
        save_and_load_array.save_array_for_user_created_playlist(this, arrayList_for_user_created_playlist, PLAYLIST_NAME);
    }


//    public void add_new_element_to_user_created_arraylist(int POSITION){
//        Recently_added_recyclerview_elements_item_class cur_item=arrayList_for_recently_added_playlist.get(POSITION);
//        String song_name1 =cur_item.getMsong_name();
//        String song_name2 =cur_item.getMpath();
//        String song_name3=cur_item.getMartist();
//        String song_name4 =cur_item.getMalbum_name();
//        int song_name5 =cur_item.getMduration();
//        long song_name6 =cur_item.getMalbum_art();
//        String STRING_ID=arrayList_for_all_playlists.get(1).getMPlaylist_name();
//        make_a_toast(STRING_ID);
//
////        make sure that when user does not open user_created _playlist and directly add playlist the should_i_load_array_list will does not have nothing
////        when first  time user open its playlist make sure to save a variable is_this_user_created_playlist_open_for_first_time
//
//        arrayList_for_user_created_playlist=save_and_load_array.load_array_for_user_created_playlist(this,STRING_ID);
//            make_a_toast(String.format("%d",arrayList_for_user_created_playlist.size()));
//        arrayList_for_user_created_playlist.add(new Recently_added_recyclerview_elements_item_class(song_name1,song_name2,song_name3,song_name4,song_name5,song_name6));
//        save_and_load_array.save_array_for_user_created_playlist(this,arrayList_for_user_created_playlist,STRING_ID);
//        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
//        SharedPreferences.Editor editor= preferences.edit();
//        editor.putBoolean(STRING_ID,true);
//        editor.apply();
//
//    }
    public Recently_added_recyclerview_elements_item_class item;
    public void play(int position) {
        try {

            miniplayer_pause_play_image_view.setImageResource(R.drawable.pause);
            music_player_play_and_pause_image_view.setImageResource(R.drawable.pause);

            //make sure  that  it not  only play for recently added and user created playlist but,
            // also for album playlist and artist playlist
            //edit: i made sure that :)

            if((!is_add_to_queue_active)&&(!is_play_next_active)){
//                load_data_into_array_list_for_recently_added();
                if(PERMISSION_TO_COPY_ARRAYLIST){
                    make_a_toast("PERMISSION GRANTED",true);
                    if (CURRENT_INTERFACE_POSITION == 1) {
                        if (USER_CREATED_PLAYLIST_POSITION == 0) {
                            temp_array_list = copy_arraylist(arrayList_for_recently_added_playlist);
                            make_a_toast(String.format("SIZE:%d",arrayList_for_recently_added_playlist.size()),true);
                            make_a_toast("RECENTLY ADDED PLAYLIST IS LOADED",false);

                        } else {
                            temp_array_list = copy_arraylist(save_and_load_array.load_array_for_user_created_playlist(this, arrayList_for_all_playlists.get(USER_CREATED_PLAYLIST_POSITION).getMPlaylist_name()));
                            make_a_toast(String.format("SIZE:%d",temp_array_list.size()),true);
                            make_a_toast(String.format("USER CREATED PLAYLIST IS LOADED : %s", arrayList_for_all_playlists.get(USER_CREATED_PLAYLIST_POSITION).getMPlaylist_name()),false);
                        }
                    } else if (CURRENT_INTERFACE_POSITION == 2) {
                        make_a_toast("ALBUM PLAYLIST",false);
                        temp_array_list = copy_arraylist(load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist, CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME, true));

                    } else if (CURRENT_INTERFACE_POSITION == 3) {
                        make_a_toast("ARTIST PLAYLIST",false);
                        temp_array_list = copy_arraylist(load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist, CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME, false));
                    } else if (CURRENT_INTERFACE_POSITION == 0) {
                        make_a_toast("ALL SONGS PLAYLIST",false);
                        temp_array_list = copy_arraylist(load_all_songs_of_all_songs_interface_in_ascending.load_songs_in_ascending(arrayList_for_recently_added_playlist));
                    }
                }else{
                    make_a_toast("PERMISSION DENIED",true);
                }

            }else{
                make_a_toast(String.format("SIZE::%d",temp_array_list.size()),true);

            }


            item = temp_array_list.get(position);
            current_song_index = position;
            if(!was_service_started_before){
                was_service_started_before=true;

                activate_notification(item.getMsong_name(),item.getMartist(),item.getMalbum_art());
                configure_notification(item.getMsong_name(),item.getMartist(),item.getMalbum_art());

            }
            if(!was_play_timer_activated_before){
                was_play_timer_activated_before=true;
                handler.removeCallbacks(runnable_for_play_timer);
                check_user_inactivity_for_play_timer();
            }
            Uri uri = Uri.parse(item.getMpath());
            make_a_toast(String.format("%s",item.getMsong_name()),true);
//

            media_player.get_media_player().reset();
            player = media_player.get_media_player();
            if (player.isPlaying()) {
                player.release();

            }
            player.setDataSource(getApplicationContext(), uri);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    store_miniplayer_permission_data();
                    make_a_toast("data_added",false);
                    editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,true);
                    editor.apply();
                    try {
                        updateWidget(getApplicationContext());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        SET_SHUFFLE_MODE();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    permission_for_app_inactivity=false;
                    was_music_player_played = true;
                    permission_to_resume_the_song_from_telephony=true;
                    new_media_player_permission = false;
                    is_media_player_paused = false;
                    play_time();
//                    ACTIVATE_MUSIC_PLAYER_INTERFACE();
                    music_player_total_duration_text_view.setText(formated_time(item.getMduration()));
                    music_player_seek_bar.setProgress(0);
                    music_player_seek_bar.setMax(item.getMduration() / 1000);

                    configure_notification(item.getMsong_name(),item.getMartist(),item.getMalbum_art());

                    try {
                        config_miniplayer(item.getMsong_name(), item.getMpath(), item.getMartist(), item.getMalbum_name(), item.getMduration(), item.getMalbum_art());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    boolean does_the_song_exist=check_the_song_is_in_favourite(item.getMsong_name(), item.getMpath(), item.getMartist(), item.getMalbum_name(), item.getMduration(), item.getMalbum_art());
                    if(does_the_song_exist){
                        Favorite_Button.setSelected(true);
                        Favorite_Button.setImageResource(R.drawable.favorite_on);
                        permission_for_miniplayer_widget_favorite=true;
                        try {
                            updateWidget(getApplicationContext());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        Favorite_Button.setSelected(false);
                        Favorite_Button.setImageResource(R.drawable.favorite_off);
                        permission_for_miniplayer_widget_favorite=false;

                        try {
                            updateWidget(getApplicationContext());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        updateWidget(getApplicationContext());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    PERMISSION_TO_COPY_ARRAYLIST=false;
                    SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
                    Count_for_Repeat_Button=preferences.getInt(REPEAT_MODE_KEY,3);
                    Count_for_Shuffle_Button=preferences.getInt(SHUFFLE_MODE_KEY,0);
                    if(is_end_of_the_track_sleep_timer){
                        finish();

                    }else{

                        if(Count_for_Repeat_Button==1 && Count_for_Shuffle_Button>=0){  //FOR REPEAT ONE SONG
                            play(current_song_index);
                        } else if (Count_for_Repeat_Button==2 & Count_for_Shuffle_Button ==0) { //FOR REPEAT ALL SONGS

                            if (current_song_index == temp_array_list.size() - 1) {
                                play(0);
                            } else {
                                current_song_index += 1;
                                play(current_song_index);
                            }
                        } else if (Count_for_Shuffle_Button==1 &&(Count_for_Repeat_Button==2 ||( Count_for_Repeat_Button<1||Count_for_Repeat_Button>2) ) ) {
//                            Random random =new Random();
//                            int shuffle_song_index=Math.abs(random.nextInt()%temp_array_list.size());
//                            play(shuffle_song_index);
//
                            if(Shuffled_Array_Index_POSITION==(Shuffled_Index_Arraylist.size()-1)){
                                Shuffled_Array_Index_POSITION=0;
//                              make_a_toast(String.format("%d %d",Shuffled_Array_Index_POSITION,Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION)));
                                play(Shuffled_Index_Arraylist.get(0));

                            }
                            else {
                                Shuffled_Array_Index_POSITION += 1;
//                              make_a_toast(String.format("%d %d",Shuffled_Array_Index_POSITION,Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION)));
                                play(Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION));
                            }
                        }
                    }




//                    if(Count_for_Shuffle_Button==1 && Count_for_Repeat_Button==1){
//                        play(current_song_index);
//                    }
//                    else if(Count_for_Repeat_Button==2 && Count_for_Shuffle_Button==1) {
//
//                    }


                }
            });
        } catch (Exception e) {
        }
    }

    public void BACK_BUTTON_OF_RECENTLY_ADDED(View view) {
        BACK_BUTTON_OF_RECENTLY_ADDED_FUNC();
    }
    public void BACK_BUTTON_OF_RECENTLY_ADDED_FUNC(){
        setting_button.setVisibility(View.VISIBLE);
        recently_added_playlist_constrain_layout.setVisibility(View.GONE);
        set_all_active_flags_to_false();
//        is_all_album_interface_active=false;
//        is_album_playlist_active=false;
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
        IS_ALL_PLAYLIST_INTERFACE_ACTIVE = true;
        all_playlist_interface.startAnimation(FADE_IN);
        all_playlist_interface.setVisibility(View.VISIBLE);
        set_all_interface_button_visibility(true);
    }

    public void play_and_pause(View view) {

        PLAY_AND_PAUSE();

    }

    public void PLAY_AND_PAUSE() {
        RemoteViews expanded=new RemoteViews(getPackageName(),R.layout.custom_notification);
        if (!new_media_player_permission) {
            if (media_player.get_media_player().isPlaying()) {

                expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.play_icon);
                miniplayer_pause_play_image_view.setImageResource(R.drawable.play_icon);
                music_player_play_and_pause_image_view.setImageResource(R.drawable.play_icon);
                is_media_player_paused = true;
                media_player.pause_media_player();
                handler.removeCallbacks(runnable2);
                was_play_timer_activated_before=false;
                permission_to_resume_the_song_from_telephony=false;
                check_user_inactivity();
                try{
                    updateWidget(this);

                }catch (Exception e){}

            } else {

                miniplayer_pause_play_image_view.setImageResource(R.drawable.pause);
                music_player_play_and_pause_image_view.setImageResource(R.drawable.pause);
                expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.pause);
                media_player.resume_media_player();
                was_play_timer_activated_before=true;
                handler.removeCallbacks(runnable_for_play_timer);
                check_user_inactivity_for_play_timer();
                permission_to_resume_the_song_from_telephony=true;
                is_media_player_paused = false;
                try{
                    updateWidget(this);
                }catch (Exception e){

                }
            }
//            notification_builder.setCustomBigContentView(expanded);

//            Notification notification=notification_builder.build();
//            notificationManager.notify(1,notification);

        } else {
            new_media_player_permission = false;
            PERMISSION_TO_COPY_ARRAYLIST=true;
            play(current_song_index);
            is_media_player_paused = false;
        }
        configure_notification(preferences.getString(MINIPLAYER_SONG_NAME_KEY,""),preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,""),preferences.getLong(MINIPLAYER_ALBUM_ART_KEY,613));

    }

    public void next_song(View view) {
        NEXT_SONG();

    }

    public void NEXT_SONG() {
        PERMISSION_TO_COPY_ARRAYLIST=false;
        was_play_timer_activated_before=false;
        if (Count_for_Shuffle_Button==1 &&(Count_for_Repeat_Button==2 ||( Count_for_Repeat_Button<1||Count_for_Repeat_Button>2) ) ) {

            
            if(Shuffled_Array_Index_POSITION==(Shuffled_Index_Arraylist.size()-1)){
                Shuffled_Array_Index_POSITION=0;
                play(Shuffled_Index_Arraylist.get(0));

            }
            else{
                Shuffled_Array_Index_POSITION+=1;
                play(Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION));

            }
        }else{
            if (current_song_index == temp_array_list.size() - 1) {
                play(0);
            } else {
                play(current_song_index + 1);
            }
        }
//

    }

    public void previous_song(View view) {
        PREVIOUS_SONG();
    }

    public void PREVIOUS_SONG() {
        PERMISSION_TO_COPY_ARRAYLIST=false;
        was_play_timer_activated_before=false;
        if (media_player.get_media_player().getCurrentPosition() < 5000) {

            if (Count_for_Shuffle_Button==1 &&(Count_for_Repeat_Button==2 ||( Count_for_Repeat_Button<1||Count_for_Repeat_Button>2) ) ) { //IF SHUFFLE IS ACTIVE
//                Random random =new Random();
//                int shuffle_song_index=Math.abs(random.nextInt()%temp_array_list.size());
//                play(shuffle_song_index);

                if(Shuffled_Array_Index_POSITION<=0){
                    Shuffled_Array_Index_POSITION=Shuffled_Index_Arraylist.size()-1;
//                    make_a_toast(String.format("%d %d",Shuffled_Array_Index_POSITION,Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION)));
                    play(Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION));

                }
                else{
                    Shuffled_Array_Index_POSITION-=1;
//                    make_a_toast(String.format("%d %d",Shuffled_Array_Index_POSITION,Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION)));
                    play(Shuffled_Index_Arraylist.get(Shuffled_Array_Index_POSITION));



                }
//
            }

            else{                               //IF SHUFFLE IS NOT ACTIVE
                if (current_song_index <= 0) {

                    play(temp_array_list.size() - 1);
                } else {
                    play(current_song_index - 1);
                }
            }

        } else {
            play(current_song_index);
        }
    }


    private int time_in_sec_for_pause_timer;

    public void check_user_inactivity() {
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        boolean permission=preferences.getBoolean(PAUSE_TIMER_ACTIVATION_KEY,false);

        if(permission){
            time_in_sec_for_pause_timer =preferences.getInt(PAUSE_TIMER_TIME_KEY,900);
            handler.postDelayed(runnable2, 0);
        }
    }

    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);

            if (time_in_sec_for_pause_timer > 0) {
                if (is_media_player_paused && preferences.getBoolean(PAUSE_TIMER_ACTIVATION_KEY,false)&& !is_sleep_timer_active) {
                    time_in_sec_for_pause_timer -= 1;
                    is_any_timer_running=true;

                    handler.postDelayed(runnable2, 1000);
                } else {
                    make_a_toast("PAUSE TIMER RESET",true);
                    time_in_sec_for_pause_timer = preferences.getInt(PAUSE_TIMER_TIME_KEY,900);
                }
            } else {
                media_player.release_media_player();
                mediaSession.release();
                mediaSession.setActive(false);
                finish();
                handler.removeCallbacks(runnable2);
                time_in_sec_for_pause_timer = preferences.getInt(PAUSE_TIMER_TIME_KEY,900);
            }

        }
    };

    private int time_in_sec_for_play_timer=0;
    public void check_user_inactivity_for_play_timer() {
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        boolean permission=preferences.getBoolean(PLAY_TIMER_ACTIVATION_KEY,false);
        if(permission){
            time_in_sec_for_play_timer =preferences.getInt(PLAY_TIMER_TIME_KEY,900);
            handler.postDelayed(runnable_for_play_timer, 0);
        }
    }

    private Runnable runnable_for_play_timer = new Runnable() {
        @Override
        public void run() {
            SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);

            if (time_in_sec_for_play_timer > 0) {
                if (was_play_timer_activated_before && preferences.getBoolean(PLAY_TIMER_ACTIVATION_KEY,false)&& !is_sleep_timer_active) {
                    time_in_sec_for_play_timer -= 1;
                    is_any_timer_running=true;
//                    music_player_Artist_Name_text_view.setText(String.format("play : %d",time_in_sec_for_play_timer));
                    handler.postDelayed(runnable_for_play_timer, 1000);
                } else {
                    make_a_toast("PLAY TIMER RESET",true);
                    time_in_sec_for_play_timer = preferences.getInt(PLAY_TIMER_TIME_KEY,900);
                }
            } else {
//                permission_for_still_listening_time=true;

                media_player.release_media_player();
                mediaSession.release();
                mediaSession.setActive(false);
                finish();

                handler.removeCallbacks(runnable_for_play_timer);
                time_in_sec_for_play_timer = preferences.getInt(PLAY_TIMER_TIME_KEY,900);
            }

        }
    };


    public void hide_Music_Player_Interface(View view) {
        HIDE_MUSIC_PLAYER_INTERFACE();


    }
    public void HIDE_MUSIC_PLAYER_INTERFACE(){
        IS_MUSIC_PLAYER_ACTIVE=false;
//        setting_button.setVisibility(View.VISIBLE);
//        setting_button.startAnimation(FADE_IN);
        ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.setVisibility(View.GONE);
//        set_all_visibility_off();
//        Animation Fade_Out_Animation=AnimationUtils.loadAnimation(this,R.anim.fade_out_animation);
        music_player.startAnimation(FADE_OUT);
        music_player.setVisibility(View.GONE);

        if (IS_USER_CREATED_PLAYLIST_ACTIVE) {
            setting_button.setVisibility(View.GONE);
//            set_all_interface_button_visibility(false);
            ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.setVisibility(View.GONE);
//                recently_added_playlist_constrain_layout.setVisibility(View.GONE);
            user_created_playlist_contrain_layout.setVisibility(View.VISIBLE);
            permission_for_flicking=false;


        } else if (IS_RECENTLY_ADDED_PLAYLIST_ACTIVE) {
            ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.setVisibility(View.GONE);
            setting_button.setVisibility(View.GONE);
            recently_added_playlist_constrain_layout.setVisibility(View.VISIBLE);
            permission_for_flicking=false;
        } else if (IS_ALBUM_PLAYLIST_ACTIVE) {
//            set_all_interface_button_visibility(false);
            setting_button.setVisibility(View.GONE);
            album_playlist_constrain_layout.setVisibility(View.VISIBLE);
            permission_for_flicking=false;

        } else if (IS_ALL_PLAYLIST_INTERFACE_ACTIVE) {
//            is_recently_added_playlist_active=true;
            set_all_interface_button_visibility(true);
            all_playlist_interface.setVisibility(View.VISIBLE);
            permission_for_flicking=true;
            setting_button.setVisibility(View.VISIBLE);
        } else if (IS_ARTIST_PLAYLIST_ACTIVE) {
//            set_all_interface_button_visibility(false);
            permission_for_flicking=false;
            setting_button.setVisibility(View.GONE);
            artist_playlist_constrain_layout.setVisibility(View.VISIBLE);

        } else if (IS_ALL_ALBUM_INTERFACE_ACTIVE) {
            all_playlist_interface.setVisibility(View.VISIBLE);
            permission_for_flicking=true;
//            is_all_album_interface_active=true;

            //            set_all_interface_button_visibility(true);
//            all_playlist_interface.setVisibility(View.VISIBLE);
        }

        else if (IS_ALL_ARTIST_INTERFACE_ACTIVE) {
            set_all_interface_button_visibility(true);
            all_playlist_interface.setVisibility(View.VISIBLE);
            permission_for_flicking=true;
        }

        else if (IS_ALL_SONGS_INTERFACE_ACTIVE) {
            all_playlist_interface.setVisibility(View.VISIBLE);
            permission_for_flicking=true;
            setting_button.setVisibility(View.VISIBLE);
//            set_all_interface_button_visibility(true);
//            all_song_interface_constrain_layout.setVisibility(View.VISIBLE);


        }
        miniplayer.startAnimation(FADE_IN);
        miniplayer.setVisibility(View.VISIBLE);
    }

    public void BACK_BUTTON_OF_USER_CREATED_PLAYLIST(View view) {
       BACK_BUTTON_OF_USER_CREATED_PLAYLIST_FUNC();
    }
    public void BACK_BUTTON_OF_USER_CREATED_PLAYLIST_FUNC(){
        if(IS_FAVOURITE_PLAYLIST_ACTIVE){
            IS_FAVOURITE_PLAYLIST_ACTIVE =false;
        }
        permission_for_flicking=true;
        setting_button.setVisibility(View.VISIBLE);

        save_and_load_array.save_array_for_user_created_playlist(this,arrayList_for_user_created_playlist,arrayList_for_all_playlists.get(CURRENT_PLAYLIST_POSITION_FOR_MORE_BUTTON).getMPlaylist_name());

        user_created_playlist_contrain_layout.setVisibility(View.GONE);
        set_all_active_flags_to_false();
//        is_all_album_interface_active=false;
//        is_album_playlist_active=false;
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
        IS_ALL_PLAYLIST_INTERFACE_ACTIVE = true;
        all_playlist_interface.startAnimation(FADE_IN);
        all_playlist_interface.setVisibility(View.VISIBLE);
        set_all_interface_button_visibility(true);
    }

    public void activate_Music_Player_interface(View view) throws Exception {

//        set_all_visibility_off();
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);

//        set_all_interface_button_visibility(false);
        Count_for_Repeat_Button=preferences.getInt(REPEAT_MODE_KEY,3);
        SET_REPEAT_MODE();
        Count_for_Shuffle_Button=preferences.getInt(SHUFFLE_MODE_KEY,0);
        SET_SHUFFLE_MODE();
        ACTIVATE_MUSIC_PLAYER_INTERFACE();

    }

    public void ACTIVATE_MUSIC_PLAYER_INTERFACE() {
        IS_MUSIC_PLAYER_ACTIVE=true;
        permission_for_flicking=true;
        miniplayer.startAnimation(FADE_OUT);
        miniplayer.setVisibility(View.GONE);
        setting_button.setVisibility(View.GONE);

        Animation fade_in_animation= AnimationUtils.loadAnimation(this,R.anim.fade_in_animation);
        music_player.startAnimation(FADE_IN);
        music_player.setVisibility(View.VISIBLE);
        Animation f=AnimationUtils.loadAnimation(this,R.anim.fade_in_animation);
//        set_all_interface_button_visibility(false);
        ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.setVisibility(View.GONE);
//        back_function_protocol(false);
        all_playlist_interface.setVisibility(View.GONE);
        album_playlist_constrain_layout.setVisibility(View.GONE);


        recently_added_playlist_constrain_layout.setVisibility(View.GONE);
        artist_playlist_constrain_layout.setVisibility(View.GONE);
        album_playlist_constrain_layout.setVisibility(View.GONE);
        all_song_interface_constrain_layout.setVisibility(View.GONE);
        user_created_playlist_contrain_layout.setVisibility(View.GONE);

//        ConstraintLayout[] constraintLayout = { all_playlist_interface, recently_added_playlist_constrain_layout, user_created_playlist_contrain_layout, all_album_interface_constraint_layout, album_playlist_constrain_layout,miniplayer};
//        for (ConstraintLayout i : constraintLayout) {
////            i.startAnimation(f);
//            i.setVisibility(View.GONE);
//        }




    }

    public void config_miniplayer(String Song_NAME, String Path, String Artist_NAME, String Album_Name, int Durration, long Album_art) throws IOException {
        if (Song_NAME.length() > 27) {
            miniplayer_song_name_text_view.setText(Song_NAME.substring(0, 27) + "...");
        } else {
            miniplayer_song_name_text_view.setText(Song_NAME);
        }

        if (Artist_NAME.length() > 24) {
            miniplayer_artist_name_text_view.setText(Artist_NAME.substring(0, 24) + "...");
        } else {
            miniplayer_artist_name_text_view.setText(Artist_NAME);

        }
        Uri AlbumUri = Uri.parse("content://media/external/audio/albumart/" + Album_art);
//        Picasso.get().load(AlbumUri).into(miniplayer_album_art_imageview);

        config_music_player(Song_NAME, Path, Artist_NAME, Album_Name, Durration, Album_art);
    }

    public void config_music_player(String Song_NAME, String Path, String Artist_NAME, String Album_Name, int Durration, long Album_art) throws IOException {
        music_player_Album_Name_text_view.setText(Album_Name);

        Uri AlbumUri = Uri.parse("content://media/external/audio/albumart/" + Album_art);
//        Picasso.get().load(AlbumUri).into(music_player_album_art_image_view);

        music_player_Song_Name_text_view.setText(Song_NAME);
        music_player_Artist_Name_text_view.setText(Artist_NAME);
        music_player_total_duration_text_view.setText(formated_time(Durration));
//        Glide.with(this)
//                .asBitmap()
//                .load(AlbumUri)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                        // Sample the color from the bitmap
//                        int color = getDominantColor(resource);
//
//                        // Adjust the color for dark theme
//                        int darkThemeColor = adjustColorForDarkTheme(color);
//
//                        // Apply the gradient background to the ConstraintLayout
////                        music_player.setBackgroundColor(darkThemeColor);
//                    }
//                });
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        if(!Path.equals("")){
            retriever.setDataSource(Path);

            byte[] albumArtBytes = retriever.getEmbeddedPicture();
            if (albumArtBytes != null) {
                Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


                File tempFile = createTempFile("album_art", ".jpg");
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
                Picasso.get().load(uri).into(music_player_album_art_image_view);
                Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//

                // Now you have the album art bitmap, you can display it or process it further
            } else {
                Picasso.get().load(R.drawable.logo).into(music_player_album_art_image_view);
                Picasso.get().load(R.drawable.logo).into(miniplayer_album_art_imageview);
                // No album art available
            }
        }else{                                                                              //IF SONG PATH IS SET TO "" .DUE IT NOT IN USER STORAGE
            Picasso.get().load(R.drawable.logo).into(music_player_album_art_image_view);
            Picasso.get().load(R.drawable.logo).into(miniplayer_album_art_imageview);
        }


        retriever.release();

    }
    private int adjustColorForDarkTheme(int color) {
        // Get the RGB components of the color
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // Calculate the luminance of the color
        double luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255;

        // You can adjust the threshold as per your preference
        double darkThreshold = 0.0; // Adjusted threshold for darker colors

        // Check if the color is dark
        if (luminance < darkThreshold) {
            // Darken the color by reducing the brightness more aggressively
            float[] hsv = new float[3];
            Color.RGBToHSV(red, green, blue, hsv);
            hsv[2] *= 0.2f; // Decrease brightness by 40%
            return Color.HSVToColor(hsv);
        }

        // If the color is already dark enough, return the original color
        return color;
    }
    private int getDominantColor(Bitmap bitmap) {
        // You can implement your color sampling algorithm here
        // For simplicity, let's just sample the color from the top-left corner pixel
        return bitmap.getPixel(0, 0);
    }

    public String formated_time(int time) {
        int min = (time / 1000) / 60;
        int sec = (time / 1000) % 60;

        return String.format("%02d:%02d", min, sec);
    }

    public void play_time() {
        if (!is_media_player_paused) {
            handler.postDelayed(mrunnable, 0);
        }


    }

    private Runnable mrunnable = new Runnable() {
        @Override
        public void run() {
            if (!is_media_player_paused) {
                int current_time = media_player.get_media_player().getCurrentPosition();
                music_player_Current_duration.setText(formated_time(current_time));
                music_player_seek_bar.setProgress(current_time / 1000);


            }
            handler.postDelayed(mrunnable, 1000);


        }
    };
/*  BELOW TWO FUNCTION ARE USED TO ADD A NEW USER CREATED PLAYLIST*/
    public void add_new_playlist(View view) {
        permission_for_flicking=false;
        setting_button.setVisibility(View.GONE);
        add_new_playlist_edit_text.setText("");
        set_all_visibility_off();
        set_all_interface_button_visibility(false);
        miniplayer.setVisibility(View.GONE);
        make_a_toast("New Playlist",false);
//        register_result();
        all_playlist_interface.setVisibility(View.GONE);
        add_new_playlist_interface.setVisibility(View.VISIBLE);
    }

    public void add_playlist_button(View view) {
        String New_Playlist = add_new_playlist_edit_text.getText().toString();

        if (!New_Playlist.isEmpty()) {
            boolean permission=check_whether_song_or_playlist_already_exists.check_the_playlist(arrayList_for_all_playlists,New_Playlist);
            if(permission){
                make_a_toast(String.format("%s Playlist Added",New_Playlist),true);


                arrayList_for_all_playlists.add(arrayList_for_all_playlists.size()-1,new Playlists_recycler_item_class(100, New_Playlist, false));

//                adapter_for_all_playlist.notifyItemInserted(arrayList_for_all_playlists.size()-2);


                ALL_PLAYLIST_FRAGMENT.NOTIFY_PLAYLIST_INSERTED(arrayList_for_all_playlists.size()-2);  //THIS CALLS METHOD IN ALL PLAYLIST FRAGMENT TO NOTIFY THAT PLAYLIST HAS TO BE INSERTED,DUE TO ADAPTER BEING IN ALL PLAYLIST FRAGMENT
//                make_a_toast(String.format("%d",arrayList_for_all_playlists.size()));
                save_and_load_array.save_array_for_all_playlist(this, arrayList_for_all_playlists);
                SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();


                 if (preferences.getBoolean(New_Playlist,false) &&preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+New_Playlist,false)) {
                    editor.putBoolean(New_Playlist,true);
                } else {
                    editor.putBoolean(New_Playlist,false);
                    editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+New_Playlist,false);
                }
                editor.apply();
                setting_button.setVisibility(View.VISIBLE);
                add_new_playlist_interface.setVisibility(View.GONE);
//                if(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)){
//                    make_a_toast("testing",true);
//                }
                miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
//                set_all_interface_button_visibility(true);
                all_playlist_interface.setVisibility(View.VISIBLE);
            }else {
                make_a_toast(String.format("%s Playlist Already Exists",New_Playlist),true);
            }





        } else {
            make_a_toast("YOU DIDN'T ENTER!!!",true);
        }

    }
    public void BACK_BUTTON_OF_ADD_NEW_PLAYLIST_INTERFACE(View view){
        permission_for_flicking=true;
        setting_button.setVisibility(View.VISIBLE);
        add_new_playlist_interface.setVisibility(View.GONE);
        miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
//        set_all_interface_button_visibility(true);
        all_playlist_interface.setVisibility(View.VISIBLE);
        all_playlist_interface.startAnimation(FADE_IN);
    }

    public void ALL_SONGS_INTERFACE(View view) {
        ALL_SONG_INTERFACE_FUNC();
    }
    public void ALL_SONG_INTERFACE_FUNC(){

//        set_all_visibility_off();
//        Animation slide_right_animation= AnimationUtils.loadAnimation(this,R.anim.slide_right_animation);
        if(!IS_ALL_SONGS_INTERFACE_ACTIVE){
            back_function_protocol(false);
            all_song_interface_constrain_layout.startAnimation(SLIDE_RIGHT_ANIMATION);
        }
        all_song_interface_constrain_layout.setVisibility(View.VISIBLE);
        set_all_interface_image_off();
        set_all_active_flags_to_false();
        ALL_SONGS_INTERFACE_IMAGEVIEW.setImageResource(R.drawable.song_interface_on);
        if (!IS_ALL_SONGS_INTERFACE_ACTIVE) {
            All_Songs_Interface();
        }

    }

    public void ALL_PLAYLIST_INTERFACE(View view) {
        ALL_PLAYLIST_INTERFACE_FUNC();

    }
    public void ALL_PLAYLIST_INTERFACE_FUNC(){
        //        set_all_visibility_off();
        if (!IS_ALL_PLAYLIST_INTERFACE_ACTIVE){
            back_function_protocol(false);
        }
//        Animation Slide_Right_Animation=AnimationUtils.loadAnimation(this,R.anim.slide_right_animation);
//        Animation Slide_Left_Animation=AnimationUtils.loadAnimation(this,R.anim.slide_left_animation);
        if(IS_ALL_ARTIST_INTERFACE_ACTIVE){
            all_playlist_interface.startAnimation(SLIDE_RIGHT_ANIMATION);
        }else if(IS_ALL_SONGS_INTERFACE_ACTIVE){
            all_playlist_interface.startAnimation(SLIDE_LEFT_ANIMATION);
        } else if (IS_ALL_ALBUM_INTERFACE_ACTIVE) {
            all_playlist_interface.startAnimation(SLIDE_RIGHT_ANIMATION);
        }


        all_playlist_interface.setVisibility(View.VISIBLE);
        set_all_interface_image_off();
        set_all_active_flags_to_false();
//        is_all_album_interface_active=false;
//        is_album_playlist_active=false;
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
        IS_ALL_PLAYLIST_INTERFACE_ACTIVE = true;
        ALL_PLAYLIST_INTERFACE_IMAGEVIEW.setImageResource(R.drawable.playlist_interface_on);
//        All_Playlist_Inteface();
    }


    public void ALL_ALBUM_INTERFACE(View view) {
        ALL_ALBUM_INTERFACE_FUNC();
    }
    public void ALL_ALBUM_INTERFACE_FUNC(){
        //set_all_visibility_off();
        set_all_interface_image_off();

//        is_all_album_interface_active=true;
        ALL_ALBUMS_INTERFACE_IMAGEVIEW.setImageResource(R.drawable.album_interface_on);
        all_playlist_interface.setVisibility(View.GONE);

//        Animation Slide_Right_Animation=AnimationUtils.loadAnimation(this,R.anim.slide_right_animation);
//        Animation Slide_Left_Animation=AnimationUtils.loadAnimation(this,R.anim.slide_left_animation);

        if(IS_ALL_ARTIST_INTERFACE_ACTIVE){
            all_album_interface_constraint_layout.startAnimation(SLIDE_RIGHT_ANIMATION);
        }else if(IS_ALL_SONGS_INTERFACE_ACTIVE){
            all_album_interface_constraint_layout.startAnimation(SLIDE_LEFT_ANIMATION);
        } else if (IS_ALL_PLAYLIST_INTERFACE_ACTIVE) {
            all_album_interface_constraint_layout.startAnimation(SLIDE_LEFT_ANIMATION);
        }

        all_album_interface_constraint_layout.setVisibility(View.VISIBLE);
//        album_playlist_constrain_layout.setVisibility(View.VISIBLE);

        if (!IS_ALL_ALBUM_INTERFACE_ACTIVE) {
            back_function_protocol(false);

            All_Album_Interface();
        }
    }

    public void ALL_ARTIST_INTERFACE(View view) {
        ALL_ARTIST_INTERFACE_FUNC();
    }
    public void ALL_ARTIST_INTERFACE_FUNC(){

//        set_all_visibility_off();
        set_all_interface_image_off();
//        all_playlist_interface.setVisibility(View.GONE);
//        Animation slide_left_animation=AnimationUtils.loadAnimation(this,R.anim.slide_left_animation);
        if(!IS_ALL_ARTIST_INTERFACE_ACTIVE){
            back_function_protocol(false);

            all_artist_interface_constrain_layout.startAnimation(SLIDE_LEFT_ANIMATION);
        }
        ALL_ARTIST_INTERFACE_IMAGEVIEW.setImageResource(R.drawable.artist_interface_on);

        all_artist_interface_constrain_layout.setVisibility(View.VISIBLE);
        if (!IS_ALL_ARTIST_INTERFACE_ACTIVE) {
            All_Artist_Interface();
        }
    }
    /*  THIS FUNCTION IS WHERE USER CAN CHOOSE TO PLAY SONGS FROM ALL THE SONGS USER HAVE IN ITS DEVICE */
    private void All_Songs_Interface() {

        set_all_active_flags_to_false();
        IS_ALL_SONGS_INTERFACE_ACTIVE = true;
//        arrayList_for_all_song_interface = load_all_songs_of_all_songs_interface_in_ascending.load_songs_in_ascending(arrayList_for_recently_added_playlist);
        recyclerView = findViewById(R.id.all_songs_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter_for_all_songs_interface = new recently_added_adapter_class(arrayList_for_all_song_interface);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_for_all_songs_interface);
        adapter_for_all_songs_interface.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) {
                CURRENT_INTERFACE_POSITION = 0;
                current_song_index = position;
                set_all_active_flags_to_false();
//                is_all_album_interface_active=false;
//                is_recently_added_playlist_active=false;
//                is_user_created_playlist_active=false;
//                is_all_playlist_interface_active=false;
                IS_ALL_SONGS_INTERFACE_ACTIVE = true;
                ACTIVATE_MUSIC_PLAYER_INTERFACE();

                was_play_timer_activated_before=false;
                play(current_song_index);
                SHUFFLE_SETUP();



            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.all_songs_interface_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_item_1_for_all_songs_interface) {

                            if (arrayList_for_all_playlists.size() > 2) {
                                is_single_song_selected = true;

                                selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
//                                arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//                                for (int i = 1; i < arrayList_for_all_playlists.size()-1; i++) {
//                                    arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(), arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));
//                                }
                                add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
//                                add_playlist__Interface();
                                CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
                                single_song_selected_playlist_position=0;
                                MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,true,arrayList_for_add_playlist_without_recently_added,true);
                                musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
                            } else {
                                make_a_toast("THERE ARE NO PLAYLIST",true);
                            }
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

    }


    public void All_Playlist_Inteface() {
    }
    /*  THIS FUNCTION IS WHERE USER CAN CHOOSE TO PLAY SONGS FROM VARIOUS ALBUMS */
    public void All_Album_Interface() {
        set_all_active_flags_to_false();
//        is_album_playlist_active=false;
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
//        is_all_playlist_interface_active=false;
        IS_ALL_ALBUM_INTERFACE_ACTIVE = true;


        arrayList_for_all_albums = new ArrayList<>();
        for (int i = 0; i < arrayList_for_recently_added_playlist.size(); i++) {
            boolean is_duplicate = false;
            Recently_added_recyclerview_elements_item_class current_item_of_Recently_Added = arrayList_for_recently_added_playlist.get(i);

            for (int j = 0; j < arrayList_for_all_albums.size(); j++) {
                all_album_interface_all_all_artist_interface_recyclerview_item_class current_item_of_All_ALbums = arrayList_for_all_albums.get(j);
                if (current_item_of_Recently_Added.getMalbum_name().equals(current_item_of_All_ALbums.getMalbum_name())) {
                    is_duplicate = true;
                    break;
                }

            }
            if (!is_duplicate) {
                arrayList_for_all_albums.add(new all_album_interface_all_all_artist_interface_recyclerview_item_class(
                        current_item_of_Recently_Added.getMalbum_art(),
                        current_item_of_Recently_Added.getMalbum_name(),
                        total_elements(current_item_of_Recently_Added.getMalbum_name(), true)));
            }
        }

        recyclerView = findViewById(R.id.all_album_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter_for_all_album_interface = new all_album_interface_and_all_artist_interface_recyclerview_adapter(arrayList_for_all_albums);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter_for_all_album_interface);
        recyclerView.setLayoutManager(layoutManager);
        adapter_for_all_album_interface.SET_ON_CLICKED_LISTENER(new all_album_interface_and_all_artist_interface_recyclerview_adapter.ON_CLICKED_LISTENER() {
            @Override
            public void ON_ITEM_CLICKED(long ALBUM_ART, String ALBUM_NAME, int TOTAL_ALBUM_SONGS, int ALBUM_POSITION) throws IOException {
//                make_a_toast(String.format("%s\n%d",ALBUM_NAME,TOTAL_ALBUM_SONGS));
                ACTIVATE_ALBUM_PLAYLIST(ALBUM_ART, ALBUM_NAME, TOTAL_ALBUM_SONGS, ALBUM_POSITION);

            }


        });


    }

/*  THIS FUNCTION IS WHERE USER CAN CHOOSE TO PLAY SONGS FROM VARIOUS ARTIST */
    public void All_Artist_Interface() {
        set_all_active_flags_to_false();
        arrayList_for_all_artist_interface = new ArrayList<>();
        IS_ALL_ARTIST_INTERFACE_ACTIVE = true;
        arrayList_for_all_artist_interface=new ArrayList<>();
        for (int i = 0; i < arrayList_for_recently_added_playlist.size(); i++) {
            Recently_added_recyclerview_elements_item_class recently_added_current_item = arrayList_for_recently_added_playlist.get(i);

            boolean is_duplicate = false;
            for (int j = 0; j < arrayList_for_all_artist_interface.size(); j++) {
                all_album_interface_all_all_artist_interface_recyclerview_item_class all_artist_playlist_current_item = arrayList_for_all_artist_interface.get(j);
                if (recently_added_current_item.getMartist().equals(all_artist_playlist_current_item.getMalbum_name())) {
                    is_duplicate = true;
                    break;
                }
            }
            if (!is_duplicate) {
                arrayList_for_all_artist_interface.add(new all_album_interface_all_all_artist_interface_recyclerview_item_class(
                        recently_added_current_item.getMalbum_art(),
                        recently_added_current_item.getMartist(),
                        total_elements(recently_added_current_item.getMartist(), false)));
            }
        }

        recyclerView = findViewById(R.id.all_artist_interface_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter_for_all_artist_interface = new all_album_interface_and_all_artist_interface_recyclerview_adapter(arrayList_for_all_artist_interface);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter_for_all_artist_interface);
        recyclerView.setLayoutManager(layoutManager);
        adapter_for_all_artist_interface.SET_ON_CLICKED_LISTENER(new all_album_interface_and_all_artist_interface_recyclerview_adapter.ON_CLICKED_LISTENER() {
            @Override
            public void ON_ITEM_CLICKED(long ALBUM_ART, String ARTIST_NAME, int TOTAL_ARTIST_SONGS, int ARTIST_POSITION) throws IOException {
                ACTIVATE_ARTIST_PLAYLIST(ALBUM_ART, ARTIST_NAME, TOTAL_ARTIST_SONGS, ARTIST_POSITION);
            }
        });

    }
/*  THIS FUNCTIONS PROVIDES TOTAL SONGS OF ALBUM OR ARTIST ON BASIS OF PERMISSION
*   IF IT IS TRUE IT WILL GIVE TOTAL SONGS OF ALBUM
*   ELSE IT WILL GIVE TOTAL SONGS OF ARTIST*/
    public int total_elements(String ALBUM_NAME_OR_ARTIST_NAME, boolean permission_for_albums) {
        int count = 0;
        for (int i = 0; i < arrayList_for_recently_added_playlist.size(); i++) {
            Recently_added_recyclerview_elements_item_class current_item = arrayList_for_recently_added_playlist.get(i);
            if (permission_for_albums) {
                if (ALBUM_NAME_OR_ARTIST_NAME.equals(current_item.getMalbum_name())) {
                    count += 1;
                }
            } else {
                if (ALBUM_NAME_OR_ARTIST_NAME.equals(current_item.getMartist())) {
                    count += 1;
                }
            }

        }
        return count;


    }

    /*  THIS FUNCTION ACTIVATES THE ALBUM PLAYLIST OF THE ALBUM USER PROVIDES*/
    public void ACTIVATE_ALBUM_PLAYLIST(long ALBUM_ART, String ALBUM_NAME, int TOTAL_ALBUM_SONGS, int ALBUM_POSITION) throws IOException {
        ScrollView scrollView =findViewById(R.id.scrollView_of_album_playlist);
        permission_for_flicking=false;
        setting_button.setVisibility(View.GONE);
        array_list_for_album_playlist = new ArrayList<>();
        miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
        artist_playlist_constrain_layout.setVisibility(View.GONE);



        array_list_for_album_playlist = load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist, ALBUM_NAME, true);
//        set_all_visibility_off();
//        all_album_interface_constraint_layout.setVisibility(View.GONE);
        set_all_interface_button_visibility(false);
        if(!is_song_info_active){
            set_all_active_flags_to_false();
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
//        is_all_playlist_interface_active=false;
            IS_ALL_ALBUM_INTERFACE_ACTIVE = true;
            IS_ALBUM_PLAYLIST_ACTIVE = true;
            album_playlist_constrain_layout.startAnimation(FADE_IN);

        }
        album_playlist_constrain_layout.setVisibility(View.VISIBLE);


        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + ALBUM_ART);

        album_name_text_view_for_album_playlist.setText(ALBUM_NAME);
        total_songs_text_view_for_album_playlist.setText(TOTAL_ALBUM_SONGS>1?String.format("SONGS : %d", TOTAL_ALBUM_SONGS):String.format("SONG : %d",TOTAL_ALBUM_SONGS));

        recyclerView = findViewById(R.id.album_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter_for_album_playlist = new recently_added_adapter_class(array_list_for_album_playlist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter_for_album_playlist);
        recyclerView.setLayoutManager(layoutManager);
        adapter_for_album_playlist.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) {
                CURRENT_INTERFACE_POSITION = 2;
                CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME = ALBUM_NAME;
                current_song_index = position;
                if(!is_song_info_active){
                    ACTIVATE_MUSIC_PLAYER_INTERFACE();
                    set_all_active_flags_to_false();
//                is_all_album_interface_active=false;
//                is_recently_added_playlist_active=false;
//                is_user_created_playlist_active=false;
//                is_all_playlist_interface_active=false;
                    IS_ALBUM_PLAYLIST_ACTIVE = true;

                }
                is_add_to_queue_active=false;
                is_play_next_active=false;

                editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
                editor.apply();
                was_play_timer_activated_before=false;
                PERMISSION_TO_COPY_ARRAYLIST=true;
                play(current_song_index);
                SHUFFLE_SETUP();

            }


            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.pop_menu_for_recently_added_songs_artist_album);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_item_1_for_all_songs_interface) {

                            if (arrayList_for_all_playlists.size() > 2) {
                                is_single_song_selected = true;
//                                is_single_song_selected_from_all_song_interface = true;

                                selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
//                                arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//                                for (int i = 1; i < arrayList_for_all_playlists.size()-1; i++) {
//                                    arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(), arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));
//                                }
                                add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
//                                add_playlist__Interface();
                                CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
                                ALBUM_OR_ARTIST_NAME = ALBUM_NAME;
                                single_song_selected_playlist_position=2;
                                MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,
                                                                                                                        true,
                                                                                                                arrayList_for_add_playlist_without_recently_added,
                                                                                                                true);
                                musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
                            } else {
                                make_a_toast("THERE ARE NO PLAYLiST",true);
                            }
                            return true;
                        }else if(item.getItemId()==R.id.add_to_queue&&preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)){
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
                            Recently_added_recyclerview_elements_item_class CURRENT=array_list_for_album_playlist.get(position);
                            temp_array_list.add(new Recently_added_recyclerview_elements_item_class(
                                    CURRENT.getMsong_name(),
                                    CURRENT.getMpath(),
                                    CURRENT.getMartist(),
                                    CURRENT.getMalbum_name(),
                                    CURRENT.getMduration(),
                                    CURRENT.getMalbum_art(),
                                    false)
                            );
                            is_add_to_queue_active=true;
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");

                            return true;
                        }
                        else if((item.getItemId()==R.id.play_next)&&(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false))){

                            is_add_to_queue_active=true;

                            Recently_added_recyclerview_elements_item_class Current=array_list_for_album_playlist.get(position);
//                            make_a_toast(String.format("PNSong Name : %s",Current.getMsong_name()),true);
                            if(is_play_next_active){
                                if(PLAY_NEXT_SONG.equals(temp_array_list.get(current_song_index).getMsong_name())){
                                    PLAY_NEXT_INDEX+=1;
//                                    make_a_toast("SONG IS SAME",true);
                                    Toast.makeText(getApplicationContext(), "SONG is Same", Toast.LENGTH_SHORT).show();

                                }else{
                                    PLAY_NEXT_INDEX=current_song_index+1;
                                    PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
//                                    make_a_toast("SONG Changed",true);
                                    Toast.makeText(getApplicationContext(), "song changed", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                PLAY_NEXT_INDEX=current_song_index+1;
                                is_play_next_active=true;
                            }
                            temp_array_list.add(PLAY_NEXT_INDEX,new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
//                            load_data_into_array_list_for_recently_added();
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
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(array_list_for_album_playlist.get(0).getMpath());

        byte[] albumArtBytes = retriever.getEmbeddedPicture();
        if (albumArtBytes != null) {
            Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


            File tempFile = createTempFile("album_art", ".jpg");
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                albumArtBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Uri uri = Uri.fromFile(tempFile);

            Picasso.get().load(uri).into(album_art_imageview_for_album_playlist);


//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

        } else {
            Picasso.get().load(R.drawable.logo).into(album_art_imageview_for_album_playlist);
            // No album art available
        }
        scrollView.scrollTo(0,album_art_imageview_for_album_playlist.getTop());


    }
    /*  THIS IS BUTTON FUNCTION OF ALBUM PLAYLIST WHICH HIDES THE PLAYLIST LAYOUT AND ACTIVATE THE ALBUM INTERFACE*/
    public void BACK_BUTTON_OF_ALBUM_PLAYLIST(View view) {
        BACK_BUTTON_OF_ALBUM_PLAYLIST_FUNC();
    }
    public void BACK_BUTTON_OF_ALBUM_PLAYLIST_FUNC(){
        if(!is_song_info_active){
            setting_button.setVisibility(View.VISIBLE);
            permission_for_flicking=true;
//            all_album_interface_constraint_layout.setVisibility(View.VISIBLE);
//            all_album_interface_constraint_layout.startAnimation(FADE_IN);
//            set_all_interface_button_visibility(true);
            set_all_active_flags_to_false();
            all_playlist_interface.setVisibility(View.VISIBLE);
            all_playlist_interface.startAnimation(FADE_IN);
//        is_album_playlist_active=false;
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
//        is_all_playlist_interface_active=false;
            IS_ALL_ALBUM_INTERFACE_ACTIVE = true;
        }else {
            music_player.setVisibility(View.VISIBLE);
            music_player.startAnimation(FADE_IN);
            is_song_info_active=false;
            miniplayer.setVisibility(View.GONE);
        }
        album_playlist_constrain_layout.setVisibility(View.GONE);
        album_playlist_constrain_layout.startAnimation(FADE_OUT);
    }


/*  THIS FUNCTION ACTIVATES THE ARTIST PLAYLIST OF THE ARTIST USER PROVIDES*/
    public void ACTIVATE_ARTIST_PLAYLIST(long ALBUM_ART, String ARTIST_NAME, int TOTAL_SONGS, int ARTIST_POSITION) throws IOException {
        ScrollView scrollView=findViewById(R.id.scrollView_of_artist_playlist);
        permission_for_flicking=false;
        setting_button.setVisibility(View.GONE);
        miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
        album_playlist_constrain_layout.setVisibility(View.GONE);
        all_artist_interface_constrain_layout.setVisibility(View.GONE);
        set_all_interface_button_visibility(false);
//        set_all_visibility_off();
        if(!is_song_info_active){
            set_all_active_flags_to_false();
//            is_all_album_interface_active = true;
//            is_album_playlist_active = true;
            IS_ALL_ARTIST_INTERFACE_ACTIVE =true;
            IS_ARTIST_PLAYLIST_ACTIVE =true;
            artist_playlist_constrain_layout.startAnimation(FADE_IN);
        }
        artist_playlist_constrain_layout.setVisibility(View.VISIBLE);
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + ALBUM_ART);

        Picasso.get().load(albumArtUri).into(album_art_imageview_for_artist_playlist);


        album_name_text_view_for_artist_playlist.setText(ARTIST_NAME);
        total_songs_text_view_for_artist_playlist.setText(String.format("SONGS : %d", TOTAL_SONGS));

        array_list_for_artist_playlist = load_songs_of_given_album_or_artist.LOAD_ARRAY_OF_THE_ALBUM_OR_ARTIST(arrayList_for_recently_added_playlist, ARTIST_NAME, false);

        recyclerView = findViewById(R.id.artist_playlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter_for_artist_playlist = new recently_added_adapter_class(array_list_for_artist_playlist);
        recyclerView.setAdapter(adapter_for_artist_playlist);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter_for_artist_playlist.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) {
                CURRENT_INTERFACE_POSITION = 3;
                CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME = ARTIST_NAME;
                current_song_index = position;
                if(!is_song_info_active){
                    ACTIVATE_MUSIC_PLAYER_INTERFACE();
                    set_all_active_flags_to_false();
//                is_all_album_interface_active=false;
//                is_recently_added_playlist_active=false;
//                is_user_created_playlist_active=false;
//                is_all_playlist_interface_active=false;
                    IS_ARTIST_PLAYLIST_ACTIVE = true;
                }
                is_add_to_queue_active=false;
                is_play_next_active=false;
                editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,false);
                editor.apply();
                was_play_timer_activated_before=false;
//                is_song_info_active=false;
                PERMISSION_TO_COPY_ARRAYLIST=true;
                play(current_song_index);
                SHUFFLE_SETUP();
            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.pop_menu_for_recently_added_songs_artist_album);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.more_button_item_1_for_all_songs_interface) {

                            if (arrayList_for_all_playlists.size() > 2) {
                                is_single_song_selected = true;
//                                is_single_song_selected_from_all_song_interface = true;

                                selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
//                                arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//                                for (int i = 1; i < arrayList_for_all_playlists.size()-1; i++) {
//                                    arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(), arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));
//
//                                }
                                add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
//                                add_playlist__Interface();
                                CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
                                ALBUM_OR_ARTIST_NAME = ARTIST_NAME;
                                single_song_selected_playlist_position=3;
                                MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,true,arrayList_for_add_playlist_without_recently_added,true);
                                musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
                            } else {
                                make_a_toast("THERE ARE NO PLAYLiST",true);
                            }
                            return true;
                        } else if((item.getItemId()==R.id.play_next)&&(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false))){

                            is_add_to_queue_active=true;

                            Recently_added_recyclerview_elements_item_class Current=array_list_for_artist_playlist.get(position);
//                            make_a_toast(String.format("PNSong Name : %s",Current.getMsong_name()),true);
                            if(is_play_next_active){
                                if(PLAY_NEXT_SONG.equals(temp_array_list.get(current_song_index).getMsong_name())){
                                    PLAY_NEXT_INDEX+=1;
//                                    make_a_toast("SONG IS SAME",true);
                                    Toast.makeText(getApplicationContext(), "SONG is Same", Toast.LENGTH_SHORT).show();

                                }else{
                                    PLAY_NEXT_INDEX=current_song_index+1;
                                    PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
//                                    make_a_toast("SONG Changed",true);
                                    Toast.makeText(getApplicationContext(), "song changed", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                PLAY_NEXT_SONG=temp_array_list.get(current_song_index).getMsong_name();
                                PLAY_NEXT_INDEX=current_song_index+1;
                                is_play_next_active=true;
                            }
                            temp_array_list.add(PLAY_NEXT_INDEX,new Recently_added_recyclerview_elements_item_class(
                                    Current.getMsong_name(),
                                    Current.getMpath(),
                                    Current.getMartist(),
                                    Current.getMalbum_name(),
                                    Current.getMduration(),
                                    Current.getMalbum_art(),
                                    false
                            ));
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
//                            load_data_into_array_list_for_recently_added();
                            return true;

                        }else if(item.getItemId()==R.id.add_to_queue&&preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)){
                            editor.putBoolean(PLAY_NEXT_AND_ADD_TO_QUEUE_KEY,true);
                            editor.apply();
                            Recently_added_recyclerview_elements_item_class CURRENT=array_list_for_artist_playlist.get(position);
                            temp_array_list.add(new Recently_added_recyclerview_elements_item_class(
                                    CURRENT.getMsong_name(),
                                    CURRENT.getMpath(),
                                    CURRENT.getMartist(),
                                    CURRENT.getMalbum_name(),
                                    CURRENT.getMduration(),
                                    CURRENT.getMalbum_art(),
                                    false)
                            );
                            is_add_to_queue_active=true;
                            save_and_load_array.save_array_for_user_created_playlist(getApplicationContext(),temp_array_list,"PLAY_NEXT_AND_ADD_TO_QUEUE");
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


        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(array_list_for_artist_playlist.get(0).getMpath());

        byte[] albumArtBytes = retriever.getEmbeddedPicture();
        if (albumArtBytes != null) {
            Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);


            File tempFile = createTempFile("album_art", ".jpg");
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

            Picasso.get().load(uri).into(album_art_imageview_for_artist_playlist);


//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

            // Now you have the album art bitmap, you can display it or process it further
        } else {
            // No album art available
            Picasso.get().load(R.drawable.logo).into(album_art_imageview_for_artist_playlist);
        }
        scrollView.scrollTo(0,album_art_imageview_for_artist_playlist.getTop());


    }
/*  THIS IS BUTTON FUNCTION OF ARTIST PLAYLIST WHICH HIDES THE PLAYLIST LAYOUT AND ACTIVATE THE ARTIST INTERFACE*/
    public void BACK_BUTTON_OF_ARTIST_PLAYLIST(View view) {
        BACK_BUTTON_OF_ARTIST_PLAYLIST_FUNC();


//        if(is_song_info_active){
//            set_all_visibility_off();
//            set_all_interface_button_visibility(false);
//            setting_button.setVisibility(View.GONE);
//            all_playlist_interface.setVisibility(View.VISIBLE);
//            is_song_info_active=false;
//        }else{
//            all_playlist_interface.setVisibility(View.VISIBLE);
//        }

    }
    public void BACK_BUTTON_OF_ARTIST_PLAYLIST_FUNC(){
        if(!is_song_info_active){
            permission_for_flicking=true;
            setting_button.setVisibility(View.VISIBLE);
//            set_all_interface_button_visibility(true);
            set_all_active_flags_to_false();
//            all_artist_interface_constrain_layout.setVisibility(View.VISIBLE);
//            all_artist_interface_constrain_layout.startAnimation(FADE_IN);
            all_playlist_interface.setVisibility(View.VISIBLE);
            all_playlist_interface.startAnimation(FADE_IN);
//        is_album_playlist_active=false;
//        is_recently_added_playlist_active=false;
//        is_user_created_playlist_active=false;
//        is_all_playlist_interface_active=false;
            IS_ALL_ARTIST_INTERFACE_ACTIVE = true;
        }else{
            music_player.startAnimation(FADE_IN);
            music_player.setVisibility(View.VISIBLE);
            is_song_info_active=false;
            IS_MUSIC_PLAYER_ACTIVE=true;
            miniplayer.setVisibility(View.GONE);
        }
        artist_playlist_constrain_layout.startAnimation(FADE_OUT);
        artist_playlist_constrain_layout.setVisibility(View.GONE);
    }


    /*THIS FUNCTION SET ALL INTERFACE CONSTRAIN LAYLOUT TO GONE(INVISIBLE) IN ORDER TO SET A SPECIFIC INTERFACE VISIBLE*/
    public void set_all_visibility_off() {
        Animation Fade_out_animation=AnimationUtils.loadAnimation(this,R.anim.fade_out_animation);
        ConstraintLayout[] array_of_interfaces = {all_song_interface_constrain_layout, all_playlist_interface, all_album_interface_constraint_layout, all_artist_interface_constrain_layout};
        for (ConstraintLayout i : array_of_interfaces) {
//            i.startAnimation(Fade_out_animation);
            i.setVisibility(View.GONE);
        }
    }

    /*THIS FUNCTION SETS ALL INTERFACE IMAGES TO OFF IMAGE ,SO SPECIFIC IMAGE IS SET TO ONIMAGE */
    public void set_all_interface_image_off() { //MAKE A FUNCTION TO SET OFF ONLY THE ONE WHICH IS NECESSARY
        ImageView[] array_of_interfaces_image_view = {ALL_SONGS_INTERFACE_IMAGEVIEW, ALL_PLAYLIST_INTERFACE_IMAGEVIEW, ALL_ALBUMS_INTERFACE_IMAGEVIEW, ALL_ARTIST_INTERFACE_IMAGEVIEW};
        int[] off_id = {R.drawable.song_interface_off, R.drawable.playlist_interface_off, R.drawable.album_interface_off, R.drawable.artist_interface_off};
        for (int i = 0; i < off_id.length; i++) {
            array_of_interfaces_image_view[i].setImageResource(off_id[i]);
        }
    }

/*  THIS FUNCTION SETS ALL ACTIVE FLAGS OF ALL INTERFACE AND ITS PLAYLIST TO FALSE ,SO I SPECIFIC FLAG WILL BE ABLE TO BE SET TRUE */
    public void set_all_active_flags_to_false() {
        IS_ALL_ARTIST_INTERFACE_ACTIVE = false;
        IS_ARTIST_PLAYLIST_ACTIVE = false;
        IS_ALL_ALBUM_INTERFACE_ACTIVE = false;
        IS_ALBUM_PLAYLIST_ACTIVE = false;
        IS_RECENTLY_ADDED_PLAYLIST_ACTIVE = false;
        IS_USER_CREATED_PLAYLIST_ACTIVE = false;
        IS_ALL_PLAYLIST_INTERFACE_ACTIVE = false;
        IS_ALL_SONGS_INTERFACE_ACTIVE = false;

    }

/*  THIS FUNCTION IS USED TO CHECKED WHETHER PERMISSION TO LOAD ALL AUDIOS ARE GIVEN OR NOT */
    public void check_permission() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // Permission already granted
//        } else {
//            // Request the permission
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // Permission already granted
//        } else {
//            // Request the permission
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INSTALL_SHORTCUT) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // Permission already granted
//        } else {
//            // Request the permission
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_SHORTCUT}, 3);
//        }

    }

    public boolean isRingtone(String filePath) {

        return filePath.toLowerCase().contains("ringtones");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform your actions
            } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }

        if (requestCode == 2 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupPhoneStateListener();
            make_a_toast("phone",false);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);

//            make_a_toast("Permission Denied");
        }


//        if (requestCode == 3 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            make_a_toast("short");
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_SHORTCUT}, 3);
//
////            make_a_toast("Permission Denied");
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        make_a_toast("resume",false);

        if(is_activity_minimize){
            is_activity_minimize=false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED){
                Permission_For_External_Storage=true;
            }else{
                Permission_For_External_Storage=false;
            }
        } else {
            if( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Permission_For_External_Storage=true;
            }else{
                Permission_For_External_Storage=false;
            }


        }
        if(!Permission_For_External_Storage||!Permission_For_Telephone){
            startActivity(new Intent(this, Required_Permission_Interface.class));
            finish();
        }
        if(Permission_For_External_Storage&&Permission_For_Telephone){
            load_data_into_array_list_for_recently_added();
            if(preferences.getInt(TOTAL_SONGS_OF_RECENTLY_ADDED_KEY,613)!=TOTAL_SONGS_OF_RECENTLY_ADDED){
                viewPager.setAdapter(adapter_for_view_pager);
                viewPager.setCurrentItem(1);
                editor.putInt(TOTAL_SONGS_OF_RECENTLY_ADDED_KEY,TOTAL_SONGS_OF_RECENTLY_ADDED);
                editor.apply();
            }
        }





//        ACTIVATE_USER_CREATED_PLAYLIST_FROM_HOME_SCREEN_SHORTCUT();
    }
/*  THIS FUNCTION WHEN USER MINIMIZE THE APP AND IT SAVES PERMISSION, SHOULD THE ALL PLAYLIST INTERFACE SHOULD BE SAVED OR NOT*/
    @Override
    protected void onStop() {
        super.onStop();
        if (arrayList_for_all_playlists.size()  >1) {
            save_and_load_array.save_array_for_all_playlist(this, arrayList_for_all_playlists);
            set_permission_for_loading_all_playlist_array(true);
        } else {
            set_permission_for_loading_all_playlist_array(false);
        }
        if(was_music_player_played && preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)){
            make_a_toast("miniplayer is true",false);
            store_miniplayer_permission_data();
        }
        is_activity_minimize=true;


    }
/*  THIS FUNCTION SAVES THE ALL PLAYLIST INTERFACE PLAYLISTS WHICH CONTAINS SYSTEM CREATED AND USER CREATED PLAYLISTS*/
    public void set_permission_for_loading_all_playlist_array(boolean permission) {
        SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOAD_ARRAY_PERMISSION_KEY_FOR_ALL_PLAYLIST, permission);

        editor.apply();
    }

/*  THIS FUNCTION STORE SONG DATA OF LAST SONG WHEN APP IS DESTROYED */
    public void store_mini_player_data() {

        Recently_added_recyclerview_elements_item_class item = temp_array_list.get(current_song_index);
        SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(MINIPLAYER_SONG_NAME_KEY, item.getMsong_name());
        editor.putString(MINIPLAYER_PATH_KEY, item.getMpath());
        editor.putString(MINIPLAYER_ARTIST_NAME_KEY, item.getMartist());
        editor.putString(MINIPLAYER_ALBUM_NAME_KEY, item.getMalbum_name());
        editor.putInt(MINIPLAYER_DURATION_KEY, item.getMduration());
        editor.putLong(MINIPLAYER_ALBUM_ART_KEY, item.getMalbum_art());
        editor.apply();
    }
/* THIS FUNCTION STORES WHICH SONG AND FROM WHICH INTERFACE'S PLAYLIST WILL PLAY WHEN USER OPENS THE APP NEXT TIME
*   IT ALSO SET WHETHER MINIPLAYER SHOULD BE VISIBLE TO USER OR NOT*/
    public void store_miniplayer_permission_data() {
        if (was_music_player_played) {
            SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
//            if (arrayList_for_all_playlists.size()>1 && USER_CREATED_PLAYLIST_POSITION >=arrayList_for_all_playlists.size()) {
//                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,false);
//            }

             if (arrayList_for_all_playlists.size()==2 && (USER_CREATED_PLAYLIST_POSITION!=0 && USER_CREATED_PLAYLIST_POSITION!=arrayList_for_all_playlists.size()-1) && CURRENT_INTERFACE_POSITION!=1){
                make_a_toast("1",false);
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,true);


            }
//            else if (arrayList_for_all_playlists.size()==1 && USER_CREATED_PLAYLIST_POSITION !=0){
//                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,false);
//
//            }

             /*NEW POSSIBILITY : IF SONG WAS PLAYED BEFORE DELETING THE SONG FROM USER STORAGE AND WIDGET WAS ACTIVE,
             SO IF USER CLICK PLAY-PAUSE BUTTON THE NOTIFICATION WON'T BE ABLE TO GET DATA SOURCE DUE PATH BEING SET TO ""
             MAKE SURE TO FIX THE CONFIGURE_NOTIFICATION FUNCTION
              */

             /*NEW POSSIBILITY : IF A SONG IS PLAYING FROM A USER CREATED PLAYLIST AND WHILE PLAYING USER DELETES THE PLAYLIST
             AND THEN PROGRAMMER RUN THE APP IT WILL CRASH THE APP DUE TO BEING NULL TEMP ARRAY BECAUSE store_miniplayer_permission FUNC WAS SKIPPED
             DUE RUNNING APP DIRECTLY
             PROBLEM WILL OCCUR FROM onCreate FUNC'S SHUFFLE SETUP
             */



            else if(arrayList_for_all_playlists.size()>2 && USER_CREATED_PLAYLIST_POSITION !=0){
                 make_a_toast("2",false);
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY, true);

            }else if(arrayList_for_all_playlists.size()==2 && (USER_CREATED_PLAYLIST_POSITION==0||USER_CREATED_PLAYLIST_POSITION==arrayList_for_all_playlists.size())){
                 make_a_toast("3",false);
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,true);
            }
            else if (arrayList_for_all_playlists.size()>2 && CURRENT_INTERFACE_POSITION!=1) {
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,true);
                 make_a_toast("4",false);

            } else if (arrayList_for_all_playlists.size()==2 && CURRENT_INTERFACE_POSITION!=1) {
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,true);
                 make_a_toast("5",false);

            } else if(CURRENT_INTERFACE_POSITION==1 && USER_CREATED_PLAYLIST_POSITION==0){
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,true);
                make_a_toast("6",false);
            }else {
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,false);
            }


            if( CURRENT_INTERFACE_POSITION==1 && Permission_To_Proceed){
                boolean permission_for_miniplayer_activation;
                if(!check_whether_song_or_playlist_already_exists.check_the_playlist(arrayList_for_all_playlists,CURRENT_PLAYING_PLAYLIST)){        //THIS LINE CHECKS WHETHER CURRENTLY_PLAYING_PLAYLIST EXISTS OR NOT

                    if(preferences.getBoolean(CURRENT_PLAYING_PLAYLIST,false)){                                                            //THIS LINE CHECKS WHETHER CURRENTLY_PLAYING_PLAYLIST IS EMPTY OR NOT

                        if(!check_whether_song_or_playlist_already_exists.check_the_song(temp_array_list,item.getMsong_name(),item.getMpath())){   //THIS LINE CHECKS WHETHER CURRENT PLAYING SONG EXISTS IN THAT PLAYLIST OR NOT
                            permission_for_miniplayer_activation=true;
                        }else{
                            make_a_toast("song_does_not_exist_in_the_playlist",false);
                            permission_for_miniplayer_activation=false;
                        }
                    }else{
                        make_a_toast("playlist is empty",false);
                        permission_for_miniplayer_activation=false;
                    }
                }else {
                    make_a_toast("Playlist_does _not exits",false);
                    permission_for_miniplayer_activation=false;
                }
                editor.putBoolean(MINIPLAYER_ACTIVATE_KEY,permission_for_miniplayer_activation);
            }else {
                make_a_toast("permission_to_proceed Is Denied",false);
            }



            editor.putInt("key", current_song_index);

            editor.putInt("key2", USER_CREATED_PLAYLIST_POSITION);
            editor.putInt(CURRENT_INTERFACE_POSITION_KEY, CURRENT_INTERFACE_POSITION);
            editor.putString(CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME_KEY, CURRENT_ALBUM_OR_ARTIST_PLAYLIST_NAME);
            editor.apply();
            store_mini_player_data();
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(IS_MUSIC_PLAYER_ACTIVE){
            HIDE_MUSIC_PLAYER_INTERFACE();
        }else if(IS_USER_CREATED_PLAYLIST_ACTIVE){
            BACK_BUTTON_OF_USER_CREATED_PLAYLIST_FUNC();
        }else if(IS_RECENTLY_ADDED_PLAYLIST_ACTIVE){
            BACK_BUTTON_OF_RECENTLY_ADDED_FUNC();
        }else if(IS_ALBUM_PLAYLIST_ACTIVE){
            BACK_BUTTON_OF_ALBUM_PLAYLIST_FUNC();
        }else if(IS_ARTIST_PLAYLIST_ACTIVE){
            BACK_BUTTON_OF_ARTIST_PLAYLIST_FUNC();
        }else if(IS_SETTING_INTERFACE_ACTIVE){
            count_for_option_button=1;
            SETTING_OPTION_BUTTON_FUNC();
        }

    }


    /* THIS OVERRIDE METHOD CLOSES THE APP AND STOPS THE MUSIC PLAYER AND SERVICE AND MANY MORE IN SIMPLE WORDS IT WRAPUPS EVERYTHING */
    @Override
    protected void onDestroy() {
        try {
            updateWidget(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        media_player.release_media_player();
        Intent ser=new Intent(this, Feel_it_Service.class);
        stopService(ser);
        SharedPreferences preferences = getSharedPreferences("preff", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOAD_ARRAY_PERMISSION_KEY_FOR_ALL_PLAYLIST, true);
        editor.putBoolean("app",false);
        editor.apply();

        handler.removeCallbacks(runnable2);
        handler.removeCallbacks(mrunnable);
        handler.removeCallbacks(runnable_for_notification_action);
        mediaSession.release();
        if (telephonyManager != null && phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        DeviceConnectionChecker connectionChecker=new DeviceConnectionChecker(getApplicationContext());
        connectionChecker.unregisterReceiver();
        super.onDestroy();

    }

    @Override
    public void BOTTOM_CLASS_SENDS_POSITION_FROM_RECYCLER_VIEW(int POSITION) {
        selected_playlist_from_bottom_fragment_position_array_list.add(POSITION);
    }



    /* THIS IS ONE OF THE MOST IMPORTANT OVERRIDDEN BOTTOM FRAGMENT CLASS BUTTON
    *   IT CHECKS WHETHER USER SELECTED SINGLE SONG OR MULTIPLE SONGS .ON BASIS THE SELECTION IT TAKES THE ACTION
    *   DOWN THERE IS A ARRAYLIST CALLED    "playlist_position_array_list " WHICH CONTAINS UNIQUE ALBUM SELECTED POSITION */
    @Override
    public void BOTTOM_CLASS_ADD_SONG_TO_MULTIPLE_PLAYLIST_BUTTON_FUNCTION() {
        selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
        for (int i = 0; i < arrayList_for_add_playlist_without_recently_added.size(); i++) {
            if (arrayList_for_add_playlist_without_recently_added.get(i).isMis_selected()) {
                selected_playlist_from_bottom_fragment_position_array_list.add(i);
                make_a_toast(String.format("%s : SELECTED", arrayList_for_add_playlist_without_recently_added.get(i).getMPlaylist_name()),true);
            }
        }
        if (is_single_song_selected) {
            make_a_toast("Single Song Selected",true);
            add_single_song_to_desired_playlist_using_unique_selected_playlist_position_array(selected_playlist_from_bottom_fragment_position_array_list, selected_playlist_from_bottom_fragment_position_array_list.size());
        } else {
            make_a_toast("Multiple Songs Selected",true);


//            arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist=new ArrayList<>();
//            for(int j=0;j<arrayList_for_recently_added_playlist.size();j++){
//                if(arrayList_for_add_multiple_songs_to_multiple_playlist.get(j).isMis_selected()){
//                    arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist.add(j);
//                    make_a_toast(String.format("%s : Selected",arrayList_for_recently_added_playlist.get(j).getMsong_name()));
//                }
//            }
            ArrayList<Integer> SELECTED_SONG_POSITION_ARRAY_IN_SEQUENCE = new ArrayList<>();
            SELECTED_SONG_POSITION_ARRAY_IN_SEQUENCE = adapter_for_add_multiple_songs_to_multiple_playlist.getSelected_songs_in_sequence();  //THIS PROVIDES A SONG POSITION ARRAYLIST WHICH IS IN SEQUENCE FROM ADAPTER OF SELECT MULTIPLE SONGS
//            String result="";
//            for (int r=0;r<SELECTED_SONG_POSITION_ARRAY_IN_SEQUENCE.size();r++){
//                result=result+String.format("%d ",SELECTED_SONG_POSITION_ARRAY_IN_SEQUENCE.get(r));
//            }
//            make_a_toast(result);
//            check_the_element_which_are_meant_to_be_selected(arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist,arrayList_for_songs_position_in_add_multiple_songs_to_multiple_playlist.size());
            add_multiple_songs_to_desired_playlist_using_unique_selected_playlist_position_array(SELECTED_SONG_POSITION_ARRAY_IN_SEQUENCE,    //SELECTED SONGS POSITION ARRAYLIST IN SEQUENCE
                                                                                                SELECTED_SONG_POSITION_ARRAY_IN_SEQUENCE.size(), //SELECTED SONGS POSITION ARRAYLIST SIZE
                                                                                                selected_playlist_from_bottom_fragment_position_array_list,//SELECTED PLAYLIST POSITION ARRAYLIST
                                                                                                selected_playlist_from_bottom_fragment_position_array_list.size()); //SELECTED PLAYLIST POSITION ARRAYLIST SIZE


        }
    }



    /* THIS IS A FUNCTION TO HIDE ALL THE INTERFACE ACTIVATE BUTTON  */
    public void set_all_interface_button_visibility(boolean permission) {
//        ImageView[] array_of_interfaces_image_view = {ALL_SONGS_INTERFACE_IMAGEVIEW, ALL_PLAYLIST_INTERFACE_IMAGEVIEW, ALL_ALBUMS_INTERFACE_IMAGEVIEW, ALL_ARTIST_INTERFACE_IMAGEVIEW};
//        for (ImageView i : array_of_interfaces_image_view) {
//            i.setVisibility(permission ? View.VISIBLE : View.GONE);
//        }
//        ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.startAnimation(permission?FADE_IN:FADE_OUT);
//        ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.setVisibility(permission?View.VISIBLE:View.GONE);

    }
/*  THIS FUNCTION CREATES A NOTIFICATION WHEN APP STARTS AND ACTIVATES THE SERVICE.IT SENDS THE SONG DETAILS BEFORE THE APP WAS CLOSED LAST TIME */
    public void activate_notification(String SONG_NAME,String ARTIST_NAME,long ALBUM_ART) {
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        Intent service_intent=new Intent(this, Feel_it_Service.class);
        service_intent.putExtra("SONG_NAME",SONG_NAME);
        service_intent.putExtra("ARTIST_NAME",ARTIST_NAME);
        service_intent.putExtra("ALBUM_ART",ALBUM_ART);
        startService(service_intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


    }


/*  THIS IS A FUNCTION WHICH CHANGES THE NOTIFICATION DATA WHEN USER CHANGES THE SONG IT STARTS A NEW SERVICE
    IT WILL CHANGE THE SONG NAME ,ARTIST NAME AND ALBUM ART

*   MESSAGE TO PROGRAMMER : TRY NOT TO START MORE SERVICES
    EDIT : I MADE SURE THAT :) */
    @SuppressLint("ResourceType")
    public void configure_notification(String Song_Name, String Artist_Name, long Album_Art){
//        make_a_toast("configure",true);
        Bitmap bitmap1,albumArtBitmap = null;
//        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + Album_Art);
        RemoteViews expanded=new RemoteViews(getPackageName(),R.layout.custom_notification);
//        expanded.setInt(R.layout.custom_notification, "setBackgroundColor", getResources().getColor(R.color.black));
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(temp_array_list.get(current_song_index).getMpath());
        Intent intent = new Intent(this, MUSIC_PLAYER_ACTIVITY.class);
        int iconResId = is_media_player_paused ? R.drawable.play_icon : R.drawable.pause;


        byte[] albumArtBytes = retriever.getEmbeddedPicture();
        if (albumArtBytes != null) {
            albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);

//            InputStream inputStream = getContentResolver().openInputStream(albumArtUri);
            albumArtBitmap = getOptimizedBitmap(albumArtBitmap);


//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                bitmap1=getRoundedCornerBitmap(bitmap,25);
            bitmap1 = getRoundedCornerBitmap(albumArtBitmap, 25);

            expanded.setImageViewBitmap(R.id.custom_notification_album_art_imageview, bitmap1);

//                inputStream.close();
            expanded.setTextViewText(R.id.custom_notification_Song_textview, Song_Name);
            expanded.setTextViewText(R.id.custom_notification_artist_textview, Artist_Name);

            if (media_player.get_media_player().isPlaying()) {
                expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview, R.drawable.pause);
            } else {
                expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview, R.drawable.play_icon);
            }


//            expanded.setOnClickPendingIntent(R.id.test,PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_MUTABLE));
            expanded.setOnClickPendingIntent(R.id.notification_linear, PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE));


            expanded.setOnClickPendingIntent(R.id.custom_notification_previous_song_imageview, get_pending_intent_for_music_player("PREVIOUS_SONG"));
            expanded.setOnClickPendingIntent(R.id.custom_notification_play_pause_imageview, get_pending_intent_for_music_player("PLAY_PAUSE"));
            expanded.setOnClickPendingIntent(R.id.custom_notification_next_song_imageview, get_pending_intent_for_music_player("NEXT_SONG"));
            expanded.setOnClickPendingIntent(R.id.custom_notification_close_imageview, get_pending_intent_for_music_player("CLOSE"));

//            if (notification_builder == null) {
//                notification_builder = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                        .setSmallIcon(R.drawable.logo)                         //this part is for custom notification
//                        .setPriority(NotificationCompat.PRIORITY_MAX)
//                        .setOnlyAlertOnce(true)
////                        .setLargeIcon(bitmap1)
//
//                        .setContentTitle("Feel It!!")
//
////                        .addAction(R.drawable.pause_button_music_player,null,get_pending_intent_for_music_player("PLAY_PAUSE"))
////                        .addAction(R.drawable.previous_song_button_music_player,null,get_pending_intent_for_music_player("PREVIOUS"))
////                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
////                                .setMediaSession(mediaSession.getSessionToken()))
//
//                        .setCustomBigContentView(expanded)
//                        .setContentText(String.format("NOW PLAYING : %s",Song_Name))
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
//                        }else {
//
//                              notification_builder.setContentText(String.format("NOW PLAYING : %s",Song_Name));
//                              notification_builder.setCustomBigContentView(expanded);
//                        }



// Get the Uri of the temporary file
//            Uri uri = Uri.fromFile(tempFile);
//            Picasso.get().load(uri).into(holder.imageView_for_song_album_art);


//            Picasso.get().load(uri).into(music_player_album_art_image_view);
//            Picasso.get().load(uri).into(miniplayer_album_art_imageview);
//            music_player_album_art_image_view.setImageBitmap(albumArtBitmap);
//            miniplayer_album_art_imageview.setImageBitmap(albumArtBitmap);

        } else {
            albumArtBitmap=drawableToBitmap(getResources().getDrawable(R.drawable.logo));
            // No album art available
        }

        try {
            retriever.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notification_builder = new NotificationCompat.Builder(this, CHANNEL_1_ID);

        notification_builder.setSmallIcon(R.drawable.logo)  //this is for mediaStyle notification
                .setContentTitle(Song_Name)
                .setContentText(Artist_Name)
                .setLargeIcon(albumArtBitmap)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setMediaSession(mediaSession.getSessionToken())
//                                .setShowActionsInCompactView(0, 1)
                )
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE))
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.skip_to_previous, null, get_pending_intent_for_music_player("PREVIOUS_SONG"))
                .addAction(iconResId, null, get_pending_intent_for_music_player("PLAY_PAUSE"))

                .addAction(R.drawable.skip_to_next, null, get_pending_intent_for_music_player("NEXT_SONG"))
                .addAction(R.drawable.baseline_close_24, null, get_pending_intent_for_music_player("CLOSE"))

                .setPriority(NotificationCompat.VISIBILITY_PUBLIC)
        ;


//                notification_builder.setSmallIcon(R.drawable.logo)
//                        .setContentTitle(Song_Name)
//                        .setContentText(Artist_Name)
//                        .setLargeIcon(albumArtBitmap)
//                        .addAction(iconResId,null,get_pending_intent_for_music_player("PLAY_PAUSE"))
//
//                        .addAction(R.drawable.skip_to_previous,null,get_pending_intent_for_music_player("PREVIOUS_SONG"))
//
//                        .addAction(R.drawable.skip_to_next,null,get_pending_intent_for_music_player("NEXT_SONG"))
//                        .addAction(R.drawable.baseline_close_24,null,get_pending_intent_for_music_player("CLOSE"));
//
//            }

        Notification notification = notification_builder.build();

        notificationManager.notify(1, notification);
        

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















    }
    private Bitmap getOptimizedBitmap(Bitmap bitmap) {
        // Ensure the bitmap uses ARGB_8888 configuration for the best quality
        Bitmap optimizedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        return Bitmap.createScaledBitmap(optimizedBitmap, 256, 256, false);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }




    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int cornerRadius) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = cornerRadius;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    public PendingIntent get_pending_intent_for_music_player(String ACTION){


        Intent intent=new Intent(getApplicationContext(),Feel_it_notification_broadcast_receiver.class);
        intent.setAction(ACTION);
        return PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_MUTABLE);
    }
    private int is_app_just_started_timer=0;
    private int app_start_inactivity_timer=0;
    private boolean permission_for_still_listening_time=false;
    private Runnable runnable_for_notification_action=new Runnable() {
        @Override
        public void run() {
            SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
            String Action=preferences.getString("ACTION","");
//                if(is_sleep_timer_active){
//                    sleep_timer_time_in_secs-=1;
//
//                    if(sleep_timer_time_in_secs==0){
//                        finish();
//                        media_player.release_media_player();
//                        mediaSession.release();
//                        mediaSession.setActive(false);
//                        handler.removeCallbacks(runnable_for_notification_action);
//                        is_sleep_timer_active=false;
//                    }
//                }

            if(!Action.equals("")){
                switch (Action){
                    case "PLAY_PAUSE":   //TOGGLE BETWEEN PLAY AND PAUSE
                        PLAY_AND_PAUSE();
                        break;

                    case "NEXT_SONG":      //PLAY NEXT SONG
                        NEXT_SONG();
                        break;

                    case "PREVIOUS_SONG":    //PLAY PREVIOUS SONG
                        PREVIOUS_SONG();
                        break;

                    case "CLOSE":          //THIS CLOSES THE APP
                        media_player.get_media_player().pause();
                        try {
                            updateWidget(getApplicationContext());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        store_miniplayer_permission_data();
                        handler.removeCallbacks(runnable_for_notification_action);
                        finish();
                        break;

                    case "FAVOURITE":     //THIS ACTIVATE FAVOURITE MODE
                        ACTIVATE_FAVOURITE();
                        break;

                    case "SHUFFLE":               //THIS ACTIVATE SHUFFLE MODE
                        try {
                            ACTIVATE_SHUFFLE();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "REPEAT":       //THIS ACTIVATE REPEAT MODE
                        try {
                            ACTIVATE_REPEAT();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "PLAYLIST_ON_CLICKED":    //THIS ACTIVATE A PLAYLIST FROM ALL PLAYLISTS FRAGMENT
                        try {
                            ON_CLICK_PLAYLIST(preferences.getInt(Action, 0));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "PLAY_PLAYLIST":   //THIS PLAY THE FIRST SONG OF A PLAYLIST
                        try {
                            PLAY_PLAYLIST(preferences.getInt(Action, 0));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "ALL_SONG_INTERFACE":    //THIS PLAYS A SONG FROM ALL SONGS FRAGMENT WHEN CLICKED
                        try {
                            PLAY_SONG_FROM_ALL_SONG_INTERFACE(preferences.getInt("ALL_SONG_INTERFACE", 0));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "ADD_SONG_FROM_ALL_SONG_INTERFACE_MORE":    //THIS ADDS A SELECTED SONG FROM ALL SONGS FRAGMENT
                        ADD_SINGLE_SONG_FROM_ALL_SONG_INTERFACE(preferences.getInt(Action, 0));
                        break;

                    case "ALBUM_PLAYLIST_ON_CLICKED":   //THIS ACTIVATE A ALBUM PLAYLIST FROM ALL ALBUM FRAGMENT
                        try {
                            ACTIVATE_ALBUM_PLAYLIST(
                                    preferences.getLong("ALBUM_PLAYLIST_ON_CLICKED_ALBUM_ART", 10),
                                    preferences.getString("ALBUM_PLAYLIST_ON_CLICKED_ALBUM_NAME", ""),
                                    preferences.getInt("ALBUM_PLAYLIST_ON_CLICKED_TOTAL_SONGS", 0),
                                    preferences.getInt("ALBUM_PLAYLIST_ON_CLICKED_ALBUM_POSITION", 10));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "ARTIST_PLAYLIST_ON_CLICKED":    //THIS ACTIVATE A ARTIST PLAYLIST FROM ALL ARTIST FRAGMENT
                        try {
                            ACTIVATE_ARTIST_PLAYLIST(
                                    preferences.getLong("ARTIST_PLAYLIST_ON_CLICKED_ALBUM_ART", 10),
                                    preferences.getString("ARTIST_PLAYLIST_ON_CLICKED_ARTIST_NAME", ""),
                                    preferences.getInt("ARTIST_PLAYLIST_ON_CLICKED_TOTAL_SONGS", 5),
                                    preferences.getInt("ARTIST_PLAYLIST_ON_CLICKED_POSITION", 10));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        break;

                }
            }



//



            put_action_command_to_null();


            handler.postDelayed(runnable_for_notification_action,1000);

        }

    };

    /*  THIS IS THE FUNCTION WHICH I HATE TO USE ,BUT I HAVE TO USE UNTIL I FIND A ALTERNATIVE METHOD
     *   THIS IS FUNCTION WHICH RUNS A RUNNABLE FROM WHEN APP IS ON UNTIL APP IS DESTROYED
     *   IT CONSTANTLY TAKES A ACTION STRING FROM SHARED PREFERENCES AND CHECKS WHETHER ANY OF THE CONDITION IS MATCHED OR NOT
     *   IF A CONDITION IS MATCHED ACTION IS TAKEN
     *   SEE THE UPPER RUNNABLE FOR MORE INFO*/
    public void check_notification_action_commands(){
        handler.postDelayed(runnable_for_notification_action,0);
    }
    public void put_action_command_to_null(){
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putString("ACTION","");
        editor.apply();

    }


    private int count_for_option_button=0;
    public void SETTING_OPTION_BUTTON(View view){
        SETTING_OPTION_BUTTON_FUNC();
    }
    public void SETTING_OPTION_BUTTON_FUNC(){
        count_for_option_button+=1;
        AnimatedVectorDrawable firstAnimation = (AnimatedVectorDrawable) ContextCompat.getDrawable(this, R.drawable.setting_start_anim);
        AnimatedVectorDrawable secondAnimation = (AnimatedVectorDrawable) ContextCompat.getDrawable(this, R.drawable.setting_end_anim);
        if(count_for_option_button==1){

            secondAnimation.stop();
            setting_button.setImageDrawable(firstAnimation);

            firstAnimation.start();
            back_function_protocol(false);
            setting_interface_constraint_layout.startAnimation(SLIDE_LEFT_ANIMATION);
            setting_interface_constraint_layout.setVisibility(View.VISIBLE);



//            setting_button.setImageResource(R.drawable.setting_on);
            miniplayer.setVisibility(View.GONE);
            IS_MUSIC_PLAYER_ACTIVE=false;
            IS_SETTING_INTERFACE_ACTIVE=true;
            permission_for_flicking=false;



            SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
            is_radioButton_of_Bluetooth_device_selected=preferences.getBoolean(AUDIO_CONNECTIVITY_MODE_KEY,false);
//            set_connectivity();

            if(preferences.getBoolean(PAUSE_TIMER_ACTIVATION_KEY,false)){
                if(!is_any_timer_running){
                    set_pause_timer(preferences.getInt(PAUSE_TIMER_TIME_KEY,900),true);
                }
                switch_of_pause_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));
                switch_of_pause_timer.setChecked(true);
            }
            else {
                switch_of_pause_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#a0a0a0")));
                switch_of_pause_timer.setChecked(false);

            }


            if(preferences.getBoolean(PLAY_TIMER_ACTIVATION_KEY,false)){
                if(!is_any_timer_running){
                    set_pause_timer(preferences.getInt(PLAY_TIMER_TIME_KEY,900),false);
                }
                switch_of_play_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));
                switch_of_play_timer.setChecked(true);
            }
            else {
                switch_of_play_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#a0a0a0")));
                switch_of_play_timer.setChecked(false);

            }
            set_event_controls();
            set_all_visibility_off();
            set_all_interface_button_visibility(false);
            set_slots_controls();

        }
        else {
            firstAnimation.stop();
            setting_button.setImageDrawable(secondAnimation);

            secondAnimation.start();
//            setting_button.setImageResource(R.drawable.setting_off);

            set_all_interface_button_visibility(true);

//            miniplayer.startAnimation(FADE_IN);
            miniplayer.setVisibility(preferences.getBoolean(MINIPLAYER_ACTIVATE_KEY,false)?View.VISIBLE:View.GONE);
//            back_function_protocol(true);
//            activate_interface();

            count_for_option_button=0;
            Animation Slide_Right=AnimationUtils.loadAnimation(this,R.anim.slide_right_animation_for_setting);
            setting_interface_constraint_layout.startAnimation(Slide_Right);
            setting_interface_constraint_layout.setVisibility(View.GONE);
            all_playlist_interface.setVisibility(View.VISIBLE);
            all_playlist_interface.startAnimation(FADE_IN);

            IS_SETTING_INTERFACE_ACTIVE=false;

            permission_for_flicking=true;


            count_for_external_audio_control=1;               
            Activate_EXTERNAL_AUDIO_CONTROL_FUNC();

            count_for_backup_playlist=1;
            ACTIVATE_BACKUP_PLAYLIST_FUNC();

            count_for_pause_timer_activity_attributes=1;
            ACTIVATE_PAUSE_TIMER_ACTIVITY_ATTRIBUTES_FUNC();

            count_for_play_timer_activity_attributes=1;
            ACTIVATE_PLAY_TIMER_ACTIVITY_ATTRIBUTES_FUNC();

            count_for_activate_customize_widget=1;
            ACTIVATE_CUSTOMIZE_WIDGET();




//            count_for_slot_1_controls=1;
//            count_for_slot_2_controls=1;
//            ACTIVATE_SLOT1_OF_WIDGET();
//            ACTIVATE_SLOT2_OF_WIDGET();


        }
    }
    public void activate_interface(){

        if(IS_ALL_SONGS_INTERFACE_ACTIVE){
            all_song_interface_constrain_layout.setVisibility(View.VISIBLE);
        } else if (IS_ALL_PLAYLIST_INTERFACE_ACTIVE) {
            all_playlist_interface.setVisibility(View.VISIBLE);
        } else if (IS_ALL_ALBUM_INTERFACE_ACTIVE) {
            all_album_interface_constraint_layout.setVisibility(View.VISIBLE);
        } else if (IS_ALL_ARTIST_INTERFACE_ACTIVE) {
            all_artist_interface_constrain_layout.setVisibility(View.VISIBLE);

        }


    }

//    public void SET_CONNECTIVITY_MODE_FOR_BLUETOOTH(View view){
//            is_radioButton_of_Bluetooth_device_selected=true;
//            set_connectivity();
//
//
//
//    }
//    public void SET_CONNECTIVITY_MODE_FOR_EARPHONES(View view){
//        is_radioButton_of_Bluetooth_device_selected=false;
//        set_connectivity();
//
//
//    }
//    public void set_connectivity(){
//        if(is_radioButton_of_Bluetooth_device_selected){
//            radioButton_of_Bluetooth_device.setImageResource(R.drawable.radio_button_on);
//            radioButton_of_Wired_device.setImageResource(R.drawable.radio_button_off);
//            make_a_toast("bluetooth selected");
//        }
//        else {
//            radioButton_of_Bluetooth_device.setImageResource(R.drawable.radio_button_off);
//            radioButton_of_Wired_device.setImageResource(R.drawable.radio_button_on);
//            make_a_toast("!bluetooth selected");
//        }
//        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putBoolean(AUDIO_CONNECTIVITY_MODE_KEY,is_radioButton_of_Bluetooth_device_selected);
//        editor.apply();
//    }

    //PLAY PREVIOUS MODE OPTIONS

    private int count_for_external_audio_control=0;
    public void Activate_EXTERNAL_AUDIO_CONTROL(View view){
        Activate_EXTERNAL_AUDIO_CONTROL_FUNC();
    }
    public void Activate_EXTERNAL_AUDIO_CONTROL_FUNC(){
        count_for_external_audio_control+=1;
        if(count_for_external_audio_control==1){
//            external_audio_control_activate_play_next_mode_options_text_view.setVisibility(View.VISIBLE);
//            TransitionManager.beginDelayedTransition(external_audio_control_expanded_layout, new ChangeBounds());


            set_visibility_of_external_audio_control_play_next(false);
            set_visibility_of_external_audio_control_play_previous(false);
            external_audio_control_expanded_layout.startAnimation(FADE_IN);
            external_audio_control_expanded_layout.setVisibility(View.VISIBLE);
//            external_audio_constrain_layout.setVisibility(View.VISIBLE);
//            external_audio_constrain_layout2.setVisibility(View.VISIBLE);
//
//            external_audio_control_activate_play_next_mode_options_text_view.setVisibility(View.VISIBLE);
//            external_audio_control_activate_play_previous_mode_options_text_view.setVisibility(View.VISIBLE);


        }
        else {
            external_audio_control_expanded_layout.setVisibility(View.GONE);
            external_audio_control_activate_play_next_mode_options_text_view.setVisibility(View.GONE);
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(external_audio_constrain_layout);
            TransitionManager.beginDelayedTransition(external_audio_control_expanded_layout, slide);

            external_audio_constrain_layout.setVisibility(View.GONE);
            external_audio_constrain_layout2.setVisibility(View.GONE);

            external_audio_control_activate_play_next_mode_options_text_view.setVisibility(View.GONE);
            external_audio_control_activate_play_previous_mode_options_text_view.setVisibility(View.GONE);

            set_visibility_of_external_audio_control_play_next(false);
            set_visibility_of_external_audio_control_play_previous(false);
            set_visibility_of_info_messages_to_gone(true);
            set_visibility_of_info_messages_to_gone(false);
            info_message_of_Play_next_mode_of_play_next_song_textview.setVisibility(View.GONE);
            info_message_of_Play_next_mode_of_play_previous_song_textview.setVisibility(View.GONE);
            info_message_of_Play_next_mode_of_play_previous_song_textview_v2.setVisibility(View.GONE);
            count_for_external_audio_control = 0;

            count_for_external_audio_controls_for_play_previous_mode_options=0;
            count_for_external_audio_control_for_play_next_mode_options=0;
        }

    }


    private int count_for_external_audio_control_for_play_next_mode_options=0;
    public void Activate_EXTERNAL_AUDIO_CONTROL_PLAY_NEXT_MODE_OPTIONS(View view){
//        count_for_external_audio_controls_for_play_previous_mode_options=0;
        count_for_external_audio_control_for_play_next_mode_options+=1;
        if(count_for_external_audio_control_for_play_next_mode_options==1){
            external_audio_constrain_layout.setVisibility(View.VISIBLE);
            external_audio_constrain_layout.startAnimation(FADE_IN);
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
//            TransitionManager.beginDelayedTransition(external_audio_control_expanded_layout, new ChangeBounds());
            set_visibility_of_external_audio_control_play_next(true);



        }
        else {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(external_audio_constrain_layout);
            external_audio_constrain_layout.setVisibility(View.GONE);

//            TransitionManager.beginDelayedTransition(external_audio_control_expanded_layout, slide);
            set_visibility_of_external_audio_control_play_next(false);

//            info_message_of_Play_next_mode_of_play_next_song_textview.setVisibility(View.GONE);
            count_for_external_audio_control_for_play_next_mode_options= 0;
        }

    }





    private int count_for_info_message_of_play_next_mode__play_next_song =0;
    public void Activate_Info_Message_For_Play_Next_Song(View view){
        count_for_info_message_of_play_next_mode__play_previous_song=0;
        count_for_info_message_of_play_next_mode__play_previous_song_v2=0;

        count_for_info_message_of_play_next_mode__play_next_song +=1;
        if(count_for_info_message_of_play_next_mode__play_next_song ==1){
//            set_visibility_of_external_audio_control_play_next(true);

//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
////            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_next_song_mode_play_next_song, new ChangeBounds());
//            set_visibility_of_info_messages_to_gone(true);
//            info_message_of_Play_next_mode_of_play_next_song_textview.setVisibility(View.VISIBLE);
//            set_radio_button_visibility(true);
//            radio_button_of_play_next_mode_play_next_song.setImageResource(R.drawable.radio_button_on);
//
//            set_external_audio_controls("PLAY_NEXT_SONG",true);

            activate_radio_button_on_click(external_audio_constrain_layout,true,info_message_of_Play_next_mode_of_play_next_song_textview,true,
                    radio_button_of_play_next_mode_play_next_song,"PLAY_NEXT_SONG",true);


        }
        else {
//            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_next_song_mode_play_next_song, new ChangeBounds());
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
//            info_message_of_Play_next_mode_of_play_next_song_textview.setVisibility(View.GONE);
            deactivate_radio_button_on_click(external_audio_constrain_layout,info_message_of_Play_next_mode_of_play_next_song_textview);
            count_for_info_message_of_play_next_mode__play_next_song =0;
        }

    }
    private int count_for_info_message_of_play_next_mode__play_previous_song=0;
    public void Activate_Info_Message_For_Play_Previous_Song(View view){
        count_for_info_message_of_play_next_mode__play_previous_song_v2=0;
        count_for_info_message_of_play_next_mode__play_next_song=0;

        count_for_info_message_of_play_next_mode__play_previous_song +=1;
        if(count_for_info_message_of_play_next_mode__play_previous_song ==1){
//            set_visibility_of_external_audio_control_play_next(true);

//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
////            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_next_song_mode_play_previous_song, new ChangeBounds());
//            set_visibility_of_info_messages_to_gone(true);
//            info_message_of_Play_next_mode_of_play_previous_song_textview.setVisibility(View.VISIBLE);
//
//            set_radio_button_visibility(true);
//            radio_button_of_play_next_mode_play_previous_song.setImageResource(R.drawable.radio_button_on);
//            set_external_audio_controls("PLAY_PREVIOUS_SONG",true);

            activate_radio_button_on_click(external_audio_constrain_layout,true,info_message_of_Play_next_mode_of_play_previous_song_textview,true,
                    radio_button_of_play_next_mode_play_previous_song,"PLAY_PREVIOUS_SONG",true);
        }
        else {
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
////            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_next_song_mode_play_previous_song, new ChangeBounds());
//            info_message_of_Play_next_mode_of_play_previous_song_textview.setVisibility(View.GONE);
            deactivate_radio_button_on_click(external_audio_constrain_layout,info_message_of_Play_next_mode_of_play_previous_song_textview);
            count_for_info_message_of_play_next_mode__play_previous_song =0;
        }

    }
    private int count_for_info_message_of_play_next_mode__play_previous_song_v2=0;
    public void Activate_Info_Message_For_Play_Previous_V2_Song(View view){
        count_for_info_message_of_play_next_mode__play_next_song=0;
        count_for_info_message_of_play_next_mode__play_previous_song=0;


        count_for_info_message_of_play_next_mode__play_previous_song_v2 +=1;
        if(count_for_info_message_of_play_next_mode__play_previous_song_v2 ==1){
//            set_visibility_of_external_audio_control_play_next(true);
//            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_next_song_mode_play_previous_song_v2, new ChangeBounds());

//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
//            set_visibility_of_info_messages_to_gone(true);
//            info_message_of_Play_next_mode_of_play_previous_song_textview_v2.setVisibility(View.VISIBLE);
//            set_radio_button_visibility(true);
//            radio_button_of_play_next_mode_play_previous_song_v2.setImageResource(R.drawable.radio_button_on);
//            set_external_audio_controls("PLAY_PREVIOUS_SONG_V2",true);
            activate_radio_button_on_click(external_audio_constrain_layout,true,info_message_of_Play_next_mode_of_play_previous_song_textview_v2,
                    true,radio_button_of_play_next_mode_play_previous_song_v2,"PLAY_PREVIOUS_SONG_V2",true);
        }
        else {
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout, new ChangeBounds());
////            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_next_song_mode_play_previous_song_v2, new ChangeBounds());
//            info_message_of_Play_next_mode_of_play_previous_song_textview_v2.setVisibility(View.GONE);
            count_for_info_message_of_play_next_mode__play_previous_song_v2 =0;
            deactivate_radio_button_on_click(external_audio_constrain_layout,info_message_of_Play_next_mode_of_play_previous_song_textview_v2);
        }

    }
    public void activate_radio_button_on_click(ConstraintLayout CONSTRAINT_LAYOUT_OF_RADIO_BUTTON,boolean INFO_MESSAGE_VISIBILITY_PERMISSION,TextView INFO_MESSAGE_TEXTVIEW,boolean RADIO_BUTTON_VISIBILITY_PERMISSION,ImageView RADIO_BUTTON_IMAGEVIEW,String EVENT_NAME,boolean PERMISSION_FOR_PLAY_NEXT_MODE){
        TransitionManager.beginDelayedTransition(CONSTRAINT_LAYOUT_OF_RADIO_BUTTON, new ChangeBounds());
        set_visibility_of_info_messages_to_gone(INFO_MESSAGE_VISIBILITY_PERMISSION);
        INFO_MESSAGE_TEXTVIEW.setVisibility(View.VISIBLE);
        set_radio_button_visibility(RADIO_BUTTON_VISIBILITY_PERMISSION);
        RADIO_BUTTON_IMAGEVIEW.setImageResource(R.drawable.radio_button_on);
        set_external_audio_controls(EVENT_NAME,PERMISSION_FOR_PLAY_NEXT_MODE);

    }
    public void deactivate_radio_button_on_click(ConstraintLayout CONSTRAINT_LAYOUT_OF_RADIO_BUTTON,TextView INFO_MESSAGE_TEXTVIEW){
        TransitionManager.beginDelayedTransition(CONSTRAINT_LAYOUT_OF_RADIO_BUTTON, new ChangeBounds());
        INFO_MESSAGE_TEXTVIEW.setVisibility(View.GONE);
    }


    //PLAY PREVIOUS MODE OPTIONS CONTROLS

    private int count_for_external_audio_controls_for_play_previous_mode_options=0;
    public void Activate_EXTERNAL_AUDIO_CONTROL_PLAY_PREVIOUS_MODE_OPTIONS(View view){
//        count_for_external_audio_control_for_play_next_mode_options=0;

        count_for_external_audio_controls_for_play_previous_mode_options+=1;
        if(count_for_external_audio_controls_for_play_previous_mode_options==1){
                external_audio_constrain_layout2.startAnimation(FADE_IN);
                external_audio_constrain_layout2.setVisibility(View.VISIBLE);
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
            set_visibility_of_external_audio_control_play_previous(true);



        }
        else {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(external_audio_constrain_layout2);
            set_visibility_of_external_audio_control_play_previous(false);

            external_audio_constrain_layout2.setVisibility(View.GONE);
//            info_message_constrain_layout_of_previous_song_mode_play_next_song.setVisibility(View.GONE);

            count_for_external_audio_controls_for_play_previous_mode_options=0;
        }

    }
    private int count_for_info_message_of_play_previous_mode__play_next=0;
    public void Activate_Info_message_Play_Previous_Mode_Play_Next_Song(View view){

        count_for_info_message_of_play_previous_mode__play_next+=1;
        count_for_info_message_of_play_previous_mode__play_previous=0;
        count_for_info_message_of_play_previous_mode__play_previous_v2=0;

        if(count_for_info_message_of_play_previous_mode__play_next==1){
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
////            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_previous_song_mode_play_next_song, new ChangeBounds());
//            set_visibility_of_info_messages_to_gone(false);
//            set_external_audio_controls("PLAY_NEXT_SONG",false);
//            set_radio_button_visibility(false);
//            radio_button_of_play_previous_mode_play_next_song.setImageResource(R.drawable.radio_button_on);
//            info_message_of_Play_previous_mode_of_play_next_song_textview.setVisibility(View.VISIBLE);

            activate_radio_button_on_click(external_audio_constrain_layout2,false,info_message_of_Play_previous_mode_of_play_next_song_textview,false,
                    radio_button_of_play_previous_mode_play_next_song,"PLAY_NEXT_SONG",false);
        }
        else {
//            info_message_of_Play_previous_mode_of_play_next_song_textview.setVisibility(View.GONE);
//
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
            deactivate_radio_button_on_click(external_audio_constrain_layout2,info_message_of_Play_previous_mode_of_play_next_song_textview);
            count_for_info_message_of_play_previous_mode__play_next=0;
        }

    }

    private int count_for_info_message_of_play_previous_mode__play_previous=0;
    public void Activate_Info_message_Play_Previous_Mode_Play_Previous_Song(View view){
        count_for_info_message_of_play_previous_mode__play_next=0;
        count_for_info_message_of_play_previous_mode__play_previous+=1;
        count_for_info_message_of_play_previous_mode__play_previous_v2=0;


        if(count_for_info_message_of_play_previous_mode__play_previous==1){
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
////            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_previous_song_mode_play_next_song, new ChangeBounds());
//            set_visibility_of_info_messages_to_gone(false);
//            set_external_audio_controls("PLAY_PREVIOUS_SONG",false);
//            set_radio_button_visibility(false);
//
//            radio_button_of_play_previous_mode_play_previous_song.setImageResource(R.drawable.radio_button_on);
//            info_message_of_Play_previous_mode_of_play_previous_song_textview.setVisibility(View.VISIBLE);

            activate_radio_button_on_click(external_audio_constrain_layout2,false,info_message_of_Play_previous_mode_of_play_previous_song_textview,false,
                    radio_button_of_play_previous_mode_play_previous_song,"PLAY_PREVIOUS_SONG",false);
        }
        else {
//            info_message_of_Play_previous_mode_of_play_previous_song_textview.setVisibility(View.GONE);
//
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
            deactivate_radio_button_on_click(external_audio_constrain_layout2,info_message_of_Play_previous_mode_of_play_previous_song_textview);
            count_for_info_message_of_play_previous_mode__play_previous=0;
        }

    }

    private int count_for_info_message_of_play_previous_mode__play_previous_v2=0;
    public void Activate_Info_message_Play_Previous_Mode_Play_Previous_Song_V2(View view){
        count_for_info_message_of_play_previous_mode__play_next=0;
        count_for_info_message_of_play_previous_mode__play_previous=0;
        count_for_info_message_of_play_previous_mode__play_previous_v2+=1;

        if(count_for_info_message_of_play_previous_mode__play_previous_v2==1){
            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
//            TransitionManager.beginDelayedTransition(info_message_constrain_layout_of_previous_song_mode_play_next_song, new ChangeBounds());
            set_visibility_of_info_messages_to_gone(false);
            set_radio_button_visibility(false);
            set_external_audio_controls("PLAY_PREVIOUS_SONG_V2",false);
            info_message_of_Play_previous_mode_of_play_previous_song_textview_v2.setVisibility(View.VISIBLE);
            radio_button_of_play_previous_mode_play_previous_song_v2.setImageResource(R.drawable.radio_button_on);

            activate_radio_button_on_click(external_audio_constrain_layout2,false,info_message_of_Play_previous_mode_of_play_previous_song_textview_v2,false,
                    radio_button_of_play_previous_mode_play_previous_song_v2,"PLAY_PREVIOUS_SONG_V2",false);
        }
        else {
//            info_message_of_Play_previous_mode_of_play_previous_song_textview_v2.setVisibility(View.GONE);
//
//            TransitionManager.beginDelayedTransition(external_audio_constrain_layout2, new ChangeBounds());
            deactivate_radio_button_on_click(external_audio_constrain_layout2,info_message_of_Play_previous_mode_of_play_previous_song_textview_v2);
            count_for_info_message_of_play_previous_mode__play_previous_v2=0;
        }

    }



    public void set_visibility_of_info_messages_to_gone(boolean permission_for_play_mode){

        TextView[] info_messages_textview={info_message_of_Play_next_mode_of_play_next_song_textview,info_message_of_Play_next_mode_of_play_previous_song_textview,info_message_of_Play_next_mode_of_play_previous_song_textview_v2};
        TextView[] info_messages_textview2={info_message_of_Play_previous_mode_of_play_next_song_textview,info_message_of_Play_previous_mode_of_play_previous_song_textview,info_message_of_Play_previous_mode_of_play_previous_song_textview_v2};
        for(int i=0;i<info_messages_textview.length;i++){
            if(permission_for_play_mode){
                info_messages_textview[i].setVisibility(View.GONE);

            }
            else {
                info_messages_textview2[i].setVisibility(View.GONE);
            }
        }
    }


    public void set_visibility_of_external_audio_control_play_next(boolean permission){
        ConstraintLayout[] array_for_text_view={
                info_message_constrain_layout_of_next_song_mode_play_next_song,
                info_message_constrain_layout_of_next_song_mode_play_previous_song,
                info_message_constrain_layout_of_next_song_mode_play_previous_song_v2};
        for (ConstraintLayout constraintLayout : array_for_text_view) {
            if (permission) {
                constraintLayout.setVisibility(View.VISIBLE);
            } else {
                constraintLayout.setVisibility(View.GONE);
            }
        }


    }

    public void set_visibility_of_external_audio_control_play_previous(boolean permission){
        ConstraintLayout[] array_for_text_view={
                info_message_constrain_layout_of_previous_song_mode_play_next_song,
                info_message_constrain_layout_of_previous_song_mode_play_previous_song,
                info_message_constrain_layout_of_previous_song_mode_play_previous_song_v2};

        for(int i=0;i<array_for_text_view.length;i++){
            if(permission){
                array_for_text_view[i].setVisibility(View.VISIBLE);
            }
            else {
                array_for_text_view[i].setVisibility(View.GONE);
            }
        }


    }

    public void set_radio_button_visibility(boolean permission){
        ImageView[] radio_buttons_of_play_next_mode={radio_button_of_play_next_mode_play_next_song,radio_button_of_play_next_mode_play_previous_song,radio_button_of_play_next_mode_play_previous_song_v2};
        ImageView[] radio_buttons_of_play_previous_mode={radio_button_of_play_previous_mode_play_next_song,radio_button_of_play_previous_mode_play_previous_song,radio_button_of_play_previous_mode_play_previous_song_v2};
        for(int i=0;i<radio_buttons_of_play_next_mode.length;i++){
            if(permission){
                radio_buttons_of_play_next_mode[i].setImageResource(R.drawable.radio_button_off);
            }else {
                radio_buttons_of_play_previous_mode[i].setImageResource(R.drawable.radio_button_off);
            }

        }


    }
    public void  set_external_audio_controls(String EVENT,boolean permission_for_play_next_mode){
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();

        if(permission_for_play_next_mode){
            editor.putBoolean(EXTERNAL_AUDIO_CONTROL_MODE_KEY,permission_for_play_next_mode);
            editor.putString(EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_NEXT_MODE,EVENT);



        }
        else{
            editor.putBoolean(EXTERNAL_AUDIO_CONTROL_MODE_KEY,permission_for_play_next_mode);
            editor.putString(EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_PREVIOUS_MODE,EVENT);
        }
        editor.apply();
    }

    public void set_event_controls(){
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        String EVENT_NAME=preferences.getString(EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_NEXT_MODE,"PLAY_NEXT_SONG");
        String EVENT_NAME_2=preferences.getString(EXTERNAL_AUDIO_CONTROL_EVENT_KEY_FOR_PREVIOUS_MODE,"PLAY_PREVIOUS_SONG");
        switch (EVENT_NAME) {
            case "PLAY_NEXT_SONG":
                set_radio_button_visibility(true);
                radio_button_of_play_next_mode_play_next_song.setImageResource(R.drawable.radio_button_on);
                break;
            case "PLAY_PREVIOUS_SONG":
                set_radio_button_visibility(true);
                radio_button_of_play_next_mode_play_previous_song.setImageResource(R.drawable.radio_button_on);
                break;
            case "PLAY_PREVIOUS_SONG_V2":
                set_radio_button_visibility(true);
                radio_button_of_play_next_mode_play_previous_song_v2.setImageResource(R.drawable.radio_button_on);
                break;
        }
        switch (EVENT_NAME_2) {
            case "PLAY_NEXT_SONG":
                set_radio_button_visibility(false);
                radio_button_of_play_previous_mode_play_next_song.setImageResource(R.drawable.radio_button_on);
                break;
            case "PLAY_PREVIOUS_SONG":
                set_radio_button_visibility(false);
                radio_button_of_play_previous_mode_play_previous_song.setImageResource(R.drawable.radio_button_on);
                break;
            case "PLAY_PREVIOUS_SONG_V2":
                set_radio_button_visibility(false);
                radio_button_of_play_previous_mode_play_previous_song_v2.setImageResource(R.drawable.radio_button_on);
                break;
        }
    }
    public void set_backup_playlist_active(RecyclerView recyclerView){
        ArrayList<Playlists_recycler_item_class> arrayList_for_backup_playlist=new ArrayList<>();
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);

        if(arrayList_for_all_playlists.size()>1){
            for(int i=1;i<arrayList_for_all_playlists.size()-1;i++){
                Playlists_recycler_item_class current_item=arrayList_for_all_playlists.get(i);

                arrayList_for_backup_playlist.add(new Playlists_recycler_item_class(
                        current_item.getMPlaylist_image_album_art(),
                        current_item.getMPlaylist_name(),
                        preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+current_item.getMPlaylist_name(),false)
                ));
            }
        }


        adapter_for_backup_playlist =new adapter_for_backup_playlist(arrayList_for_backup_playlist,this);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_for_backup_playlist);
        adapter_for_backup_playlist.SET_ON_CLICK(new adapter_for_backup_playlist.set_on_click_listener() {
            @Override
            public void add_playlist_to_backup_playlist(String BACKUP_PLAYLIST_NAME) {
                ADD_PLAYLIST_TO_BACKUP_PLAYLIST_LIST(BACKUP_PLAYLIST_NAME);
//                make_a_toast(BACKUP_PLAYLIST_NAME);

            }

            @Override
            public void remove_playlist_from_backup_playlist(String BACKUP_PLAYLIST_NAME, int POSITION_OF_THE_PLAYLIST) {
//
                REMOVE_PLAYLIST_TO_BACKUP_PLAYLIST_LIST(BACKUP_PLAYLIST_NAME,POSITION_OF_THE_PLAYLIST);
//                make_a_toast(BACKUP_PLAYLIST_NAME+"df");
            }
        });




    }
    public void ADD_PLAYLIST_TO_BACKUP_PLAYLIST_LIST(String BACKUP_PLAYLIST_NAME){
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        boolean permission=preferences.getBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,false);
        SharedPreferences.Editor editor = preferences.edit();
        if(permission){
            arrayList_for_backup_playlist_list=save_and_load_array.load_array_for_available_backup_playlists(this);
//            arrayList_for_backup_playlist_list=make_it_unique.MAKE_THE_ARRAYLIST_UNIQUE(arrayList_for_backup_playlist_list);
//            arrayList_for_backup_playlist_list=make_it_unique.MAKE_THE_ARRAYLIST_UNIQUE(arrayList_for_backup_playlist_list);
            save_and_load_array.save_array_for_available_backup_playlist_list(this,arrayList_for_backup_playlist_list);
        }
        else {
            arrayList_for_backup_playlist_list=new ArrayList<>();

        }
        boolean permission_for_adding_to_backup_playlist=true;

        for(int i=0;i<arrayList_for_backup_playlist_list.size();i++){

            if(arrayList_for_backup_playlist_list.get(i).equals(BACKUP_PLAYLIST_NAME)){
                permission_for_adding_to_backup_playlist=false;
                break;

            }

        }
        if(permission_for_adding_to_backup_playlist){
                arrayList_for_backup_playlist_list.add(BACKUP_PLAYLIST_NAME);
                save_and_load_array.save_array_for_available_backup_playlist_list(this,arrayList_for_backup_playlist_list);

        }



//        arrayList_for_backup_playlist_list.add(BACKUP_PLAYLIST_NAME);
//        adapter_for_backup_playlist_list.notifyItemInserted();
//        save_and_load_array.save_array_for_available_backup_playlist_list(this,arrayList_for_backup_playlist_list);
        editor.putBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,true);
        editor.apply();
        make_a_toast(String.format("%d",arrayList_for_backup_playlist_list.size()),false);
    }

    public void REMOVE_PLAYLIST_TO_BACKUP_PLAYLIST_LIST(String BACKUP_PLAYLIST_NAME,int POSITION_OF_PLAYLIST){
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        boolean permission=preferences.getBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,false);
        SharedPreferences.Editor editor = preferences.edit();

        if(permission){
            arrayList_for_backup_playlist_list=save_and_load_array.load_array_for_available_backup_playlists(this);
            arrayList_for_backup_playlist_list=make_it_unique.MAKE_THE_ARRAYLIST_UNIQUE(arrayList_for_backup_playlist_list);
            boolean permission_to_remove_element =false;
            int remove_element=0;
            for(int i=0;i<arrayList_for_backup_playlist_list.size();i++){
                if(BACKUP_PLAYLIST_NAME.equals(arrayList_for_backup_playlist_list.get(i))){
                    permission_to_remove_element=true;
                    remove_element=i;
                    break;
                }
                make_a_toast(String.format("SIZE : %d",arrayList_for_backup_playlist_list.size()),false);
            }
            if(permission_to_remove_element){
                arrayList_for_backup_playlist_list.remove(remove_element);
                if(arrayList_for_backup_playlist_list.size()!=0){
                    save_and_load_array.save_array_for_available_backup_playlist_list(this,arrayList_for_backup_playlist_list);

                }

            }
        }
        if(arrayList_for_backup_playlist_list.size()!=0){
            editor.putBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY, true);
            save_and_load_array.save_array_for_available_backup_playlist_list(this,arrayList_for_backup_playlist_list);

        }else {
            editor.putBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,false);
        }
        make_a_toast(String.format("size : %d",arrayList_for_backup_playlist_list.size()),false);

        editor.apply();
//        else {
//            arrayList_for_backup_playlist_list=new ArrayList<>();
//
//        }
    }

    private int count_for_backup_playlist=0;
    public void ACTIVATE_BACKUP_PLAYLIST(View view){
        ACTIVATE_BACKUP_PLAYLIST_FUNC();

    }

    public void ACTIVATE_BACKUP_PLAYLIST_FUNC(){
        recyclerView=findViewById(R.id.recyclerview_of_backup_playlist);

        count_for_backup_playlist+=1;
        if(count_for_backup_playlist==1){
            info_message_of_backup_playlist.setVisibility(View.VISIBLE);
            set_backup_playlist_active(recyclerView);
            recyclerView.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(backup_Playlist_extended_layout, new ChangeBounds());
            down_button_for_backup_playlist.setImageResource(R.drawable.up);
        }
        else {

            count_for_backup_playlist=0;
            info_message_of_backup_playlist.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(backup_Playlist_extended_layout, new ChangeBounds());
            down_button_for_backup_playlist.setImageResource(R.drawable.down);
        }
    }

    private int count_for_backup_playlist_list=0;
    public void ACTIVATE_AVAILABLE_BACKUP_PLAYLISTS(View view){
        count_for_backup_playlist_list+=1;
        recyclerView=findViewById(R.id.recyclerview_of_backup_playlist_list);

        if(count_for_backup_playlist_list==1){
            recyclerView.setVisibility(View.VISIBLE);

            activate_recyclerview_of_available_backup_playlist(recyclerView);
            TransitionManager.beginDelayedTransition(available_backup_playlist_list_extended_layout, new ChangeBounds());
        }else {
            recyclerView.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(available_backup_playlist_list_extended_layout, new ChangeBounds());
            count_for_backup_playlist_list=0;
        }
    }
    public void activate_recyclerview_of_available_backup_playlist(RecyclerView recyclerView){
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        boolean permission=preferences.getBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,false);
        if(permission){
            arrayList_for_backup_playlist_list=new ArrayList<>();
            arrayList_for_backup_playlist_list=save_and_load_array.load_array_for_available_backup_playlists(this);

            for(int i=0;i<arrayList_for_backup_playlist_list.size();i++){
                if(!preferences.getBoolean(arrayList_for_backup_playlist_list.get(i),false) || !preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+arrayList_for_backup_playlist_list.get(i),false)){
                    String playlist_name=arrayList_for_backup_playlist_list.get(i);
                    arrayList_for_backup_playlist_list.remove(i);

                    if(arrayList_for_backup_playlist_list.size()==0){
                        editor.putBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,false);
                    }else {
                        editor.putBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,true);
                    }
                    editor.apply();



                    ArrayList<Playlists_recycler_item_class> arrayList_for_backup_playlist=new ArrayList<>();

                    if(arrayList_for_all_playlists.size()>1){
                        for(int j=0;j<arrayList_for_all_playlists.size();j++){
                            Playlists_recycler_item_class current_item=arrayList_for_all_playlists.get(j);

                            if(playlist_name.equals(current_item.getMPlaylist_name())){
                                editor.putBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+current_item.getMPlaylist_name(),false);
                                editor.apply();
                            }
//                            arrayList_for_backup_playlist.add(new Playlists_recycler_item_class(
//                                    current_item.getMPlaylist_image_album_art(),
//                                    current_item.getMPlaylist_name(),
//                                    preferences.getBoolean(BACKUP_PLAYLIST_PERMISSION_KEY_PLUS_PLAYLIST_NAME+current_item.getMPlaylist_name(),false)
//                            ));
                        }





                    }





                }
            }

        }
        else {
            arrayList_for_backup_playlist_list=new ArrayList<>();

        }
        recyclerView.setHasFixedSize(true);
        adapter_for_backup_playlist_list =new adapter_for_BACKUP_PLAYLIST_LIST(arrayList_for_backup_playlist_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter_for_backup_playlist_list);
        if(arrayList_for_backup_playlist_list.size()==0){
            editor.putBoolean(BACKUP_PLAYLIST_LIST_PERMISSION_KEY,false);
            editor.apply();
        }

    }
    private int count_for_pause_timer_activity_attributes=0;
    public void ACTIVATE_PAUSE_TIMER_ACTIVITY_ATTRIBUTES(View view){
        ACTIVATE_PAUSE_TIMER_ACTIVITY_ATTRIBUTES_FUNC();
    }
    public void ACTIVATE_PAUSE_TIMER_ACTIVITY_ATTRIBUTES_FUNC(){
        count_for_pause_timer_activity_attributes+=1;
        if(count_for_pause_timer_activity_attributes==1){
            contrain_layout_of_pause_timer_attributes.setVisibility(View.VISIBLE);
            contrain_layout_of_pause_timer_attributes.startAnimation(FADE_IN);

            TransitionManager.beginDelayedTransition(pause_timer_extended_layout, new ChangeBounds());

        }
        else {
            count_for_pause_timer_activity_attributes=0;
            contrain_layout_of_pause_timer_attributes.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(pause_timer_extended_layout,new ChangeBounds());
            set_visibility_of_pause_timer_custom_timer(false);
            count_for_pause_timer_num_picker=0;

        }
    }

    private int count_for_play_timer_activity_attributes=0;
    public void ACTIVATE_PLAY_TIMER_ACTIVITY_ATTRIBUTES(View view){
        ACTIVATE_PLAY_TIMER_ACTIVITY_ATTRIBUTES_FUNC();
    }
    public void ACTIVATE_PLAY_TIMER_ACTIVITY_ATTRIBUTES_FUNC(){
        count_for_play_timer_activity_attributes+=1;
        if(count_for_play_timer_activity_attributes==1){
            constraint_layout_of_play_timer_attributes.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(play_timer_extended_layout, new ChangeBounds());

        }
        else {
            count_for_play_timer_activity_attributes=0;
            constraint_layout_of_play_timer_attributes.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(play_timer_extended_layout,new ChangeBounds());
            set_visibility_of_play_timer_custom_timer(false);
            count_for_play_timer_num_picker=0;

        }
    }



    public int count_for_switch_of_pause_timer=0;
    public void ACTIVATE_PAUSE_TIMER_FROM_SWITCH(View view){
        count_for_switch_of_pause_timer+=1;
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if(switch_of_pause_timer.isChecked()){
            contrain_layout_of_pause_timer_attributes.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(pause_timer_extended_layout, new ChangeBounds());
            editor.putBoolean(PAUSE_TIMER_ACTIVATION_KEY,true);
            switch_of_pause_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));;

        }
        else{
            switch_of_pause_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#a0a0a0")));;
            contrain_layout_of_pause_timer_attributes.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(pause_timer_extended_layout, new ChangeBounds());
            editor.putBoolean(PAUSE_TIMER_ACTIVATION_KEY,false);
            editor.putInt(PAUSE_TIMER_TIME_KEY,900);
            handler.removeCallbacks(runnable2);
        }
        editor.apply();

    }

    private int count_for_switch_of_play_timer=0;
    public void ACTIVATE_PLAY_TIMER_FROM_SWITCH(View view){
        count_for_switch_of_play_timer+=1;
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if(switch_of_play_timer.isChecked()){
            constraint_layout_of_play_timer_attributes.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(play_timer_extended_layout, new ChangeBounds());
            editor.putBoolean(PLAY_TIMER_ACTIVATION_KEY,true);
            switch_of_play_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));

        }
        else{
            switch_of_play_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#a0a0a0")));;
            constraint_layout_of_play_timer_attributes.setVisibility(View.GONE);
            editor.putBoolean(PLAY_TIMER_ACTIVATION_KEY,false);
            editor.putInt(PLAY_TIMER_TIME_KEY,900);
            permission_for_still_listening_time=false;
            app_start_inactivity_timer=0;
            TransitionManager.beginDelayedTransition(play_timer_extended_layout, new ChangeBounds());

            handler.removeCallbacks(runnable_for_play_timer);
        }
        editor.apply();

    }


    private int count_for_pause_timer_num_picker=0;
    public void ACTIVATE_NUM_PICKERS_OF_PAUSE_TIMER(View view){
        count_for_pause_timer_num_picker+=1;
        if(count_for_pause_timer_num_picker==1){
            set_visibility_of_pause_timer_custom_timer(true);
            TransitionManager.beginDelayedTransition(pause_timer_extended_layout,new ChangeBounds());
        }
        else {
            count_for_pause_timer_num_picker=0;
            set_visibility_of_pause_timer_custom_timer(false);
            TransitionManager.beginDelayedTransition(pause_timer_extended_layout,new ChangeBounds());
        }

    }


    private int count_for_play_timer_num_picker=0;
    public void ACTIVATE_NUM_PICKERS_OF_PLAY_TIMER(View view){
        count_for_play_timer_num_picker+=1;
        if(count_for_play_timer_num_picker==1){
            set_visibility_of_play_timer_custom_timer(true);
            TransitionManager.beginDelayedTransition(play_timer_extended_layout,new ChangeBounds());
        }
        else {
            count_for_play_timer_num_picker=0;
            set_visibility_of_play_timer_custom_timer(false);
//            play_timer_extended_layout.startAnimation(FADE_OUT);
            TransitionManager.beginDelayedTransition(play_timer_extended_layout,new ChangeBounds());
        }

    }
    public void set_visibility_of_pause_timer_custom_timer(boolean permission){
        if(permission){
            hours_numberPicker_for_pause_timer.setVisibility(View.VISIBLE);
            min_numberPicker_for_pause_timer.setVisibility(View.VISIBLE);
            hour_textview_of_pause_timer.setVisibility(View.VISIBLE);
            min_textview_of_pause_timer.setVisibility(View.VISIBLE);
            cardView_of_set_timer_of_pause_timer.setVisibility(View.VISIBLE);
        }else {
            hours_numberPicker_for_pause_timer.setVisibility(View.GONE);
            min_numberPicker_for_pause_timer.setVisibility(View.GONE);
            hour_textview_of_pause_timer.setVisibility(View.GONE);
            min_textview_of_pause_timer.setVisibility(View.GONE);
            cardView_of_set_timer_of_pause_timer.setVisibility(View.GONE);
        }
    }
    public void set_visibility_of_play_timer_custom_timer(boolean permission){
        if(permission){
            hours_numberPicker_for_play_timer.setVisibility(View.VISIBLE);
            min_numberPicker_for_play_timer.setVisibility(View.VISIBLE);
            hour_textview_of_play_timer.setVisibility(View.VISIBLE);
            min_textview_of_play_timer.setVisibility(View.VISIBLE);
            cardView_of_set_timer_of_play_timer.setVisibility(View.VISIBLE);
        }else {
            hours_numberPicker_for_play_timer.setVisibility(View.GONE);
            min_numberPicker_for_play_timer.setVisibility(View.GONE);
            hour_textview_of_play_timer.setVisibility(View.GONE);
            min_textview_of_play_timer.setVisibility(View.GONE);
            cardView_of_set_timer_of_play_timer.setVisibility(View.GONE);
        }
    }

    public void ACTIVATE_SYSTEM_TIMER_FOR_PAUSE_AND_PLAY_TIMER(View view){

        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        String tag=(String)view.getTag();

        //FOR PAUSE TIMER
        if(tag.equals("thirty")){
            set_pause_timer(1800,true);
        }else if(tag.equals("forty")){
            set_pause_timer(2700,true);
        }else if(tag.equals("one")){
            set_pause_timer(3600,true);
        } else if(tag.equals("custom_timer")) {
            if(min_numberPicker_for_pause_timer.getValue()==0 && hours_numberPicker_for_pause_timer.getValue()==0){
                make_a_toast("TIMER CAN'T BE 00:00",true);
            }
            else{
                set_pause_timer(min_numberPicker_for_pause_timer.getValue()*60+
                                hours_numberPicker_for_pause_timer.getValue()*3600,true);
            }
        }

        //FOR PLAY TIMER
        else if(tag.equals("thirtyPLAY")){
            set_pause_timer(1800,false);
        }else if(tag.equals("fortyPLAY")){
            set_pause_timer(2700,false);
        }else if(tag.equals("onePLAY")){
            set_pause_timer(3600,false);
        } else if(tag.equals("custom_timerPLAY")) {
            if(min_numberPicker_for_play_timer.getValue()==0 && hours_numberPicker_for_play_timer.getValue()==0){
                make_a_toast("TIMER CAN'T BE 00:00",true);
            }
            else{
                set_pause_timer(min_numberPicker_for_play_timer.getValue()*60+
                        hours_numberPicker_for_play_timer.getValue()*3600,false);
            }
        }


    }

    public void set_pause_timer(int time,boolean permission_for_pause){
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();

        if(permission_for_pause){
            editor.putBoolean(PAUSE_TIMER_ACTIVATION_KEY,true);
            switch_of_pause_timer.setChecked(true);
            time_in_sec_for_pause_timer =time;
            make_a_toast(String.format("PAUSE TIMER SET : %s",formated_time(time)),true);
            switch_of_pause_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));
            editor.putInt(PAUSE_TIMER_TIME_KEY,time);
        }
        else {
            editor.putBoolean(PLAY_TIMER_ACTIVATION_KEY,true);
            switch_of_play_timer.setChecked(true);
            time_in_sec_for_play_timer =time;
            make_a_toast(String.format("PLAY TIMER SET : %s",formated_time(time)),true);
            switch_of_play_timer.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#79C9C8")));
            editor.putInt(PLAY_TIMER_TIME_KEY,time);
        }


        editor.apply();

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


    public void activate_customize_widget(View view){
        ACTIVATE_CUSTOMIZE_WIDGET();
    }

    private int count_for_activate_customize_widget=0;
    public void ACTIVATE_CUSTOMIZE_WIDGET(){
        count_for_activate_customize_widget+=1;
        if(count_for_activate_customize_widget==1){
            constraintLayout_which_contains_all_customize_widget_controls.startAnimation(FADE_IN);
            constraintLayout_which_contains_all_customize_widget_controls.setVisibility(View.VISIBLE);
        }else{
//            constraintLayout_which_contains_all_customize_widget_controls.startAnimation(FADE_IN);
            constraintLayout_which_contains_all_customize_widget_controls.setVisibility(View.GONE);

            count_for_activate_customize_widget=0;
            count_for_slot_1_controls=1;
            count_for_slot_2_controls=1;
            ACTIVATE_SLOT1_OF_WIDGET();
            ACTIVATE_SLOT2_OF_WIDGET();
        }

    }

    public void activate_slot1_of_widget(View view){
        ACTIVATE_SLOT1_OF_WIDGET();
    }
    private int count_for_slot_1_controls=0;
    public void ACTIVATE_SLOT1_OF_WIDGET(){
        count_for_slot_1_controls+=1;
        if(count_for_slot_1_controls==1){
            constraintLayout_which_contains_slot1_controls.setVisibility(View.VISIBLE);
            constraintLayout_which_contains_slot1_controls.startAnimation(FADE_IN);
        }else {
            constraintLayout_which_contains_slot1_controls.startAnimation(FADE_OUT);
            constraintLayout_which_contains_slot1_controls.setVisibility(View.GONE);
            count_for_slot_1_controls=0;
        }
    }


    public void set_radio_button_of_slots_of_miniplayer_widget(boolean permission_for_slot_one){
        ImageView[] radio_buttons_slot1={radio_button_for_shuffle_slot_1,radio_button_for_repeat_slot_1,radio_button_for_favourite_slot_1};
        ImageView[] radio_buttons_slot2={radio_button_for_shuffle_slot_2,radio_button_for_repeat_slot_2,radio_button_for_favourite_slot_2};
        if(permission_for_slot_one){
            for(ImageView radio_button: radio_buttons_slot1){
                radio_button.setImageResource(R.drawable.radio_button_off);
            }
        }
        else {
            for(ImageView radio_button: radio_buttons_slot2){
                radio_button.setImageResource(R.drawable.radio_button_off);
            }
        }

    }
    public void CONFIGURE_SLOTS(View view) throws Exception {
        String tag=(String)view.getTag();
        if(tag.equals("favourite_slot_1")){
            imageView_for_slot1.setImageResource(R.drawable.favorite_on);
            set_radio_button_of_slots_of_miniplayer_widget(true);
            radio_button_for_favourite_slot_1.setImageResource(R.drawable.radio_button_on);
            SET_WIDGET_OPTION("SLOT_1","FAVOURITE");

        } else if (tag.equals("repeat_slot_1")) {
            imageView_for_slot1.setImageResource(R.drawable.repeat_all);
            set_radio_button_of_slots_of_miniplayer_widget(true);
            radio_button_for_repeat_slot_1.setImageResource(R.drawable.radio_button_on);
            SET_WIDGET_OPTION("SLOT_1","REPEAT");

        } else if (tag.equals("shuffle_slot_1")) {
            imageView_for_slot1.setImageResource(R.drawable.shuffle_on);
            set_radio_button_of_slots_of_miniplayer_widget(true);
            radio_button_for_shuffle_slot_1.setImageResource(R.drawable.radio_button_on);
            SET_WIDGET_OPTION("SLOT_1","SHUFFLE");


        }

        else if(tag.equals("favourite_slot_2")){
            imageView_for_slot2.setImageResource(R.drawable.favorite_on);
            set_radio_button_of_slots_of_miniplayer_widget(false);
            radio_button_for_favourite_slot_2.setImageResource(R.drawable.radio_button_on);
            SET_WIDGET_OPTION("SLOT_2","FAVOURITE");

        } else if (tag.equals("repeat_slot_2")) {
            imageView_for_slot2.setImageResource(R.drawable.repeat_all);
            set_radio_button_of_slots_of_miniplayer_widget(false);
            radio_button_for_repeat_slot_2.setImageResource(R.drawable.radio_button_on);
            SET_WIDGET_OPTION("SLOT_2","REPEAT");

        } else if (tag.equals("shuffle_slot_2")) {
            imageView_for_slot2.setImageResource(R.drawable.shuffle_on);
            set_radio_button_of_slots_of_miniplayer_widget(false);
            radio_button_for_shuffle_slot_2.setImageResource(R.drawable.radio_button_on);
            SET_WIDGET_OPTION("SLOT_2","SHUFFLE");


        }
        updateWidget(this);
    }
    public void SET_WIDGET_OPTION(String SLOT,String MODE){
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(SLOT,MODE);
        editor.apply();
    }

    public void set_slots_controls(){
        String SLOT_1_MODE=preferences.getString("SLOT_1","FAVOURITE");
        String SLOT_2_MODE=preferences.getString("SLOT_2","SHUFFLE");

        if(SLOT_1_MODE.equals("FAVOURITE")){
            radio_button_for_favourite_slot_1.setImageResource(R.drawable.radio_button_on);
            imageView_for_slot1.setImageResource(R.drawable.favorite_on);

        } else if (SLOT_1_MODE.equals("REPEAT")) {
            radio_button_for_repeat_slot_1.setImageResource(R.drawable.radio_button_on);
            imageView_for_slot1.setImageResource(R.drawable.repeat_all);

        } else if (SLOT_1_MODE.equals("SHUFFLE")) {
            radio_button_for_shuffle_slot_1.setImageResource(R.drawable.radio_button_on);
            imageView_for_slot1.setImageResource(R.drawable.shuffle_on);
        }

        if(SLOT_2_MODE.equals("FAVOURITE")){
            radio_button_for_favourite_slot_2.setImageResource(R.drawable.radio_button_on);
            imageView_for_slot2.setImageResource(R.drawable.favorite_on);

        } else if (SLOT_2_MODE.equals("REPEAT")) {
            radio_button_for_repeat_slot_2.setImageResource(R.drawable.radio_button_on);
            imageView_for_slot2.setImageResource(R.drawable.repeat_all);

        } else if (SLOT_2_MODE.equals("SHUFFLE")) {
            radio_button_for_shuffle_slot_2.setImageResource(R.drawable.radio_button_on);
            imageView_for_slot2.setImageResource(R.drawable.shuffle_on);
        }
    }

    public void activate_slot2_of_widget(View view){
        ACTIVATE_SLOT2_OF_WIDGET();
    }
    public int count_for_slot_2_controls=0;
    public void ACTIVATE_SLOT2_OF_WIDGET(){
        count_for_slot_2_controls+=1;

        if(count_for_slot_2_controls==1){
            constraintLayout_which_contains_slot2_controls.startAnimation(FADE_IN);
            constraintLayout_which_contains_slot2_controls.setVisibility(View.VISIBLE);
        }else{
            constraintLayout_which_contains_slot2_controls.startAnimation(FADE_OUT);
            constraintLayout_which_contains_slot2_controls.setVisibility(View.GONE);
            count_for_slot_2_controls=0;
        }
    }





    public void ADD_SINGLE_SONG_USING_ADD_TO_PLAYLIST_FROM_MUSIC_PLAYER(View view){
        CURRENT_SONG_POSITION_FOR_MORE_BUTTON=current_song_index;
        if (arrayList_for_all_playlists.size() > 1) {
            is_single_song_selected = true;

            selected_playlist_from_bottom_fragment_position_array_list = new ArrayList<>();
//            arrayList_for_add_playlist_without_recently_added = new ArrayList<>();
//
//            for (int i = 1; i < arrayList_for_all_playlists.size(); i++) {
//                arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(), arrayList_for_all_playlists.get(i).getMPlaylist_name(), false));
//            }
            add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite();
            single_song_selected_playlist_position=613;
//                                add_playlist__Interface();

            MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,true,arrayList_for_add_playlist_without_recently_added,true);
            musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
        } else {
            make_a_toast("THERE ARE NO PLAYLiST",true);
        }
    }


    public void activate_repeat(View view) throws Exception {
        ACTIVATE_REPEAT();
    }

    public void ACTIVATE_REPEAT() throws Exception {
        Count_for_Repeat_Button+=1;

        if(Count_for_Repeat_Button==1){
            make_a_toast("REPEAT THIS SONG",true);

        } else if (Count_for_Repeat_Button==2) {
            make_a_toast("REPEAT ALL SONGS",true);

        } else if (Count_for_Repeat_Button>2 || Count_for_Repeat_Button<1) {
            make_a_toast("REPEAT OFF",true);
        }
        SET_REPEAT_MODE();

    }
    public void SET_REPEAT_MODE() throws Exception {
        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if(Count_for_Repeat_Button==1){
            Repeat_Button.setImageResource(R.drawable.repeat_one);

            editor.putInt(REPEAT_MODE_KEY,Count_for_Repeat_Button);

        } else if (Count_for_Repeat_Button==2) {
            Repeat_Button.setImageResource(R.drawable.repeat_all);
            editor.putInt(REPEAT_MODE_KEY,Count_for_Repeat_Button);

        } else if (Count_for_Repeat_Button>2 || Count_for_Repeat_Button<1) {
            Repeat_Button.setImageResource(R.drawable.repeat_off);
            Count_for_Repeat_Button=0;

            editor.putInt(REPEAT_MODE_KEY,Count_for_Repeat_Button);
        }
        updateWidget(this);
        editor.apply();
    }



    public void activate_shuffle(View view) throws IOException {
        ACTIVATE_SHUFFLE();
    }
    public void ACTIVATE_SHUFFLE() throws IOException {
        Count_for_Shuffle_Button+=1;
        SET_SHUFFLE_MODE();
        SHUFFLE_SETUP();
        if(Count_for_Shuffle_Button==1){
            make_a_toast("SHUFFLE ALL",true);
            permission_for_miniplayer_widget_shuffle=true;
        }else {
            make_a_toast("SHUFFLE OFF",true);
            permission_for_miniplayer_widget_shuffle=false;
        }

    }


    public void SET_SHUFFLE_MODE() throws IOException {
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if(Count_for_Shuffle_Button==1){
            permission_for_miniplayer_widget_shuffle=true;

            Shuffle_Button.setImageResource(R.drawable.shuffle_on);
            editor.putInt(SHUFFLE_MODE_KEY,Count_for_Shuffle_Button);
            updateWidget(getApplicationContext());

//            SHUFFLE_SETUP();
        }else {
            permission_for_miniplayer_widget_shuffle=false;

            Shuffle_Button.setImageResource(R.drawable.shuffle_off);
            Count_for_Shuffle_Button=0;
            editor.putInt(SHUFFLE_MODE_KEY,Count_for_Shuffle_Button);
            updateWidget(getApplicationContext());

        }
        editor.apply();
    }

    public void SHUFFLE_SETUP(){
        Shuffled_Array_Index_POSITION=0;
        Shuffled_Index_Arraylist=new ArrayList<>();
        Shuffled_Index_Arraylist.add(current_song_index);

        if((temp_array_list.size()!=1)&& (temp_array_list.size()!=0)){
            Put_Shuffled_Elements();
        }

    }
    public void Put_Shuffled_Elements(){
        Random random_shuffled_index=new Random();
        int Shuffled_Index=random_shuffled_index.nextInt(temp_array_list.size());
        boolean permission_to_add_shuffled_index=true;

//        int size=Shuffled_Index_Arraylist.size()-1;

        for(int i=0;i<Shuffled_Index_Arraylist.size();i++){
            if(Shuffled_Index_Arraylist.get(i)==Shuffled_Index){
                permission_to_add_shuffled_index=false;
                break;
            }
        }
        if(permission_to_add_shuffled_index){
            Shuffled_Index_Arraylist.add(Shuffled_Index);
            if(Shuffled_Index_Arraylist.size()!=temp_array_list.size()){
                Put_Shuffled_Elements();
            }
        }else{
            Put_Shuffled_Elements();
        }

    }

    public void activate_favorite(View view){
        ACTIVATE_FAVOURITE();



    }
    public void ACTIVATE_FAVOURITE(){
        Count_for_Favorite_Button+=1;

        SharedPreferences preferences =getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
//        if(Favorite_Button.isSelected()){
//
//        }
        ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_favourite;
        if(preferences.getBoolean("Favourite",false)){
            arrayList_for_favourite=save_and_load_array.load_array_for_user_created_playlist(this,"Favourite");

        }
        else {
            arrayList_for_favourite=new ArrayList<>();
        }
        Recently_added_recyclerview_elements_item_class current_item=temp_array_list.get(current_song_index);

//        check_the_song_is_in_favourite(current_item.getMsong_name(),current_item.getMpath(),current_item.getMartist(),current_item.getMalbum_name(),current_item.getMduration(),current_item.getMalbum_art());
        if(Favorite_Button.isSelected()){
            Favorite_Button.setSelected(false);
            Favorite_Button.setImageResource(R.drawable.favorite_off); //REMOVES SONG FROM HERE
            permission_for_miniplayer_widget_favorite=false;          //IT REPRESENT THAT THE FAVOURITE BUTTON IS NOT SELECTED
            try {
                updateWidget(getApplicationContext());

            }catch (Exception e){

            }
            for(int i=0;i<arrayList_for_favourite.size();i++){
                if(current_item.getMsong_name().equals(arrayList_for_favourite.get(i).getMsong_name())){
                    make_a_toast(String.format("%s Removed From Favourite",arrayList_for_favourite.get(i).getMsong_name()),true);

                    arrayList_for_favourite.remove(i);
                    if(IS_FAVOURITE_PLAYLIST_ACTIVE){
                            adapter_for_user_created_playlist.notifyItemRemoved(i);
                    }


                    if(arrayList_for_favourite.size()==0){
                        editor.putBoolean("Favourite",false);
//                        make_a_toast("false");
                    }
                    else {
                        editor.putBoolean("Favourite",true);
//                        make_a_toast("true");
                        save_and_load_array.save_array_for_user_created_playlist(this,arrayList_for_favourite,"Favourite");
                    }
                    editor.apply();
                }
            }



        }else {
            Favorite_Button.setImageResource(R.drawable.favorite_on);
            Favorite_Button.setSelected(true);   //ADDS SONG TO FAVORITE FROM HERE
            permission_for_miniplayer_widget_favorite=true;         //IT REPRESENT THAT THE FAVOURITE BUTTON IS SELECTED
            try {
                updateWidget(getApplicationContext());

            }catch (Exception e){

            }
            make_a_toast(String.format("%s Added To Favourite",temp_array_list.get(current_song_index).getMsong_name()),true);
            arrayList_for_favourite.add(new Recently_added_recyclerview_elements_item_class(current_item.getMsong_name(),current_item.getMpath(),current_item.getMartist(),current_item.getMalbum_name(),current_item.getMduration(),current_item.getMalbum_art(),false));
//            make_a_toast(String.format("Size : %d",arrayList_for_favourite.size()));

//            adapter_for_user_created_playlist.notifyItemInserted(arrayList_for_favourite.size()-1);
            save_and_load_array.save_array_for_user_created_playlist(this,arrayList_for_favourite,"Favourite");

            editor.putBoolean("Favourite",true);

        }
        editor.apply();
        save_the_all_playlist_array();


        //        if(Count_for_Favorite_Button==1){
//            Favorite_Button.setImageResource(R.drawable.favorite_on);
//        }else{
//            Favorite_Button.setImageResource(R.drawable.favorite_off);
//            Count_for_Favorite_Button=0;
//        }
    }

    public boolean check_the_song_is_in_favourite(String SONG_NAME, String PATH, String ARTIST_NAME, String ALBUM_NAME, int DURATION, long ALBUM_ART){
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        ArrayList<Recently_added_recyclerview_elements_item_class> arrayList_for_favourite;

        boolean permission_for_favorite=preferences.getBoolean("Favourite",false);

        if(!permission_for_favorite){
            arrayList_for_favourite=new ArrayList<>();
        }
        else {
            arrayList_for_favourite=save_and_load_array.load_array_for_user_created_playlist(this,"Favourite");
        }


        boolean is_song_exists=false;
        for(int i=0;i<arrayList_for_favourite.size();i++){
            Recently_added_recyclerview_elements_item_class current_item=arrayList_for_favourite.get(i);

            if(SONG_NAME.equals(current_item.getMsong_name())){
                is_song_exists=true;
                break;
            }
        }
        if(!is_song_exists){
            return false;

        }else {
            return true;


        }





    }
    public void add_elements_to_arraylist_for_add_playlist_without_recently_added_and_favourite(){  //THIS CREATES A ARRAYLIST FOR DISPLAYING THE USER CREATED PLAYLIST WITHOUT RECENTLY ADDED AND FAVOURITE

        arrayList_for_add_playlist_without_recently_added = new ArrayList<>();

        for (int i = 0; i < arrayList_for_all_playlists.size(); i++) {
            Playlists_recycler_item_class current_item=arrayList_for_all_playlists.get(i);


            if(  !( (current_item.getMPlaylist_name().equals("Recently Added"))  || current_item.getMPlaylist_name().equals("Favourite") ) ){
                arrayList_for_add_playlist_without_recently_added.add(new Playlists_recycler_item_class(
                        arrayList_for_all_playlists.get(i).getMPlaylist_image_album_art(),
                        arrayList_for_all_playlists.get(i).getMPlaylist_name(),
                        false));

            }
        }


    }
    @Override
    public void BOTTOM_CLASS_GO_TO_ARTIST_OR_ALBUM(long ALBUM_ART, String ARTIST_NAME_OR_ALBUM_NAME, boolean PERMISSION_FOR_ALBUM) throws IOException {
//        make_a_toast(String.format("%d",ALBUM_ART));
//        make_a_toast(String.format("%s",ARTIST_NAME_OR_ALBUM_NAME));
//        HIDE_MUSIC_PLAYER_INTERFACE();


        is_song_info_active=true;
        music_player.setVisibility(View.GONE);
        IS_MUSIC_PLAYER_ACTIVE=false;
//        ALL_INTERFACE_BUTTON_HOLDER_PARENT_CONSTRAINT_LAYOUT.setVisibility(View.GONE);
        if(PERMISSION_FOR_ALBUM){
            ACTIVATE_ALBUM_PLAYLIST(ALBUM_ART,ARTIST_NAME_OR_ALBUM_NAME,total_elements(ARTIST_NAME_OR_ALBUM_NAME,true),613);
        }else {
            ACTIVATE_ARTIST_PLAYLIST(ALBUM_ART,ARTIST_NAME_OR_ALBUM_NAME,total_elements(ARTIST_NAME_OR_ALBUM_NAME,false),613);
        }


    }

    @Override
    public void BOTTOM_CLASS_ACTIVATE_SLEEP_TIMER_BOTTOM_FRAGMENT() {
        make_a_toast("Sleep Timer",false);
        MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass=new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.sleep_timer_bottom,false,arrayList_for_add_playlist_without_recently_added,true);
        musicPlayerBottomClass.show(getSupportFragmentManager(),"613");
    }

    @Override
    public void BOTTOM_CLASS_SLEEP_TIMER(int TIME) {
        if(TIME==613){
            is_end_of_the_track_sleep_timer=true;
            is_sleep_timer_active=true;
        }else{
            ACTIVATE_SLEEP_TIMER(TIME);
            is_end_of_the_track_sleep_timer=false;
        }
    }

    @Override
    public void ACTIVATE_QUEUE() {
        ACTIVATE_QUEUE_INTERFACE();
        music_player.setVisibility(View.GONE);
    }

    public void ACTIVATE_SONG_INFO(View view){

        MUSIC_PLAYER_BOTTOM_CLASS musicPlayerBottomClass = new MUSIC_PLAYER_BOTTOM_CLASS(R.layout.add_song_to_multiple_playlist_bottom,true,arrayList_for_add_playlist_without_recently_added,false);
        musicPlayerBottomClass.show(getSupportFragmentManager(), "taf");
    }
    public void ACTIVATE_SLEEP_TIMER(int TIME){
        make_a_toast(String.format("TIME : %d",TIME),false);

        if(TIME!=0){
            sleep_timer_time_in_secs=TIME;
            is_sleep_timer_active=true;
            handler.postDelayed(runnable_for_sleep_timer,0);
            STOP_SLEEP_TIMER_CONSTAINT_LAYOUT.setVisibility(View.VISIBLE);

        }else{
            make_a_toast("PLEASE SET TIME",true);
        }


    }
    private Runnable runnable_for_sleep_timer=new Runnable() {
        @Override
        public void run() {
            if(is_sleep_timer_active){
                sleep_timer_time_in_secs-=1;

                if(sleep_timer_time_in_secs==0){
                    finish();
                    media_player.release_media_player();
                    mediaSession.release();
                    mediaSession.setActive(false);
                    handler.removeCallbacks(runnable_for_notification_action);
                    is_sleep_timer_active=false;
                }else{
                    int hour= sleep_timer_time_in_secs/3600;
                    int min=(sleep_timer_time_in_secs/60)%60;
                    int sec=sleep_timer_time_in_secs%60;
                    Remaining_Sleep_Timer_Textview.setText(String.format("%02d:%02d:%02d",hour,min,sec));
                    handler.postDelayed(runnable_for_sleep_timer,1000);
                }
            }

        }
    };

    public void STOP_SLEEP_TIMER(View view){
        STOP_SLEEP_TIMER_CONSTAINT_LAYOUT.setVisibility(View.GONE);
        is_sleep_timer_active=false;
        handler.removeCallbacks(runnable_for_sleep_timer);
        handler.removeCallbacks(runnable_for_play_timer);
        handler.removeCallbacks(runnable2);
        if(!is_media_player_paused){
            check_user_inactivity_for_play_timer();
        }else{
            check_user_inactivity();
        }


    }
    public void back_function_protocol(boolean permission_for_fade_in_anim){
        if(IS_ALL_SONGS_INTERFACE_ACTIVE){
            SET_FADE_OUT_ANIMATION(all_song_interface_constrain_layout,permission_for_fade_in_anim);
        } else if (IS_ALL_PLAYLIST_INTERFACE_ACTIVE) {
            SET_FADE_OUT_ANIMATION(all_playlist_interface,permission_for_fade_in_anim);
        } else if (IS_ALL_ALBUM_INTERFACE_ACTIVE) {
            SET_FADE_OUT_ANIMATION(all_album_interface_constraint_layout,permission_for_fade_in_anim);
        } else if (IS_ALL_ARTIST_INTERFACE_ACTIVE) {
            SET_FADE_OUT_ANIMATION(all_artist_interface_constrain_layout,permission_for_fade_in_anim);
        }

    }


    public void SET_FADE_OUT_ANIMATION(ConstraintLayout Interface,boolean PERMISSION_FOR_FADE_IN){
        if (PERMISSION_FOR_FADE_IN) {
            Interface.startAnimation(FADE_IN);
            Interface.setVisibility(View.VISIBLE);
        } else {
            Interface.startAnimation(FADE_OUT);
            Interface.setVisibility(View.GONE);

        }
    }


    public static void updateWidget(Context context) throws IOException {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, miniplayer_widget.class));

        for (int appWidgetId : appWidgetIds) {
            miniplayer_widget.updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    public void ADD_HOME_SCREEN_SHORTCUT(String Path,String PLAYLIST_NAME){
        Icon icon;
        Bitmap albumArtBitmap;
        if(Path.equals("")){
            icon=Icon.createWithResource(this,R.drawable.logo);


        }else {
            MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(Path);
            byte[] albumArtBytes = mediaMetadataRetriever.getEmbeddedPicture();
            if (albumArtBytes != null) {
                albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);
                icon=Icon.createWithBitmap(albumArtBitmap);
            }else {
                icon=Icon.createWithResource(this,R.drawable.logo);
            }


        }



        Intent intent = new Intent(this, Short.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("playlist_name",PLAYLIST_NAME);

//        intent.setAction("DS");

//        make_a_toast(intent.getAction());

        // Create a ShortcutInfo object
        @SuppressLint({"NewApi", "LocalSuppress"}) ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, PLAYLIST_NAME)
                .setShortLabel(PLAYLIST_NAME)
                .setIcon(icon)
                .setIntent(intent)

                .build();

        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
            shortcutManager.requestPinShortcut(shortcutInfo, null);
        }


    }

    public void ACTIVATE_USER_CREATED_PLAYLIST_FROM_HOME_SCREEN_SHORTCUT(){
//        if(!((getIntent().getStringExtra(SHORTCUT_PLAYLIST_ACTIVATION)==null) || (getIntent().getStringExtra(SHORTCUT_PLAYLIST_ACTIVATION).equals("613"))) ){
            make_a_toast("tedd",false);
            for(int i=0;i<arrayList_for_all_playlists.size();i++){

                String PLAYLIST_NAME=getIntent().getStringExtra(SHORTCUT_PLAYLIST_ACTIVATION);
//                String PLAYLIST_NAME=preferences.getString(SHORTCUT_PLAYLIST_ACTIVATION,"");

                if(arrayList_for_all_playlists.get(i).getMPlaylist_name().equals(PLAYLIST_NAME)){

                    try {
                        set_all_interface_button_visibility(false);
                        user_created_playlist(PLAYLIST_NAME,i);
//                        getIntent().putExtra(SHORTCUT_PLAYLIST_ACTIVATION,"613");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                }
            }
//        }
    }
    private void updatePlaybackState(int state) {
        long position = media_player.get_media_player().getCurrentPosition();
        long duration = media_player.get_media_player().getDuration();
//        long bufferedPosition = (media_player.get_media_player().getBufferPercentage() * duration) / 100;

        float playbackSpeed = (state == PlaybackStateCompat.STATE_PLAYING) ? 1.0f : 0f;

        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                       )
                .setState(state, position, playbackSpeed)
                .setBufferedPosition(45); // Set the buffered position

        mediaSession.setPlaybackState(stateBuilder.build());
    }


    public void BACK_BUTTON_OF_QUEUE_INTERFACE(View view){
        constraintLayout_of_queue_interface.setVisibility(View.GONE);
        music_player.setVisibility(View.VISIBLE);
    }
    public void ACTIVATE_QUEUE_INTERFACE(){
        constraintLayout_of_queue_interface.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recyclerview_of_queue_interface);
        layoutManager=new LinearLayoutManager(this);
        adapter_for_queue_interface=new recently_added_adapter_class(temp_array_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter_for_queue_interface);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(current_song_index);
        adapter_for_queue_interface.set_ON_CLICKED_LISTENER(new recently_added_adapter_class.OnCLICK_LISTENER() {
            @Override
            public void on_ITEM_Clicked(int position) throws Exception {
                    play(position);
                    constraintLayout_of_queue_interface.setVisibility(View.GONE);
                    music_player.setVisibility(View.VISIBLE);
            }

            @Override
            public void more_button_ITEM_Clicked(View view, int position) {
                SharedPreferences.Editor editor= preferences.edit();
                CURRENT_SONG_POSITION_FOR_MORE_BUTTON = position;
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END);
                popupMenu.inflate(R.menu.queue_interface_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.remove_from_queue) {
                            if(position!=current_song_index){

                                temp_array_list.remove(position);
                                adapter_for_queue_interface.notifyItemRemoved(position);
                                if(position<current_song_index){
                                    current_song_index-=1;
                                }
                            }else{
                                make_a_toast("CAN'T REMOVE FROM QUEUE",true);
                            }

                            return true;

                        }
                        return false;
                    }

                });
                popupMenu.show();
                //REMOVE SONG FROM THE QUEUE
            }

            @Override
            public void on_ITEM_LONG_CLICKED(int Long_pressed_song) {

            }
        });


    }

    public ArrayList<Recently_added_recyclerview_elements_item_class> copy_arraylist(ArrayList<Recently_added_recyclerview_elements_item_class> arraylist){
        make_a_toast("Playlist Is Copied ",true);
        ArrayList<Recently_added_recyclerview_elements_item_class> Copied_arrayList=new ArrayList<>();
        for(Recently_added_recyclerview_elements_item_class item : arraylist){
            Copied_arrayList.add(item);
        }
        return Copied_arrayList;
    }



    private void connectToDevice(String address) {
//        showToast("2");
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
//        showToast("3");
        try {
//            showToast("4");
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

            bluetoothSocket.connect();
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
            listenForData();
            showToast("Connected");

        } catch (IOException e) {
            Log.e(TAG, "Connection failed: " + e.getMessage());
            showToast("Connection failed: " + e.getMessage());
        }
    }
    public String receivedString="";
    private void listenForData() {
        new Thread(() -> {
            byte[] buffer = new byte[1024];
            int bytes;

            // Continue reading while the socket is connected and the thread is not stopped
            while (!stopThread && bluetoothSocket != null && bluetoothSocket.isConnected()) {
                try {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        String charr=new String(buffer, 0, bytes);
                        switch(charr){
                            case "PLAY_PAUSE":
                                editor.putString("ACTION","PLAY_PAUSE");

                                break;
                            case "PREVIOUS_SONG":
                                editor.putString("ACTION","PREVIOUS_SONG");
                                break;

                            case "NEXT_SONG":
                                editor.putString("ACTION","NEXT_SONG");
                                break;

                        }
                        editor.apply();

//                        if()
//                        while (!receivedString.equals(" ")){
//                            dt=String.format("%s",dt+receivedString);
//                        }
                        showToast(charr);
                        Log.d(TAG, "Received: " + charr);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error reading data", e);
                    break;
                }
            }
        }).start();
    }
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }
    public void CONNECT(View view){
        connectToDevice(DEVICE_ADDRESS);
    }
//    public void send (View view){
//
////        String message = editText.getText().toString();
//        if (!message.isEmpty()) {
//            try {
//                outputStream.write(message.getBytes());  // Send message as bytes
//                outputStream.write('\n');  // Optionally add a newline
//                Log.d(TAG, "Message sent: " + message);
//            } catch (IOException e) {
//                Log.e(TAG, "Error sending data", e);
//            }
//        }
//    }

    public String dt = "";

    public void dtt() {
//        EditText editText = findViewById(R.id.ip_edittext);
//        editText.setText("Text updated");
    }
    public void put_temp_songs(){
        ArrayList<String> Songs=new ArrayList<>();

        Songs.add("Dynamite");
        Songs.add("피 땀 눈물");
        Songs.add("Butter");
        Songs.add("Mamacita 아야야");
        Songs.add("Ko Ko Bop");
        Songs.add("Stay Gold");
        Songs.add("ON");
        Songs.add("Lo Siento");
        Songs.add("MIC Drop");
        Songs.add("불타오르네");
        Songs.add("DNA");
        Songs.add("Permission to Dance");
        Songs.add("War of Hormone");
        Songs.add("쩔어");

        Songs.add("Kill This Love");
        Songs.add("Make It Right");
        Songs.add("Not Today");
        Songs.add("How You Like That");
        Songs.add("Save ME");
        Songs.add("FAKE LOVE");
        Songs.add("작은 것들을 위한 시");
        Songs.add("Life Goes On");
        Songs.add("IDOL");
        Songs.add("고민보다");
        Songs.add("We are Bulletproof - the Eternal");
        Songs.add("Airplane pt.2");
        Songs.add("Zero O'Clock");
        Songs.add("소우주");

        Songs.add("Money");
        Songs.add("Dionysus");
        Songs.add("LALISA");
        Songs.add("No More Dream");
        Songs.add("I NEED U");
        Songs.add("RUN");
        Songs.add("Filter");
        Songs.add("Epiphany");

        Songs.add("Euphoria");
        Songs.add("Chicken Noodle Soup");
        Songs.add("대취타");
        Songs.add("tokyo");
        Songs.add("네시");
        Songs.add("Pied Piper");
        Songs.add("Black Swan");
        Songs.add("Anpanman");
        Songs.add("Love Shot");
        Songs.add("붐바야");
        Songs.add("On The Ground");
        Songs.add("Spine Breaker");
        Songs.add("Tempo");
        Songs.add("RUN2U");

        Songs.add("Boy In Luv");
        Songs.add("Danger");
        Songs.add("Ν.Ο");
        Songs.add("으르렁");
        Songs.add("MaMa Beat");
        Songs.add("진격의 방탄");
        Songs.add("색안경");
        Songs.add("神메뉴");
        Songs.add("MANIAC");
        Songs.add("My Universe");
        Songs.add("The Truth Untold");
        Songs.add("We are bulletproof PT.2");
        Songs.add("소리꾼");
        Songs.add("It's Definitely You");

        Songs.add("OH");
        Songs.add("FEVER");
        Songs.add("Back Door");
        Songs.add("Mr. Simple");
        Songs.add("미인아");
        Songs.add("쏘리 쏘리");
        Songs.add("One More Time");
        Songs.add("DOMINO");
        Songs.add("거미줄");
        Songs.add("불장난");
        Songs.add("강남스타일");
        Songs.add("That That");
        Songs.add("FOR YOU");
        Songs.add("봄날");

        Songs.add("Run Away");
        Songs.add("Given-Taken");
        Songs.add("별안간");
        Songs.add("Good Boy Gone Bad");
        Songs.add("잠시");
        Songs.add("SOLO");
        Songs.add("Butterfly");
        Songs.add("뱁새");
        Songs.add("Yet To Come");
        Songs.add("친구");
        Songs.add("달려라 방탄");
        Songs.add("For Youth");
        Songs.add("Young Forever");
        Songs.add("My You");

        Songs.add("Film out");
        Songs.add("Best Of Me");
        Songs.add("Magic");
        Songs.add("CHEESE");
        Songs.add("Pretty Savage");
        Songs.add("Left and Right");
        Songs.add("강박");
        Songs.add("뚜두뚜두");
        Songs.add("MORE");
        Songs.add("LO$ER=LO♡ER");
        Songs.add("Gone");
        Songs.add("Gentleman");
        Songs.add("방화");
        Songs.add("Side Effects");

        Songs.add("CIRCUS");
        Songs.add("Coffee");
        Songs.add("Whalien 52");
        Songs.add("ParadoXXX Invasion");
        Songs.add("Cat & Dog");
        Songs.add("Drunk-Dazed");
        Songs.add("Bad Decisions");
        Songs.add("The Feels");
        Songs.add("Wrap Me In Plastic");
        Songs.add("Agust D");
        Songs.add("CROWN");
        Songs.add("Pink Venom");
        Songs.add("Lie");
        Songs.add("BEAUTIFUL MONSTER");

        Songs.add("SO BAD");
        Songs.add("ASAP");
        Songs.add("Talk that Talk");
        Songs.add("MIROH");
        Songs.add("Time Out");
        Songs.add("Shut Down");
        Songs.add("Pandora's Box");
        Songs.add("=");
        Songs.add("Safety Zone");
        Songs.add("Rush Hour");
        Songs.add("Blue & Grey");
        Songs.add("난 괜찮아");
        Songs.add("So What");
        Songs.add("Ma City");

        Songs.add("DADDY");
        Songs.add("House Party");
        Songs.add("다라리");
        Songs.add("Moon");
        Songs.add("CASE 143");
        Songs.add("SUPER BOARD");
        Songs.add("The Astronaut");
        Songs.add("Seoul Town Road");
        Songs.add("Family Song");
        Songs.add("Airplane");
        Songs.add("Magic Shop");
        Songs.add("승전가");
        Songs.add("Hellevator");
        Songs.add("Wings");

        Songs.add("Obsession");
        Songs.add("슈퍼 참치");
        Songs.add("Like");
        Songs.add("Tamed-Dashed");
        Songs.add("Christmas EveL");
        Songs.add("21세기 소녀");
        Songs.add("Polaroid Love");
        Songs.add("Blue Hour");
        Songs.add("HOME");
        Songs.add("Dreamers");
        Songs.add("CHEER UP");
        Songs.add("What is Love");
        Songs.add("SCIENTIST");
        Songs.add("Waste It On Me");

        Songs.add("JUMP");
        Songs.add("식혀");
        Songs.add("Give Me Your TMI");
        Songs.add("들꽃놀이");
        Songs.add("Lonely");
        Songs.add("자전거");
        Songs.add("Still Life");
        Songs.add("Serendipity");
        Songs.add("하루만");
        Songs.add("House Of Cards");
        Songs.add("Converse High");
        Songs.add("Tomorrow");
        Songs.add("2! 3!");
        Songs.add("흥탄소년단");

        Songs.add("잡아줘");  // 노래 십삼 개
        Songs.add("Louder than bombs");
        Songs.add("Jamais Vu");
        Songs.add("Shadow");
        Songs.add("Ego");
        Songs.add("VIBE");
        Songs.add("뱅 뱅 뱅");
        Songs.add("Fantastic Baby");
        Songs.add("몬스터");
        Songs.add("No.2");
        Songs.add("Lights");
        Songs.add("여기 봐");
        Songs.add("이사");

        Songs.add("Stigma");
        Songs.add("눈, 코, 입");
        Songs.add("Future");
        Songs.add("Yun");
        Songs.add("What if...");
        Songs.add("Sugar Rush Ride");
        Songs.add("Stay Alive");
        Songs.add("⟭⟬Stay⟭⟬");
        Songs.add("병");
        Songs.add("UGH!");
        Songs.add("HIP");
        Songs.add("전야");
        Songs.add("on the street");
        Songs.add("너 같은 사람 또 없어");

        Songs.add("직진");
        Songs.add("안녕");
        Songs.add("BOY");
        Songs.add("Set Me Free Pt.2");
        Songs.add("보조개");
        Songs.add("스테이");
        Songs.add("약속");
        Songs.add("Christmas Love");
        Songs.add("이 사랑");
        Songs.add("Stay With Me");
        Songs.add("You're My Garden");
        Songs.add("Heartbeat");
        Songs.add("Double Trouble Couple");
        Songs.add("Smoke Sprite");

        Songs.add("⟭⟬ 땡 ⟭⟬");
        Songs.add("땡");
        Songs.add("Like Crazy");
        Songs.add("Beautiful");
        Songs.add("이쁘다니까");
        Songs.add("너만 보여");
        Songs.add("In Silence");
        Songs.add("숨");
        Songs.add("Seesaw");
        Songs.add("사람 Pt.2");
        Songs.add("해금");
        Songs.add("AMYGDALA");
        Songs.add("환청");
        Songs.add("TRUE");

        Songs.add("Love Diamond");
        Songs.add("Skool Luv Affair");
        Songs.add("알아요");
        Songs.add("Still With You");
        Songs.add("The Planet");
        Songs.add("Angel Pt. 1");
        Songs.add("D-Day");
        Songs.add("Rover");
        Songs.add("다시 너를");
        Songs.add("Paradise");
        Songs.add("Pop star");
        Songs.add("네가 분다");
        Songs.add("It's you");
        Songs.add("Bittersweet");

        Songs.add("Persona");
        Songs.add("내 방을 여행하는 법");
        Songs.add("Lilith");
        Songs.add("특");
        Songs.add("Take Two");
        Songs.add("Youtiful");
        Songs.add("Face-off");
        Songs.add("꽃");
        Songs.add("Alone");
        Songs.add("If I Ruled The World");
        Songs.add("울고 싶지 않아");
        Songs.add("Ring Ding Dong");
        Songs.add("Seven");
        Songs.add("퀸카");

        Songs.add("TOMBOY");
        Songs.add("Nxde");
        Songs.add("Love Me Again");
        Songs.add("누나 너무 예뻐");
        Songs.add("0X1=LOVESONG");
        Songs.add("Rainy Days");
        Songs.add("도깨비집");
        Songs.add("TOPLINE");
        Songs.add("Hype Boy");
        Songs.add("3D");
        Songs.add("OMG");
        Songs.add("Blue");
        Songs.add("Slow Dancing");
        Songs.add("빛나리");

        Songs.add("썸 탈꺼야");
        Songs.add("Back for More");
        Songs.add("For Us");
        Songs.add("Love Maze");
        Songs.add("BONA BONA");
        Songs.add("손오공");
        Songs.add("Chasing That Feeling");
        Songs.add("Standing Next to You");
        Songs.add("Too Sad to Dance");
        Songs.add("Happily Ever After");
        Songs.add("Shot Glass of Tears");
        Songs.add("Closer Than This");
        Songs.add("Tinnitus");
        Songs.add("Yes or No");

        Songs.add("끝나지 않을 이야기");
        Songs.add("내 눈에만 보여");
        Songs.add("락");
        Songs.add("MEGAVERSE");
        Songs.add("FRI(END)S");
        Songs.add("첫사랑");
        Songs.add("한 번도 하지 못한 이야기");
        Songs.add("Come Back To Me");
        Songs.add("Sweet Venom");
        Songs.add("Pass The Mic");
        Songs.add("Hate You");
        Songs.add("MAESTRO");
        Songs.add("아주 Nice");
        Songs.add("You & Me");

        Songs.add("Would You");
        Songs.add("Come Back Home");
        Songs.add("Smeraldo Garden Marching Band");
        Songs.add("i wonder...");
        Songs.add("i don't know");
        Songs.add("NEURON");
        Songs.add("졸업");
        Songs.add("길");
        Songs.add("MAMA");
        Songs.add("이불킥");
        Songs.add("I Miss You");
        Songs.add("And I'm here");
        Songs.add("Hush");
        Songs.add("첫눈처럼 너에게 가겠다");



        //SONGS WHICH DOES NOT ABLE TO DETECT
        //Zero O'Clock
        //N.O
        //Pandora's Box
        //What if...


        for(int i=0;i<Songs.size();i++){

            for(int j=0;j<arrayList_for_recently_added_playlist.size();j++){

                if(Songs.get(i).equals(arrayList_for_recently_added_playlist.get(j).getMsong_name())){

                    String SONG_NAME=arrayList_for_recently_added_playlist.get(j).getMsong_name();
                    String PATH=arrayList_for_recently_added_playlist.get(j).getMpath();
                    String ARTIST_NAME=arrayList_for_recently_added_playlist.get(j).getMartist();
                    String ALBUM_NAME=arrayList_for_recently_added_playlist.get(j).getMalbum_name();
                    int DURATION=arrayList_for_recently_added_playlist.get(j).getMduration();
                    long ALBUM_ART=arrayList_for_recently_added_playlist.get(j).getMalbum_art();

                    arrayList_for_user_created_playlist.add(new Recently_added_recyclerview_elements_item_class(SONG_NAME,PATH,ARTIST_NAME,ALBUM_NAME,DURATION,ALBUM_ART,false));

                    break;
                }
            }
        }
    }



    












}