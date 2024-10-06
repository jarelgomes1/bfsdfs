package search;

import visualization.Edge;
import visualization.Graph;
import visualization.Node;
import visualization.VisualizationCallback;

import java.util.*;

public class Kruskal {
    public static void kruskal(Graph graph, VisualizationCallback callback) {
        List<Edge> edges = new ArrayList<>();
        for (Node node : graph.getNodes()) {
            edges.addAll(node.getEdges());
        }

        Collections.sort(edges, Comparator.comparingInt(Edge::getWeight));
        UnionFind unionFind = new UnionFind(graph.getNodes().size());
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            if (unionFind.union(edge.getSource().getId(), edge.getDestination().getId())) {
                mst.add(edge);
                callback.update(mst, unionFind.getComponents(graph));

                if (mst.size() == graph.getNodes().size() - 1) {
                    break;
                }
            }
        }
    }

    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int p) {
            if (parent[p] != p) {
                parent[p] = find(parent[p]);
            }
            return parent[p];
        }

        public boolean union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ)
                return false;

            if (rank[rootP] > rank[rootQ]) {
                parent[rootQ] = rootP;
            } else if (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
            return true;
        }

        public Set<Node> getComponents(Graph graph) {
            Set<Node> components = new HashSet<>();
            for (int i = 0; i < parent.length; i++) {
                int rootId = find(i);
                Node rootNode = graph.getNodeById(rootId);
                components.add(new Node(rootNode.getX(), rootNode.getY(), rootNode.getLabel(), rootNode.getId()));
            }
            return components;
        }
    }
}
