package com.kletto.bot_tutorial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.kletto.bot_tutorial.game.rendering.GameSurface;
import com.kletto.bot_tutorial.game.runner.MainThread;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("scripts", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("UserScript", "UserScript = ( |i| run = ( i := 0. [ i < 100] whileTrue: [ 'toto' println. i := i+1. ] ) )");
        editor.putString("UserScript", "UserScript = ( |i| run = ( i := 0. [ i < 100] whileTrue: [ bot powerLeft:100 powerRight:80. i := i+1. ] ) )");
        editor.commit();

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        MainThread thread = new MainThread(sharedPreferences);
        thread.run();

        setContentView(new GameSurface(this));
    }

}