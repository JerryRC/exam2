package com.java.service;

import com.java.bean.Message;
import com.java.bean.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The service instance realizes the function in the requirement.
 */
public class MessageService extends UnicastRemoteObject implements MessageInterface {

    private static final long serialVersionUID = 1L;
    private final List<User> userList = new ArrayList<>();

    //必须定义构造方法，即使是默认构造方法，也必须把它明确地写出来，因为它必须抛出出RemoteException异常
    public MessageService() throws RemoteException {
    }

    public String echo(String msg) {
        System.out.println("receive: " + msg);
        return "[rmi echo]: " + msg;
    }

    @Override
    public User findUser(String name) {
        for (User u : userList) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean register(String name, String password) {
        for (User u : userList) {
            if (u.getName().equals(name)) {
                return false;
            }
        }
        User u = new User(name, password);
        userList.add(u);
        return true;
    }

    @Override
    public boolean login(String name, String password) {
        for (User u : userList) {
            if (u.getName().equals(name)) {
                return u.getPassword().equals(password);
            }
        }
        return false;
    }

    @Override
    public String leaveMsg(String name, String toPerson, Date time, String msg) {
        User creator = findUser(name);
        User other = findUser(toPerson);
        //查看用户是否存在
        if (creator == null || other == null) {
            return "User not found";
        }

        Message message = new Message(msg, name, toPerson, time);

        //没有错误则发送消息
        other.getMessageList().add(message);

        return "Leaving msg successfully";
    }

    @Override
    public List<String> checkMsg(String name){
        User user = findUser(name);
        //查看用户是否存在
        if (user == null) {
            return null;
        }

        List<String> msg = new ArrayList<>();
        for(Message m: user.getMessageList()){
            msg.add(m.toString());
        }

        return msg;
    }

    @Override
    public List<String> showUsers() {
        List<String> users = new ArrayList<>();
        for(User u:userList){
            users.add(u.toString());
        }
        return users;
    }
}
