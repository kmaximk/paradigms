package expression;

import java.util.Objects;

public abstract class UnOperation implements TripleExpression, Expression{
    final TripleExpression exp;
    public UnOperation(final Expression ex1) {
        this.exp = (TripleExpression) ex1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnOperation unOperation = (UnOperation) o;
        return Objects.equals(exp, unOperation.exp);
    }

    @Override
    public String toString() {
        return this.getSign() + "(" + exp.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return this.getSign() + exp.toMiniString();
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(exp) + Objects.hash(this.getSign());
    }

    @Override
    public int evaluate(int x) {
        Expression op1 = (Expression) exp;
        return count(op1.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return count(exp.evaluate(x, y, z));
    }


    public abstract int count(int x);

    public abstract String getSign();
}
