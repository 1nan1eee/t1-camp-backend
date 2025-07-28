package com.bishopprototype.service;

import com.weylandyutani.synthetichumancorestarter.exception.QueueFullException;
import com.weylandyutani.synthetichumancorestarter.model.Command;
import com.weylandyutani.synthetichumancorestarter.service.CommandProcessor;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    private final CommandProcessor commandProcessor;

    public CommandService(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public String processCommand(Command command) {
        try {
            commandProcessor.processCommand(command);
            return "Command accepted for processing: " + command.getDescription();
        } catch (QueueFullException e) {
            throw new RuntimeException("Failed to process command: Queue is full. " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid command: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while processing command: " + e.getMessage(), e);
        }
    }
}
