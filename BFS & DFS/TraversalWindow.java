import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import visualization.Node;
import visualization.Edge;
import visualization.Graph;
import visualization.VisualizationCallback;
import search.BFS;
import search.DFS;
import search.Prim;
import search.Kruskal;
import search.FloydWarshall;

class TraversalWindow extends JFrame {
    private String traversalType;
    private List<Node> nodes;
    private List<Edge> edges;
    private int currentStep = 0;
    private List<Node> visitedNodes = new ArrayList<>();
    private List<Edge> pathEdges = new ArrayList<>();
    private VisualizationCallback callback;

    public TraversalWindow(String traversalType, List<Node> nodes, List<Edge> edges, int selectedPath) {
        this.traversalType = traversalType;
        this.nodes = nodes;
        this.edges = edges;

        setTitle(traversalType + " Visualization - Path " + selectedPath);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw edges
                g2d.setColor(Color.BLACK);
                for (Edge edge : edges) {
                    edge.draw(g2d);
                }

                // Draw nodes
                g2d.setColor(Color.BLUE);
                for (Node node : nodes) {
                    node.draw(g2d);
                }

                // Highlight the path edges
                g2d.setColor(Color.GREEN);
                for (Edge edge : pathEdges) {
                    edge.draw(g2d);
                }

                // Highlight the visited nodes
                g2d.setColor(Color.RED);
                for (Node node : visitedNodes) {
                    node.draw(g2d);
                }

                // Highlight the current step node
                if (!visitedNodes.isEmpty()) {
                    g2d.setColor(Color.ORANGE);
                    Node currentNode = visitedNodes.get(currentStep);
                    currentNode.draw(g2d);
                }
            }
        };
        add(panel);

        callback = new VisualizationCallback() {
            @Override
            public void update(Collection<Edge> edges, Set<Node> visited) {
                pathEdges.clear();
                pathEdges.addAll(edges);
                visitedNodes.clear();
                visitedNodes.addAll(visited);
                panel.repaint();
            }

            @Override
            public void visitNode(Node node) {
                visitedNodes.add(node);
                panel.repaint();
            }

            @Override
            public void floydWarshallUpdate(int[][] dist, int[][] next) {
                // Handle Floyd-Warshall updates if needed
            }
        };

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT && currentStep > 0) {
                    currentStep--;
                    panel.repaint();
                } else if (keyCode == KeyEvent.VK_RIGHT && currentStep < visitedNodes.size() - 1) {
                    currentStep++;
                    panel.repaint();
                }
            }
        });

        Graph graph = new Graph();
        for (Node node : nodes) {
            graph.addNode(node);
        }

        Node startNode = nodes.get(0); // Assuming the start node is the first node

        switch (traversalType) {
            case "DFS":
                DFS.dfs(graph, startNode, callback);
                break;
            case "BFS":
                BFS.bfs(graph, startNode, callback);
                break;
            case "Prim":
                Prim.prim(graph, startNode, callback);
                break;
            case "Kruskal":
                Kruskal.kruskal(graph, callback);
                break;
            case "Floyd-Warshall":
                FloydWarshall.floydWarshall(graph, callback);
                break;
        }
    }
}
