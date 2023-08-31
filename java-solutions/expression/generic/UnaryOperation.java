package expression.generic;

public abstract class UnaryOperation<T extends Number> implements TripleParser<T> {
    protected final TripleParser<T> ex1;

    protected final Operation<T> op;

    protected UnaryOperation(TripleParser<T> ex1, Operation<T> op) {
        this.ex1 = ex1;
        this.op = op;
    }
}
