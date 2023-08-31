package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.OverflowException;

import java.math.BigInteger;

public class BigIntOp implements Operation<BigInteger> {
    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger square(BigInteger x) {
        return x.multiply(x);
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        if (y.signum() <= 0) {
            throw new OverflowException("Module < 0");
        }
        return x.mod(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.valueOf(0))) {
            throw new DBZException("Division by zero");
        }
        return x.divide(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger valueOf(String s) {
        return new BigInteger(s);
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }
}
