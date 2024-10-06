package visualization;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private int x;
    private int y;
    private String label;
    private int id;
    private List<Edge> edges;

    public Node(int x, int y, String label, int id) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.id = id;
        this.edges = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public boolean contains(Point point) {
        return Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2) <= Math.pow(20 / 2.0, 2); // 20 is the
                                                                                                       // diameter
    }

    public void draw(Graphics2D g2d) {
        g2d.fillOval(x - 10, y - 10, 20, 20); // 20 is the diameter
        g2d.setColor(Color.WHITE);
        g2d.drawString(label, x - 5, y + 5); // Adjust the position as needed
        g2d.setColor(Color.BLACK);
    }
}
