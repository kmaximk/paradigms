package expression.exceptions;

import expression.Expression;
import expression.Operation;
import expression.Add;

public class CheckedAdd extends Add {

    public CheckedAdd(Expression ex1, Expression ex2) {
        super(ex1, ex2);
    }


    @Override
    public int count(int x, int y) {
        if (y > 0 && Integer.MAX_VALUE - y < x) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        } else if (y < 0 && Integer.MIN_VALUE - y > x) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        }
        return x + y;
    }
}
