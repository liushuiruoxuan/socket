package com.xt.tcp;

import lombok.var;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws IOException {
            Socket sock = new Socket("127.0.0.1", 20388);
            try (InputStream input = sock.getInputStream()) {
                try  (OutputStream output = sock.getOutputStream()) {
                    handle(input, output);
                }
            }
            sock.close();
    }

    private static void handle(InputStream input, OutputStream output){
        try {
            var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
            var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print(">>> "); // 打印提示
                String s = scanner.nextLine(); // 读取一行输入
                writer.write(s);
                writer.newLine();
                writer.flush();
                String resp = reader.readLine();
                System.out.println("<<< " + resp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
