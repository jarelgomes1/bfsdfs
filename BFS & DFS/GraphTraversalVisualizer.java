import javax.swing.*;
import visualization.Node;
import visualization.Edge;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphTraversalVisualizer extends JFrame {

    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Stack<Action> undoStack = new Stack<>();
    private Stack<Action> redoStack = new Stack<>();
    private JButton addNodeButton;
    private JButton addEdgeButton;
    private JButton submitButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton dfsButton;
    private JButton bfsButton;
    private JButton primButton;
    private JButton kruskalButton;
    private JButton floydWarshallButton;
    private boolean isDragging = false;
    private Node startNode = null;
    private Node endNode = null;
    private int nodeIdCounter = 0;

    public GraphTraversalVisualizer() {
        setTitle("Graph Traversal Visualizer");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        GraphPanel graphPanel = new GraphPanel();
        panel.add(graphPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addNodeButton = new JButton("Add Node");
        addEdgeButton = new JButton("Add Edge");
        submitButton = new JButton("Submit");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        dfsButton = new JButton("DFS");
        bfsButton = new JButton("BFS");
        primButton = new JButton("Prim's");
        kruskalButton = new JButton("Kruskal's");
        floydWarshallButton = new JButton("Floyd-Warshall");

        dfsButton.setVisible(false);
        bfsButton.setVisible(false);
        primButton.setVisible(false);
        kruskalButton.setVisible(false);
        floydWarshallButton.setVisible(false);

        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.setAddNodeMode(true);
            }
        });

        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.setAddEdgeMode(true);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Process the graph here
                System.out.println("Nodes:");
                for (Node node : nodes) {
                    System.out.println("(" + node.getLabel() + ": " + node.getX() + ", " + node.getY() + ")");
                }
                System.out.println("Edges:");
                for (Edge edge : edges) {
                    System.out.println("((" + edge.getSource().getLabel() + ": " + edge.getSource().getX() + ", "
                            + edge.getSource().getY() + ") -> (" +
                            edge.getDestination().getLabel() + ": " + edge.getDestination().getX() + ", "
                            + edge.getDestination().getY() + "): " +
                            edge.getLabel() + ", " + edge.getWeight() + ")");
                }
                // Show all algorithm buttons
                dfsButton.setVisible(true);
                bfsButton.setVisible(true);
                primButton.setVisible(true);
                kruskalButton.setVisible(true);
                floydWarshallButton.setVisible(true);
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!undoStack.isEmpty()) {
                    Action action = undoStack.pop();
                    action.undo();
                    redoStack.push(action);
                    graphPanel.repaint();
                }
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!redoStack.isEmpty()) {
                    Action action = redoStack.pop();
                    action.redo();
                    undoStack.push(action);
                    graphPanel.repaint();
                }
            }
        });

        dfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PathSelectionWindow("DFS", nodes, edges).setVisible(true);
            }
        });

        bfsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PathSelectionWindow("BFS", nodes, edges).setVisible(true);
            }
        });

        primButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PathSelectionWindow("Prim", nodes, edges).setVisible(true);
            }
        });

        kruskalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PathSelectionWindow("Kruskal", nodes, edges).setVisible(true);
            }
        });

        floydWarshallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PathSelectionWindow("Floyd-Warshall", nodes, edges).setVisible(true);
            }
        });

        buttonPanel.add(addNodeButton);
        buttonPanel.add(addEdgeButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(primButton);
        buttonPanel.add(kruskalButton);
        buttonPanel.add(floydWarshallButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private class GraphPanel extends JPanel {
        private boolean addNodeMode = false;
        private boolean addEdgeMode = false;
        private JTextField labelField;
        private JTextField weightField;
        private JRadioButton directedButton;
        private JRadioButton undirectedButton;

        public GraphPanel() {
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());

            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new FlowLayout());

            labelField = new JTextField(10);
            weightField = new JTextField(5);
            directedButton = new JRadioButton("Directed");
            undirectedButton = new JRadioButton("Undirected");
            ButtonGroup group = new ButtonGroup();
            group.add(directedButton);
            group.add(undirectedButton);

            controlPanel.add(new JLabel("Label:"));
            controlPanel.add(labelField);
            controlPanel.add(new JLabel("Weight:"));
            controlPanel.add(weightField);
            controlPanel.add(directedButton);
            controlPanel.add(undirectedButton);

            add(controlPanel, BorderLayout.SOUTH);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (addNodeMode) {
                        String label = JOptionPane.showInputDialog(GraphTraversalVisualizer.this, "Enter node label:");
                        if (label != null && !label.isEmpty()) {
                            int x = e.getX();
                            int y = e.getY();
                            nodes.add(new Node(x, y, label, nodeIdCounter++));
                            repaint();
                            addNodeMode = false;
                            undoStack.push(new AddNodeAction(nodes.size() - 1));
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (addEdgeMode) {
                        startNode = getNodeAtPoint(e.getPoint());
                        if (startNode != null) {
                            isDragging = true;
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (addEdgeMode && isDragging) {
                        endNode = getNodeAtPoint(e.getPoint());
                        if (endNode != null && startNode != endNode) {
                            String label = labelField.getText();
                            String weight = weightField.getText();
                            boolean isDirected = directedButton.isSelected();
                            Edge edge = new Edge(startNode, endNode, label, Integer.parseInt(weight), isDirected);
                            edges.add(edge);
                            startNode.addEdge(edge);
                            endNode.addEdge(edge);
                            undoStack.push(new AddEdgeAction(edges.size() - 1));
                            repaint();
                        }
                        startNode = null;
                        endNode = null;
                        isDragging = false;
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (addEdgeMode && isDragging) {
                        endNode = getNodeAtPoint(e.getPoint());
                        repaint();
                    }
                }
            });
        }

        public void setAddNodeMode(boolean addNodeMode) {
            this.addNodeMode = addNodeMode;
            this.addEdgeMode = false;
        }

        public void setAddEdgeMode(boolean addEdgeMode) {
            this.addEdgeMode = addEdgeMode;
            this.addNodeMode = false;
        }

        private Node getNodeAtPoint(Point point) {
            for (Node node : nodes) {
                if (node.contains(point)) {
                    return node;
                }
            }
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            for (Node node : nodes) {
                node.draw(g2d);
            }
            for (Edge edge : edges) {
                edge.draw(g2d);
            }
            if (isDragging && startNode != null && endNode != null) {
                g2d.drawLine(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphTraversalVisualizer().setVisible(true);
            }
        });
    }

    private interface Action {
        void undo();

        void redo();
    }

    private class AddNodeAction implements Action {
        private int index;

        public AddNodeAction(int index) {
            this.index = index;
        }

        @Override
        public void undo() {
            nodes.remove(index);
        }

        @Override
        public void redo() {
            // Not needed for adding nodes
        }
    }

    private class AddEdgeAction implements Action {
        private int index;

        public AddEdgeAction(int index) {
            this.index = index;
        }

        @Override
        public void undo() {
            edges.remove(index);
        }

        @Override
        public void redo() {
            // Re-add the removed edge
            Edge edge = edges.get(index);
            edge.getSource().addEdge(edge);
            edge.getDestination().addEdge(edge);
            edges.add(index, edge);
        }
    }
}
