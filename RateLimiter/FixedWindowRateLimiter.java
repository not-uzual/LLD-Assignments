package RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter implements RateLimiter {

    private static class Window {
        long startTime;
        int count;

        Window(long startTime) {
            this.startTime = startTime;
            this.count = 0;
        }
    }

    private final ConcurrentHashMap<String, Window> store = new ConcurrentHashMap<>();
    private final RateLimitConfig config;
    private long lastCleanupTime = System.currentTimeMillis();
    private static final long CLEANUP_INTERVAL = 60000; // 1 minute

    public FixedWindowRateLimiter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public boolean allow(String key) {
        long now = System.currentTimeMillis();
        
        // Periodic cleanup of expired windows
        if (now - lastCleanupTime > CLEANUP_INTERVAL) {
            cleanup(now);
            lastCleanupTime = now;
        }

        store.putIfAbsent(key, new Window(now));
        Window window = store.get(key);

        synchronized (window) {
            // Window expired, reset it
            if (now - window.startTime >= config.getWindowSizeInMillis()) {
                window.startTime = now;
                window.count = 0;
            }

            // Allow request if quota available
            if (window.count < config.getMaxRequests()) {
                window.count++;
                return true;
            }
            return false;
        }
    }

    private void cleanup(long now) {
        store.entrySet().removeIf(entry -> 
            now - entry.getValue().startTime >= config.getWindowSizeInMillis() * 2
        );
    }
}
