package RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

    private static class Bucket {
        double tokens;
        long lastRefillTime;
        final double refillRate; // tokens per millisecond
        final double capacity;

        Bucket(double capacity, double refillRate) {
            this.tokens = capacity;
            this.capacity = capacity;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }
    }

    private final ConcurrentHashMap<String, Bucket> store = new ConcurrentHashMap<>();
    private final RateLimitConfig config;
    private long lastCleanupTime = System.currentTimeMillis();
    private static final long CLEANUP_INTERVAL = 60000; // 1 minute

    public TokenBucketRateLimiter(RateLimitConfig config) {
        this.config = config;
        // refillRate = maxRequests / windowSizeInMillis
    }

    @Override
    public boolean allow(String key) {
        long now = System.currentTimeMillis();

        // Periodic cleanup of old buckets
        if (now - lastCleanupTime > CLEANUP_INTERVAL) {
            cleanup();
            lastCleanupTime = now;
        }

        double refillRate = (double) config.getMaxRequests() / config.getWindowSizeInMillis();
        store.putIfAbsent(key, new Bucket(config.getMaxRequests(), refillRate));
        Bucket bucket = store.get(key);

        synchronized (bucket) {
            // Refill tokens based on elapsed time
            long timePassed = now - bucket.lastRefillTime;
            bucket.tokens = Math.min(bucket.capacity, 
                bucket.tokens + (timePassed * bucket.refillRate));
            bucket.lastRefillTime = now;

            // Allow request if token available
            if (bucket.tokens >= 1.0) {
                bucket.tokens -= 1.0;
                return true;
            }
            return false;
        }
    }

    private void cleanup() {
        // Remove inactive buckets
        long now = System.currentTimeMillis();
        store.entrySet().removeIf(entry -> 
            now - entry.getValue().lastRefillTime > CLEANUP_INTERVAL * 2
        );
    }
}
