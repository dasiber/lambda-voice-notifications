package com.ufonia.model;

public class EventInput {
    String phone;
    String message;

    public EventInput() {
    }

    public EventInput(final String phone, final String message) {
        this.phone = phone;
        this.message = message;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "EventInput{" +
                "phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
