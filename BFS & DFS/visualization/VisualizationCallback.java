package visualization;

import java.util.Collection;
import java.util.Set;

public interface VisualizationCallback {
    void update(Collection<Edge> edges, Set<Node> visited);

    void visitNode(Node node);

    void floydWarshallUpdate(int[][] dist, int[][] next);
}
