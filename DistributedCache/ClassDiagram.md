# Distributed Cache System - Class Diagram

```mermaid
classDiagram
    class DistributedCache {
        - nodes: List~CacheNode~
        - strategy: DistributionStrategy
        - database: Database
        - metrics: CacheMetrics
        + DistributedCache(nodes, strategy, database)
        + get(key: String) String
        + put(key: String, value: String)
        + printMetrics()
        + printNodeStats()
    }

    class CacheNode {
        - nodeId: int
        - storage: Storage
        - evictionPolicy: EvictionPolicy
        + CacheNode(nodeId, storage, evictionPolicy)
        + get(key: String) String
        + put(key: String, value: String)
        + getNodeId() int
        + getSize() int
    }

    class Storage {
        <<interface>>
        + get(key: String) String
        + put(key: String, value: String)
        + remove(key: String)
        + isFull() boolean
        + size() int
    }

    class HashMapStorage {
        - map: Map~String, String~
        - capacity: int
        + HashMapStorage(capacity: int)
        + get(key: String) String
        + put(key: String, value: String)
        + remove(key: String)
        + isFull() boolean
        + size() int
    }

    class EvictionPolicy {
        <<interface>>
        + keyAccessed(key: String)
        + keyAdded(key: String)
        + evictKey() String
    }

    class LRUEvictionPolicy {
        - head: Node
        - tail: Node
        - map: Map~String, Node~
        + LRUEvictionPolicy()
        + keyAccessed(key: String)
        + keyAdded(key: String)
        + evictKey() String
        - addToHead(key: String)
        - removeNode(node: Node)
        - moveToHead(node: Node)
    }

    class DistributionStrategy {
        <<interface>>
        + getNode(key: String, nodes: List) CacheNode
    }

    class ModuloDistributionStrategy {
        + getNode(key: String, nodes: List) CacheNode
    }

    class ConsistentHashingDistributionStrategy {
        - VIRTUAL_NODES: int
        - ring: SortedMap~Integer, CacheNode~
        - nodes: List~CacheNode~
        + ConsistentHashingDistributionStrategy(nodes)
        + getNode(key: String, nodes: List) CacheNode
        - buildRing()
        - addNode(node: CacheNode)
        - hash(key: String) int
    }

    class Database {
        - db: Map~String, String~
        + get(key: String) String
        + put(key: String, value: String)
    }

    class CacheMetrics {
        - hits: long
        - misses: long
        - evictions: long
        + recordHit()
        + recordMiss()
        + recordEviction()
        + getHitRate() double
        + printStats()
        + reset()
    }

    class Main {
        + main(args: String[])
    }

    DistributedCache --> "1" CacheMetrics
    DistributedCache --> "1" Database
    DistributedCache --> "1" DistributionStrategy
    DistributedCache --> "3..*" CacheNode
    
    CacheNode --> "1" Storage
    CacheNode --> "1" EvictionPolicy
    
    HashMapStorage ..|> Storage
    
    LRUEvictionPolicy ..|> EvictionPolicy
    
    ModuloDistributionStrategy ..|> DistributionStrategy
    ConsistentHashingDistributionStrategy ..|> DistributionStrategy
    ConsistentHashingDistributionStrategy --> "*" CacheNode
    
    Main --> DistributedCache
```

## Architecture Overview

**DistributedCache**: Main entry point managing multiple cache nodes, distribution strategy, and metrics tracking.

**CacheNode**: Individual cache instance with its own storage and eviction policy. Supports synchronized get/put operations.

**Storage Interface**: Abstraction for cache storage (currently HashMap-based with ConcurrentHashMap for thread safety).

**EvictionPolicy Interface**: Handles cache replacement when full. LRU implementation uses a doubly-linked list for O(1) operations.

**DistributionStrategy Interface**:
- **ModuloDistribution**: Basic hash distribution (problematic when nodes scale)
- **ConsistentHashingDistribution**: Advanced distribution with virtual nodes (minimal rehashing on node changes)

**Database**: Persistent storage for cache misses (write-through consistency).

**CacheMetrics**: Tracks hit rate, miss rate, and eviction statistics for monitoring.

## Key Improvements Made

✅ **Thread Safety**: ConcurrentHashMap + synchronized methods
✅ **O(1) LRU**: Doubly-linked list instead of Deque
✅ **Scalable Hashing**: Consistent hashing for minimal key movement
✅ **Monitoring**: Hit rate, miss rate, and per-node statistics
