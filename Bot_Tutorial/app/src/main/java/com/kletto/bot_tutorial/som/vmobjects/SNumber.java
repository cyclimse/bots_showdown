package com.kletto.bot_tutorial.som.vmobjects;

import java.math.BigInteger;

import com.kletto.bot_tutorial.som.vm.Universe;


public abstract class SNumber extends SAbstractObject {
  public abstract SString primAsString(Universe universe);

  public abstract SNumber primSqrt(Universe universe);

  public abstract SNumber primAdd(SNumber right, Universe universe);

  public abstract SNumber primSubtract(SNumber right, Universe universe);

  public abstract SNumber primMultiply(SNumber right, Universe universe);

  public abstract SNumber primDoubleDivide(SNumber right, Universe universe);

  public abstract SNumber primIntegerDivide(SNumber right, Universe universe);

  public abstract SNumber primModulo(SNumber right, Universe universe);

  public abstract SNumber primBitAnd(SNumber right, Universe universe);

  public abstract SNumber primBitXor(SNumber right, Universe universe);

  public abstract SNumber primLeftShift(SNumber right, Universe universe);

  public abstract SObject primEqual(SAbstractObject right, Universe universe);

  public abstract SObject primLessThan(SNumber right, Universe universe);

  protected final SNumber intOrBigInt(final double value, final Universe universe) {
    if (value > Long.MAX_VALUE || value < Long.MIN_VALUE) {
      return universe.newBigInteger(new BigInteger(Double.toString(Math.rint(value))));
    } else {
      return universe.newInteger((long) Math.rint(value));
    }
  }

  protected final SObject asSBoolean(final boolean result, final Universe universe) {
    if (result) {
      return universe.trueObject;
    } else {
      return universe.falseObject;
    }
  }

}
