package expression.generic;

public class Mod<T extends Number> extends BinaryOperation<T> {
    public Mod(TripleParser<T> ex1, TripleParser<T> ex2, Operation<T> op) {
        super(ex1, ex2, op);
    }

    @Override
    public T count(T x, T y, T z) {
        return op.mod(ex1.count(x, y, z), ex2.count(x, y, z));
    }
}
