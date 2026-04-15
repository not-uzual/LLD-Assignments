package DistributedCache;

import java.util.List;

public class DistributedCache {

    private List<CacheNode> nodes;
    private DistributionStrategy strategy;
    private Database database;
    private CacheMetrics metrics;

    public DistributedCache(List<CacheNode> nodes,
            DistributionStrategy strategy,
            Database database) {
        this.nodes = nodes;
        this.strategy = strategy;
        this.database = database;
        this.metrics = new CacheMetrics();
    }

    public String get(String key) {
        CacheNode node = strategy.getNode(key, nodes);
        String value = node.get(key);

        if (value == null) {
            // Cache miss - fetch from database
            value = database.get(key);
            node.put(key, value);
            metrics.recordMiss();
        } else {
            // Cache hit
            metrics.recordHit();
        }
        return value;
    }

    public void put(String key, String value) {
        CacheNode node = strategy.getNode(key, nodes);
        node.put(key, value);

        // write-through assumption
        database.put(key, value);
    }

    public void printMetrics() {
        metrics.printStats();
    }

    public void printNodeStats() {
        System.out.println("=== Node Stats ===");
        for (CacheNode node : nodes) {
            System.out.println("Node " + node.getNodeId() + ": " + node.getSize() + " entries");
        }
    }
}