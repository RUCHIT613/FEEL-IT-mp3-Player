package com.cscorner.feelit;

import static com.cscorner.feelit.App.CHANNEL_1_ID;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;

public  class Feel_it_Service extends Service {
        private NotificationCompat.Builder notification_builder;
        private PendingIntent pendingIntent;

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @SuppressLint("ForegroundServiceType")
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            MUSIC_PLAYER_ACTIVITY musicPlayerActivity=new MUSIC_PLAYER_ACTIVITY();

            String song_name=intent.getStringExtra("SONG_NAME");
            String artist_name=intent.getStringExtra("ARTIST_NAME");
            long album_art=intent.getLongExtra("ALBUM_ART",0);

            @SuppressLint("RemoteViewLayout") RemoteViews expanded=new RemoteViews(getPackageName(),
                    R.layout.custom_notification);

            Uri albumArtUri = Uri.parse("content://media/external/audio/albumart/" + album_art);
            try {
                InputStream inputStream = getContentResolver().openInputStream(albumArtUri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap bitmap1=getRoundedCornerBitmap(bitmap,25);
                expanded.setImageViewBitmap(R.id.custom_notification_album_art_imageview,bitmap1);
                inputStream.close();




            } catch (IOException e) {
                e.printStackTrace();
            }



            expanded.setTextViewText(R.id.custom_notification_Song_textview,song_name);
            expanded.setTextViewText(R.id.custom_notification_artist_textview,artist_name);
//            expanded.setImageViewUri(R.id.custom_notification_album_art_imageview,albumArtUri);
            if(media_player.get_media_player().isPlaying()){
                expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.pause);
            }else {
                expanded.setImageViewResource(R.id.custom_notification_play_pause_imageview,R.drawable.play_icon);
            }

            expanded.setOnClickPendingIntent(R.id.custom_notification_previous_song_imageview,get_pending_intent_for_music_player("PREVIOUS_SONG"));
            expanded.setOnClickPendingIntent(R.id.custom_notification_play_pause_imageview,get_pending_intent_for_music_player("PLAY_PAUSE"));
            expanded.setOnClickPendingIntent(R.id.custom_notification_next_song_imageview,get_pending_intent_for_music_player("NEXT_SONG"));
            expanded.setOnClickPendingIntent(R.id.custom_notification_close_imageview,get_pending_intent_for_music_player("CLOSE"));






//        Intent notificationIntent=new Intent(this)
            if(notification_builder==null) {
                notification_builder = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//
//            notification_builder=new Notification.Builder(this,"channel_1")
                        .setSmallIcon(R.drawable.logo)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setOnlyAlertOnce(true)
                        .setContentTitle("Feel It!!")
                        .setCustomBigContentView(expanded)

//                        .setContent(expanded)
//                        .setCustomHeadsUpContentView(expanded)
//                        .setContentText(String.format("%s\n%s",song_name,artist_name))
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


            }

            Notification notification =notification_builder.build();

            startForeground(1,notification);
            return START_NOT_STICKY;
        }
        public PendingIntent get_pending_intent_for_music_player(String ACTION){
            Intent intent=new Intent(getApplicationContext(),Feel_it_notification_broadcast_receiver.class);
            intent.setAction(ACTION);
            return PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_MUTABLE);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
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
    }
