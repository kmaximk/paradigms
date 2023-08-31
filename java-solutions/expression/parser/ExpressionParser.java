package expression.parser;

import expression.*;
import expression.exceptions.TripleParser;

public class ExpressionParser implements TripleParser {

    public TripleExpression parse(String expression) {
        return parse(new StringSource(expression));
    }

    public static TripleExpression parse(CharSource src) {
        return new ExpParser(src).parseExpression();
    }

    private static final class ExpParser extends BaseParser {

        private ExpParser(CharSource source) {
            super(source);
        }

        public TripleExpression parseExpression() {
            final TripleExpression result = setClear();
            eof();
            return result;
        }


        private TripleExpression lowPrio() {
            TripleExpression left = medPrio();

            while (true) {
                skipWhitespace();
                if (take('+')) {
                    left = new Add((Expression) left, (Expression) medPrio());
                }
                else if (take('-')) {
                    left = new Subtract((Expression) left, (Expression) medPrio());
                } else {
                    return left;
                }
            }
        }


        private TripleExpression setClear() {
            TripleExpression left = lowPrio();
            while (!eof()) {
                skipWhitespace();
                if (take('s')) {
                    expect("et");
                    left = new Set((Expression) left, (Expression) lowPrio());
                }
                else if (take('c')) {
                    expect("lear");
                    left = new Clear((Expression) left, (Expression) lowPrio());
                } else if (eof() || test(')')){
                    return left;
                } else {
                    throw new ArithmeticException();
                }
            }
            return left;
        }


        private TripleExpression medPrio() {
            TripleExpression left = unMinus();
            while (true) {
                skipWhitespace();
                if (take('*')) {
                    left =  new Multiply((Expression) left, (Expression) brackets());
                } else if (take('/')) {
                    left =  new Divide((Expression) left, (Expression) brackets());
                } else {
                    return left;
                }
            }
        }

        private TripleExpression unMinus() {
            return brackets();
        }

        private TripleExpression brackets() {
            skipWhitespace();
            if (take('(')) {
                TripleExpression left = setClear();
                if (!take(')')) {

                }
                return left;
            }
            return numOrVar();
        }

        private TripleExpression numOrVar() {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            if (test('-')) {
                sb.append(take());
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            if (sb.length() > 1) {
                return new Const(Integer.parseInt(sb.toString()));
            } else if (sb.length() == 1 && sb.charAt(0) != '-') {
                return new Const(Integer.parseInt(sb.toString()));
            } else if (sb.length() == 1 && sb.charAt(0) == '-') {
                return new Negate((Expression) brackets());
            }
            while (between('a', 'z') || between('A', 'Z')) {
                sb.append(take());
            }
            return new Variable(sb.toString());
        }


        private void skipWhitespace() {
            while (take('\r') || take('\n') || take('\t') || ws()) {
                // skip
            }
        }
    }
}
