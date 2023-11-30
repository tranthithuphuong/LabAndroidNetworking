package com.example.appchat;

public class Message {
    private String userId;
    private String messageText;

    public Message() {

    }

    public Message(String userId, String messageText) {
        this.userId = userId;
        this.messageText = messageText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}