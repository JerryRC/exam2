package com.java.bean;

import java.util.Date;

/**
 * JavaBean Message
 */
public class Message implements Comparable<Message> {
    private String msg;        //message
    private Date time;            //forming time
    private String sender;        //from person
    private String receiver;   //to person

    public Message(String msg, String sender, String receiver, Date time) {
        this.msg = msg;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString() {
        return "From " + sender + " ; Date " + time +
                "\n[Msg: " + msg + "]";
    }

    @Override
    public int compareTo(Message message) {
        return time.compareTo(message.getTime());
    }

}

