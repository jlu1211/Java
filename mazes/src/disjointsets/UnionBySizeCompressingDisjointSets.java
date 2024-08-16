package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    private final Map<T, Integer> itemToIndex;
    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        this.pointers = new ArrayList<>();
        this.itemToIndex = new HashMap<>();
    }

    @Override
    public void makeSet(T item) {
        if (!itemToIndex.containsKey(item)) {
            itemToIndex.put(item, pointers.size());
            pointers.add(-1);
        }
    }

    @Override
    public int findSet(T item) {
        if (!itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException("Such item is not in disjoint sets");
        }
        int index = itemToIndex.get(item);
        if (pointers.get(index) < 0) {
            return index;
        }
        List<Integer> compress = new ArrayList<>();
        while (pointers.get(index) >= 0) {
            compress.add(index);
            index = pointers.get(index);
        }
        pathCompression(compress, index);
        return index;
    }

    private void pathCompression(List<Integer> compress, int root) {
        for (int i: compress) {
            pointers.set(i, root);
        }
    }

    @Override
    public boolean union(T item1, T item2) {
        int root1 = findSet(item1);
        int root2 = findSet(item2);
        if (root1 == root2) {
            return false;
        } else {
            int root1Weight = -pointers.get(root1);
            int root2Weight = -pointers.get(root2);
            if (root1Weight >= root2Weight) {
                pointers.set(root2, root1);
                pointers.set(root1, -(root1Weight + root2Weight));
            } else {
                pointers.set(root2, -(root1Weight + root2Weight));
                pointers.set(root1, root2);
            }
            return true;
        }
    }
}
