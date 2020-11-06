package com.kletto.bot_tutorial.som.primitives;

import com.kletto.bot_tutorial.som.interpreter.Interpreter;
import com.kletto.bot_tutorial.som.interpreter.Frame;
import com.kletto.bot_tutorial.som.vm.Universe;
import com.kletto.bot_tutorial.som.vmobjects.SMethod;
import com.kletto.bot_tutorial.som.vmobjects.SPrimitive;


public class MethodPrimitives extends Primitives {
  public MethodPrimitives(final Universe universe) {
    super(universe);
  }

  @Override
  public void installPrimitives() {
    installInstancePrimitive(new SPrimitive("holder", universe) {

      @Override
      public void invoke(Frame frame, Interpreter interpreter) {
        SMethod self = (SMethod) frame.pop();
        frame.push(self.getHolder());
      }
    });

    installInstancePrimitive(new SPrimitive("signature", universe) {

      @Override
      public void invoke(Frame frame, Interpreter interpreter) {
        SMethod self = (SMethod) frame.pop();
        frame.push(self.getSignature());
      }
    });
  }
}
