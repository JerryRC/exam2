package com.java.service;

import com.java.bean.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface MessageInterface extends Remote {

    String echo(String msg) throws RemoteException;

    User findUser(String name) throws RemoteException;

    boolean register(String name, String password) throws RemoteException;

    boolean login(String name, String password) throws RemoteException;

    String leaveMsg(String name, String toPerson, Date time, String msg) throws RemoteException;

    List<String> checkMsg(String name) throws RemoteException;

    List<String> showUsers() throws RemoteException;


}
