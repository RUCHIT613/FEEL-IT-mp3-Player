package com.cscorner.feelit;

import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.auth;
import static com.cscorner.feelit.MUSIC_PLAYER_ACTIVITY.user;

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

public class LOGIN extends AppCompatActivity {
    public EditText Sign_in_email;
    public EditText Sign_in_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        Sign_in_email=findViewById(R.id.sign_in_email);
        Sign_in_password=findViewById(R.id.sign_in_password);
    }
    @SuppressLint("NotConstructor")
    public void LOGIN(View view){
        String EMAIL=Sign_in_email.getText().toString();
        String PASSWORD=Sign_in_password.getText().toString();
        Log.d("LOGIN", String.format("%s \n%s",EMAIL,PASSWORD));


        auth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            user=auth.getCurrentUser();
                            Toast.makeText(LOGIN.this, "YOU HAVE LOGGED IN", Toast.LENGTH_SHORT).show();
                            finish();
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LOGIN.this, "USER DOES NOT FOUND", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void sign_up(View view){
        startActivity(new Intent(LOGIN.this, SIGN_UP.class));
        finish();
    }
    public void forget_password(View view){
        startActivity(new Intent(LOGIN.this, FORGET_PASSWORD.class));
        finish();
    }
}