import java.util.*;

class SimpleLFUCache<K, V> {
    private final int capacity;
    private int minFrequency;
    private final Map<K, V> keyValueMap;
    private final Map<K, Integer> keyFrequencyMap;
    private final Map<Integer, LinkedHashSet<K>> frequencyKeysMap;

    public SimpleLFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.keyValueMap = new HashMap<>();
        this.keyFrequencyMap = new HashMap<>();
        this.frequencyKeysMap = new HashMap<>();
    }

    public V get(K key) {
        if (!keyValueMap.containsKey(key)) {
            return null;
        }
        int frequency = keyFrequencyMap.get(key);
        frequencyKeysMap.get(frequency).remove(key);

        if (frequencyKeysMap.get(frequency).isEmpty()) {
            frequencyKeysMap.remove(frequency);
            if (minFrequency == frequency) {
                minFrequency++;
            }
        }

        frequency++;
        keyFrequencyMap.put(key, frequency);
        frequencyKeysMap.computeIfAbsent(frequency, k -> new LinkedHashSet<>()).add(key);

        return keyValueMap.get(key);
    }

    public void put(K key, V value) {
        if (capacity <= 0) {
            return;
        }

        if (keyValueMap.containsKey(key)) {
            keyValueMap.put(key, value);
            get(key); // Update frequency
            return;
        }

        if (keyValueMap.size() >= capacity) {
            K evict = frequencyKeysMap.get(minFrequency).iterator().next();
            frequencyKeysMap.get(minFrequency).remove(evict);
            if (frequencyKeysMap.get(minFrequency).isEmpty()) {
                frequencyKeysMap.remove(minFrequency);
            }
            keyValueMap.remove(evict);
            keyFrequencyMap.remove(evict);
        }

        keyValueMap.put(key, value);
        keyFrequencyMap.put(key, 1);
        frequencyKeysMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFrequency = 1;
    }

    public static void main(String[] args) {
        SimpleLFUCache<Integer, String> lfuCache = new SimpleLFUCache<>(3);
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");
        System.out.println(lfuCache.get(2)); // Output: null, as key 2 is evicted
        System.out.println(lfuCache.get(3)); // Output: C
        System.out.println(lfuCache.get(4)); // Output: D
    }
}
