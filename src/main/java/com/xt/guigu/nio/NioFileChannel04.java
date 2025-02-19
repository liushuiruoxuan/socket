package com.xt.guigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NioFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("d:\\a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\a2.jpg");


        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
