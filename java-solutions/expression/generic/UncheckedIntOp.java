package expression.generic;


import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;

public class UncheckedIntOp implements Operation<Integer> {

    @Override
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (y == 0) {
            throw new DBZException("Division by zero");
        }
        return x / y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        return x * y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer negate(Integer x) {
        return -x;
    }

    @Override
    public Integer valueOf(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public Integer abs(Integer x) {
        return x > 0 ? x : -x;
    }

    @Override
    public Integer square(Integer x) {
        return x * x;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        if (y == 0) {
            throw new DBZException("Module == 0, division by zero");
        }
        return x % y;
    }
}
