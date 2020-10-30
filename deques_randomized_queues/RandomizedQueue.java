import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
  // Queue size
  private int head, tail;
  private Item[] queue; 

  // construct an empty randomized queue
  public RandomizedQueue() {

    head = 0;
    tail = 0;
    queue = (Item[]) new Object[1];

  }

  // is the randomized queue empty?
  public boolean isEmpty() {

    return size() == 0;

  }

  // return the number of items on the randomized queue
  public int size() {

    return tail - head;

  }

  // resizing queue array
  private void resize(int size) {

    Item[] oldQueue = queue;
    queue = (Item[]) new Object[size];
      
    for (int i = 0; i < oldQueue.length; i++) {

      queue[i] = oldQueue[i];

    }

  }

  // add the item
  public void enqueue(Item item) {

    if (item == null) throw new IllegalArgumentException();

    if (tail == queue.length) resize(queue.length*2);

    queue[tail++] = item; 

  }

  // remove and return a random item
  public Item dequeue() {

    if (isEmpty()) throw new NoSuchElementException();

    if (tail <= 0.25 * queue.length) resize(queue.length/2);

    StdRandom.shuffle(queue, head, tail);

    Item item = queue[head];
    queue[head++] = null;
      
    return item;
 
  }

  // return a random item (but do not remove it)
  public Item sample() {

    if (isEmpty()) throw new NoSuchElementException();
      
    int pos = StdRandom.uniform(head, tail);
      
    return queue[pos];

  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() { return new ListIterator(); }

  private class ListIterator implements Iterator<Item> {

    int current = head;

    public boolean hasNext() {

      return current != tail;     

    }

    public Item next() {

      if (!hasNext()) throw new NoSuchElementException();

      StdRandom.shuffle(queue, current, tail);

      return queue[current++];

    }

    public void remove() {

      throw new UnsupportedOperationException();

    }

  }

   // unit testing (required)
  public static void main(String[] args) {

    RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

    Integer v = 1;
    queue.enqueue(v);
    Integer n = queue.size();
    StdOut.printf("enqueue, value: %d, size: %d\n", v, n);

    v = 2;
    queue.enqueue(v);
    n = queue.size();
    StdOut.printf("enqueue, value: %d, size: %d\n", v, n);  

    v = queue.sample();
    n = queue.size();
    StdOut.printf("sample, value: %d, size: %d\n", v, n); 

    v = queue.dequeue();
    n = queue.size();
    StdOut.printf("dequeue, value: %d, size: %d\n", v, n);

    v = queue.dequeue();
    n = queue.size();
    StdOut.printf("dequeue, value: %d, size: %d\n", v, n);

    StdOut.printf("Is empty? %b\n", queue.isEmpty());

    StdOut.printf("Iterator: it enqueues 0 to 9 and dequeues them randomly\n"); 
      
    for (int i = 0; i < 10; i++) queue.enqueue(i);
    for (Integer i : queue) StdOut.printf("%d ", i);

    StdOut.printf("\nIs empty? %b", queue.isEmpty());

  }

}
