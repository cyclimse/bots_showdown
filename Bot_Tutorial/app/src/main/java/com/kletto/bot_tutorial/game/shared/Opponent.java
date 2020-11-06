package com.kletto.bot_tutorial.game.shared;

import android.graphics.Bitmap;

import com.kletto.bot_tutorial.game.rendering.GameSurface;

public class Opponent extends BotDrawable {

    private final IStrategy strategy;

    public Opponent(GameSurface gameSurface, IStrategy strategy, Bitmap sprite, double angle, int x, int y) {
        super(gameSurface, sprite, angle, x, y);

        this.strategy = strategy;

    }

    public void applyStrategy() {
        strategy.move(this);
    }




}
