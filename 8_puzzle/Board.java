import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

      this.n = tiles.length;
      this.tiles = new int[this.n][this.n]; 

      for (int i = 0; i < this.n; i++)
        for (int j = 0; j < this.n; j++)
          this.tiles[i][j] = tiles[i][j];


    }
                                           
    // string representation of this board
    public String toString() {

      String s;

      s = String.valueOf(this.n);
      s += "\n";

      for (int i = 0; i < this.n; i++)  {

        for (int j = 0; j < this.n; j++)  {

          s += " ";
          s += String.valueOf(tiles[i][j]);
 
        }

        s += "\n";

      }

      return s;

    }

    // board dimension n
    public int dimension() {

      return tiles.length;

    }

    // number of tiles out of place
    public int hamming() {

      int h = 0;

      for (int i = 0; i < this.n; i++) {

        for (int j = 0; j < this.n; j++) {

          if (this.tiles[i][j] != (i*this.n)+j+1) {

            h++;

          }          

        }

      }

      return --h;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

      int m = 0;

      for (int i = 0; i < this.n; i++) {

        for (int j = 0; j < this.n; j++) {

          if (this.tiles[i][j] != 0) {
            
            int gi = (this.tiles[i][j] - 1) / n;
            int gj = this.tiles[i][j] - gi*n - 1;
            m += Math.abs(i - gi) + Math.abs(j - gj);

          }

        }

      }

      return m;

    }

    // is this board the goal board?
    public boolean isGoal() {

      return hamming() == 0;

    }

    // does this board equal y?
    public boolean equals(Object y) {

      if (this == y) return true;
      if (y == null) return false;
      if (this.getClass() != y.getClass()) return false;
      
      Board that = (Board) y;

      if (this.dimension() != that.dimension()) return false;

      for (int i = 0; i < this.dimension(); i++) {

        for (int j = 0; j < this.dimension(); j++) {

          if (this.tiles[i][j] != that.tiles[i][j]) return false;

        }

      }

      return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

      Stack<Board> nb = new Stack<Board>();
      int bi = 0, bj = 0; // blank i and blank j positions
      int[][][] nbTiles = new int[4][n][n]; // 4 neighbors at most

      for (int i = 0; i < n; i++) {

        for (int j = 0; j < n; j++) {

          if (this.tiles[i][j] == 0) {

            bi = i;
            bj = j;

          }

          nbTiles[0][i][j] = this.tiles[i][j];
          nbTiles[1][i][j] = this.tiles[i][j];
          nbTiles[2][i][j] = this.tiles[i][j];
          nbTiles[3][i][j] = this.tiles[i][j];

        }

      }


      if (bi > 0) {

        nbTiles[0][bi][bj] = nbTiles[0][bi-1][bj]; // top neighbor
        nbTiles[0][bi-1][bj] = 0;

        nb.push(new Board(nbTiles[0]));

      }

      if (bi < n-1) {

        nbTiles[1][bi][bj] = nbTiles[1][bi+1][bj]; // bottom neighbor
        nbTiles[1][bi+1][bj] = 0;
        
        nb.push(new Board(nbTiles[1]));

      }

      if (bj > 0) {

        nbTiles[2][bi][bj] = nbTiles[2][bi][bj-1]; // left neighbor
        nbTiles[2][bi][bj-1] = 0;
        
        nb.push(new Board(nbTiles[2]));

      }

      if (bj < n-1) {

        nbTiles[3][bi][bj] = nbTiles[3][bi][bj+1]; // right neighbor
        nbTiles[3][bi][bj+1] = 0;
  
        nb.push(new Board(nbTiles[3]));

      }
        
      return nb; 

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

      int[][] twinTiles = new int[n][n];
      int t0i = -1, t0j = -1, t1i = -1, t1j = -1;
      int temp; 
      Board twinBoard;

      for (int i = 0; i < n; i++) {

        for (int j = 0; j < n; j++) {

          twinTiles[i][j] = this.tiles[i][j];

          if (this.tiles[i][j] != 0 && t0i < 0) {

            t0i = i;
            t0j = j;

          } else if (this.tiles[i][j] != 0 && t1i < 0) {

            t1i = i;
            t1j = j;

          }

        }

      }

      temp = twinTiles[t0i][t0j];
      twinTiles[t0i][t0j] = twinTiles[t1i][t1j];
      twinTiles[t1i][t1j] = temp;

      twinBoard = new Board(twinTiles);

      return twinBoard;

    }

    // unit testing (not graded)
    public static void main(String[] args) {

      int[][] tiles = { {8, 0, 3}, {4, 1, 2}, {7, 6, 5} };
      int[][] tiles2 = { {8, 1, 2}, {4, 0, 3}, {7, 6, 5} };

      Board board = new Board(tiles);
      Board board2 = new Board(tiles2);

      StdOut.println(board.toString()); 
      StdOut.println(board.hamming());
      StdOut.println(board.manhattan());
      StdOut.println(board.isGoal());
      StdOut.println(board.equals(board2));

      Iterable<Board> neighbors = board.neighbors();
  
      for (Board nb : neighbors)
        StdOut.println(nb.toString());

      StdOut.println(board.twin().toString());

    }

}
