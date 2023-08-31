package queue;

import java.util.Arrays;
import java.util.Objects;

//Model a[1] .. a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
//immutable(n) : i = 1..n: a'[i] == a[i]

public class ArrayQueueModule {
    public static int head = 9;

    public static int tail = 0;

    private static Object[] elements = new Object[10];

    //Pred element != null
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        increaseSize();
    }
    //Post n' = n + 1 && a[n'] == element && immutable(n)

    //Pred: n > 0
    public static Object element() {
        return elements[(head + 1) % elements.length];
    }
    //Post: R == a[1] && n' == n && immutable(n)

    //Pred: true
    public static boolean isEmpty() {
        return (head + 1) % elements.length == tail;
    }
    //Post: R = (n == 0) && n' = n && immutable(n)

    //Pred: true
    public static int size() {
        return (elements.length + tail - head - 1) % elements.length;
    }
    //Post: R = n && n' == n && immutable(n)

    //Pred n >= 1
    public static Object dequeue() {
        head = (head + 1) % elements.length;
        return elements[head];
    }
    //Post n' = n - 1 && R = a[1]
    //forall i < n - 1: a'[i] = a[i + 1]

    //Pred:
    public static void clear() {
        head = 9;
        tail = 0;
        elements = new Object[10];
    }
    //Post n' == 0

    //Pred: true
    private static void increaseSize() {
        if (tail == (head + 1) % elements.length) {
            Object[] temp = new Object[elements.length * 2];
            System.arraycopy(elements, (head + 1) % elements.length, temp, 0, elements.length - (head + 1) % elements.length);
            System.arraycopy(elements, 0, temp, elements.length - (head + 1) % elements.length, tail);
            head = 2 * elements.length - 1;
            tail = elements.length;
            elements = temp;
        }
    }
    //Post: immutable(n) && n' == n

    //Pred: n > 0
    public static Object remove() {
        tail = (tail - 1 + elements.length) % elements.length;
        return elements[tail];
    }
    //Post: n' = n - 1 & immutable(n') & R = a[n]

    //Pred: n > 0
    public static Object peek() {
        return elements[(tail + elements.length - 1) % elements.length];
    }
    //Post: R = a[n] && immutable(n) & n' == n

    //Pred: element != null
    public static void push(Object element) {
        elements[head] = element;
        head = (head - 1 + elements.length) % elements.length;
        increaseSize();
    }
    //Post: n' = n + 1 & a'[1] = element & forall 1 < i <= n: a'[i] = a[i - 1]

    //Pred: true
    public static String toStr() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = (head + 1) % elements.length; i != tail; i = (i + 1) % elements.length){
            if (i == (head + 1) % elements.length) {
                sb.append(elements[i]);
            } else {
                sb.append(", ").append(elements[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }
    //Post: immutable(n) & n' = n & R = a[1], a[2] .. a[n]
}
