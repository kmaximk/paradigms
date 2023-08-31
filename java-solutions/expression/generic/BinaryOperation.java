package expression.generic;


public abstract class BinaryOperation<T extends Number> implements TripleParser<T> {
    protected final TripleParser<T> ex1;

    protected final TripleParser<T> ex2;

    protected final Operation<T> op;

    protected BinaryOperation(TripleParser<T> ex1, TripleParser<T> ex2, Operation<T> op) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }


}
