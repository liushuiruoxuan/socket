package com.xt.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ServerThread extends Thread {
    private Socket socket;
    private PrintWriter out;
    private HashMap<String, Socket> clientList = new HashMap<>();

    public ServerThread(Socket socket, HashMap<String, Socket> socketList) throws IOException {
        this.socket = socket;
        this.clientList = socketList;
        start();
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            int read = inputStream.read();
            System.out.println(read);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Client: " + getName() + " come in...");

        //每当客户端连接上,就向相应的客户端进行回应
        Iterator<HashMap.Entry<String, Socket>> entries = clientList.entrySet().iterator();
        while (entries.hasNext()) {
            HashMap.Entry<String, Socket> entry = entries.next();
            System.out.println(entry.getKey());
            if (!String.valueOf(entry.getKey()).equals("")) {
                System.out.println(entry.getValue());
                System.out.println("-------------");
                socket = entry.getValue();
                if (socket != null) {
                    try {
                        out = new PrintWriter(socket.getOutputStream());  //回复client的ID
                        out.println(entry.getKey());
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
