import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
   
  private final SET<Point2D> pointSet;

  // construct an empty set of points
  public PointSET() {
   
    this.pointSet = new SET<Point2D>();  

  }

  // is the set empty? 
  public boolean isEmpty() {

    return this.pointSet.isEmpty();
 
  }

  // number of points in the set 
  public int size() {

    return this.pointSet.size();

  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {

    if (p == null) throw new IllegalArgumentException();

    this.pointSet.add(p);

  }

  // does the set contain point p? 
  public boolean contains(Point2D p) {

    if (p == null) throw new IllegalArgumentException();

    return this.pointSet.contains(p);

  }

  // draw all points to standard draw 
  public void draw() {

    for (Point2D point : pointSet) point.draw();

  }

  // all points that are inside the rectangle (or on the boundary) 
  public Iterable<Point2D> range(RectHV rect) {

    SET<Point2D> pointsInsideRect = new SET<Point2D>();

    if (rect == null) throw new IllegalArgumentException();

    for (Point2D point : pointSet) if (rect.contains(point)) pointsInsideRect.add(point);

    return pointsInsideRect;

  }          
 
  // a nearest neighbor in the set to point p; null if the set is empty 
  public Point2D nearest(Point2D p) {

    double minDist = 0.0;
    double xmin = 0;
    double ymin = 0;
    Point2D nearestPoint;

    if (p == null) throw new IllegalArgumentException();

    for (Point2D point : this.pointSet) {

      double dist = p.distanceSquaredTo(point);

      if (minDist == 0 && dist > 0) minDist = dist;

      if (dist > 0 && dist < minDist) {

        minDist = dist;
        xmin = point.x();
        ymin = point.y();

      }

    }

    nearestPoint = minDist > 0 ? new Point2D(xmin, ymin) : null;

    return nearestPoint;

  }          

  // unit testing of the methods (optional) 
  public static void main(String[] args)  {

    PointSET ps = new PointSET();
    Point2D p1 = new Point2D(0.1, 0.5);
    Point2D p2 = new Point2D(0.2, 0.5);
    Point2D nearestPoint;
    String result;
    RectHV rect = new RectHV(0, 0, 0.3, 0.6); 
    ps.insert(p1);
    ps.insert(p2);
    StdOut.println(ps.size());
    ps.draw();
    for (Point2D p : ps.range(rect)) StdOut.println(p.toString());
    nearestPoint = ps.nearest(new Point2D(0.3, 0.5));
    result = nearestPoint != null ? nearestPoint.toString() : "não tem";
    StdOut.printf("Ponto mais próximo de (0.3, 0.5): %s", result);

  }

}
