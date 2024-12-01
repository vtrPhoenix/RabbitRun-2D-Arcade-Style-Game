package com.project.RabbitRun.exceptions;

public class MapLoadingException extends RuntimeException{

    private final String mapPath;

    /**
     * Constructor with message and cause.
     *
     * @param message Detailed error message.
     * @param cause   Underlying exception cause.
     */
    public MapLoadingException(String message, Throwable cause) {
        super(message, cause);
        this.mapPath = null; // No specific image path provided
    }

    /**
     * Constructor with message, cause, and the map path.
     *
     * @param message   Detailed error message.
     * @param cause     Underlying exception cause.
     * @param mapPath Path of the image causing the exception.
     */
    public MapLoadingException(String message, Throwable cause, String mapPath) {
        super(message, cause);
        this.mapPath = mapPath;
    }

    /**
     * Constructor with message only.
     *
     * @param message Detailed error message.
     */
    public MapLoadingException(String message) {
        super(message);
        this.mapPath = null; // No specific map path provided
    }

    /**
     * Gets the path of the map that caused the exception.
     *
     * @return The map path, or null if not specified.
     */
    public String getMapPath() {
        return mapPath;
    }

    @Override
    public String toString() {
        return super.toString() + (mapPath != null ? " [Map Path: " + mapPath + "]" : "");
    }

}
