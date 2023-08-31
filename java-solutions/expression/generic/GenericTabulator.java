package expression.generic;

import expression.exceptions.OverflowException;
import expression.exceptions.ParseException;

import java.math.BigInteger;
import java.util.Map;

public class GenericTabulator implements Tabulator {

    Map<String, Operation<? extends Number>> m = Map.of(
            "i", new IntOp(),
            "d", new DoubleOp(),
            "l", new UncheckedLongOp(),
            "s", new UncheckedShortOp(),
            "bi", new BigIntOp(),
            "u", new UncheckedIntOp(),
            "f", new FloatOp()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return count(x1, x2, y1, y2, z1, z2, expression, m.get(mode));
    }

    private <T extends Number> Object[][][] count(int x1, int x2, int y1, int y2, int z1, int z2, String expression, Operation<T> op) throws ParseException {
        GenericParser<T> prs = new GenericParser<>();
        TripleParser<T> expr = prs.parse(expression, op);
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i < x2 + 1; i++) {
            for (int j = y1; j < y2 + 1; j++) {
                for (int k = z1; k < z2 + 1; k++) {
                    try {
                        ans[i - x1][j - y1][k - z1] = expr.count(op.valueOf(String.valueOf(i)), op.valueOf(String.valueOf(j)), op.valueOf(String.valueOf(k)));
                    } catch (OverflowException e) {
                        ans[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return ans;
    }
}
