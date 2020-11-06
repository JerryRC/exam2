package com.java.service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * server
 * By specifying the network location, the service is registered to the specified server.
 */
public class RMIServer {
    public static void main(String[] args) {
        try {
            //需要参数 arg 0
            if(args.length<1){
                System.out.println("You need a parameter to indicate the RMI server address");
                return;
            }

            // 启动RMI注册服务，指定端口为1099　（1099为默认端口）
            LocateRegistry.createRegistry(1099);

            // 注册一个新的实例
            MessageInterface messageService = new MessageService();

            // 把实例注册到 “另一台” RMI注册服务器上，命名为MessageService
            Naming.rebind("//" + args[0] + ":1099/MessageService", messageService);

            System.out.println("MessageService is ready");
        } catch (Exception e) {
            System.out.println("MessageService not found: " + e);
        }
    }
}
