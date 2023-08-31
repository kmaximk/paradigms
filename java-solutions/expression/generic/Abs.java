package expression.generic;

public class Abs<T extends Number> extends UnaryOperation<T> {
    public Abs(TripleParser<T> ex1, Operation<T> op) {
        super(ex1, op);
    }


    @Override
    public T count(T x, T y, T z) {
        return op.abs(ex1.count(x, y, z));
    }
}
