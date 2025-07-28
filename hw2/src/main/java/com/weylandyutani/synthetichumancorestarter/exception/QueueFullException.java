package com.weylandyutani.synthetichumancorestarter.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueueFullException extends RuntimeException {

    private final String queueIdentifier;
    private final int capacity;
    private final LocalDateTime timestamp;

    public QueueFullException(String message) {
        super(message);
        this.queueIdentifier = "UnknownQueue";
        this.capacity = 0;
        this.timestamp = LocalDateTime.now();
    }

    public QueueFullException(String queueIdentifier, int capacity) {
        super(String.format("Queue '%s' is full. Maximum capacity: %d", queueIdentifier, capacity));
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.timestamp = LocalDateTime.now();
    }

    public QueueFullException(String queueIdentifier, int capacity, String message) {
        super(message);
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.timestamp = LocalDateTime.now();
    }

    public QueueFullException(String queueIdentifier, int capacity, String message, Throwable cause) {
        super(message, cause);
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.timestamp = LocalDateTime.now();
    }
}
