package com.kletto.bot_tutorial.game.shared;

import android.graphics.Bitmap;
import android.graphics.Point;

import org.dyn4j.dynamics.Body;

// Base class for the manipulation of 2D sprites
// /!\ Not directly drawable!
public class Base2D extends Body {

    protected Bitmap sprite;

    // Sprite dimensions
    protected final int WIDTH;
    protected final int HEIGHT;

    protected final int rowCount;
    protected final int colCount;

    // Dimensions for drawing
    protected final int width;
    protected final int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Base2D(Bitmap sprite, int rowCount, int colCount) {
        this.sprite = sprite;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.WIDTH = sprite.getWidth();
        this.HEIGHT = sprite.getHeight();

        this.width = this.WIDTH/colCount;
        this.height = this.HEIGHT/colCount;

    }
}
