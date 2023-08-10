package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Annapurnath");

        Button loginButton=findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_login=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(new_login);
            }
        });

        Button RegisterButton=findViewById(R.id.register_btn);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_register=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(new_register);
            }
        });
    }
}