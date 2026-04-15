package RateLimiter;

public class RateLimiterFactory {

    public enum Type {
        FIXED_WINDOW,
        SLIDING_WINDOW,
        TOKEN_BUCKET
    }

    public static RateLimiter create(Type type, RateLimitConfig config) {
        switch (type) {
            case FIXED_WINDOW:
                return new FixedWindowRateLimiter(config);
            case SLIDING_WINDOW:
                return new SlidingWindowRateLimiter(config);
            case TOKEN_BUCKET:
                return new TokenBucketRateLimiter(config);
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}