package expression.generic;

public class FloatOp implements Operation<Float> {

    @Override
    public Float add(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float divide(Float x, Float y) {
        return x / y;
    }

    @Override
    public Float multiply(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float subtract(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float negate(Float x) {
        return -x;
    }

    @Override
    public Float valueOf(String s) {
        return Float.valueOf(s);
    }

    @Override
    public Float abs(Float x) {
        return Math.abs(x);
    }

    @Override
    public Float square(Float x) {
        return x * x;
    }

    @Override
    public Float mod(Float x, Float y) {
        return x % y;
    }
}
