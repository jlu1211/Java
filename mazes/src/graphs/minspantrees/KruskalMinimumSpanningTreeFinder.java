package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
// import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Comparator;
// import java.util.HashMap;
import java.util.List;
// import java.util.NoSuchElementException;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        // return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));
        DisjointSets<V> disjointSets = createDisjointSets();
        List<E> mstEdges = new ArrayList<>();
        List<V> vertices = new ArrayList<>(graph.allVertices());

        for (V vertex: vertices) {
            disjointSets.makeSet(vertex);
        }

        for (E edge: edges) {
            V u = edge.from();
            V v = edge.to();
            if (u == v) {
                continue;
            }
            if (disjointSets.findSet(u) != disjointSets.findSet(v)) {
                mstEdges.add(edge);
                disjointSets.union(u, v);
            }
        }

        if (mstEdges.size() < graph.allVertices().size() - 1) {
            return new MinimumSpanningTree.Failure<>();
        } else {
            return new MinimumSpanningTree.Success<>(mstEdges);
        }
    }
}
