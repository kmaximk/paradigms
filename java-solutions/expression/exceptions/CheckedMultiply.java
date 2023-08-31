package expression.exceptions;



import expression.Expression;
import expression.Operation;
import  expression.Multiply;

public class CheckedMultiply extends Multiply {

    public CheckedMultiply(Expression ex1, Expression ex2) {
        super(ex1, ex2);
    }


    @Override
    public int count(int x, int y) {
        if (x > 0 && (y > Integer.MAX_VALUE / x || y < Integer.MIN_VALUE/ x)) {
            throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
        } else if (x < 0) {
            if (x != -1 && (y > Integer.MIN_VALUE / x || y < Integer.MAX_VALUE / x)) {
                throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
            } else if (x == -1 && y == Integer.MIN_VALUE) {
                throw new OverflowException("Integer overflow, x: " + x + ", y: " + y);
            }
        }
        return y * x;
    }
}
