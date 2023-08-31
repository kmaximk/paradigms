package search;


public class BinarySearchShift {
    // Pred: существует индекс i такой, что при i1 > i2 >= i -> a[i2] > a[i1], и при i1 < i2 < i -> a[i1] > a[i2].
    // и i1 < i <= i2 -> a[i1] < a[i2] <-> i1 < i <= i2 -> a[i1] < a[a.length -1] & a[i2] >= a[a.length - 1]
    // val = a[a.length - 1]
    public static int iterative(int[] a, int val) {
        // P1: Pred
        int l = 0;
        // Q1: Pred & l == 0
        // P2: Pred & l == 0
        int r = a.length;
        // Q2: r >= 0 & l == 0 & Pred & r >= l
        // I: Pred & будем считать что a[-1] = a[a.length - 1] - 1, a[a.length] = a[a.length - 1],
        // тогда для всех i < l a[i] < a[a.length -1], a для всех i >= r a[i] >= a[a.length - 1] & r >= l,
        while (r - l > 0) {
            // P3: I & Pred & l <= (r + l) / 2 < r
            // если (r + l) четно то 2l < r + l < 2r, иначе 2l <= r + l - 1 < 2r
            int m = (r + l) / 2;
            // Q3: l <= m < r & I & Pred
            // P4: I & l <= m < r & Pred
            if (a[m] >= val) {
                //P: a[m] >= a[a.length - 1] & Pred & I & при r > i >= m -> a[m] >= a[i] > a[r] >= a[a.length - 1] & l <= m
                r = m;
                //Q: I & Pred & r' >= l
            } else {
                //P a[m] < a[a.length - 1] & Pred & I & при  l < i < m + 1 -> a[m] <= a[i] < a[l] < a[a.length - 1] & m + 1 <= r
                l = m + 1;
                //Q: I & Pred & r >= l'
            }
            //Q4: I & Pred & r >= l
        }
        /*
        Q: r >= l & r <= l & I & Pred
           l == r, i >= r -> a[i] >= a[a.length - 1], i < r -> a[i] < a[a.length - 1]
           -> i это граница и величина циклического сдвига
        */
        return r;
    }
    // Post: R == i, где r индекс максимума в массиве и значит величина циклического сдвига

    // I: Будем считать что a[-1] = a[a.length - 1] - 1, a[a.length] = a[a.length - 1],
    // тогда для всех i < l a[i] < a[a.length -1], a для всех i >= r a[i] >= a[a.length - 1] & r >= l,
    // Pred: I & существует индекс i такой, что при i1 > i2 >= i -> a[i2] > a[i1], и при i1 < i2 < i -> a[i1] > a[i2].
    // и i1 < i <= i2 -> a[i1] < a[i2] <-> i1 >= i > i2 a[i1] >= a[a.length - 1], а a[i2] < a[a.length - 1]
    public static int recursive(int[] a, int val, int l, int r) {
        //P1: Pred & I & r >= l
        if (r - l <= 0) {
            //P: I & Pred & r == l ->
            // i2 < l == r <= i1, a[i1] >= a[a.length - 1], a[i2] < a[a.length - 1]
            // -> r это индекс первого элемента второй половины массива
            return r;
            //Q: Post
        }
        //Q1: Pred & r > l
        //P2: Pred & l <= (l + r) / 2 < r
        // если (r + l) четно то 2l < r + l < 2r, иначе 2l <= r + l - 1 < 2r
        int m = (l + r) / 2;
        //Q2: Pred & l <= m < r
        //P3: Pred & l <= m < r
        if (a[m] >= val) {
            //P: Pred & m => l & a[m] >= val, при r > i >= m -> a[m] >= a[i] > a[r] > val
            // -> Pred & I
            return recursive(a, val, l, m);
        } else {
            //P: Pred & m + 1 <= r & a[m] < val, при l <= i < m + 1 -> val > a[l] >= a[i] > a[m]
            // -> Pred & I
            return recursive(a, val, m + 1, r);
        }
    }

    //Pred: args[0]... args[args.length - 1] массив полученный циклическим сдвигом отсортированного по убыванию
    public static void main(String[] args) {
        int[] a = new int[args.length];
        //P: true
        int sum = 0;
        //Q: sum == 0
        //P: sum == 0
        int i = 0;
        //Q: i == 0 & sum == 0
        //I: sum = a[0] + a[1] + .. + a[i - 1] & i <= args.length
        while (i < args.length) {
            a[i] = Integer.parseInt(args[i]);
            //P: I
            sum += a[i];
            //Q: sum' = sum + a[i]
            //P: sum = a[0] + a[1] + .. a[i]
            i++;
            //Q: i' = i + 1 & sum = a[0] + a[1] + .. + a[i - 1] -> I
        }
        //Q: sum = a[0] + a[1] + .. + a[args.length - 1]
        //P: sum = a[0] + a[0] + .. + a[args.length - 1]
        if (sum % 2 == 0) {
            System.out.println(recursive(a, a[a.length - 1], 0, a.length));
        } else {
            System.out.println(iterative(a, a[a.length - 1]));
        }
        //Post: выводит величину циклического сдвига
    }
    //Post: Выводит k, где k величина циклического сдвига

}
