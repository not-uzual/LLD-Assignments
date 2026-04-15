package DistributedCache;

public interface EvictionPolicy {
    void keyAccessed(String key);
    void keyAdded(String key);
    String evictKey();
}
