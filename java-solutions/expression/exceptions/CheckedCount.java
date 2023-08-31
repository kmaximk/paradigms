package expression.exceptions;

import expression.Expression;
import expression.UnOperation;

public class CheckedCount extends UnOperation {
    public CheckedCount(Expression ex1) {
        super(ex1);
    }

    @Override
    public int count(int x) {
        int cnt = x & 1;
        for (int i = 0; i < 31; i++) {
            cnt += (x >> 1) & 1;
            x = x >> 1;
        }
        return cnt;
    }


    @Override
    public String getSign() {
        return "count";
    }
}
