package com.java.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean User
 */
public class User implements Serializable {
    private String name;
    private String password;
    private List<Message> messageList;

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
        this.messageList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String toString() {
        return "[name: " + name + "]";
    }

}
