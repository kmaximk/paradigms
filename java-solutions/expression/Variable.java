package expression;


import java.util.Objects;

public class Variable implements Prioriable {
    private final String str;
    public Variable(String str) {
        this.str = str;
    }

    @Override
    public int evaluate(int var) {
        return var;
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(str, variable.str);
    }

    @Override
    public int hashCode() {
        return str.hashCode();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (str.equals("x")) {
            return x;
        } else if (str.equals("y")) {
            return y;
        } else {
            return z;
        }
    }

    @Override
    public int getPrior() {
        return 4;
    }
}











