import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Solver {

  private class SearchNode implements Comparable<SearchNode> {

    private final Board state;
    private final SearchNode parent;
    private final int actionCost;

    public SearchNode(Board state, SearchNode parent, int actionCost) {

      this.state = state;
      this.parent = parent;
      this.actionCost = actionCost;

    }

    public SearchNode parent() {

      return this.parent;

    }

    public Board state() {

      return this.state;

    }

    public int actionCost() {

      return this.actionCost;

    }

    public int totalCost() {

      return this.actionCost() + this.state.manhattan();

    }

    public Iterable<Board> nextNodes() {

      return state.neighbors();

    } 

    public boolean isGoal() {

      return state.isGoal();

    }

    public int compareTo(SearchNode that) {

      return this.totalCost() - that.totalCost();

    }

    public Board twin() {

      return state.twin();

    }
    
    public boolean equals(Object y) {

      return state.equals(y);

    }


  }
 
  private SearchNode result = null;
  private final Board initial;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {

    MinPQ<SearchNode> frontier;
    MinPQ<SearchNode> twinFrontier;
    SearchNode node;
    SearchNode twinNode;

    if (initial == null) throw new IllegalArgumentException();

    this.initial = initial;

    frontier = new MinPQ<SearchNode>();
    twinFrontier = new MinPQ<SearchNode>();
 
    node = new SearchNode(initial, null, 0);
    twinNode = new SearchNode(node.twin(), null, 0);

    frontier.insert(node);
    twinFrontier.insert(twinNode);

    while (!frontier.isEmpty() || !twinFrontier.isEmpty()) {

      node = frontier.delMin();
      twinNode = twinFrontier.delMin();
      
      if (node.isGoal()) {

        this.result = node;
        break;

      }

      if (twinNode.isGoal()) {

        this.result = null;
        break;

      }

      // insert neighbors in frontier
      for (Board n : node.nextNodes()) {

        if (node.parent() == null || !node.parent().state().equals(n)) {
          
          SearchNode nb = new SearchNode(n, node, node.actionCost()+1);
          frontier.insert(nb);

        }

      }

      for (Board n : twinNode.nextNodes()) {

        if (twinNode.parent() == null || !twinNode.parent().state().equals(n)) {
          
          SearchNode nb = new SearchNode(n, twinNode, twinNode.actionCost()+1);
          twinFrontier.insert(nb);

        }

      }  

    }

  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {

    return this.solution() != null;

  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {

    if (!this.isSolvable()) return -1;

    return this.result.actionCost();

  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {

    Stack<Board> s = new Stack<Board>();
    SearchNode node = this.result;

    if (node == null) return null;

    while (node.parent != null) {

      s.push(node.state());
      node = node.parent();

    }

    s.push(this.initial);
 
    return s;

  }

  // test client (see below) 
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];

    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
          
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
      
      else {
      
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
          StdOut.println(board);    
     }

  }

}
