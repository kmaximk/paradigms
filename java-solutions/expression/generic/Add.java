package expression.generic;

public class Add<T extends Number> extends BinaryOperation<T> {
    public Add(TripleParser<T> ex1, TripleParser<T> ex2, Operation<T> op) {
        super(ex1, ex2, op);
    }


    @Override
    public T count(T x, T y, T z) {
        return op.add(ex1.count(x, y, z), ex2.count(x, y, z));
    }
}
