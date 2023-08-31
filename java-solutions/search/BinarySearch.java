package search;

public class BinarySearch {
    // пусть R это ответ, тогда i1 > i2 > R -> a[i1
    // Pred: a.length >= 0 & i1 > i2 -> a[i1] <= a[i2]
    public static int iterative(int[] a, int val) {
        // P1: Pred
        int l = 0;
        // Q1: Pred & l == 0
        // P2: Pred & l == 0
        int r = a.length;
        // Q2: r >= 0 & l == 0 & Pred & r >= l
        // I: Pred & будем считать что a[-1] = +inf, a[a.length] = -inf,
        // тогда для всех i < l a[i] > val, a для всех i >= r a[i] <= val & r >= l
        while (r - l > 0) {
            // P3: I & Pred & l <= (r + l) / 2 < r
            // если (r + l) четно то 2l < r + l < 2r, иначе 2l <= r + l - 1 < 2r
            int m = (r + l) / 2;
            // Q3: l <= m < r & I & Pred
            // P4: I & l <= m < r & Pred
            if (a[m] <= val) {
                //P: a[m] <= val & Pred & I & при i >= m -> a[i] <= a[m] <= val & l <= m
                r = m;
                //Q: I & Pred & r >= l
            } else {
                //P a[m] > val & Pred & I & при i < m + 1 -> a[i] >= a[m] > val & m + 1 <= r
                l = m + 1;
                //Q: I & Pred & r >= l
            }
            //Q4: I & Pred & r >= l
        }
        /*
        Q: r >= l & r <= l & I & Pred
           i1 < l == r <= i2, i >= i2 -> a[i] <= val, i < i1 -> a[i] > val
        */
        return r;
    }
    // Post: R == i, где a[i] <= val и i минимально

    // Pred: i1 > i2 -> a[i1] <= a[i2] & r => l &
    // будем считать что a[-1] = +inf, a[a.length] = -inf,
    // тогда для всех i < l a[i] > val, a для всех i >= r a[i] <= val
    public static int recursive(int[] a, int val, int l, int r) {
        //P1: Pred & r >= l
        if (r - l <= 0) {
            //P: Pred & r == l ->
            // i2 < l == r <= i1, i >= i1 -> a[i] <= val, i < i2 -> a[i] > val
            return r;
            //Q: Post
        }
        //Q1: Pred & r > l
        //P2: Pred & l <= (l + r) / 2 < r
        // если (r + l) четно то 2l < r + l < 2r, иначе 2l <= r + l - 1 < 2r
        int m = (l + r) / 2;
        //Q2: Pred & l <= m < r
        //P3: Pred & l <= m < r
        if (a[m] <= val) {
            //P: m => l & i1 > i2 -> a[i1] <= a[i2] & a[m] <= val, при i > m -> a[i] <= a[m] <= val
            // -> Pred
            return recursive(a, val, l, m);
        } else {
            //P: m + 1 <= r & i1 > i2 -> a[i1] <= a[i2] & a[m] > val, при i < m + 1 -> a[i] >= a[m] > val
            // -> Pred
            return recursive(a, val, m + 1, r);
        }
    }
    //Post: R == i, где a[i] <= val и i минимально
    //Pred: args[1]... args[args.length - 1] отсортирован по невозрастанию, массив целых чисел
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        int sum = 0;
        for (int i = 0; i < args.length - 1; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
            sum += a[i];
        }
        if (sum % 2 == 0) {
            System.out.println(recursive(a, x, 0, a.length));
        } else {
            System.out.println(iterative(a, x));
        }
    }
    //Post: Выводит i, где a[i] <= val и i минимально
}