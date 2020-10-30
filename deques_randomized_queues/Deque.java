import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  // Node class implemented as a double linked list
  private class Node<Item> {

    Item item;
    Node<Item> next;
    Node<Item> prev;

  }

  // first and last nodes
  private Node<Item> first;
  private Node<Item> last;

  // number of items
  private int n;

  // construct an empty deque
  public Deque() {

    first = null;
    last = first;     

    n = 0;

  }

  // is the deque empty?
  public boolean isEmpty() {

    return size() == 0;

  }

  // return the number of items on the deque
  public int size() {

    return n;

  }

  // add the item to the front
  public void addFirst(Item item) {

    if (item == null) throw new IllegalArgumentException();

    Node<Item> oldFirst = first;
      
    first = new Node<Item>();
    first.item = item;
    first.next = oldFirst;
    first.prev = null;

    if (oldFirst != null) oldFirst.prev = first;
    else last = first;

    n++;

  }

  // add the item to the back
  public void addLast(Item item) {

    if (item == null) throw new IllegalArgumentException();

    Node<Item> oldLast = last;

    last = new Node<Item>();
    last.item = item;
    last.next = null;
    last.prev = oldLast;

    if (oldLast != null) oldLast.next = last;
    else first = last;

    n++;

  }

  // remove and return the item from the front
  public Item removeFirst() {

    if (isEmpty()) throw new NoSuchElementException();

    Item item = first.item;
    first = first.next;
    n--;
  
    if (!isEmpty()) first.prev = null;
    else last = first;

    return item;

  }

  // remove and return the item from the back
  public Item removeLast() {

    if (isEmpty()) throw new NoSuchElementException();

    Item item = last.item;
    last = last.prev;
    n--;

    if (!isEmpty()) last.next = null;
    else first = last;

    return item;  

  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {  return new ListIterator(); }

  private class ListIterator implements Iterator<Item> {

    private Node<Item> current = first;

    public boolean hasNext() {

      return current != null;

    }

    public Item next() {

      if (!hasNext()) throw new NoSuchElementException();

      Item item = current.item;
      current = current.next;

      return item;

    }

    public void remove() {

      throw new UnsupportedOperationException();

    }

  }

  // unit testing (required)
  public static void main(String[] args) {

    Deque<Integer> deque = new Deque<Integer>();

    Integer d, n;

    StdOut.printf("Is Empty? %b\n", deque.isEmpty());

    d = 1;
    deque.addFirst(d);
    n = deque.size();
    StdOut.printf("addFirst, item added: %d, size: %d\n", d, n);
      
    d = 2;
    deque.addLast(d);
    n = deque.size();
    StdOut.printf("addLast, item added: %d, size: %d\n", d, n);
      
    d = deque.removeLast();
    n = deque.size();
    StdOut.printf("removeLast, item removed: %d, size: %d\n", d, n);
      
    d = deque.removeFirst();
    n  = deque.size();
    StdOut.printf("removeFirst, item removed: %d, size: %d\n", d, n);

    StdOut.println("Stack behavior (it pushes 0 to 9 and pops them):");

    for (int i = 0; i < 10; i++) deque.addFirst(i);
    
    for (Integer i : deque) {

      StdOut.printf("%d ", i);
      deque.removeFirst();

    }

    StdOut.println("\nQueue behavior (it enqueues 0 to 9 and dequeues them):");
    
    for (int i = 0; i < 10; i++) deque.addLast(i);

    for (Integer i : deque) StdOut.printf("%d ", i);

  }

}
