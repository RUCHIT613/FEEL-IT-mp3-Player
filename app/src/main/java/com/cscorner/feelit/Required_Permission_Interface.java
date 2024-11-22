package com.cscorner.feelit;

import static com.cscorner.feelit.MainActivity.Permission_For_External_Storage;
import static com.cscorner.feelit.MainActivity.Permission_For_Telephone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class Required_Permission_Interface extends AppCompatActivity {
    private ConstraintLayout constraintLayout_of_storage_access_grant_button;
    private TextView Textview_of_storage_access_grant_button;
    private ConstraintLayout constraintLayout_of_phone_access_grant_button;
    private TextView Textview_of_phone_access_grant_button;
    @SuppressLint({"MissingInflatedId", "ResourceAsColor", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_required_permission_interface);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        constraintLayout_of_storage_access_grant_button=findViewById(R.id.Constraint_Layout_of_Storage_Access_Grant_Button);
        Textview_of_storage_access_grant_button=findViewById(R.id.Textview_of_Storage_Access_Grant_Button);

        constraintLayout_of_phone_access_grant_button=findViewById(R.id.Constraint_Layout_of_Phone_Access_Grant_Button);
        Textview_of_phone_access_grant_button=findViewById(R.id.Textview_of_Phone_Access_Grant_Button);


       CHECK_APP_PERMISSION();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CHECK_APP_PERMISSION();
    }
    public void CHECK_APP_PERMISSION(){

//        //FOR STORAGE PERMISSIONS
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                PackageManager.PERMISSION_GRANTED) {
//            CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(true,constraintLayout_of_storage_access_grant_button,Textview_of_storage_access_grant_button);
//            Permission_For_External_Storage=true;
//
//        } else {
//            CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(false,constraintLayout_of_storage_access_grant_button,Textview_of_storage_access_grant_button);
//            Permission_For_External_Storage=false;
//        }
//
//
//        FOR TELEPHONE PERMISSIONS
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(true,constraintLayout_of_phone_access_grant_button,Textview_of_phone_access_grant_button);
            Permission_For_Telephone=true;


        } else {
            CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(false,constraintLayout_of_phone_access_grant_button,Textview_of_phone_access_grant_button);
            Permission_For_Telephone=false;

        }
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED){
                Permission_For_External_Storage=true;
                CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(true,constraintLayout_of_storage_access_grant_button,Textview_of_storage_access_grant_button);
            }else{
                Permission_For_External_Storage=false;
                CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(false,constraintLayout_of_storage_access_grant_button,Textview_of_storage_access_grant_button);
            }
        } else {
            if( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Permission_For_External_Storage=true;
                CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(true,constraintLayout_of_storage_access_grant_button,Textview_of_storage_access_grant_button);
            }else{
                CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(false,constraintLayout_of_storage_access_grant_button,Textview_of_storage_access_grant_button);
                Permission_For_External_Storage=false;
            }


        }
        if(Permission_For_External_Storage&&Permission_For_Telephone){
            startActivity(new Intent(this, MUSIC_PLAYER_ACTIVITY.class));
            overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
            finish();
        }
    }




    public void GRANT_STORAGE_ACCESS(View view){
        if(!Permission_For_External_Storage){
            OPEN_APP_SETTING();
        }

    }
    public void GRANT_PHONE_ACCESS(View view ){
        if(!Permission_For_Telephone){
            OPEN_APP_SETTING();
        }
    }


    public void OPEN_APP_SETTING(){

        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
    public void CONFIGURE_CONSTRAINT_LAYOUT_AND_TEXTVIEW(boolean Permission,ConstraintLayout constraintLayout,TextView textView){
        if(Permission){
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            textView.setText("GRANTED");
            textView.setTextColor(ContextCompat.getColor(this, R.color.black));
        }else{
            constraintLayout.setBackgroundResource(R.drawable.off_grant_button);
            textView.setText("GRANT");
            textView.setTextColor(ContextCompat.getColor(this,R.color.green));
        }
    }
}