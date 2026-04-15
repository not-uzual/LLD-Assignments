package DistributedCache;

import java.util.*;

public class LRUEvictionPolicy implements EvictionPolicy {

    // Node structure for doubly-linked list
    private static class Node {
        String key;
        Node prev, next;

        Node(String key) {
            this.key = key;
        }
    }

    private Node head, tail;
    private Map<String, Node> map;

    public LRUEvictionPolicy() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
    }

    @Override
    public synchronized void keyAccessed(String key) {
        if (map.containsKey(key)) {
            moveToHead(map.get(key));
        }
    }

    @Override
    public synchronized void keyAdded(String key) {
        if (map.containsKey(key)) {
            removeNode(map.get(key));
        }
        addToHead(key);
    }

    @Override
    public synchronized String evictKey() {
        if (map.isEmpty()) {
            return null;
        }
        Node lru = tail.prev;
        removeNode(lru);
        return lru.key;
    }

    private void addToHead(String key) {
        Node newNode = new Node(key);
        newNode.next = head.next;
        newNode.prev = head;
        head.next.prev = newNode;
        head.next = newNode;
        map.put(key, newNode);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        map.remove(node.key);
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node.key);
    }
}
