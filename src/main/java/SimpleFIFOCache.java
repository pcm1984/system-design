import java.util.*;

public class SimpleFIFOCache<K, V> {

    //Size of cache
    private final int capacity;
    //The cache data structure iteself
    private final HashMap<K, V> cache;

    private final Queue<K> keyQueue;

    public SimpleFIFOCache(int capacity) {
        this.capacity = capacity;
        //Need to pass access order true as we need to track access.
        this.cache = new HashMap<K, V>(capacity);
        this.keyQueue = new LinkedList<>();
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() == capacity) {
            K keyToRemove = keyQueue.remove();
            cache.remove(keyToRemove);
        }
        cache.put(key, value);
        keyQueue.add(key);
    }


    public static void main(String[] args) {
        SimpleFIFOCache<Integer, String> mruCache = new SimpleFIFOCache<>(3);

        mruCache.put(1, "A");
        mruCache.put(2, "B");
        mruCache.put(3, "C");
        mruCache.put(4, "D"); // Evicts key 1 (one that was added first.)

        System.out.println(mruCache.get(1)); // Output: null
        System.out.println(mruCache.get(2)); // Output: B
        System.out.println(mruCache.get(3)); // Output: C
        System.out.println(mruCache.get(4)); // Output: D
    }
}
