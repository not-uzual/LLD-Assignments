package DistributedCache;

public interface Storage {
    String get(String key);
    void put(String key, String value);
    void remove(String key);
    boolean isFull();
    int size();
}