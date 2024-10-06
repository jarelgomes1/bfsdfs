package visualization;

import java.awt.*;

public class Edge {
    private Node source;
    private Node destination;
    private String label;
    private int weight;
    private boolean isDirected;

    public Edge(Node source, Node destination, String label, int weight, boolean isDirected) {
        this.source = source;
        this.destination = destination;
        this.label = label;
        this.weight = weight;
        this.isDirected = isDirected;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public String getLabel() {
        return label;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawLine(source.getX(), source.getY(), destination.getX(), destination.getY());
        if (!label.isEmpty()) {
            g2d.drawString(label, (source.getX() + destination.getX()) / 2, (source.getY() + destination.getY()) / 2);
        }
        if (weight != 0) {
            g2d.drawString(String.valueOf(weight), (source.getX() + destination.getX()) / 2,
                    (source.getY() + destination.getY()) / 2 + 10);
        }
        if (isDirected) {
            drawArrow(g2d, source.getX(), source.getY(), destination.getX(), destination.getY());
        }
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - 10, xn = xm, ym = 5, yn = -5, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = { x2, (int) xm, (int) xn };
        int[] ypoints = { y2, (int) ym, (int) yn };

        g2d.fillPolygon(xpoints, ypoints, 3);
    }
}
