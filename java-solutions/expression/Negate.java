package expression;


public class Negate extends UnOperation {
    public Negate(final Expression ex1) {
        super(ex1);
    }


    @Override
    public int count(int x) {
        return -x;
    }


    @Override
    public String getSign() {
        return "-";
    }
}
