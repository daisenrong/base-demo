package com.lazydsr.base.demo.basedemo;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.BitSet;

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

    @Test
    public void test01(){
        BitSet bitSet = new BitSet(1 << 24);
        System.out.println(bitSet.size());
        bitSet.set(1);
    }

    @Test
    public void test02(){
        long count = 2L;
        for (int i = 2; i < 65; i++) {
            count=count*2;
            System.out.println(i + "  次  =   " + count);
        }
    }

    @Test
    public void test03(){
        LocalDate now = LocalDate.now();
        System.out.println("ssss"+now);
    }

    @Test
    public void test04() {
        System.out.println(Short.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Double.MAX_VALUE);
    }

    @Test
    public void test05(){
        LocalDateTime registerTimeDayOfLastMonth =
                LocalDate.parse("2019-01-01", DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay()
                        .with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(registerTimeDayOfLastMonth.toString());
    }




}
