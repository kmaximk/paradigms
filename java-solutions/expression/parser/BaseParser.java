package expression.parser;


public class BaseParser {
    private static final char END = '\0';
    private final CharSource source;
    private char ch = 0xffff;

    private int index;

    protected BaseParser(final CharSource source) {
        this.source = source;
        index = 0;
        take();
    }


    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        index++;
        return result;
    }


    protected boolean test(final char expected) {
        return ch == expected;
    }


    protected int getIndex() {
        return index - 1;
    }

    protected boolean ws() {
        if (Character.isWhitespace(ch)) {
            take();
            return true;
        }
        return false;
    }


    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }


    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }


    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }


    protected boolean eof() {
        return take(END);
    }


    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }


    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }


    protected char getNext() {
        return ch;
    }
}