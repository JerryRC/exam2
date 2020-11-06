package com.java.client;

import com.java.service.MessageInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author JR Chan
 * client
 * When you first enter, you need to sign up for an account.
 * If the login is successful, there is no need to input the account and password for each command.
 */
public class Client {
    private final String serverName;
    private final int port;
    private String userName;
    private String userPassword;
    private boolean hasLogin;
    MessageInterface messageInterface;

    /**
     * @param serverName the IP or domain
     * @param port       the port num --- e.g. 1099
     */
    public Client(String serverName, int port) {
        this.serverName = serverName.trim();
        this.port = port;
        this.hasLogin = false;
        this.userName = "";
        this.userPassword = "";
    }

    /**
     * @param argv IP port
     */
    public static void main(String[] argv) {
        try {
            if (argv.length < 2) {
                System.out.println("Parameter error");
                return;
            }

            Client client = new Client(argv[0], Integer.parseInt(argv[1]));
            client.run();

        } catch (Exception e) {
            System.out.println("Illegal Port Number");
        }
    }

    public void run() {
        try {
            messageInterface = (MessageInterface) Naming.lookup("//" + serverName + ":" + port + "/MessageService");
            // 调用远程方法
            System.out.println(messageInterface.echo("Good morning"));
            waiting();
        } catch (Exception e) {
            System.out.println("Method not found");
        }
    }

    public String register() throws RemoteException {
        if (messageInterface.register(userName, userPassword)) {
            hasLogin = true;
            return "Sign up successfully";
        }
        return "User already exists";
    }

    public String login() throws RemoteException {
        if (messageInterface.login(userName, userPassword)) {
            hasLogin = true;
            return "Sign in successfully";
        }
        return "Wrong user name or password";
    }

    /**
     * @param receiver the person you want to send message
     * @param msg      msg text
     * @return if it's ok
     * @throws RemoteException
     */
    public String leaveMsg(String receiver, String msg) throws RemoteException {
        //先登录
        if (!hasLogin) {
            String log = login();
            if (!log.startsWith("S")) {
                return log;
            }
        }

        if (receiver.equals(userName)) {
            return "You can't leave msg to yourself";
        }

        //get time
        Date time = new Date();
        return messageInterface.leaveMsg(userName, receiver, time, msg);
    }

    /**
     * show users list
     *
     * @return if it's ok
     * @throws RemoteException
     */
    public String showUsers() throws RemoteException {
        if (!hasLogin) {
            String log = login();
            if (!log.startsWith("S")) {
                return log;
            }
        }

        List<String> strings = messageInterface.showUsers();
        for (String s : strings) {
            System.out.println(s);
        }
        return "";
    }

    /**
     * show msg list
     *
     * @return if it's ok
     * @throws RemoteException
     */
    public String checkMsg() throws RemoteException {
        if (!hasLogin) {
            String log = login();
            if (!log.startsWith("S")) {
                return log;
            }
        }

        List<String> strings = messageInterface.checkMsg(userName);
        for (String s : strings) {
            System.out.println(s);
        }
        return "";
    }

    /**
     * waiting for input
     *
     * @throws IOException
     */
    public void waiting() throws IOException {
        while (true) {
            if (hasLogin) {
                printLoginMenu();
            } else {
                printNoLoginMenu();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String command = br.readLine();

            String result;
            StringTokenizer st = new StringTokenizer(command, " ");

            if (st.hasMoreTokens()) {
                command = st.nextToken();
            }

            if (command.startsWith("login")) {
                if (st.countTokens() < 2) {
                    result = "Parameter error";
                } else {
                    userName = st.nextToken();
                    userPassword = st.nextToken();
                    result = login();
                }
            } else if (command.startsWith("register")) {
                if (st.countTokens() < 2) {
                    result = "Parameter error";
                } else {
                    userName = st.nextToken();
                    userPassword = st.nextToken();
                    result = register();
                }
            } else if (command.startsWith("leavemsg")) {
                if (st.countTokens() < 2) {
                    result = "Parameter error";
                } else {
                    String receiver = st.nextToken();
                    StringBuilder msg = new StringBuilder(st.nextToken());
                    while (st.hasMoreTokens()) {
                        msg.append(" ").append(st.nextToken());
                    }
                    result = leaveMsg(receiver, msg.toString());
                }
            } else if (command.startsWith("showusers")) {
                result = showUsers();
            } else if (command.startsWith("checkmsg")) {
                result = checkMsg();
            } else if (command.startsWith("help")) {
                printHelp();
                result = "";
            } else if (command.startsWith("quit")) {
                break;
            } else if (command.startsWith("logout")) {
                hasLogin = false;
                result = "Sign out successfully";
            } else {
                result = "Command not found";
            }
            System.out.println(result);
        }
    }

    public void printLoginMenu() {
        System.out.println("Command Menu:");
        System.out.println("\t 1.leavemsg");
        System.out.println("\t\t arguments: <receiver name> <msg>");
        System.out.println("\t 2.showusers");
        System.out.println("\t\t arguments: no args");
        System.out.println("\t 3.checkmsg");
        System.out.println("\t\t arguments: no args");
        System.out.println("\t 4.logout");
        System.out.println("\t\t arguments: no args");
        System.out.println("\t 5.help");
        System.out.println("\t\t arguments: no args");
        System.out.println("\t 6.quit");
        System.out.println("\t\t arguments: no args");
    }

    public void printNoLoginMenu() {
        System.out.println("Command Menu:");
        System.out.println("\t 1.login");
        System.out.println("\t\t arguments: <username> <password>");
        System.out.println("\t 2.register");
        System.out.println("\t\t arguments: <username> <password>");
        System.out.println("\t 3.help");
        System.out.println("\t\t arguments: no args");
        System.out.println("\t 4.quit");
        System.out.println("\t\t arguments: no args");
    }

    public void printHelp() {
        if (!hasLogin) {
            printNoLoginMenu();
        } else {
            printLoginMenu();
        }
    }
}
