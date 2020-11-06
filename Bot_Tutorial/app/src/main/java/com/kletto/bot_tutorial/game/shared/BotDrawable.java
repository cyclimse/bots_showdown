package com.kletto.bot_tutorial.game.shared;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.kletto.bot_tutorial.game.rendering.GameSurface;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class BotDrawable extends Base2D {

    private GameSurface gameSurface;

    public BotDrawable(GameSurface gameSurface, Bitmap sprite, double angle, int x, int y) {
        super(sprite, 1, 1);

        this.gameSurface = gameSurface;

        this.addFixture(Geometry.createRectangle(this.getWidth(), this.getHeight()));
        this.setMass(MassType.NORMAL);

        this.rotate(Math.toRadians(angle));
        this.translate(new Vector2(x, y));

    }

    public double getAngle() {
        return this.transform.getRotationAngle();
    }

    public void draw(Canvas canvas) {

        float scale = 1.0F;

        for (BodyFixture fixture : this.fixtures) {
           this.drawFixture(canvas, scale, fixture, null);
        }
    }

    public void drawFixture(Canvas canvas, float scale, BodyFixture fixture, Paint paint) {
        Convex convex = fixture.getShape();

        if (convex instanceof Rectangle) {
            Rectangle r = (Rectangle) convex;
            double w = r.getWidth();
            double h = r.getHeight();



            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            matrix.postRotate((float) Math.toDegrees(transform.getRotationAngle()), ((float) w * scale)/2F, ((float) h * scale)/2F);
            matrix.postTranslate(((float) this.transform.getTranslationX()  - ((float) w * scale)/2F), (float) this.transform.getTranslationY() - ((float) h * scale)/2F);

            canvas.drawBitmap(sprite, matrix, paint);
        }
    }


}
