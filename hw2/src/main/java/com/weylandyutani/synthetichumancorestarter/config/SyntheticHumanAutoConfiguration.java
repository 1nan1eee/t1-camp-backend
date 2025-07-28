package com.weylandyutani.synthetichumancorestarter.config;

import com.weylandyutani.synthetichumancorestarter.aspect.AuditAspect;
import com.weylandyutani.synthetichumancorestarter.service.AndroidMetrics;
import com.weylandyutani.synthetichumancorestarter.service.CommandProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableAspectJAutoProxy
public class SyntheticHumanAutoConfiguration {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(2, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
    }

    @Bean
    public CommandProcessor commandProcessor(AndroidMetrics androidMetrics, ThreadPoolExecutor threadPoolExecutor) {
        return new CommandProcessor(androidMetrics, threadPoolExecutor);
    }

    @Bean
    public AndroidMetrics androidMetrics(MeterRegistry meterRegistry, ThreadPoolExecutor threadPoolExecutor) {
        return new AndroidMetrics(meterRegistry, threadPoolExecutor);
    }

    @Bean
    public AuditAspect auditAspect(KafkaTemplate<String, String> kafkaTemplate) {
        return new AuditAspect(kafkaTemplate);
    }
}
