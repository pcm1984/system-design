import java.util.*;

public class SimpleRandomReplacementCache<K, V>{
    private final List<K> listOfKeys;

    private final Integer capacity;

    private final Map<K, V> cache;

    public SimpleRandomReplacementCache(int capacity){
        this.cache = new HashMap<>(capacity);
        this.capacity = capacity;
        this.listOfKeys = new ArrayList<>();
    }

    public void put(K key, V value){
        if(this.cache.size() == capacity){
            K keyToRemove = getKeyToRemove();
            this.cache.remove(keyToRemove);
            this.listOfKeys.remove(keyToRemove);
        }
        this.cache.put(key, value);
        this.listOfKeys.add(key);
    }

    public V get(K key){
        return this.cache.get(key);
    }

    private K getKeyToRemove() {
        Random random = new Random();
        return this.listOfKeys.get(random.nextInt(listOfKeys.size()));
    }

    public static void main(String[] args){
        SimpleRandomReplacementCache<Integer, String> simpleRandomReplacementCache = new SimpleRandomReplacementCache<>(3);
        simpleRandomReplacementCache.put(1,"A");
        simpleRandomReplacementCache.put(2,"B");
        simpleRandomReplacementCache.put(3,"C");
        simpleRandomReplacementCache.put(4,"D");
        System.out.println(simpleRandomReplacementCache.get(1));
        System.out.println(simpleRandomReplacementCache.get(2));
        System.out.println(simpleRandomReplacementCache.get(3));
        System.out.println(simpleRandomReplacementCache.get(4));
        System.out.print("--------------------------------------");
        simpleRandomReplacementCache.put(5,"E");
        System.out.println(simpleRandomReplacementCache.get(1));
        System.out.println(simpleRandomReplacementCache.get(2));
        System.out.println(simpleRandomReplacementCache.get(3));
        System.out.println(simpleRandomReplacementCache.get(4));
        System.out.println(simpleRandomReplacementCache.get(5));
        System.out.print("--------------------------------------");

        simpleRandomReplacementCache.put(6,"F");
        System.out.println(simpleRandomReplacementCache.get(1));
        System.out.println(simpleRandomReplacementCache.get(2));
        System.out.println(simpleRandomReplacementCache.get(3));
        System.out.println(simpleRandomReplacementCache.get(4));
        System.out.println(simpleRandomReplacementCache.get(5));
        System.out.println(simpleRandomReplacementCache.get(6));
        System.out.print("--------------------------------------");

    }
}