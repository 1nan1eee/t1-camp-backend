package com.weylandyutani.synthetichumancorestarter.service;

import com.weylandyutani.synthetichumancorestarter.exception.QueueFullException;
import com.weylandyutani.synthetichumancorestarter.model.Command;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommandProcessor {
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

        public void processCommand(Command command) throws QueueFullException {
        if (command.getPriority() == Priority.CRITICAL) {
            executeCommand(command);
        } else {
            if (executor.getQueue().remainingCapacity() == 0) {
                throw new QueueFullException("Command queue is full");
            }
            executor.submit(() -> executeCommand(command));
        }
    }

    private void executeCommand(Command command) {
        log.info("Executing command: {}", command);
    }
}
