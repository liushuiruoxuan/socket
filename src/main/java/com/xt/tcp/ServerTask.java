package com.xt.tcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ServerTask {
    public static List<Socket> clients = new ArrayList<>();
    @Resource(name = "tExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostConstruct
    public void TcpServer() {
        try {
            ServerSocket ss = new ServerSocket(20388); // 监听指定端口
            log.info("TCP 服务器已经启动...");
            while (true) {
                Socket sock = ss.accept();
                //启动心跳
                sock.setKeepAlive(true);
                //设置客户端连接超时时间
                sock.setSoTimeout(90000);
                log.info("{}有人上线了{}", threadPoolTaskExecutor.getThreadNamePrefix(), sock.getRemoteSocketAddress());
                clients.add(sock);
                log.info("上线设备数量:{}", clients.size());
                threadPoolTaskExecutor.execute(new TCPServerThread(sock));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

