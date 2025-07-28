package com.weylandyutani.synthetichumancorestarter.aspect;

import com.weylandyutani.synthetichumancorestarter.annotation.AuditMode;
import com.weylandyutani.synthetichumancorestarter.annotation.WeylandWatchingYou;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class AuditAspect {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Around("@annotation(weylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, WeylandWatchingYou weylandWatchingYou) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        String auditMessage = String.format("Method: %s, Args: %s, Result: %s", methodName, Arrays.toString(args), result);

        if (weylandWatchingYou.mode() == AuditMode.CONSOLE) {
            log.info("Audit: {}", auditMessage);
        } else {
            kafkaTemplate.send("audit-topic", auditMessage);
        }
        return result;
    }
}
