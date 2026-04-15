package DistributedCache;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashingDistributionStrategy implements DistributionStrategy {

    private static final int VIRTUAL_NODES = 150;
    private SortedMap<Integer, CacheNode> ring;
    private List<CacheNode> nodes;

    public ConsistentHashingDistributionStrategy(List<CacheNode> nodes) {
        this.nodes = nodes;
        this.ring = new TreeMap<>();
        buildRing();
    }

    private void buildRing() {
        for (CacheNode node : nodes) {
            addNode(node);
        }
    }

    private void addNode(CacheNode node) {
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            int hash = hash(node.getNodeId() + ":" + i);
            ring.put(hash, node);
        }
    }

    @Override
    public CacheNode getNode(String key, List<CacheNode> nodeList) {
        if (nodeList.isEmpty()) {
            return null;
        }

        int hash = hash(key);
        SortedMap<Integer, CacheNode> tailMap = ring.tailMap(hash);

        // If hash is greater than all node hashes, wrap around to first
        if (tailMap.isEmpty()) {
            return ring.get(ring.firstKey());
        }

        return tailMap.get(tailMap.firstKey());
    }

    private int hash(String key) {
        return Math.abs(key.hashCode());
    }
}
