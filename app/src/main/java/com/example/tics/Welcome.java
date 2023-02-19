package com.example.tics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
    private ImageButton signupbtn;
    private ImageButton signinbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        signupbtn =(ImageButton) findViewById(R.id.Welcome_SignUp);
        signinbtn= (ImageButton) findViewById(R.id.SignIn_SignIn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignup();
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignin();
            }
        });
    }
    public void opensignup() {
        Intent intent = new Intent(Welcome.this, SignUp.class);
        startActivity(intent);
    }

    public void opensignin(){
        Intent intent = new Intent(Welcome.this, SignIn.class);
        startActivity(intent);
    }
}