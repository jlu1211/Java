package graphs.shortestpaths;


//import graphs.AdjacencyListUndirectedGraph;

// import graphs.AdjacencyListUndirectedGraph;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// import java.util.PriorityQueue;
import java.util.List;
// import java.util.Collection;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        // creat map that record all the distance from the start point
        Map<V, Double> distancesFromStart = new HashMap<>();
        Map<V, E> shortestPathTree = new HashMap<>();
        ExtrinsicMinPQ<V> rooms = this.createMinPQ();

        // initialize the distances of all vertex to infinity
        distancesFromStart.put(start, 0.0);


        // start generate map
        rooms.add(start, 0.0);
        while (!rooms.isEmpty()) {
            V current = rooms.removeMin();

            if (current.equals(end)) { // reached the end
                break;
            }


            for (E edge : graph.outgoingEdgesFrom(current)) {
                V connectedVertex = edge.to();
                double weight = edge.weight();
                double newDistance = weight + distancesFromStart.getOrDefault(current, Double.POSITIVE_INFINITY);

                if (newDistance < distancesFromStart.getOrDefault(connectedVertex, Double.POSITIVE_INFINITY)) {
                    distancesFromStart.put(connectedVertex, newDistance);
                    shortestPathTree.put(connectedVertex, edge);

                    if (!rooms.contains(connectedVertex)) {
                        rooms.add(connectedVertex, newDistance);
                    } else {
                        rooms.changePriority(connectedVertex,
                            distancesFromStart.getOrDefault(connectedVertex, Double.POSITIVE_INFINITY));
                    }
                }
            }


        }
        return shortestPathTree;
    }



    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        List<E> path = new ArrayList<>();

        // when the start and end are same, return empty path
        if (start.equals(end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        if (!spt.containsKey(end)) {
            return new ShortestPath.Failure<>();
        }



        V current = end;
        while (!current.equals(start)) {
            E edge = spt.get(current);
            if (edge == null) {
                return new ShortestPath.Failure<>();
            }
            path.add(0, edge);
            current = edge.from();
        }

        return path.isEmpty() ? new ShortestPath.Failure<>() : new ShortestPath.Success<>(path);
    }
}
