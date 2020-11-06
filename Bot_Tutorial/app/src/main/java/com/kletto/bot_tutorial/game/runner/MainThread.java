package com.kletto.bot_tutorial.game.runner;

import android.content.SharedPreferences;

import com.kletto.bot_tutorial.som.compiler.ProgramDefinitionError;
import com.kletto.bot_tutorial.som.vm.Universe;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.crypto.ExemptionMechanism;

public class MainThread extends Thread {

    private final SharedPreferences sharedPreferences;
    private final String codeIndex;

    public MainThread(SharedPreferences sharedPreferences, String codeIndex) {
        this.sharedPreferences = sharedPreferences;
        this.codeIndex = codeIndex;
    }

    @Override
    public void run() {

        Universe universe = new Universe(sharedPreferences);
        String[] arguments = {"-cp", "res/raw", codeIndex+".som"};

        try {
            universe.interpret(arguments);
        } catch (ProgramDefinitionError programDefinitionError) {
            programDefinitionError.printStackTrace();
        }
    }
}
