package DistributedCache;

public class CacheMetrics {
    private long hits;
    private long misses;
    private long evictions;

    public synchronized void recordHit() {
        hits++;
    }

    public synchronized void recordMiss() {
        misses++;
    }

    public synchronized void recordEviction() {
        evictions++;
    }

    public synchronized double getHitRate() {
        long total = hits + misses;
        return total == 0 ? 0 : (double) hits / total * 100;
    }

    public synchronized void printStats() {
        System.out.println("=== Cache Metrics ===");
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Evictions: " + evictions);
        System.out.printf("Hit Rate: %.2f%%\n", getHitRate());
    }

    public synchronized void reset() {
        hits = 0;
        misses = 0;
        evictions = 0;
    }
}
