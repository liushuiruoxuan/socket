package com.xt.guigu.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2：int start ：起始位置，即将该内存映射到文件内的起始位置
         * 参数3：int size：映射内存大小，即将内存映射到文件内的结束位置
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');


        randomAccessFile.close();
    }
}
