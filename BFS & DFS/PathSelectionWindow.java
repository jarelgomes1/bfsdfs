import javax.swing.*;
import visualization.Node;
import visualization.Edge;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PathSelectionWindow extends JFrame {
    private String traversalType;
    private List<Node> nodes;
    private List<Edge> edges;
    private JComboBox<String> pathSelector;
    private JButton startTraversalButton;

    public PathSelectionWindow(String traversalType, List<Node> nodes, List<Edge> edges) {
        this.traversalType = traversalType;
        this.nodes = nodes;
        this.edges = edges;

        setTitle(traversalType + " Path Selection");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        pathSelector = new JComboBox<>();
        pathSelector.addItem("Path 1");
        pathSelector.addItem("Path 2");
        // Add more paths as needed

        startTraversalButton = new JButton("Start Traversal");
        startTraversalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPath = (String) pathSelector.getSelectedItem();
                if (selectedPath != null) {
                    switch (selectedPath) {
                        case "Path 1":
                            new TraversalWindow(traversalType, nodes, edges, 1).setVisible(true);
                            break;
                        case "Path 2":
                            new TraversalWindow(traversalType, nodes, edges, 2).setVisible(true);
                            break;
                        // Add more cases as needed
                    }
                }
                dispose();
            }
        });

        panel.add(pathSelector, BorderLayout.CENTER);
        panel.add(startTraversalButton, BorderLayout.SOUTH);

        add(panel);
    }
}
