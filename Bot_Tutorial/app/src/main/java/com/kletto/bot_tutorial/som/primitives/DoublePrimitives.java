/**
 * Copyright (c) 2009 Michael Haupt, michael.haupt@hpi.uni-potsdam.de
 * Software Architecture Group, Hasso Plattner Institute, Potsdam, Germany
 * http://www.hpi.uni-potsdam.de/swa/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.kletto.bot_tutorial.som.primitives;

import com.kletto.bot_tutorial.som.interpreter.Frame;
import com.kletto.bot_tutorial.som.interpreter.Interpreter;
import com.kletto.bot_tutorial.som.vm.Universe;
import com.kletto.bot_tutorial.som.vmobjects.SAbstractObject;
import com.kletto.bot_tutorial.som.vmobjects.SDouble;
import com.kletto.bot_tutorial.som.vmobjects.SNumber;
import com.kletto.bot_tutorial.som.vmobjects.SPrimitive;

public class DoublePrimitives extends Primitives {

  public DoublePrimitives(final Universe universe) {
    super(universe);
  }

  @Override
  public void installPrimitives() {
    installInstancePrimitive(new SPrimitive("asString", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SDouble self = (SDouble) frame.pop();
        frame.push(self.primAsString(universe));
      }
    });

    installInstancePrimitive(new SPrimitive("asInteger", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SDouble self = (SDouble) frame.pop();
        frame.push(self.primAsInteger(universe));
      }
    });

    installInstancePrimitive(new SPrimitive("sqrt", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SDouble self = (SDouble) frame.pop();
        frame.push(self.primSqrt(universe));
      }
    });

    installInstancePrimitive(new SPrimitive("+", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SNumber op1 = (SNumber) frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primAdd(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("-", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SNumber op1 = (SNumber) frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primSubtract(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("*", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SNumber op1 = (SNumber) frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primMultiply(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("//", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SNumber op1 = (SNumber) frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primDoubleDivide(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("%", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SNumber op1 = (SNumber) frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primModulo(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("=", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SAbstractObject op1 = frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primEqual(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("<", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SNumber op1 = (SNumber) frame.pop();
        SDouble op2 = (SDouble) frame.pop();
        frame.push(op2.primLessThan(op1, universe));
      }
    });

    installInstancePrimitive(new SPrimitive("round", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SDouble rcvr = (SDouble) frame.pop();
        long result = Math.round(rcvr.getEmbeddedDouble());
        frame.push(universe.newInteger(result));
      }
    });

    installInstancePrimitive(new SPrimitive("sin", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SDouble rcvr = (SDouble) frame.pop();
        double result = Math.sin(rcvr.getEmbeddedDouble());
        frame.push(universe.newDouble(result));
      }
    });

    installInstancePrimitive(new SPrimitive("cos", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        SDouble rcvr = (SDouble) frame.pop();
        double result = Math.cos(rcvr.getEmbeddedDouble());
        frame.push(universe.newDouble(result));
      }
    });

    installClassPrimitive(new SPrimitive("PositiveInfinity", universe) {
      @Override
      public void invoke(final Frame frame, final Interpreter interpreter) {
        frame.pop();
        frame.push(universe.newDouble(Double.POSITIVE_INFINITY));
      }
    });
  }
}
