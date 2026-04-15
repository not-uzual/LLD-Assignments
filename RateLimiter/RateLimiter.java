package RateLimiter;

public interface RateLimiter {
    boolean allow(String key);
}
