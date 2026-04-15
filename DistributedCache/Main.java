package DistributedCache;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        int numNodes = 3;
        int capacityPerNode = 3;

        // Create cache nodes
        List<CacheNode> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new CacheNode(
                    i,
                    new HashMapStorage(capacityPerNode),
                    new LRUEvictionPolicy()
            ));
        }

        // Use consistent hashing for better distribution
        DistributedCache cache = new DistributedCache(
                nodes,
                new ConsistentHashingDistributionStrategy(nodes),
                new Database()
        );

        // Test write operations
        System.out.println("=== Writing Data ===");
        cache.put("A", "Apple");
        cache.put("B", "Ball");
        cache.put("C", "Cat");
        cache.put("D", "Dog");
        cache.put("E", "Elephant");
        cache.put("F", "Fox");

        // Test read operations (some hits, some misses)
        System.out.println("\n=== Reading Data ===");
        System.out.println("A: " + cache.get("A"));  // hit
        System.out.println("B: " + cache.get("B"));  // hit
        System.out.println("X: " + cache.get("X"));  // miss → from DB

        // More reads to generate statistics
        System.out.println("C: " + cache.get("C"));  // hit
        System.out.println("D: " + cache.get("D"));  // hit
        System.out.println("Y: " + cache.get("Y"));  // miss → from DB
        System.out.println("E: " + cache.get("E"));  // hit

        // Print statistics
        System.out.println();
        cache.printNodeStats();
        System.out.println();
        cache.printMetrics();
    }
}
