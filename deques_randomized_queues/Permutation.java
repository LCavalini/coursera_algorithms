import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

  public static void main(String[] args) {

    RandomizedQueue<String> queue = new RandomizedQueue<String>();

    if (args.length > 0) {

      while (!StdIn.isEmpty()) {

        String item = StdIn.readString();
        queue.enqueue(item);

      }

      for (int i = 0; i < Integer.parseInt(args[0]); i++) {

        StdOut.println(queue.dequeue());

      }

    } 

  }

}
