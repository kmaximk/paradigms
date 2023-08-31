package queue;
//Model a[1] .. a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
//immutable(n) : i = 1..n: a'[i] == a[i]

public interface Queue {
    //Pred: element != null
    void enqueue(Object element);
    //Post: n' = n + 1 & immutable(n) & a[n'] = element

    //Pred: n > 0
    Object element();
    //Post: R == a[1] && n' == n && immutable(n)

    //Pred: true
    boolean isEmpty();
    //Post: R = (n == 0) && n' = n && immutable(n)

    //Pred: true
    int size();
    //Post: R = n && n' == n && immutable(n)

    //Pred n >= 1
    Object dequeue();
    //Post n' = n - 1 && R = a[1]
    //forall i <= n - 1: a'[i] = a[i + 1]

    //Pred: true
    void clear();
    //Post: n' = 0

    //Pred: element != null
    int count(Object element);
    //Post: R == cnt, где a[i1] = a[i2] = ... = a[cnt] = element, a для других i не принадлежащих {i1 .. cnt} a[i] != element & immutable(n') & n' == n,


}
