package com.kletto.bot_tutorial.game.runner;

import android.content.SharedPreferences;

import com.kletto.bot_tutorial.som.compiler.ProgramDefinitionError;
import com.kletto.bot_tutorial.som.vm.Universe;

public class MainThread extends Thread {

    private final SharedPreferences sharedPreferences;

    public MainThread(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void run() {

        this.setDaemon(true);

        Universe universe = new Universe(sharedPreferences);
        String[] arguments = {"-cp", "res/raw", "UserScript.som"};

        try {
            universe.interpret(arguments);
        } catch (ProgramDefinitionError e) {
            e.printStackTrace();
        }
    }
}
