package com.taotao.util;

import org.junit.Test;

/**
 * Created by Administrator on 2017/3/27.
 */
public class TestSecond {
    @Test
    public void testSecond(){
        for(int i=0;i<100;i++){
          //  long currentTimeMillis = System.currentTimeMillis();
            long currentTimeMillis = System.nanoTime();
            System.out.println(currentTimeMillis);
        }

    }
}
