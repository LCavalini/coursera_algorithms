import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class FastCollinearPoints {
  
  private int n;
  private LineSegment[] l;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    
    n = 0;
    l = new LineSegment[points.length];

    if (points == null) throw new IllegalArgumentException();
   
    Point[] orderedPoints = new Point[points.length]; 

    for (int i = 0; i < points.length; i++) orderedPoints[i] = points[i];

    Point p, q, r, s;
    double pq, pr, ps;

    if (points.length == 2 && points[0].compareTo(points[1]) == 0)
      throw new IllegalArgumentException();

    if (points.length == 3 && (points[0].compareTo(points[1]) == 0 ||
points[0].compareTo(points[2]) == 0 || points[1].compareTo(points[2]) == 0))
      throw new IllegalArgumentException();
 
    for (int i = 0; i < points.length; i++) {

      if (points[i] == null) throw new IllegalArgumentException();

      p = points[i];
      
      Arrays.sort(orderedPoints, p.slopeOrder());

      for (int j = 1; j < orderedPoints.length-2; j++) {

        if (orderedPoints[j] == null || orderedPoints[j+1] == null ||
orderedPoints[j+2] == null) throw new IllegalArgumentException();

        q = orderedPoints[j];
        r = orderedPoints[j+1];
        s = orderedPoints[j+2];

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


          LineSegment t = new LineSegment(min, max);
          boolean isNew = true;

          for (int k = 0; k < n; k++) {

            if (t.toString().equals(l[k].toString())) {

              isNew = false;
              break;

            }

          }
      
          if (isNew == true) {            
           
            l[n] = new LineSegment(min, max);
            n++;
          }

        }

      }
        
    }

  }

  // the number of line segments
  public int numberOfSegments() {

    return n;

  }

  // the line segments
  public LineSegment[] segments() {

    LineSegment[] s = new LineSegment[n];

    for (int i = 0; i < n; i++) s[i] = l[i];

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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    
    StdDraw.show();
  }

}
