package com.weylandyutani.synthetichumancorestarter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AndroidMetrics {
    private final MeterRegistry meterRegistry;
    private final ThreadPoolExecutor executor;
    private final Map<String, Counter> executedCommandsByAuthor = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        if (executor != null) {
            Gauge.builder("android.queue.size", executor.getQueue(), Queue::size)
                 .description("Current size of command queue")
                 .register(meterRegistry);
        } else {
            log.warn("ThreadPoolExecutor is null, queue size metric will not be registered");
        }
    }

    public void recordCommandExecution(String author) {
        if (author != null) {
            Counter counter = executedCommandsByAuthor.computeIfAbsent(author,
                k -> Counter.builder("android.commands.executed")
                           .tag("author", k)
                           .description("Number of executed commands by author")
                           .register(meterRegistry));
            counter.increment();
        }
    }

    @PreDestroy
    public void shutdown() {
        if (executor != null) {
            log.info("Shutting down ThreadPoolExecutor");
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                log.error("Interrupted during ThreadPoolExecutor shutdown", e);
            }
        }
    }
}
