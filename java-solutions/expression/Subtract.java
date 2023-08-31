package expression;


public class Subtract extends Operation {
    public Subtract(Expression ex1, Expression ex2) {
        super(ex1, ex2);
    }


    @Override
    public String getSign() {
        return "-";
    }


    @Override
    public int count(int x, int y) {
        return x - y;
    }

    @Override
    public int getPrior() {
        return 1;
    }
}
