package com.kletto.bot_tutorial.game.shared;

import org.dyn4j.geometry.Vector2;

public class DummyStrategy implements IStrategy {

    private int power;

    public DummyStrategy(int power) {
        this.power = power;
    }

    @Override
    public void move(Opponent opponent) {
        Vector2 impulseRight = opponent.getWorldPoint(new Vector2(0, opponent.getHeight()));
        Vector2 impulseLeft = opponent.getWorldPoint(new Vector2(0, -opponent.getHeight()));
        opponent.applyImpulse(new Vector2(1,0).rotate(opponent.getAngle()).multiply(100000*power), impulseLeft);
        opponent.applyImpulse(new Vector2(1,0).rotate(opponent.getAngle()).multiply(100000*power), impulseRight);
    }
}
