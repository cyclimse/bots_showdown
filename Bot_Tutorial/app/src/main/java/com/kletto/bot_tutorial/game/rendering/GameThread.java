package com.kletto.bot_tutorial.game.rendering;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameThread extends Thread {

    private boolean isRunning;

    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        while(isRunning) {
            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (canvas) {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime();
            long sleepTime = (now - startTime) / 1_000_000;
            if (sleepTime < 10) {
                sleepTime = 10;
            }

            try {
                this.sleep(sleepTime);
            } catch (InterruptedException e) {

            }
            startTime = System.nanoTime();
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}