package com.cscorner.feelit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class Feel_it_notification_broadcast_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MUSIC_PLAYER_ACTIVITY musicPlayerActivity =new MUSIC_PLAYER_ACTIVITY();
        SharedPreferences preferences = context.getSharedPreferences("preff",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String action=intent.getAction();


        Intent intent1=new Intent(context, MUSIC_PLAYER_ACTIVITY.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        if(action !=null&& action.equals("PLAY_PAUSE")){
            editor.putString("ACTION","PLAY_PAUSE");
        }
        else if(action !=null&& action.equals("NEXT_SONG")){
            editor.putString("ACTION","NEXT_SONG");
        }
        else if(action !=null&& action.equals("PREVIOUS_SONG")){
            editor.putString("ACTION","PREVIOUS_SONG");
        } else if(action !=null&& action.equals("CLOSE")){
            editor.putString("ACTION","CLOSE");
        } else if (action!=null && action.equals("FAVOURITE")) {
            editor.putString("ACTION","FAVOURITE");
        } else if (action!=null && action.equals("SHUFFLE")) {
            editor.putString("ACTION","SHUFFLE");
        } else if (action!=null && action.equals("REPEAT")){
            editor.putString("ACTION","REPEAT");
        } else if (action!=null && action.equals("P")) {
            editor.putString("ACTION","ITS");

        }

        editor.apply();

    }
    

}
