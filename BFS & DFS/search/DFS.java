package search;

import visualization.Node;
import visualization.Edge;
import visualization.Graph;
import visualization.VisualizationCallback;

import java.util.*;

public class DFS {
    public static void dfs(Graph graph, Node startNode, VisualizationCallback callback) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        List<Edge> edges = new ArrayList<>();

        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                callback.visitNode(currentNode);
                callback.update(edges, visited);

                for (Edge edge : currentNode.getEdges()) {
                    Node neighbor = edge.getDestination();
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        edges.add(edge);
                    }
                }
            }
        }
    }
}
