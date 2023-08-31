package expression;

public class Set extends Operation{
    public Set(Expression ex1, Expression ex2) {
        super(ex1, ex2);
    }

    @Override
    public String getSign() {
        return "set";
    }

    @Override
    public int getPrior() {
        return 0;
    }

    @Override
    public int count(int x, int y) {
        return x | (1 << y);
    }
}
