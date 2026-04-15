package RateLimiter;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Allow 5 requests per 10 seconds
        RateLimitConfig config = new RateLimitConfig(5, 10, TimeUnit.SECONDS);

        System.out.println("=== Rate Limiter Comparison ===\n");

        // Test 1: Fixed Window
        System.out.println("--- Fixed Window Rate Limiter ---");
        testRateLimiter(
            RateLimiterFactory.Type.FIXED_WINDOW,
            config,
            "T1",
            10,
            200
        );

        System.out.println("\n--- Sliding Window Rate Limiter ---");
        testRateLimiter(
            RateLimiterFactory.Type.SLIDING_WINDOW,
            config,
            "T2",
            10,
            200
        );

        System.out.println("\n--- Token Bucket Rate Limiter ---");
        testRateLimiter(
            RateLimiterFactory.Type.TOKEN_BUCKET,
            config,
            "T3",
            10,
            200
        );
    }

    private static void testRateLimiter(
            RateLimiterFactory.Type type,
            RateLimitConfig config,
            String key,
            int numRequests,
            long delayMillis) throws InterruptedException {

        RateLimiter limiter = RateLimiterFactory.create(type, config);
        ExternalServiceCaller service = new ExternalServiceCaller(limiter);

        for (int i = 0; i < numRequests; i++) {
            service.process(key, true);
            Thread.sleep(delayMillis);
        }

        service.printMetrics();
    }
}
