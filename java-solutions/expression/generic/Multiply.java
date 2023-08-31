package expression.generic;

public class Multiply<T extends Number> extends BinaryOperation<T> {

    public Multiply(TripleParser<T> ex1, TripleParser<T> ex2, Operation<T> op) {
        super(ex1, ex2, op);
    }


    @Override
    public T count(T x, T y, T z) {
        return op.multiply(ex1.count(x, y, z), ex2.count(x, y, z));
    }
}
