package net.javaguides.springboot.exception;

public class DuplicateInspectionNameException extends RuntimeException {
    public DuplicateInspectionNameException(String message) {
        super(message);
    }
}