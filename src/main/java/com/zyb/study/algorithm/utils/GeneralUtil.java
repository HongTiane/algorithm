package com.zyb.study.algorithm.utils;

import java.util.Objects;

public class GeneralUtil {

    /**
     * 求平方根
     * @param value 待求平方根的值
     * @param accuracy 精度
     * @return 返回{@code value}的平方根
     */
    public static double sqrt(double value, int accuracy) {
        if(value < 0) {
            throw new IllegalArgumentException("value: " + value);
        }
        if(accuracy < 0) {
            throw new IllegalArgumentException("accuracy: " + accuracy);
        }

        double result = 0;
        // 求整数部分
        if(value >= 1) {
            result += calculateInteger((int) value);
        }

        // 求小数部分
        if(accuracy > 0) {
            result += calculateDouble(value, result, accuracy);
        }

        return result;
    }

    private static double calculateDouble(double value, double integerValue, int accuracy) {
        int low = 0;
        int high = 9;

        double base = 10;
        for(int i = 1; i < accuracy; ++i) {
            base *= 10;
            high = high * 10 + 9;
        }

        while(high > (low + 1)) {
            int mid = low + ((high - low) >> 1);
            double temp = integerValue + mid / base;
            int compareResult = Double.compare(value, temp * temp);
            if(compareResult == 0) {
                return mid / base;
            }
            if(compareResult > 0) {
                low = mid;
            } else {
                high = mid;
            }
        }

        double temp = integerValue + high / base;
        if(high > low && Double.compare(value, temp * temp) >= 0) {
            return high / base;
        }
        return low / base;
    }

    private static int calculateInteger(int value) {

        int low = 1;
        int high = value / 2;

        while(high > (low + 1)) {
            int mid = low  + ((high - low) >> 1);
            int square = mid * mid;
            if(square == value) {
                return mid;
            }
            if(square < value) {
                low = mid;
            } else {
                high = mid;
            }
        }

        if(high > low && high * high <= value) {
            return high;
        }

        return low;
    }

//    private static double calculateDecimalByInt() {
//
//    }

}
