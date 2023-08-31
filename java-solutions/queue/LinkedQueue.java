package queue;

public class LinkedQueue extends AbstractQueue {


    //Model a[1] .. a[n]
// I: n >= 0 && forall i=1..n: a[i] != null
//immutable(n) : i = 1..n: a'[i] == a[i]
    private Node tail;


    @Override
    protected void enqueueImpl(Object element) {
        if (size > 1) {
            tail.prev = new Node(element, tail.prev);
            tail = tail.prev;
        } else {
            tail = new Node(element, tail);
            tail.prev = tail;
        }
    }


    //Pred: n > 0
    @Override
    public Object element() {
        return tail.prev.element;
    }
    //Post: R == a[1] && n' == n && immutable(n)


    //Pred: true
    @Override
    protected void clearImpl() {
        tail = null;
    }
    //Post: n' = 0


    @Override
    protected void dequeueImpl() {
        tail.prev = tail.prev.prev;
    }


    private static class Node {
        private final Object element;

        private Node prev;

        Node(Object element, Node prev) {
            this.element = element;
            this.prev = prev;
        }
    }
}
