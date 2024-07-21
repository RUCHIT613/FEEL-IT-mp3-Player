package com.cscorner.feelit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;

public class MediaButtonReceiver extends BroadcastReceiver {

    private MediaSessionCompat mediaSession;

    public MediaButtonReceiver(MediaSessionCompat mediaSession) {
        this.mediaSession = mediaSession;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            // Get the KeyEvent from the intent
            android.view.KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event != null && event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                // Dispatch the KeyEvent to the MediaSessionCompat
                mediaSession.getController().dispatchMediaButtonEvent(event);
            }
        }
    }
}
