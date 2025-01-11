package com.xt.msb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        //客户端通信必备
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        //服务器通讯地址
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 8888);
        try {
            socket = new Socket();
            //连接服务器
            socket.connect(isa);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            //发送数据
            oos.writeUTF("我是客户端");
            oos.flush();

            //接收数据
            System.out.println(ois.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                oos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
