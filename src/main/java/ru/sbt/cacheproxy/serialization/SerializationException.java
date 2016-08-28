package ru.sbt.cacheproxy.serialization;

/**
 * Created by Никита on 24.08.2016.
 */
public class SerializationException extends Exception {

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
