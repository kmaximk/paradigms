package expression.generic;

public class Square<T extends Number> extends UnaryOperation<T> {
    public Square(TripleParser<T> ex1, Operation<T> op) {
        super(ex1, op);
    }

    @Override
    public T count(T x, T y, T z) {
        return op.square(ex1.count(x, y, z));
    }
}
