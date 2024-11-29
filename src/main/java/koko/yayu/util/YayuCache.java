package koko.yayu.util;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class YayuCache<V> {

  private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE
    = Executors.newScheduledThreadPool(1);

  private final LoadingCache<String, V> cache;
  private final Map<String, AtomicInteger> access = new ConcurrentHashMap<>();
  private final int keepAlive;

  public YayuCache(Duration expire, int keepAlive, Supplier<V> loader) {
    this.keepAlive = keepAlive;
    this.cache = CacheBuilder.newBuilder()
      .expireAfterWrite(expire.multipliedBy(2))
      .build(CacheLoader.from(loader::get));
    SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(
      this::refresh, 0, expire.toMillis(), TimeUnit.MILLISECONDS);
  }

  private void refresh() {
    for (Map.Entry<String, AtomicInteger> entry : access.entrySet()) {
      String key = entry.getKey();
      if (0 < entry.getValue().decrementAndGet()) {
        cache.refresh(key);
      } else {
        access.remove(key);
      }
    }
  }

  public V get() {
    String authToken = YayuUtil.getAuthToken();
    access.put(authToken, new AtomicInteger(keepAlive));
    return cache.getUnchecked(authToken);
  }
}
