/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {

      if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY;
      else if (that.y == this.y) return 0.0;
      else if (that.x == this.x) return Double.POSITIVE_INFINITY;
      else return Double.valueOf(that.y - this.y) / Double.valueOf(that.x - this.x);

    }


    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        
      if (that.y == this.y) {

        if (that.x == this.x) return 0;
        else return this.x - that.x;

      } else return this.y - that.y;  

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        
      Comparator<Point> BY_SLOPE = new BySlope(); 

      return BY_SLOPE;

    }

    private class BySlope implements Comparator<Point> {

      public int compare(Point p1, Point p2) {


        double diffSlope = slopeTo(p1) - slopeTo(p2);

        if (diffSlope > 0)  return +1;
        else if (diffSlope < 0) return -1;
        else return 0;

      }

    }
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */

      StdDraw.setPenRadius(0.01);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setXscale(-1, 10);
      StdDraw.setYscale(-1, 10);

      Point p1, p2, p3, p4, p5, p6;
      p1 = new Point(1, 5);
      p2 = new Point(0, 5);
      p3 = new Point(2, 5);
      p4 = new Point(1, 5);
      p5 = new Point(1, 4);
      p6 = new Point(3, 4);

      p1.draw();
      p2.draw();
      p3.draw();
      p4.draw();
      p5.draw();
      p6.draw();

      StdOut.printf("p1: %s\n", p1.toString());
      StdOut.printf("p2: %s\n", p2.toString());
      StdOut.printf("p3: %s\n", p3.toString());
      StdOut.printf("p4: %s\n", p4.toString());
      StdOut.printf("p5: %s\n", p5.toString());
      StdOut.printf("p6: %s\n", p6.toString());
      
      StdOut.println("== compareTo ==");
      StdOut.printf("p1 and p2: %d\n", p1.compareTo(p2));
      StdOut.printf("p1 and p3: %d\n", p1.compareTo(p3));
      StdOut.printf("p1 and p4: %d\n", p1.compareTo(p4));
      StdOut.printf("p1 and p5: %d\n", p1.compareTo(p5));
  
      StdOut.println("== slopeTo ==");
      StdOut.printf("p1 to p2: %f\n", p1.slopeTo(p2));      
      StdOut.printf("p1 to p3: %f\n", p1.slopeTo(p3));      
      StdOut.printf("p1 to p4: %f\n", p1.slopeTo(p4));      
      StdOut.printf("p1 to p5: %f\n", p1.slopeTo(p5));      
      StdOut.printf("p1 to p6: %f\n", p1.slopeTo(p6));

      StdOut.println("== slopeOrder ==");
      Point[] points = {p1, p2, p3, p4, p5, p6};
      Arrays.sort(points, p1.slopeOrder());
      
      for (Point p : points) StdOut.println(p.toString());


    }
}
