package com.feicui365.live.eventbus;

public class LoginChangeBus {
    public String message;
    public static LoginChangeBus getInstance(String message) {
        return new LoginChangeBus(message);
    }

    private LoginChangeBus(String message) {
        this.message = message;
    }
}
