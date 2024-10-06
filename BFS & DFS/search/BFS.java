package search;

import visualization.Node;
import visualization.Edge;
import visualization.Graph;
import visualization.VisualizationCallback;

import java.util.*;

public class BFS {
    public static void bfs(Graph graph, Node startNode, VisualizationCallback callback) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        List<Edge> edges = new ArrayList<>();

        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                callback.visitNode(currentNode);
                callback.update(edges, visited);

                for (Edge edge : currentNode.getEdges()) {
                    Node neighbor = edge.getDestination();
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        edges.add(edge);
                    }
                }
            }
        }
    }
}
