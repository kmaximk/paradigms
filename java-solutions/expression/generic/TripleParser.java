package expression.generic;

public interface TripleParser<T extends Number> {
    T count(T x, T y, T z);

}
