package queue;
import queue.AbstractQueue;

import java.util.Arrays;
import java.util.Objects;

//Model a[1] .. a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
//immutable(n) : i = 1..n: a'[i] == a[i]
public class ArrayQueue extends AbstractQueue {
    private int head = 0;

    private Object[] elements = new Object[10];

    public ArrayQueue() {

    }


    //Pred: element != null
    @Override
    protected void enqueueImpl(Object element) {
        this.elements[(head + size - 1) % elements.length] = element;
        increaseSize();
    }
    //Post: immutable(n)

    //Pred: n > 0
    @Override
    public Object element() {
        return this.elements[this.head];
    }
    //Post: R == a[1] && n' == n && immutable(n)


    @Override
    protected void dequeueImpl() {
        this.head = (this.head + 1) % this.elements.length;
    }

    //Pred: true
    @Override
    protected void clearImpl() {
        this.head = 0;
        this.elements = new Object[10];
    }
    //Post: n' = 0

    //Pred: true
    private void increaseSize() {
        if (size == elements.length) {
            Object[] temp = new Object[this.elements.length * 2];
            System.arraycopy(this.elements, this.head, temp, 0, this.elements.length - this.head);
            System.arraycopy(this.elements, 0, temp, this.elements.length - this.head, (head + size) % elements.length);
            this.head = 0;
            this.elements = temp;
        }
    }
    //Post: immutable(n) & n' == n


    //Pred: true
    public String toStr() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = (head) % elements.length; i != (head + size) % elements.length; i = (i + 1) % elements.length) {
            if (i == (head) % elements.length) {
                sb.append(elements[i]);
            } else {
                sb.append(", ").append(elements[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }
    //Post: immutable(n) & n' = n & R = "[a[1], a[2], .. a[n]]"
}
