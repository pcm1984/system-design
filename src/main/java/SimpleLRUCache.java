import java.util.HashMap;

class SimpleLRUCache<K, V> {
    private final int capacity;
    private final HashMap<K, Node<K, V>> cache;
    private final DoublyLinkedList<K, V> accessOrderedKeyQueue;

    public SimpleLRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.accessOrderedKeyQueue = new DoublyLinkedList<>();
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node<K, V> node = cache.get(key);
        accessOrderedKeyQueue.moveToFront(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            node.value = value;
            accessOrderedKeyQueue.moveToFront(node);
        } else {
            if (cache.size() >= capacity) {
                Node<K, V> lruNode = accessOrderedKeyQueue.removeLast();
                if (lruNode != null) {
                    cache.remove(lruNode.key);
                }
            }
            Node<K, V> newNode = new Node<>(key, value);
            accessOrderedKeyQueue.addFirst(newNode);
            cache.put(key, newNode);
        }
    }

    // Node class for doubly linked list
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Doubly linked list class
    private static class DoublyLinkedList<K, V> {
        private Node<K, V> head, tail;

        DoublyLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        void addFirst(Node<K, V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        void moveToFront(Node<K, V> node) {
            remove(node);
            addFirst(node);
        }

        Node<K, V> removeLast() {
            if (tail.prev == head) {
                return null;
            }
            Node<K, V> lruNode = tail.prev;
            remove(lruNode);
            return lruNode;
        }

        void remove(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public static void main(String[] args) {
        SimpleLRUCache<Integer, String> lruCache = new SimpleLRUCache<>(3);
        lruCache.put(1, "A");
        lruCache.put(2, "B");
        lruCache.put(3, "C");

        System.out.println(lruCache.get(1)); // Output: A
        lruCache.put(4, "D");
        System.out.println(lruCache.get(2)); // Output: null (evicted)
        System.out.println(lruCache.get(3)); // Output: C
        System.out.println(lruCache.get(4)); // Output: D
        lruCache.put(5, "E");
        System.out.println(lruCache.get(1)); // Output: null (evicted)
        System.out.println(lruCache.get(2)); // Output: null (evicted)
        System.out.println(lruCache.get(3)); // Output: C
        System.out.println(lruCache.get(4)); // Output: D
        System.out.println(lruCache.get(5)); // Output: E
    }
}
