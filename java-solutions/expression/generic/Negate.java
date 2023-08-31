package expression.generic;

public class Negate<T extends Number> extends UnaryOperation<T> {
    public Negate(TripleParser<T> ex1, Operation<T> op) {
        super(ex1, op);
    }


    @Override
    public T count(T x, T y, T z) {
        return op.negate(ex1.count(x, y, z));
    }
}
