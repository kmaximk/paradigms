package expression;


import java.util.Objects;

public class Const implements Prioriable {


    private final int num;


    public Const(int num) {
        this.num = num;
    }


    public int evaluate(int var) {
        return this.num;
    }


    @Override
    public String toString() {
        return String.valueOf(num);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return Objects.equals(num, aConst.num);
    }


    @Override
    public int hashCode() {
        return num;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return num;
    }

    @Override
    public int getPrior() {
        return 4;
    }
}
