package com.xt.guigu.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {

        // 创建一个Buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //取数据
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
