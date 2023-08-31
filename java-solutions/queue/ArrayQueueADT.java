package queue;

import java.util.Arrays;
import java.util.Objects;

//Model a[1] .. a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
//immutable(n) : i = 1..n: a'[i] == a[i]

public class ArrayQueueADT {
    private int head = 9;

    private int tail = 0;

    private Object[] elements = new Object[10];

    public ArrayQueueADT() {
    }


    //Pred: element != null && queue != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
        increaseSize(queue);
    }
    //Post: n' = n + 1 & immutable(n) & a[n'] = element

    //Pred: queue != null && n > 0
    public static Object element(ArrayQueueADT queue) {
        return queue.elements[(queue.head + 1) % queue.elements.length];
    }
    //Post: R = a[n] & immutable(n) & n' == n


    //Pred: queue != null
    public static boolean isEmpty(ArrayQueueADT queue) {
        return (queue.head + 1) % queue.elements.length == queue.tail;
    }
    //Post: R = (n == 0) && immutable(n) & n' == n

    //Pred: queue != null
    public static int size(ArrayQueueADT queue) {
        return (queue.elements.length + queue.tail - queue.head - 1) % queue.elements.length;
    }
    //Post: R = n && n' == n & immutable(n)

    //Pred: queue != null & n > 0
    public static Object dequeue(ArrayQueueADT queue) {
        queue.head = (queue.head + 1) % queue.elements.length;
        return queue.elements[queue.head];
    }
    //Post: R = a[1] & n' = n - 1 &
    //forall i < n - 1: a'[i] = a[i + 1]

    //Pred: queue != null
    public static void clear(ArrayQueueADT queue) {
        queue.head = 9;
        queue.tail = 0;
        queue.elements = new Object[10];
    }
    //Post: n' = 0

    //Pred: queue != null
    private static void increaseSize(ArrayQueueADT queue) {
        if (queue.tail == (queue.head + 1) % queue.elements.length) {
            Object[] temp = new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, (queue.head + 1) % queue.elements.length, temp, 0, queue.elements.length - (queue.head + 1) % queue.elements.length);
            System.arraycopy(queue.elements, 0, temp, queue.elements.length - (queue.head + 1) % queue.elements.length, queue.tail);
            queue.head = 2 * queue.elements.length - 1;
            queue.tail = queue.elements.length;
            queue.elements = temp;
        }
    }
    //Post: immutable(n) & n' == n

    //Pred: n > 0 & queue != null
    public static Object remove(ArrayQueueADT queue) {
        queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
        return queue.elements[queue.tail];
    }
    //Post: n' = n - 1 & immutable(n') & R = a[n]

    //Pred: n > 0 & queue != null
    public static Object peek(ArrayQueueADT queue) {
        return queue.elements[(queue.tail + queue.elements.length - 1) % queue.elements.length];
    }
    //Post: R = a[n] && immutable(n) & n' == n

    //Pred: element != null & queue != null
    public static void push(ArrayQueueADT queue, Object element) {
        queue.elements[queue.head] = element;
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        increaseSize(queue);
    }
    //Post: n' = n + 1 & a'[1] = element & forall 1 < i <= n: a'[i] = a[i - 1]

    //Pred: queue != null
    public static String toStr(ArrayQueueADT queue) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = (queue.head + 1) % queue.elements.length; i != queue.tail; i = (i + 1) % queue.elements.length) {
            if (i == (queue.head + 1) % queue.elements.length) {
                sb.append(queue.elements[i]);
            } else {
                sb.append(", ").append(queue.elements[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }
    //Post: immutable(n) & n' = n & R = "[a[1], a[2], .. a[n]]"
}
