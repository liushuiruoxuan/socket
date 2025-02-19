package com.xt.guigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel02 {
    public static void main(String[] args) throws IOException {
        //创建一个文件
        File file = new File("d:\\file01.txt");

        //创建一个输入流
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过输入流获取channel
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将 通道的数据读入到Buffer
        fileChannel.read(byteBuffer);

        //将Buffer的数据输出到控制台
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

}
