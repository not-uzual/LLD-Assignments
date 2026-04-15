package DistributedCache;

public class CacheNode {

    private Storage storage;
    private EvictionPolicy evictionPolicy;
    private int nodeId;

    public CacheNode(int nodeId, Storage storage, EvictionPolicy evictionPolicy) {
        this.nodeId = nodeId;
        this.storage = storage;
        this.evictionPolicy = evictionPolicy;
    }

    public synchronized String get(String key) {
        String value = storage.get(key);
        if (value != null) {
            evictionPolicy.keyAccessed(key);
        }
        return value;
    }

    public synchronized void put(String key, String value) {
        if (storage.isFull() && storage.get(key) == null) {
            String evictKey = evictionPolicy.evictKey();
            if (evictKey != null) {
                storage.remove(evictKey);
            }
        }
        storage.put(key, value);
        evictionPolicy.keyAdded(key);
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getSize() {
        return storage.size();
    }
}
