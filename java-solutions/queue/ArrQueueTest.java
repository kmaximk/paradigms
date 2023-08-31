package queue;

import java.util.Arrays;

public class ArrQueueTest {
    public static void push() {
        for (int i = 0; i < 15; i++) {
            ArrayQueueModule.push(i);
        }
    }
    public static void main(String[] args) {
        LinkedQueue q = new LinkedQueue();
        q.enqueue("fda");
        q.enqueue(1);
        int x = (int) q.dequeue();
        System.out.println(x);
        System.out.println(q.dequeue());
        //System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
//        for (int i = 0; i < 15; i++) {
//            //System.out.println(ArrayQueueModule.size());
//            //System.out.println(ArrayQueueModule.head);
//            System.out.println(ArrayQueueModule.tail);
//        }
//        for (int i  =0 ; i <10; i++) {
//            System.out.println(ArrayQueueModule.dequeue());
//        }
    }
}
