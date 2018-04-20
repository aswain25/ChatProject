package com.example.admin.chatproject.model;

import java.util.Date;

public class Message
{
    String senderID;
    String receiverID;
    String message;
    long date;

    public Message(String senderID, String receiverID, String message, long date) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.date = date;
    }

    public String getSenderID()
    {
        return senderID;
    }

    public void setSenderID(String senderID)
    {
        this.senderID = senderID;
    }

    public String getReceiverID()
    {
        return receiverID;
    }

    public void setReceiverID(String receiverID)
    {
        this.receiverID = receiverID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return senderID + ": "  + message;
    }
}
