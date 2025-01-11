package com.xt.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Run {
    private final static int PORT = 30000;
    public static HashMap<String, Socket> socketList = new HashMap<>();
    public static String channelToken;  //socket 令牌
    private static BufferedReader bufferedReader;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("server is listenning...");
            while (true) {//不断循环随时等待新的客户端接入服务器
                Socket clientSocket = server.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                channelToken = bufferedReader.readLine();
                socketList.put(channelToken, clientSocket);   //保存会话ID和socket
                System.out.println(socketList.size());
                System.out.println(socketList);
                new ServerThread(clientSocket, socketList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
