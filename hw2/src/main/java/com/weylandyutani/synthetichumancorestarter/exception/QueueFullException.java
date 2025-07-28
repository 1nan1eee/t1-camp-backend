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

    /**
     * Конструктор для создания исключения с указанием идентификатора очереди и её максимальной вместимости.
     *
     * @param queueIdentifier Уникальный идентификатор очереди (например, "CommonCommandQueue").
     * @param capacity Максимальная вместимость очереди.
     */
    public QueueFullException(String queueIdentifier, int capacity) {
        super(String.format("Queue '%s' is full. Maximum capacity: %d", queueIdentifier, capacity));
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Конструктор для создания исключения с указанием идентификатора очереди, вместимости и пользовательского сообщения.
     *
     * @param queueIdentifier Уникальный идентификатор очереди.
     * @param capacity Максимальная вместимость очереди.
     * @param message Пользовательское сообщение об ошибке.
     */
    public QueueFullException(String queueIdentifier, int capacity, String message) {
        super(message);
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Конструктор для создания исключения с указанием идентификатора очереди, вместимости, сообщения и причины.
     *
     * @param queueIdentifier Уникальный идентификатор очереди.
     * @param capacity Максимальная вместимость очереди.
     * @param message Пользовательское сообщение об ошибке.
     * @param cause Причина возникновения исключения.
     */
    public QueueFullException(String queueIdentifier, int capacity, String message, Throwable cause) {
        super(message, cause);
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.timestamp = LocalDateTime.now();
    }
}
