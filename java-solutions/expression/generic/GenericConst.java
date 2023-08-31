package expression.generic;


public class GenericConst<T extends Number> implements TripleParser<T> {


    private final T num;

    Operation<T> op;


    public GenericConst(T num, Operation<T> op) {
        this.num = num;
        this.op = op;
    }


    @Override
    public String toString() {
        return String.valueOf(num);
    }


    @Override
    public T count(T x, T y, T z) {
        return num;
    }
}