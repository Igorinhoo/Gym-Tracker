package com.application.gymtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getPull(View view){
        Intent intent = new Intent(this, pullActivity.class);
        startActivity(intent);
    }
    public void getPush(View view){
        Intent intent = new Intent(this, pushActivity.class);
        startActivity(intent);
    }


}