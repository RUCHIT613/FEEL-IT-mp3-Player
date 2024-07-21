package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.Count_for_Repeat_Button;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_ARTIST_NAME_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_PATH_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.MINIPLAYER_SONG_NAME_KEY;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_all_playlists;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.arrayList_for_recently_added_playlist;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.permission_for_miniplayer_widget_favorite;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.permission_for_miniplayer_widget_shuffle;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Implementation of App Widget functionality.
 */
public class miniplayer_widget extends AppWidgetProvider {
    private static Context mcontext;
    private Handler handler;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        mcontext=context;
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        SharedPreferences preferences= context.getSharedPreferences("preff",Context.MODE_PRIVATE);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.miniplayer_widget);

        String Song_Name=preferences.getString(MINIPLAYER_SONG_NAME_KEY ,"");
        String Artist_Name=preferences.getString(MINIPLAYER_ARTIST_NAME_KEY,"");
        String Path=preferences.getString(MINIPLAYER_PATH_KEY,"");







        if(Song_Name.length()>42){
            Song_Name=Song_Name.substring(0,39)+"...";
        }
        if(Artist_Name.length()>42){
            Artist_Name=Artist_Name.substring(0,39)+"...";
        }

        Bitmap bitmap;

        if(!preferences.getString(MINIPLAYER_PATH_KEY,"").equals("") ) {

//            Toast.makeText(context, preferences.getString(MINIPLAYER_PATH_KEY,""), Toast.LENGTH_SHORT).show();
            MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(Path);
            byte[] albumArtBytes = mediaMetadataRetriever.getEmbeddedPicture();
            if (albumArtBytes != null) {
                Bitmap albumArtBitmap = BitmapFactory.decodeByteArray(albumArtBytes, 0, albumArtBytes.length);

                //                InputStream inputStream = getContentResolver().openInputStream(albumArtUri);

//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                bitmap1=getRoundedCornerBitmap(bitmap,25);
                bitmap=getRoundedCornerBitmap(albumArtBitmap,100);
                views.setImageViewBitmap(R.id.MINIPLAYER_WIDGET_IMAGEVIEW,bitmap);
            }



            if(media_player.get_media_player().isPlaying()){
                views.setImageViewResource(R.id.MINIPLAYER_WIDGET_PLAY_IMAGEVIEW,R.drawable.pause);
            }else{
                views.setImageViewResource(R.id.MINIPLAYER_WIDGET_PLAY_IMAGEVIEW,R.drawable.play_icon);
            }

            views.setTextViewText(R.id.MINIPLAYER_WIDGET_SONG_NAME_TEXTVIEW,Song_Name);
            views.setTextViewText(R.id.MINIPLAYER_WIDGET_ARTIST_NAME_TEXTVIEW,Artist_Name);
            Intent intent=new Intent(mcontext, MUSIC_PLAYER_ACTIVITY.class);
            views.setOnClickPendingIntent(R.id.widget_relative,PendingIntent.getActivity(mcontext,0,intent,PendingIntent.FLAG_IMMUTABLE ));

            views.setOnClickPendingIntent(R.id.MINIPLAYER_WIDGET_PREVIOUS_IMAGEVIEW,get_pending_intent_for_music_player(context,"PREVIOUS_SONG"));
            views.setOnClickPendingIntent(R.id.MINIPLAYER_WIDGET_PLAY_IMAGEVIEW,get_pending_intent_for_music_player(context,"PLAY_PAUSE"));
            views.setOnClickPendingIntent(R.id.MINIPLAYER_WIDGET_NEXT_IMAGEVIEW,get_pending_intent_for_music_player(context,"NEXT_SONG"));
//        views.setTextViewText(R.id.appwidget_text, widgetText);

            String SLOT_1_MODE=preferences.getString("SLOT_1","FAVOURITE");
            String SLOT_2_MODE=preferences.getString("SLOT_2","SHUFFLE");


            if(SLOT_1_MODE.equals("FAVOURITE")){
                if(permission_for_miniplayer_widget_favorite){
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.favorite_on);
                }else {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.favorite_off);
                }
            } else if (SLOT_1_MODE.equals("SHUFFLE")) {
                if(permission_for_miniplayer_widget_shuffle){
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.shuffle_on);
                }else {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.shuffle_off);
                }
            } else if (SLOT_1_MODE.equals("REPEAT")) {
                if(Count_for_Repeat_Button==1){
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.repeat_one);
                } else if (Count_for_Repeat_Button==2) {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.repeat_all);
                } else  {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION1,R.drawable.repeat_off);
                }

            }



            if(SLOT_2_MODE.equals("FAVOURITE")){
                if(permission_for_miniplayer_widget_favorite){
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.favorite_on);
                }else {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.favorite_off);
                }
            } else if (SLOT_2_MODE.equals("SHUFFLE")) {
                if(permission_for_miniplayer_widget_shuffle){
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.shuffle_on);
                }else {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.shuffle_off);
                }
            } else if (SLOT_2_MODE.equals("REPEAT")) {
                if(Count_for_Repeat_Button==1){
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.repeat_one);
                } else if (Count_for_Repeat_Button==2) {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.repeat_all);
                } else  {
                    views.setImageViewResource(R.id.MINIPLAYER_WIDGET_OPTION2,R.drawable.repeat_off);
                }

            }



            views.setOnClickPendingIntent(R.id.MINIPLAYER_WIDGET_OPTION1,get_pending_intent_for_music_player(context,SLOT_1_MODE));
            views.setOnClickPendingIntent(R.id.MINIPLAYER_WIDGET_OPTION2,get_pending_intent_for_music_player(context,SLOT_2_MODE));
            // Instruct the widget manager to update the widget

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }else {
            views.setImageViewResource(R.id.MINIPLAYER_WIDGET_IMAGEVIEW,R.drawable.logo);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }



    }

    @Override
    public void onEnabled(Context context) {

        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static PendingIntent get_pending_intent_for_music_player(Context context,String ACTION) {


        Intent intent = new Intent(context, Feel_it_notification_broadcast_receiver.class);
        intent.setAction(ACTION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int cornerRadius) {
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
