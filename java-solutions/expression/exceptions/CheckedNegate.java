package expression.exceptions;


import expression.Expression;
import expression.UnOperation;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(final Expression ex1) {
        super(ex1);
    }


    @Override
    public int count(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Integer overflow, x: " + x);
        }
        return -x;
    }
}
