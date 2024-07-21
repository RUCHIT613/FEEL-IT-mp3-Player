package com.cscorner.feelit;

import android.media.MediaPlayer;

public class media_player {

    private static MediaPlayer player;
    public static MediaPlayer get_media_player(){
        if(player==null){

            player=new MediaPlayer();

        }
        return player;
    }
//    public static boolean play_the_song


    public static void pause_media_player(){
        player.pause();
    }

    public static void resume_media_player(){
        player.start();
    }
    public static void release_media_player(){
        if(player!=null){
            player.release();
            player=null;
        }
    }
}
