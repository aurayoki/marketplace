package com.jm.marketplace.telegram.exception;

public class TelegramBotException extends Exception {
    public TelegramBotException() {
        super();
    }

    public TelegramBotException(String message) {
        super(message);
    }

    public TelegramBotException(String message, Throwable cause) {
        super(message, cause);
    }
}
