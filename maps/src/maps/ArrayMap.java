package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    // You may add extra fields or helper methods though!
    private int size = 0;
    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     * Note that each element in the array will initially be null.
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    private int getIndex(Object key) {
        for (int i = 0; i < this.size(); i++) {
            if (key == null && this.entries[i].getKey() == null) {
                return i;
            } else if (this.entries[i].getKey() != null && this.entries[i].getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public V get(Object key) {
        int keyIndex = getIndex(key);
        if (keyIndex == -1) {
            return null;
        }
        return this.entries[keyIndex].getValue();
    }

    @Override
    public V put(K key, V value) {
        int keyIndex = getIndex(key);
        if (keyIndex != -1) {
            V oldVal = this.entries[keyIndex].getValue();
            this.entries[keyIndex].setValue(value);
            return oldVal;
        }
        if (size == this.entries.length) {
            resize();
        }
        SimpleEntry<K, V> newEntry = new SimpleEntry<>(key, value);
        this.entries[this.size] = newEntry;
        size++;
        return null;
    }

    private void resize() {
        SimpleEntry<K, V>[] newEntries = this.createArrayOfEntries(this.size * 2);
        System.arraycopy(this.entries, 0, newEntries, 0, this.size);
        this.entries = newEntries;
    }

    @Override
    public V remove(Object key) {
        int keyIndex = getIndex(key);
        if (keyIndex == -1) {
            return null;
        }
        V oldVal = this.entries[keyIndex].getValue();
        this.entries[keyIndex] = this.entries[this.size - 1];
        this.entries[this.size - 1] = null;
        size--;
        return oldVal;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.entries[i] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getIndex(key) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        private int currIndex = 0;
        // You may add more fields and constructor parameters

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
        }

        @Override
        public boolean hasNext() {
            while (currIndex < entries.length && entries[currIndex] == null) {
                currIndex++;
            }
            return currIndex < entries.length;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No such element");
            }
            return entries[currIndex++];
        }
    }
}

