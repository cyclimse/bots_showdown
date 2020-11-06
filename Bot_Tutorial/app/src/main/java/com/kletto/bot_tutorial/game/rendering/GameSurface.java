package com.kletto.bot_tutorial.game.rendering;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kletto.bot_tutorial.R;
import com.kletto.bot_tutorial.game.shared.BotDrawable;
import com.kletto.bot_tutorial.game.shared.DummyStrategy;
import com.kletto.bot_tutorial.game.shared.Opponent;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.collision.Bounds;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private BotDrawable botDrawable;
    private Opponent opponent;

    private double arenaRadius;
    private Vector2 arenaCenter;

    private World<BotDrawable> world;

    // I wasn't a big fan of having a shared instance of a botDrawable, especially since it can't really be created statically
    // Making this class a singleton isn't elegant and would require to use deinit() instead of relying on garbage-collection since we need multiple instances
    private static AtomicInteger powerLeft = new AtomicInteger();
    private static AtomicInteger powerRight = new AtomicInteger();

    private static AtomicInteger angle = new AtomicInteger();
    private static AtomicInteger xpos = new AtomicInteger();
    private static AtomicInteger ypos = new AtomicInteger();


    public GameSurface(Context context) {
        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);
    }

    public static void receiveInput(long powerLeft, long powerRight) {
        setPowerLeft(powerLeft > 100 ? 100 : (int) (powerLeft < 0 ? 0 : powerLeft));
        setPowerRight(powerRight > 100 ? 100 : (int) (powerRight < 0 ? 0 : powerRight));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setPowerLeft(int powerLeft) {
        GameSurface.powerLeft.set(powerLeft);
    }

    public static void setPowerRight(int powerRight) {
        GameSurface.powerRight.set(powerRight);
    }

    public static int getAngle() {
        return angle.get();
    }

    public static int getXPos() {
        return xpos.get();
    }

    public static int getYPos() {
        return ypos.get();
    }

    private boolean isInsideArena(BotDrawable bot) {
        Vector2 pos = bot.getWorldPoint(new Vector2()).subtract(arenaCenter);
        return pos.dot(pos) < arenaRadius*arenaRadius;
    }

    public void update() {

        Vector2 impulseRight = botDrawable.getWorldPoint(new Vector2(0, botDrawable.getHeight()));
        Vector2 impulseLeft = botDrawable.getWorldPoint(new Vector2(0, -botDrawable.getHeight()));
        botDrawable.applyImpulse(new Vector2(1,0).rotate(botDrawable.getAngle()).multiply(100000*powerLeft.get()), impulseLeft);
        botDrawable.applyImpulse(new Vector2(1,0).rotate(botDrawable.getAngle()).multiply(100000*powerRight.get()), impulseRight);

        opponent.applyStrategy();

        world.step(1,  0.01);
        botDrawable.setAngularVelocity(0);
        opponent.setAngularVelocity(0);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        textPaint.setColor(getResources().getColor(R.color.colorAccent));

        canvas.drawCircle((float) arenaCenter.x, (float) arenaCenter.y, (float) arenaRadius, paint);
        botDrawable.draw(canvas);
        opponent.draw(canvas);

        if (!isInsideArena(botDrawable)) {
            String text = "You lose!";
            int width = (int) textPaint.measureText(text);
            StaticLayout staticLayout = new StaticLayout(text, textPaint, (int) width, Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);
            staticLayout.draw(canvas);
        }

        if (!isInsideArena(opponent)) {
            String text = "You win!";
            int width = (int) textPaint.measureText(text);
            StaticLayout staticLayout = new StaticLayout(text, textPaint, (int) width, Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);
            staticLayout.draw(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        Bitmap botSprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.bot);
        this.botDrawable = new BotDrawable(this, botSprite, 90, this.getWidth()/2, this.getHeight()/3);
        this.opponent = new Opponent(this, new DummyStrategy(), botSprite, -90, this.getWidth()/2, (2*this.getHeight())/3);

        arenaCenter = new Vector2(this.getWidth() >> 1, this.getHeight() >> 1);
        arenaRadius = (3/8F)*this.getWidth();

        world = new World<>();
        world.addBody(botDrawable);
        world.addBody(opponent);
        world.setGravity(0, 0);
        world.setBounds(new Bound(this.getWidth(), this.getHeight()));

        this.gameThread = new GameThread(this, surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameThread.setRunning(false);

                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }
}
