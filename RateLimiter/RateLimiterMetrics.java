package RateLimiter;

public class RateLimiterMetrics {
    private long allowedRequests = 0;
    private long rejectedRequests = 0;

    public synchronized void recordAllowed() {
        allowedRequests++;
    }

    public synchronized void recordRejected() {
        rejectedRequests++;
    }

    public synchronized void printStats() {
        long total = allowedRequests + rejectedRequests;
        double rejectionRate = total == 0 ? 0 : (double) rejectedRequests / total * 100;
        
        System.out.println("=== Rate Limiter Metrics ===");
        System.out.println("Allowed: " + allowedRequests);
        System.out.println("Rejected: " + rejectedRequests);
        System.out.printf("Rejection Rate: %.2f%%\n", rejectionRate);
    }

    public long getAllowedRequests() {
        return allowedRequests;
    }

    public long getRejectedRequests() {
        return rejectedRequests;
    }
}
