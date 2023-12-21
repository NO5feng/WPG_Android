package com.example.wpg.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataUtils {
    public static List<String> getTypeItems() {
        return Arrays.asList("年", "月", "日");
    }

    public static List<Integer> getMonthData() {
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
    }

    public static List<Integer> getDayData() {
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
    }

    public static List<Integer> getYearData() {
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    }

    public static List<String> getRemindData() {
        return Arrays.asList("当天", "提前一天", "提前两天", "提前一周", "提前一个月");
    }
}

