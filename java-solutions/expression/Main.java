package expression;


import expression.exceptions.ParseException;
import expression.generic.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        int x = -5;
        System.out.println(x + ~x + 1);
        System.out.println(425452 % Short.MAX_VALUE);
        System.out.println(Short.valueOf((short) (Integer.MAX_VALUE - 1000)));
        System.out.println(4 / -4);
        //CClass2 t2 = new CClass2();
      //  System.out.println(c.count(1, 5));
//        GenericParser prs = new GenericParser();
//        TripleParser<Number> op = prs.parse("x", "d");
//        System.out.println(op);
//        System.out.println(op.count(Double.valueOf(1), Double.valueOf(5), Double.valueOf(7)));
    }
}
