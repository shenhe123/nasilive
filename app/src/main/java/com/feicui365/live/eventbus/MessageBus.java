package com.feicui365.live.eventbus;

public class MessageBus {
    public String message;
    public static MessageBus getInstance(String message) {
        return new MessageBus(message);
    }

    private MessageBus(String message) {
        this.message = message;
    }
}
