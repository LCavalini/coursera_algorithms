import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {

  private int n;
  private final LineSegment[] lines;

  public BruteCollinearPoints(Point[] points) {

    if (points == null) throw new IllegalArgumentException();

    n = 0;
    lines = new LineSegment[points.length];

    Point p, q, r, s;
    double pq, pr, ps;
    
    for (int i = 0; i < points.length; i++) {

      if (points[i] == null) throw new IllegalArgumentException();

      p = points[i];

      for (int j = i+1; j < points.length; j++) {

        if (points[j] == null) throw new IllegalArgumentException();

        q = points[j];

        if (p.compareTo(q) == 0) throw new IllegalArgumentException();

        for (int k = j+1; k < points.length; k++) {

          if (points[k] == null) throw new IllegalArgumentException();

          r = points[k];

          if (p.compareTo(r) == 0 || q.compareTo(r) == 0)
            throw new IllegalArgumentException();

          for (int l = k+1; l < points.length; l++) {

            if (points[l] == null) throw new IllegalArgumentException();

            s = points[l];

            if (p.compareTo(s) == 0 || q.compareTo(s) == 0 || r.compareTo(s) ==
0) throw new IllegalArgumentException();
        
            pq = p.slopeTo(q);
            pr = p.slopeTo(r);
            ps = p.slopeTo(s);


            if (pq == Double.NEGATIVE_INFINITY || pr == Double.NEGATIVE_INFINITY || ps == Double.NEGATIVE_INFINITY)
              throw new IllegalArgumentException();

            if (pq == pr && pq == ps) {

              Point min = p;
              Point max = q;

              if (p.compareTo(q) > 0) {

                min = q;
                max = p;

              }

              if (min.compareTo(r) > 0) min = r;

              if (max.compareTo(r) < 0) max = r;

              if (min.compareTo(s) > 0) min = s;

              if (max.compareTo(s) < 0) max = s;

              lines[n] = new LineSegment(min, max);
              n++;

            }

          }

        }
      
      }

    }

  }

  public int numberOfSegments() {

    return n;

  }

  public LineSegment[] segments() {

    LineSegment[] s = new LineSegment[n];

    for (int i = 0; i < n; i++) s[i] = lines[i];
    
    return s;

  }

  public static void main(String[] args) {


      // read the n points from a file
      In in = new In(args[0]);
      int n = in.readInt();
      Point[] points = new Point[n];
      for (int i = 0; i < n; i++) {
          int x = in.readInt();
          int y = in.readInt();
          points[i] = new Point(x, y);

          if (points[i] == null) throw new IllegalArgumentException();

      }


      // draw the points
      StdDraw.enableDoubleBuffering();
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : points) {
          p.draw();
      }
      StdDraw.show();

      // print and draw the line segments
      BruteCollinearPoints collinear = new BruteCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
          StdOut.println(segment);
          segment.draw();
      }

      StdDraw.show();
  }

}
