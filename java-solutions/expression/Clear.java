package expression;

public class Clear extends Operation{

    public Clear(Expression ex1, Expression ex2) {
        super(ex1, ex2);
    }

    @Override
    public String getSign() {
        return "clear";
    }

    @Override
    public int getPrior() {
        return 0;
    }

    @Override
    public int count(int x, int y) {
        return x & ~(1 << y);
    }
}
