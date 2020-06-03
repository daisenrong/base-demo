package com.lazydsr.base.demo.basedemo;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * DemoTest
 * Description : TODO
 *
 * @author : daisenrong
 * @date : 2020/06/03 18:21
 */
public class DemoTest {
    public static void main(String[] args) {
        int count = 1000000;
        BloomFilter<CharSequence> filter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), count);
        for (int i = 0; i < count; i++) {
            filter.put(i+"");
        }
        int hitCount = 0;
        for (int i = 0; i < count+10000; i++) {
            if (filter.mightContain(i + "")){
                hitCount++;
            }
        }
        System.out.println("命中数量："+hitCount);
    }
}
