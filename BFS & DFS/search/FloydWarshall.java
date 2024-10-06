package search;

import visualization.Edge;
import visualization.Graph;
import visualization.Node;
import visualization.VisualizationCallback;

public class FloydWarshall {
    public static void floydWarshall(Graph graph, VisualizationCallback callback) {
        int n = graph.getNodes().size();
        int[][] dist = new int[n][n];
        int[][] next = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Integer.MAX_VALUE / 2; // To avoid overflow
                }
                next[i][j] = -1;
            }
        }

        for (Node node : graph.getNodes()) {
            for (Edge edge : node.getEdges()) {
                dist[node.getId()][edge.getDestination().getId()] = edge.getWeight();
                next[node.getId()][edge.getDestination().getId()] = edge.getDestination().getId();
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        callback.floydWarshallUpdate(dist, next);
    }
}
