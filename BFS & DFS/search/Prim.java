package search;

import visualization.Edge;
import visualization.Graph;
import visualization.Node;
import visualization.VisualizationCallback;

import java.util.*;

public class Prim {
    public static void prim(Graph graph, Node startNode, VisualizationCallback callback) {
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        Set<Node> visited = new HashSet<>();
        List<Edge> mst = new ArrayList<>();

        visited.add(startNode);
        callback.visitNode(startNode);

        for (Edge edge : startNode.getEdges()) {
            edgeQueue.add(edge);
        }

        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            Node node = edge.getDestination();

            if (!visited.contains(node)) {
                visited.add(node);
                mst.add(edge);
                callback.visitNode(node);
                callback.update(mst, visited);

                for (Edge nextEdge : node.getEdges()) {
                    if (!visited.contains(nextEdge.getDestination())) {
                        edgeQueue.add(nextEdge);
                    }
                }
            }
        }
    }
}
