package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SIGN_UP extends AppCompatActivity {
    public EditText Sign_up_email;
    public EditText Sign_up_password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Sign_up_email=findViewById(R.id.sign_up_email);
        Sign_up_password=findViewById(R.id.sign_up_password);
    }
    @SuppressLint("NotConstructor")
    public void SIGN_UP(View view){
        String EMAIL=Sign_up_email.getText().toString();
        String PASSWORD=Sign_up_password.getText().toString();
        Log.d("SIGN UP", "SIGN_UP: "+EMAIL+" "+PASSWORD);

        auth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SIGN_UP.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SIGN_UP.this,LOGIN.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SIGN_UP.this, "UNSUCCESSFUL", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
}