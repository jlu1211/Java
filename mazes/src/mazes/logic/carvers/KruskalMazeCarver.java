package mazes.logic.carvers;

// import graphs.Edge;
import graphs.EdgeWithData;
// import graphs.minspantrees.KruskalMinimumSpanningTreeFinder;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
// import java.util.stream.Collectors;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        Collection<EdgeWithData<Room, Wall>> edges = new ArrayList<>();
        for (Wall wall: walls) {
            EdgeWithData<Room, Wall> edge;
            edge = new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), rand.nextDouble(), wall);
            edges.add(edge);
        }
        MazeGraph mazeGraph = new MazeGraph(edges);
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> mst;
        mst = this.minimumSpanningTreeFinder.findMinimumSpanningTree(mazeGraph);
        Set<Wall> removals = new HashSet<>();
        for (EdgeWithData<Room, Wall> edge: mst.edges()) {
            Wall wall = new Wall(edge.from(), edge.to(), edge.data().getDividingLine());
            removals.add(wall);
        }
        return removals;
    }
}
