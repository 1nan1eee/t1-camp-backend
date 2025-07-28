package com.weylandyutani.synthetichumancorestarter.service;

import com.weylandyutani.synthetichumancorestarter.exception.QueueFullException;
import com.weylandyutani.synthetichumancorestarter.model.Command;
import com.weylandyutani.synthetichumancorestarter.model.Priority;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandProcessor {
    private final AndroidMetrics androidMetrics;
    private final ThreadPoolExecutor executor;

    public void processCommand(Command command) throws QueueFullException {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        if (command.getPriority() == Priority.CRITICAL) {
            executeCommand(command);
        } else {
            if (executor.getQueue().remainingCapacity() == 0) {
                throw new QueueFullException("CommonCommandQueue", 100, "Command queue is full");
            }
            executor.submit(() -> executeCommand(command));
        }
    }

    private void executeCommand(Command command) {
        try {
            log.info("Executing command: {}", command);
            androidMetrics.recordCommandExecution(command.getAuthor());
        } catch (Exception e) {
            log.error("Error executing command: {}", command, e);
        }
    }
}
