package com.example.store.core;

public class StoreException extends Exception {
    String code;
    String message;

    public StoreException(Error error) {
        super(error.code + "::" + error.message);
        this.code = error.code;
        this.message = error.message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
