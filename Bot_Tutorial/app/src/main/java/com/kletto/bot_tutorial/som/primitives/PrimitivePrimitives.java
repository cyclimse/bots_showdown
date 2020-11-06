package com.kletto.bot_tutorial.som.primitives;

import com.kletto.bot_tutorial.som.interpreter.Interpreter;
import com.kletto.bot_tutorial.som.interpreter.Frame;
import com.kletto.bot_tutorial.som.vm.Universe;
import com.kletto.bot_tutorial.som.vmobjects.SPrimitive;


public class PrimitivePrimitives extends Primitives {
  public PrimitivePrimitives(final Universe universe) {
    super(universe);
  }

  @Override
  public void installPrimitives() {
    installInstancePrimitive(new SPrimitive("holder", universe) {

      @Override
      public void invoke(Frame frame, Interpreter interpreter) {
        SPrimitive self = (SPrimitive) frame.pop();
        frame.push(self.getHolder());
      }
    });

    installInstancePrimitive(new SPrimitive("signature", universe) {

      @Override
      public void invoke(Frame frame, Interpreter interpreter) {
        SPrimitive self = (SPrimitive) frame.pop();
        frame.push(self.getSignature());
      }
    });
  }
}
