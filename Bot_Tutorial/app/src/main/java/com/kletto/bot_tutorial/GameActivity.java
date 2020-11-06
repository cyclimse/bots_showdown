package com.kletto.bot_tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.kletto.bot_tutorial.game.rendering.GameSurface;
import com.kletto.bot_tutorial.game.runner.MainThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GameActivity extends AppCompatActivity {

    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("scripts", MODE_PRIVATE);

        Intent intent = getIntent();
        String checkCodeIndex = intent.getStringExtra("codeIndex");

        MainThread thread;
        if (checkCodeIndex == null || checkCodeIndex.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.putString("USER_CODE_DEFAULT", "USER_CODE_DEFAULT = ( |i| run = ( i := 0. [ i < 100] whileTrue: [ bot powerLeft:100 powerRight:80. i := i+1. ] ) )");
            editor.putString("USER_CODE_DEFAULT", "USER_CODE_DEFAULT = ( |i| run = ( i := 0. [ (i < 100) && (bot angle < 45) ] whileTrue: [ bot powerLeft:100 powerRight:60. i := i+1. ] ) )");
            editor.apply();
            thread = new MainThread(sharedPreferences, "USER_CODE_DEFAULT");
        } else {
            thread = new MainThread(sharedPreferences, checkCodeIndex);
        }

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        executor = Executors.newSingleThreadExecutor();
        final Future future = executor.submit(thread);

        try {
            future.get(20, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (!executor.isTerminated()) {
            executor.shutdownNow();
        }

        setContentView(new GameSurface(this, checkCodeIndex));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        executor.shutdownNow();
    }

}