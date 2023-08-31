package expression.generic;

public interface Operation<T extends Number> {
    T add(T x, T y);

    T divide(T x, T y);

    T multiply(T x, T y);

    T subtract(T x, T y);

    T negate(T x);

    T valueOf(String s);

    T abs(T x);

    T square(T x);

    T mod(T x, T y);

}
