import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

  private static class Node {

    private Point2D p;
    private RectHV rect;
    private Node lb;      // left-bottom
    private Node rt;      // right-top

  }

  private Node root;
  private int size;

  // construct an empty set of points 
  public KdTree() {
   
    this.size = 0;

  }

  // is the set empty?   
  public boolean isEmpty() {

    return this.size == 0;

  }

  // number of points in the set 
  public int size() {

    return this.size;

  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {

    if (p == null) throw new IllegalArgumentException();

    if (this.isEmpty()) {
    
      this.root = new Node();
      this.root.p = new Point2D(p.x(), p.y());
      this.root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
      this.root.lb = null;
      this.root.rt = null;
      size++;

    }

    else {

      Node currNode = this.root;
      int step = 0;
      Node newNode = new Node();
      newNode.p = new Point2D(p.x(), p.y());
      newNode.lb = null;
      newNode.rt = null;

      while (true) {
     
        if (p.equals(currNode.p)) break;

        if (step % 2 == 0) {

          if (p.x() < currNode.p.x()) {
          
            if (currNode.lb == null) {
              
              currNode.lb = newNode;
              newNode.rect = new RectHV(currNode.rect.xmin(),
                                         currNode.rect.ymin(),
                                         currNode.p.x(),
                                         currNode.rect.ymax());
              size++;
              break;

            } else currNode = currNode.lb;

          } else {
                  
            if (currNode.rt == null) {
                    
              currNode.rt = newNode;
              newNode.rect = new RectHV(currNode.p.x(),
                                         currNode.rect.ymin(),
                                         currNode.rect.xmax(),
                                         currNode.rect.ymax());
              size++;
              break;

            } else currNode = currNode.rt;

          }

        }

        else {
    
          if (p.y() < currNode.p.y()) {
          
            if (currNode.lb == null) {
            
              currNode.lb = newNode;
              newNode.rect = new RectHV(currNode.rect.xmin(),
                                         currNode.rect.ymin(),
                                         currNode.rect.xmax(),
                                         currNode.p.y());
              size++;
              break;

            } else currNode = currNode.lb;

          } else {
                  
            if (currNode.rt == null) {
                    
              currNode.rt = newNode;
              newNode.rect = new RectHV(currNode.rect.xmin(),
                                         currNode.p.y(),
                                         currNode.rect.xmax(),
                                         currNode.rect.ymax());
              size++;
              break;

            } else currNode = currNode.rt;

          }

        }

        step++;

      }

    } 

  }

  // does the set contain point p? 
  public boolean contains(Point2D p) {

    Node currNode = this.root;
    int step = 0;
    boolean contained = false;

    if (p == null) throw new IllegalArgumentException();

    while (currNode != null) {
     
      if (p.equals(currNode.p)) {

        contained = true;
        break;

      }

      if (step % 2 == 0) {

        if (p.x() < currNode.p.x()) currNode = currNode.lb;
        else currNode = currNode.rt;

    
       } else {

        if (p.y() < currNode.p.y()) currNode = currNode.lb;
        else currNode = currNode.rt;
                  
      }

      step++;

    }

    return contained;

  }
  
  // draw all points to standard draw 
  public void draw() {

    if (!this.isEmpty()) draw(this.root, true);

  }

  private void draw(Node n, boolean vertical) {

    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.point(n.p.x(), n.p.y());

    if (vertical) {

      StdDraw.setPenRadius();
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());

     } else {

      StdDraw.setPenRadius();
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

    }

    if (n.lb != null) draw(n.lb, !vertical);
    if (n.rt != null) draw(n.rt, !vertical);

  }

  // all points that are inside the rectangle (or on the boundary) 
  public Iterable<Point2D> range(RectHV rect) {

    Stack<Point2D> pointStack = new Stack<Point2D>();
    
    if (rect == null) throw new IllegalArgumentException();
    if (this.isEmpty()) return pointStack;

    return range(this.root, pointStack, rect);

  }

  private Iterable<Point2D> range(Node n, Stack<Point2D> pointStack, RectHV rect) {

    if (n.rect.intersects(rect)) {
      
      if (rect.contains(n.p)) pointStack.push(n.p);

      if (n.lb != null) range(n.lb, pointStack, rect);
      if (n.rt != null) range(n.rt, pointStack, rect);

    }

    return pointStack;

  }

  // a nearest neighbor in the set to point p; null if the set is empty 
  public Point2D nearest(Point2D p) {

    if (p == null) throw new IllegalArgumentException();
    if (this.isEmpty()) return null;

    return nearest(this.root, p, this.root.p, true);

  }

  private Point2D nearest(Node n, Point2D p, Point2D minp, boolean vertical) {

    if (n.rect.distanceSquaredTo(p) < minp.distanceSquaredTo(p)) {

      minp = n.p;

      if (vertical) {

        if (p.x() < n.p.x() && n.lb != null) minp = nearest(n.lb, p, minp, !vertical);
        else if (n.rt != null) minp = nearest(n.rt, p, minp, !vertical);

      } else {

        if (p.y() < n.p.y() && n.lb != null) minp = nearest(n.lb, p, minp, !vertical);
        else if (n.rt != null) minp = nearest(n.rt, p, minp, !vertical);

      }

    }

    return minp;

  }

  // unit testing of the methods (optional) 
  public static void main(String[] args) {

    KdTree tree = new KdTree();
    StdOut.printf("Está vazia? %b\n", tree.isEmpty());
    tree.insert(new Point2D(0.2, 0.3));
    tree.insert(new Point2D(0.4, 0.5));
    tree.insert(new Point2D(0.1, 0.2));
    StdOut.printf("Qual é o tamanho? %d\n", tree.size());
    StdOut.printf("O ponto (0.4, 0.5) está na árvore? %b\n",
                  tree.contains(new Point2D(0.4, 0.5)));
    StdOut.printf("O ponto (0.6, 0.7) está na árvore? %b\n",
                  tree.contains(new Point2D(0.6, 0.7)));
    StdOut.printf("Pontos dentro do retângulo (0.1, 0.1, 0.5, 0.4): ");
    for (Point2D p : tree.range(new RectHV(0.1, 0.1, 0.5, 0.4))) StdOut.printf("%s ", p.toString());
    StdOut.printf("\nPonto mais próximo de (0.6, 0.5): %s\n", tree.nearest(new Point2D(0.6, 0.5)));
    StdOut.printf("Ponto mais próximo de (0.1, 0.2): %s\n", tree.nearest(new Point2D(0.1, 0.2)));
    tree.draw();

  }          

}

