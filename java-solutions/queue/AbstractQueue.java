package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    //Pred: element != null
    @Override
    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        size++;
        enqueueImpl(element);
    }
    //Post: n' = n + 1 & immutable(n) & a[n'] = element

    protected abstract void enqueueImpl(Object element);

    //Pred n >= 1
    @Override
    public Object dequeue() {
        Object element = element();
        dequeueImpl();
        size--;
        return element;
    }
    //Post n' = n - 1 && R = a[1]
    //forall i <= n - 1: a'[i] = a[i + 1]

    protected abstract void dequeueImpl();

    //Pred: true
    @Override
    public int size() {
        return size;
    }
    //Post: R = n && n' == n && immutable(n)

    //Pred: true
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    //Post: R = (n == 0) && n' = n && immutable(n)

    @Override
    public int count(Object element) {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            Object temp = dequeue();
            if (temp.equals(element)) {
                cnt++;
            }
            enqueue(temp);
        }
        return cnt;
    }

    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();
}
