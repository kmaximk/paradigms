package expression.generic;

import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringSource;
import expression.exceptions.*;

public class GenericParser<T extends Number> {

    public TripleParser<T> parse(String expression, Operation<T> op) throws ParseException {
        return parse(new StringSource(expression), op);
    }

    public TripleParser<T> parse(CharSource src, Operation<T> op) throws ParseException {
        return new ExpParser(src, op).parseExpression();
    }

    private final class ExpParser extends BaseParser {

        private ExpParser(CharSource source, Operation<T> op) {
            super(source);
            this.op = op;
        }

        Operation<T> op;

        public TripleParser<T> parseExpression() throws ParseException {
            final TripleParser<T> result = AddSub();
            eof();
            return result;
        }


        private TripleParser<T> AddSub() throws ParseException {
            TripleParser<T> left = MulDiv();
            skipWhitespace();
            while (true) {
                skipWhitespace();
                if (take('+')) {
                    skipWhitespace();
                    left = new Add<T>(left, MulDiv(), op);
                } else if (take('-')) {
                    left = new Subtract<T>(left, MulDiv(), op);
                } else {
                    return left;
                }
            }
        }


        private TripleParser<T> MulDiv() throws ParseException {
            TripleParser<T> left = brackets();
            skipWhitespace();
            while (true) {
                skipWhitespace();
                if (take('*')) {
                    left = new Multiply<T>(left, brackets(), op);
                } else if (take('/')) {
                    left = new Divide<T>(left, brackets(), op);
                } else if (take('m')) {
                    expect("od");
                    left = new Mod<T>(left, brackets(), op);
                } else {
                    return left;
                }
            }
        }


        private TripleParser<T> brackets() throws ParseException {
            skipWhitespace();
            if (take('(')) {
                TripleParser<T> left = AddSub();
                if (!take(')')) {
                    throw new ParseException("No closing bracket");
                }
                return left;
            } else {
                return abs();
            }
        }

        private TripleParser<T> abs() throws ParseException {
            skipWhitespace();
            if (take('a')) {
                expect("bs");
                return new Abs<T>(brackets(), op);
            } else if (take('s')) {
                expect("quare");
                return new Square<T>(brackets(), op);
            }
            return numOrVar();
        }


        private TripleParser<T> numOrVar() throws ParseException {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            if (take('-')) {
                sb.append('-');
                if (between('0', '9')) {
                    return new GenericConst<T>(op.valueOf(parseInt(sb)), op);
                }
                return new Negate<T>(brackets(), op);
            } else if (between('0', '9')) {
                return new GenericConst<T>(op.valueOf(parseInt(sb)), op);
            } else {
                return new GenVariable<T>(parseVar());
            }
        }

        private String parseInt(StringBuilder sb) throws ParseException {
            while (between('0', '9') || test('.')) {
                sb.append(take());
            }
            if (Character.isLetter(getNext())) {
                throw new ParseException("Letter immediately after the number at index " + getIndex());
            }
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
            if (!between('x', 'z')) {
                char x = take();
                if (x == '\0') {
                    throw new ParseException("End of the string reached, but argument expected");
                } else if (x == ')') {
                    throw new ParseException("No argument found before closing bracket at index " + (getIndex() - 1));
                }
                throw new ParseException("Wrong argument: " + x + " at index " + (getIndex() - 1));
            } else {
                return Character.toString(take());
            }
        }


        private void skipWhitespace() {
            while (take('\r') || take('\n') || take('\t') || ws() || take('\f')) {
                // skip
            }
        }
    }
}

