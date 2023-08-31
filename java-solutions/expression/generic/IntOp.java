package expression.generic;


import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;

public class IntOp implements Operation<Integer> {

    @Override
    public Integer add(Integer x, Integer y) {
        if (y > 0 && Integer.MAX_VALUE - y < x || y < 0 && Integer.MIN_VALUE - y > x) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        }
        return y + x;
    }

    @Override
    public Integer square(Integer x) {
        return multiply(x, x);
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        if (y == 0) {
            throw new DBZException("Module == 0, division by zero");
        }
        return x % y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (y == 0) {
            throw new DBZException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        }
        return x / y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (x > 0 && (y > Integer.MAX_VALUE / x || y < Integer.MIN_VALUE / x)) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        } else if (x < 0) {
            if (x != -1 && (y > Integer.MIN_VALUE / x || y < Integer.MAX_VALUE / x) || y == Integer.MIN_VALUE) {
                throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
            }
        }
        return y * x;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (y < 0 && Integer.MAX_VALUE + y < x || y > 0 && Integer.MIN_VALUE + y > x) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        }
        return x - y;
    }

    @Override
    public Integer negate(Integer x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Integer overflow, x: " + x);
        } else {
            return -x;
        }
    }

    @Override
    public Integer valueOf(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public Integer abs(Integer x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Integer overflow " + x);
        }
        return x >= 0 ? x : -x;
    }
}
