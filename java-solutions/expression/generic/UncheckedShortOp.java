package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;

public class UncheckedShortOp implements Operation<Short> {
    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short divide(Short x, Short y) {
        if (y == 0) {
            throw new DBZException("Division by zero");
        }
        return (short) (x / y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short negate(Short x) {
        return (short) -x;
    }

    @Override
    public Short valueOf(String s) {
        return (short) Integer.parseInt(s);
    }

    @Override
    public Short abs(Short x) {
        return (short) Math.abs(x);
    }

    @Override
    public Short square(Short x) {
        return (short) (x * x);
    }

    @Override
    public Short mod(Short x, Short y) {
        if (y == 0) {
            throw new DBZException("Module == 0, division by zero");
        }
        return (short) (x % y);
    }
}
