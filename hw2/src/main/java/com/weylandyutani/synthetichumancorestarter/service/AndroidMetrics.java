package com.weylandyutani.synthetichumancorestarter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AndroidMetrics {
    private final MeterRegistry meterRegistry;
    private final ThreadPoolExecutor executor;
    private final Map<String, Counter> executedCommandsByAuthor = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Gauge.builder("android.queue.size", executor.getQueue(), Queue::size)
             .description("Current size of command queue")
             .register(meterRegistry);
    }

    public void recordCommandExecution(String author) {
        Counter counter = executedCommandsByAuthor.computeIfAbsent(author,
            k -> Counter.builder("android.commands.executed")
                       .tag("author", k)
                       .description("Number of executed commands by author")
                       .register(meterRegistry));
        counter.increment();
    }
}
