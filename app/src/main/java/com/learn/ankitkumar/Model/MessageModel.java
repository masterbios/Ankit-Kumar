package com.learn.ankitkumar.Model;

public class MessageModel {
    String text, sender;
    Long timestamp;

    public MessageModel(String text, String sender, Long timestamp) {
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public MessageModel() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
