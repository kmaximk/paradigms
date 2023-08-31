package expression.generic;

public class Main {
    public static void main(String[] args) throws Exception {
        GenericTabulator tab = new GenericTabulator();
        try {
            Object[][][] res = tab.tabulate(args[0].substring(1), args[1], -2, 2, -2, 2, -2, 2);
            for (int i = 0; i < res.length; i++) {
                for (int j = 0; j < res.length; j++) {
                    for (int k = 0; k < res.length; k++) {
                        System.out.print(res[i][j][k] + " ");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
