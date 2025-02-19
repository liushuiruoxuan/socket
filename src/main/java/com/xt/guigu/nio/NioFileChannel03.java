package com.xt.guigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel03 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {//循环读取

            byteBuffer.clear();

            int read = fileChannel01.read(byteBuffer);
            System.out.println(read);
            if (read == -1) {
                break;
            }

            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
