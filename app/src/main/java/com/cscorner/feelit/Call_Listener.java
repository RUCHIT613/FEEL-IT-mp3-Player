package com.cscorner.feelit;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class Call_Listener extends PhoneStateListener {
    private AudioManager audioManager;
    public Call_Listener(Context context){
        audioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
            case TelephonyManager.CALL_STATE_OFFHOOK:
                // Pause or stop media playback
                if (media_player.get_media_player().isPlaying()) {
                    media_player.pause_media_player(); // Pause the MediaPlayer
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                // Resume media playback if it was paused
                if (!media_player.get_media_player().isPlaying()) {
                    media_player.resume_media_player(); // Resume the MediaPlayer
                }
                break;
            default:
                break;
        }
    }
}
