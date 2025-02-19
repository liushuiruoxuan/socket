package com.xt.guigu.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello,尚硅谷";
        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");

        // 获取一个channel
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入buffer
        byteBuffer.put(str.getBytes());

        //对buffer进行反转 filp
        byteBuffer.flip();

        //将byteBuffer 写入到FileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
