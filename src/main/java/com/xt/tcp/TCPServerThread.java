package com.xt.tcp;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static com.xt.tcp.ServerTask.clients;

@Slf4j
public class TCPServerThread implements Runnable {

    Socket sock;

    public TCPServerThread(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {
            log.info("有人下线了{}", sock.getRemoteSocketAddress());
            try {
                this.sock.close();
                clients.remove(this.sock);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private synchronized void handle(InputStream input, OutputStream output) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("已经连接上服务器\n");
        writer.flush();
        while (true) {
            String msg = reader.readLine();
            sendMsgToAllClients(msg, writer);
        }
    }


    private void sendMsgToAllClients(String msg, BufferedWriter writer) {
        log.info("转发的客户端数量:{},转发消息:{}", clients.size(), msg);
        Iterator<Socket> iterator = clients.iterator();
        while (iterator.hasNext()) {
            Socket client = iterator.next();
            if (!client.isClosed()) {
                try {
                    writer.write(msg + "\n");
                    writer.flush();
                } catch (IOException e) {
                    // 如果发送失败，可能是Socket已经关闭，从列表中移除
                    e.printStackTrace();
                    iterator.remove();
                }
            } else {
                // 如果Socket已经关闭，直接从列表中移除
                iterator.remove();
            }
        }
    }

}
