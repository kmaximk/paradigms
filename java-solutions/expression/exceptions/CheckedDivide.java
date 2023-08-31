package expression.exceptions;


import expression.Divide;
import expression.Expression;
import expression.Operation;

import java.rmi.AccessException;

public class CheckedDivide extends Divide {
    public CheckedDivide(Expression ex1, Expression ex2) {
        super(ex1, ex2);
    }


    @Override
    public int count(int x, int y) {
        if (x == Integer.MIN_VALUE && y == -1 ) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        } else if (y == 0) {
            throw new DBZException("Division by zero");
        }
        return x / y;
    }
}
