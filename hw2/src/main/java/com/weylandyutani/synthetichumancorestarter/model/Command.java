package com.weylandyutani.synthetichumancorestarter.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;
    
    @NotNull
    private Priority priority; // Используется наше перечисление Priority из пакета model
    
    @Size(max = 100, message = "Author must be less than 100 characters")
    private String author;
    
    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*", message = "Time must be in ISO 8601 format")
    private String time;
}
