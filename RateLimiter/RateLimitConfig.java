package RateLimiter;

import java.util.concurrent.TimeUnit;

public class RateLimitConfig {
    private final int maxRequests;
    private final long windowSizeInMillis;

    public RateLimitConfig(int maxRequests, long duration, TimeUnit unit) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = unit.toMillis(duration);
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }
}