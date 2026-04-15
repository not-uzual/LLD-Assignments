package RateLimiter;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final ConcurrentHashMap<String, Deque<Long>> store = new ConcurrentHashMap<>();
    private final RateLimitConfig config;
    private long lastCleanupTime = System.currentTimeMillis();
    private static final long CLEANUP_INTERVAL = 60000; // 1 minute

    public SlidingWindowRateLimiter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public boolean allow(String key) {
        long now = System.currentTimeMillis();

        // Periodic cleanup of empty queues
        if (now - lastCleanupTime > CLEANUP_INTERVAL) {
            cleanup();
            lastCleanupTime = now;
        }

        store.putIfAbsent(key, new ConcurrentLinkedDeque<>());
        Deque<Long> queue = store.get(key);

        synchronized (queue) {
            // Remove timestamps outside the window
            while (!queue.isEmpty() && now - queue.peekFirst() >= config.getWindowSizeInMillis()) {
                queue.pollFirst();
            }

            // Allow request if quota available
            if (queue.size() < config.getMaxRequests()) {
                queue.addLast(now);
                return true;
            }
            return false;
        }
    }

    private void cleanup() {
        store.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}
