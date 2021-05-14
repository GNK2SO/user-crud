package br.com.manager.util;

public enum HttpStatus {
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500);

    private int value;

    HttpStatus(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
