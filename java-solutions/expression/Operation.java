package expression;


import java.util.Map;
import java.util.Objects;

public abstract class Operation implements Prioriable {

    private final Expression ex1, ex2;


    public Operation(Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    @Override
    public String toString() {
        String sb = "(" + ex1.toString() + " " +
                this.getSign() + " " +
                ex2.toString() + ")";
        return sb;
    }


    @Override
    public String toMiniString() {
//        StringBuilder sb = new StringBuilder();
//        Prioriable op1 = (Prioriable) ex1;
//        Prioriable op2 = (Prioriable) ex2;
//        if (op1.getPrior() < this.getPrior()) {
//            sb.append("(").append(op1.toMiniString()).append(")");
//        } else {
//            sb.append(op1.toMiniString());
//        }
//        sb.append(" ").append(this.getSign()).append(" ");
//        if (op2.getPrior() < this.getPrior()) {
//            sb.append("(").append(op2.toMiniString()).append(")");
//        } else if (this.getPrior() == op2.getPrior() && check()) {
//            sb.append("(").append(op2.toMiniString()).append(")");
//        } else {
//            sb.append(op2.toMiniString());
//        }
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(ex1, operation.ex1) && Objects.equals(ex2, operation.ex2) && this.getSign() == operation.getSign();
    }


    @Override
    public int hashCode() {
        return (31*31)*ex1.hashCode() + 31*ex2.hashCode() + this.getClass().hashCode();
    }


    public abstract String getSign();


    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression ex1 = (TripleExpression) this.ex1;
        TripleExpression ex2 = (TripleExpression) this.ex2;
        return count(ex1.evaluate(x, y, z),  ex2.evaluate(x, y ,z));
    }


    @Override
    public int evaluate(int x) {
        return this.count(ex1.evaluate(x), ex2.evaluate(x));
    }


    public int count(int x, int y) {
        return 0;
    }


    public abstract int getPrior();


    private boolean check() {
        return this instanceof Divide || this instanceof Subtract || ex2 instanceof Divide;
    }
}
