package com.kletto.bot_tutorial.som.primitives;

import com.kletto.bot_tutorial.game.rendering.GameSurface;
import com.kletto.bot_tutorial.game.rendering.GameThread;
import com.kletto.bot_tutorial.som.interpreter.Frame;
import com.kletto.bot_tutorial.som.interpreter.Interpreter;
import com.kletto.bot_tutorial.som.vm.Universe;
import com.kletto.bot_tutorial.som.vmobjects.*;

public class BotPrimitives extends Primitives {

    public BotPrimitives(final Universe universe) {
        super(universe);
    }

    @Override
    public void installPrimitives() {

        installInstancePrimitive(new SPrimitive("powerLeft:powerRight:", universe) {

            @Override
            public void invoke(final Frame frame, final Interpreter interpreter) {
                SInteger rightPower = (SInteger) frame.pop();
                SInteger leftPower = (SInteger) frame.pop();
                frame.pop();
                GameSurface.receiveInput(leftPower.getEmbeddedInteger(), rightPower.getEmbeddedInteger());
            }

        });

        installInstancePrimitive(new SPrimitive("x", universe) {

            @Override
            public void invoke(final Frame frame, final Interpreter interpreter) {
                SObject self = (SObject) frame.pop();
                //frame.push(universe.newInteger(Engine.getInstance().getPlayer().getPosition().x));
            }

        });

        installInstancePrimitive(new SPrimitive("y", universe) {

            @Override
            public void invoke(final Frame frame, final Interpreter interpreter) {
                SObject self = (SObject) frame.pop();
                //frame.push(universe.newInteger(Engine.getInstance().getPlayer().getPosition().y));
            }

        });

        installInstancePrimitive(new SPrimitive("angle", universe) {

            @Override
            public void invoke(final Frame frame, final Interpreter interpreter) {
                SObject self = (SObject) frame.pop();
                frame.push(universe.newDouble(Math.toDegrees(GameSurface.getAngle())));
            }

        });

    }

}
