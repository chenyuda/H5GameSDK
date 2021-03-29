package com.sdk.app2.test;

import android.os.Bundle;
import android.view.View;

import com.sdk.app2.R;
import com.sdk.h5game.H5Game;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        H5Game.start(this);
    }
}