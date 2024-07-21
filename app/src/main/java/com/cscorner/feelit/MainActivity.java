package com.cscorner.feelit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Boolean Permission_For_External_Storage=false;
    public static Boolean Permission_For_Telephone=false;
    public Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView splashImage = findViewById(R.id.splash);
        AnimatedVectorDrawable splashAnimation = (AnimatedVectorDrawable) splashImage.getDrawable();
        splashAnimation.start();
        handler.postDelayed(runnable,3500);


    }
    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            start();
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//
//            }
//        }
    }
    public void start(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Permission_For_External_Storage=true;
        } else {
            Permission_For_External_Storage=false;
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            Permission_For_Telephone=true;

        } else {
            Permission_For_Telephone=false;
        }
        if(Permission_For_External_Storage&&Permission_For_Telephone){
            START_ACTIVITY(new Intent(this, MUSIC_PLAYER_ACTIVITY.class));

        }else{
            START_ACTIVITY(new Intent(this, Required_Permission_Interface.class));
        }

    }
    public void START_ACTIVITY(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_animation,R.anim.fade_out_animation);
        finish();

    }
}