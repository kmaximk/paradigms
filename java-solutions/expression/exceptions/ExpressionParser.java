package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringSource;

public class ExpressionParser implements TripleParser {

    public TripleExpression parse(String expression) throws ParseException {
        return parse(new StringSource(expression));
    }

    public static TripleExpression parse(CharSource src) throws ParseException {
        return new ExpParser(src).parseExpression();
    }

    private static final class ExpParser extends BaseParser {

        private ExpParser(CharSource source) {
            super(source);
        }

        public TripleExpression parseExpression() throws ParseException {
            final TripleExpression result = setClear(0);
            eof();
            return result;
        }


        private TripleExpression AddSub() throws ParseException {
            TripleExpression left = MulDiv();
            skipWhitespace();
            while (true) {
                skipWhitespace();
                if (take('+')) {
                    skipWhitespace();
                    left = new CheckedAdd((Expression) left, (Expression) MulDiv());
                } else if (take('-')) {
                    skipWhitespace();
                    left = new CheckedSubtract((Expression) left, (Expression) MulDiv());
                } else {
                    return left;
                }
            }
        }


        private TripleExpression setClear(int wereBrackets) throws ParseException {
            TripleExpression left = AddSub();
            while (!eof()) {
                skipWhitespace();
                if (take('s')) {
                    expect("et");
                    left = new CheckedSet((Expression) left, (Expression) AddSub());
                } else if (take('c')) {
                    expect("lear");
                    left = new CheckedClear((Expression) left, (Expression) AddSub());
                } else if (eof() || (test(')') && wereBrackets == 1)) {
                    return left;
                } else {
                    if (test(')')) {
                        throw new ParseException("No opening bracket at index " + getIndex());
                    }
                    throw new ParseException("Wrong expression, mistake at index " + getIndex());
                }
            }
            return left;
        }


        private TripleExpression MulDiv() throws ParseException {
            TripleExpression left = count();
            skipWhitespace();
            while (true) {
                skipWhitespace();
                if (take('*')) {
                    left = new CheckedMultiply((Expression) left, (Expression) count());
                } else if (take('/')) {
                    left = new CheckedDivide((Expression) left, (Expression) count());
                } else {
                    return left;
                }
            }
        }


        private TripleExpression brackets() throws ParseException {
            skipWhitespace();
            if (take('(')) {
                TripleExpression left = setClear(1);
                if (!take(')')) {
                    throw new ParseException("No closing bracket");
                }
                return left;
            } else {
                return numOrVar();
            }
        }


        private TripleExpression count() throws ParseException {
            skipWhitespace();
            if (take('c')) {
                expect("ount");
                if (!ws() && !test('(')) {
                    throw new ParseException("No space after count at index " + getIndex());
                }
                return new CheckedCount((Expression) count());
            }
            return brackets();
        }

        private TripleExpression numOrVar() throws ParseException {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            if (take('-')) {
                sb.append('-');
                if (between('0', '9')) {
                    return new Const(Integer.parseInt(parseInt(sb)));
                }
                return new CheckedNegate((Expression) count());
            } else if (between('0', '9')) {
                return new Const(Integer.parseInt(parseInt(sb)));
            } else {
                return new Variable(parseVar());
            }
        }

        private String parseInt(StringBuilder sb) throws ParseException{
            while (between('0', '9')) {
                sb.append(take());
            }
            if (Character.isLetter(getNext())) {
                throw new ParseException("Letter immediately after the number at index " + getIndex());
            }
            // 2147483648
            if (sb.charAt(0) == '-' &&
                    (sb.length() > 11 || (sb.length() == 11 && sb.compareTo(new StringBuilder(Integer.toString(Integer.MIN_VALUE))) > 0))
            ) {
                throw new OverflowException("Integer overflow: " + sb);
            } else if (sb.charAt(0) != '-' &&
                    (sb.length() > 10 || (sb.length() == 10 && sb.compareTo(new StringBuilder(new StringBuilder(Integer.toString(Integer.MAX_VALUE)))) > 0))
            ) {
                throw new OverflowException("Integer overflow: " + sb);
            }
            return sb.toString();
        }

        private String parseVar() throws ParseException {
            if (between('x', 'z')) {
                return Character.toString(take());
            } else {
                char x = take();
                if (x == '\0') {
                    throw new ParseException("End of the string reached, but argument expected");
                } else if (x == ')') {
                    throw new ParseException("No argument found before closing bracket at index " + (getIndex() - 1));
                }
                throw new ParseException("Wrong argument: " + x + " at index " + (getIndex() - 1));
            }
        }


        private void skipWhitespace() {
            while (take('\r') || take('\n') || take('\t') || ws() || take('\f')) {
                // skip
            }
        }
    }
}
