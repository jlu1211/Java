package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1.5;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 3;

    private final double resizingLoadFactorThreshold;
    private final int chainInitialCapacity;
    private int chainCount;
    private int numOfKeys;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chainInitialCapacity = chainInitialCapacity;
        this.chainCount = initialChainCount;
        this.numOfKeys = 0;
        this.chains = createArrayOfChains(chainCount);
        for (int i = 0; i < chainCount; i++) {
            this.chains[i] = createChain(chainInitialCapacity);
        }
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    private int hashing(Object key, int chainCounts) {
        if (key != null) {
            int hashCode = key.hashCode();
            if (hashCode < 0) {
                hashCode = -1 * hashCode;
            }
            return hashCode%chainCounts;
        } else {
            return 0;
        }
    }


    private void resize() {
        chainCount = chainCount * 2;
        AbstractIterableMap<K, V>[] newOne = createArrayOfChains(chainCount);
        for (int i = 0; i < chainCount; i++) {
            newOne[i] = createChain(chainInitialCapacity);
        }
        for (AbstractIterableMap<K, V> i:chains) {
            for (Entry<K, V> j:i) {
                if (j != null) {
                    newOne[hashing(j.getKey(), chainCount)].put(j.getKey(),
                        chains[hashing(j.getKey(), chainCount/2)].get(j.getKey()));
                }
            }
        }

        this.chains = newOne;
    }


    @Override
    public V get(Object key) {
        if (numOfKeys != 0) {
            return this.chains[hashing(key, chainCount)].get(key);
        } else {
            return  null;
        }
    }

    @Override
    public V put(K key, V value) {
        if (this.containsKey(key)) {
            V oldVal = this.get(key);
            this.chains[hashing(key, chainCount)].put(key, value);
            return oldVal;
        }
        this.chains[hashing(key, chainCount)].put(key, value);
        numOfKeys++;
        if ((double) numOfKeys/chainCount > resizingLoadFactorThreshold) {
            resize();
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        if (numOfKeys != 0 && get(key) != null) {
            numOfKeys--;
            return this.chains[hashing(key, chainCount)].remove(key);
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        this.chains = createArrayOfChains(DEFAULT_INITIAL_CHAIN_COUNT);
        for (int i = 0; i < DEFAULT_INITIAL_CHAIN_COUNT; i++) {
            this.chains[i] = createChain(DEFAULT_INITIAL_CHAIN_CAPACITY);
        }
        numOfKeys = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.chains[hashing(key, chainCount)].containsKey(key);
    }

    @Override
    public int size() {
        return this.numOfKeys;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final AbstractIterableMap<K, V>[] chains;
        private int yIndex = 0;
        Iterator<Map.Entry<K, V>> iter;
        // You may add more fields and constructor parameters

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            iter = chains[yIndex].iterator();
        }

        @Override
        public boolean hasNext() {
            if (iter.hasNext()) {
                return true;
            }
            int tempIndex = yIndex+1;
            while (tempIndex < this.chains.length) {
                if (chains[tempIndex].size() != 0) {
                    return true;
                }
                tempIndex++;
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No such element");
            }
            if (!iter.hasNext()) {
                yIndex++;
                while (chains[yIndex].size() == 0) {
                    yIndex++;
                }
                iter = chains[yIndex].iterator();
            }
            return iter.next();
        }
    }
}
