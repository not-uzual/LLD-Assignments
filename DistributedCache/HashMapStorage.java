package DistributedCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class HashMapStorage implements Storage {

    private Map<String, String> map;
    private int capacity;

    public HashMapStorage(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>();  // Thread-safe
    }

    @Override
    public synchronized String get(String key) {
        return map.get(key);
    }

    @Override
    public synchronized void put(String key, String value) {
        map.put(key, value);
    }

    @Override
    public synchronized void remove(String key) {
        map.remove(key);
    }

    @Override
    public synchronized boolean isFull() {
        return map.size() >= capacity;
    }

    @Override
    public synchronized int size() {
        return map.size();
    }
}
