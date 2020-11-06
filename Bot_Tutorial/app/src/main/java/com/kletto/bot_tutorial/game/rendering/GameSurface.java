package com.kletto.bot_tutorial.game.rendering;

import android.content.Context;
import android.content.Intent;
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

import androidx.navigation.Navigation;

import com.kletto.bot_tutorial.GameActivity;
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

import static java.lang.Integer.parseInt;

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

    String codeIndex;

    private boolean hasWon;
    private long ticksAfterVictory;

    private GameActivity activity;

    public GameSurface(GameActivity context, String codeIndex) {
        super((Context) context);

        this.activity = context;

        this.setFocusable(true);

        this.getHolder().addCallback(this);

        this.codeIndex = codeIndex;
        this.hasWon = false;
        this.ticksAfterVictory = 0;
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

        if (ticksAfterVictory > 60) {
                activity.finish();
        }

        Vector2 impulseRight = botDrawable.getWorldPoint(new Vector2(0, botDrawable.getHeight()));
        Vector2 impulseLeft = botDrawable.getWorldPoint(new Vector2(0, -botDrawable.getHeight()));
        botDrawable.applyImpulse(new Vector2(1,0).rotate(botDrawable.getAngle()).multiply(100000*powerLeft.get()), impulseLeft);
        botDrawable.applyImpulse(new Vector2(1,0).rotate(botDrawable.getAngle()).multiply(100000*powerRight.get()), impulseRight);

        opponent.applyStrategy();

        world.step(1,  0.01);

        botDrawable.setAngularVelocity(0);
        opponent.setAngularVelocity(0);

        this.angle.set((int) botDrawable.getAngle());

        if (ticksAfterVictory < 1 && !isInsideArena(botDrawable)) {
            ticksAfterVictory = 1;
            return;
        } else if (ticksAfterVictory < 1 && !isInsideArena(opponent)) {
            hasWon = true;
            ticksAfterVictory = 1;
            return;
        }

        if (ticksAfterVictory > 0) {
            ticksAfterVictory++;
        }


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));

        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.colorBackground));
        canvas.drawPaint(background);

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30 * getResources().getDisplayMetrics().density);
        textPaint.setColor(getResources().getColor(R.color.colorPrimary));

        canvas.drawCircle((float) arenaCenter.x, (float) arenaCenter.y, (float) arenaRadius, paint);
        botDrawable.draw(canvas);
        opponent.draw(canvas);

        if (ticksAfterVictory > 0) {
            String text;
            if (hasWon) {
                text = "You have won!";
            } else {
                text = "You have lost!";
            }
            int width = (int) textPaint.measureText(text);
            StaticLayout staticLayout = new StaticLayout(text, textPaint, (int) width, Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);
            canvas.save();
            canvas.translate(this.getWidth()/4F, this.getHeight()/8F);
            staticLayout.draw(canvas);
            canvas.restore();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        Bitmap botSprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.bot);
        Bitmap oppSprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.opponent);

        this.botDrawable = new BotDrawable(this, botSprite, 90, this.getWidth()/2, this.getHeight()/3);

        int index = Integer.parseInt(codeIndex.substring(codeIndex.length()-1));
        switch (index) {
            case 1:
                this.opponent = new Opponent(this, new DummyStrategy(0), oppSprite, -90, this.getWidth()/2, (2*this.getHeight())/3);
                break;
            case 2:
                this.opponent = new Opponent(this, new DummyStrategy(150), oppSprite, -90, this.getWidth()/2, (2*this.getHeight())/3);
                break;
            default:
                this.opponent = new Opponent(this, new DummyStrategy(100), oppSprite, -90, this.getWidth()/2, (2*this.getHeight())/3);
                break;
        }

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
        this.gameThread.setRunning(false);
        try {
            this.gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
