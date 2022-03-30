package com.azamat.notificationsexample;

public class Message {
    private CharSequence text;
    private CharSequence sender;
    private long timeStamp;

    public Message(CharSequence text, CharSequence sender) {
        this.text = text;
        this.sender = sender;
        timeStamp = System.currentTimeMillis();
    }

    public CharSequence getText() {
        return text;
    }

    public CharSequence getSender() {
        return sender;
    }
    public long getTimeStamp() {
        return timeStamp;
    }

}
