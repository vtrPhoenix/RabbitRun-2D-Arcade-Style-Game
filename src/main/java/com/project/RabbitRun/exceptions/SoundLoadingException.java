package com.project.RabbitRun.exceptions;

public class SoundLoadingException extends RuntimeException{

    // Optional field to store the name or path of the sound causing the issue
    private final String soundPath;

    /**
     * Constructor with message and cause.
     *
     * @param message Detailed error message.
     * @param cause   Underlying exception cause.
     */
    public SoundLoadingException(String message, Throwable cause) {
        super(message, cause);
        this.soundPath = null; // No specific sound path provided
    }

    /**
     * Constructor with message, cause, and the sound path.
     *
     * @param message   Detailed error message.
     * @param cause     Underlying exception cause.
     * @param soundPath Path of the sound causing the exception.
     */
    public SoundLoadingException(String message, Throwable cause, String soundPath) {
        super(message, cause);
        this.soundPath = soundPath;
    }

    /**
     * Constructor with message only.
     *
     * @param message Detailed error message.
     */
    public SoundLoadingException(String message) {
        super(message);
        this.soundPath = null; // No specific sound path provided
    }

    /**
     * Gets the path of the sound that caused the exception.
     *
     * @return The sound path, or null if not specified.
     */
    public String getSoundPath() {
        return soundPath;
    }

    @Override
    public String toString() {
        return super.toString() + (soundPath != null ? " [Sound Path: " + soundPath + "]" : "");
    }

}
