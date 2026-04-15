package RateLimiter;

public class ExternalServiceCaller {

    private final RateLimiter rateLimiter;
    private final RateLimiterMetrics metrics;

    public ExternalServiceCaller(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
        this.metrics = new RateLimiterMetrics();
    }

    public void process(String key, boolean needsExternalCall) {
        if (!needsExternalCall) {
            System.out.println("No external call needed");
            return;
        }

        if (rateLimiter.allow(key)) {
            callExternal();
            metrics.recordAllowed();
        } else {
            System.out.println("Rate limit exceeded for key: " + key);
            metrics.recordRejected();
        }
    }

    private void callExternal() {
        System.out.println("✓ External API called");
    }

    public void printMetrics() {
        metrics.printStats();
    }

    public RateLimiterMetrics getMetrics() {
        return metrics;
    }
}
