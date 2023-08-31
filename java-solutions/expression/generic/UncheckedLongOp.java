package expression.generic;

import expression.exceptions.DBZException;

public class UncheckedLongOp implements Operation<Long> {
    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long divide(Long x, Long y) {
        if (y == 0) {
            throw new DBZException("Division by zero");
        }
        return x / y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long negate(Long x) {
        return -x;
    }

    @Override
    public Long valueOf(String s) {
        return (long) Integer.parseInt(s);
    }

    @Override
    public Long abs(Long x) {
        return Math.abs(x);
    }

    @Override
    public Long square(Long x) {
        return x * x;
    }

    @Override
    public Long mod(Long x, Long y) {
        if (y == 0) {
            throw new DBZException("Module == 0, division by zero");
        }
        return x % y;
    }
}
