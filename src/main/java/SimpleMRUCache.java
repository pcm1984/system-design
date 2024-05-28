import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleMRUCache<K, V> {

    //Size of cache
    private final int capacity;
    //The cache data structure iteself
    private final LinkedHashMap<K, V> cache;
    //Keep track of the key we want to evict.
    private K mostRecentlyUsedKey;

    public SimpleMRUCache(int capacity) {
        this.capacity = capacity;
        //Need to pass access order true as we need to track access.
        this.cache = new LinkedHashMap<K, V>(capacity,0.75f,true);
    }

    public V get(K key) {
        V value = cache.get(key);
        //Any key that is accessed becomes the most recently used key.
        if (value != null) {
            mostRecentlyUsedKey = key;
        }
        return value;
    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            mostRecentlyUsedKey = key;
            return;
        }

        if (cache.size() >= capacity) {
            cache.remove(mostRecentlyUsedKey);
        }

        cache.put(key, value);
        mostRecentlyUsedKey = key;
    }
    public K getMostRecentlyUsedKey() {
        return mostRecentlyUsedKey;
    }

    public static void main(String[] args) {
        SimpleMRUCache<Integer, String> mruCache = new SimpleMRUCache<>(3);

        mruCache.put(1, "A");
        mruCache.put(2, "B");
        mruCache.put(3, "C");

        System.out.println(mruCache.get(1)); // Output: A
        System.out.println(mruCache.getMostRecentlyUsedKey()); // Output: 1

        mruCache.put(4, "D"); // Evicts key 1 (the most recently used)

        System.out.println(mruCache.get(1)); // Output: null
        System.out.println(mruCache.get(2)); // Output: B
        System.out.println(mruCache.get(3)); // Output: C
        System.out.println(mruCache.get(4)); // Output: D

        System.out.println(mruCache.getMostRecentlyUsedKey()); // Output: 4
    }
}
