package com.cscorner.feelit;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Short extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_short);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences preferences=getSharedPreferences("preff",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
//        ImageButton imageButton=new ImageButton();

        Toast.makeText(this, getIntent().getStringExtra("playlist_name"), Toast.LENGTH_SHORT).show();
//        Intent intent=new Intent(this, Feel_it_notification_broadcast_receiver.class);
//        intent.setAction("AP");
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//        try {
//            pendingIntent.send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
//
//        if(preferences.getBoolean("app",false)){
//            editor.putString("ACTION","AP");
//            editor.apply();
//        }else {
//            startActivity(intent);
//
//        }
    }

    @Override
    public void onBackPressed() {

    }
}