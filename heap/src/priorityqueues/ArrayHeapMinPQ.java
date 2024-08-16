package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    int size;
    final HashMap<T, Integer> itemToIndex;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        itemToIndex = new HashMap<>();
        this.size = 0;
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> itemA = items.get(a);
        PriorityNode<T> itemB = items.get(b);
        items.set(a, itemB);
        items.set(b, itemA);
        itemToIndex.put(itemA.getItem(), b);
        itemToIndex.put(itemB.getItem(), a);
    }

    private int getParentNode(int i) {
        return i / 2;
    }

    private int getLeftChildNode(int i) {
        return 2 * i;
    }

    private int getRightChildNode(int i) {
        return 2 * i + 1;
    }

    private void percolateUp(int i) {
        if (i != 1) {
            while (i != 1 && items.get(i).getPriority() < items.get(getParentNode(i)).getPriority()) {
                swap(i, getParentNode(i));
                i = getParentNode(i);
            }
        }
    }

    private void percolateDown(int i) {
        int min = i;
        int rightIndex = getRightChildNode(i);
        int leftIndex = getLeftChildNode(i);
        if (leftIndex <= size && items.get(leftIndex).getPriority() < items.get(min).getPriority()) {
            min = leftIndex;
        }
        if (rightIndex <= size && items.get(rightIndex).getPriority() < items.get(min).getPriority()) {
            min = rightIndex;
        }
        if (i != min) {
            swap(i, min);
            percolateDown(min);
        }
    }

    @Override
    public void add(T item, double priority) {
        if (itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode<T> newItem = new PriorityNode<>(item, priority);
        items.add(newItem);
        this.size++;
        itemToIndex.put(item, size);
        if (size > 1) {
            percolateUp(size);
        }
    }

    @Override
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items.get(START_INDEX).getItem();
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T minItem = peekMin();
        swap(START_INDEX, size);
        items.remove(size);
        itemToIndex.remove(minItem);
        size--;
        percolateDown(START_INDEX);
        return minItem;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!itemToIndex.containsKey(item)) {
            throw new NoSuchElementException();
        }
        int index = itemToIndex.get(item);
        double oldPriority = items.get(index).getPriority();
        items.get(index).setPriority(priority);
        if (priority <= oldPriority) {
            percolateUp(index);
        } else {
            percolateDown(index);
        }
    }

    @Override
    public int size() {
        return size;
    }
}
