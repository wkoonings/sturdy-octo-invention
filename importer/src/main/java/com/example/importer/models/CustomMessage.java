package com.example.importer.models;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CustomMessage implements Serializable {

    private String message;
    private LocalDateTime timestamp;

}
