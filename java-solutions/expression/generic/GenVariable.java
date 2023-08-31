package expression.generic;


public class GenVariable<T extends Number> implements TripleParser<T> {

    public String s;

    public GenVariable(String s) {
        this.s = s;
    }

    @Override
    public T count(T x, T y, T z) {
        if (s.equals("x")) {
            return x;
        } else if (s.equals("y")) {
            return y;
        } else if (s.equals("z")) {
            return z;
        }
        throw new AssertionError();
    }
}
