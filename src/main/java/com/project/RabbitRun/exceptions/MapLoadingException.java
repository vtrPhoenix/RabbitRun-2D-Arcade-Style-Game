package com.project.RabbitRun.exceptions;

public class MapLoadingException extends RuntimeException{

    private final String imagePath;

    /**
     * Constructor with message and cause.
     *
     * @param message Detailed error message.
     * @param cause   Underlying exception cause.
     */
    public MapLoadingException(String message, Throwable cause) {
        super(message, cause);
        this.imagePath = null; // No specific image path provided
    }

    /**
     * Constructor with message, cause, and the image path.
     *
     * @param message   Detailed error message.
     * @param cause     Underlying exception cause.
     * @param imagePath Path of the image causing the exception.
     */
    public MapLoadingException(String message, Throwable cause, String imagePath) {
        super(message, cause);
        this.imagePath = imagePath;
    }

    /**
     * Constructor with message only.
     *
     * @param message Detailed error message.
     */
    public MapLoadingException(String message) {
        super(message);
        this.imagePath = null; // No specific image path provided
    }

    /**
     * Gets the path of the image that caused the exception.
     *
     * @return The image path, or null if not specified.
     */
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return super.toString() + (imagePath != null ? " [Image Path: " + imagePath + "]" : "");
    }

}
