package com.zyb.study.algorithm.sort;

public class CountingSort extends AbstractSort<Integer> {
    @Override
    protected void doSort(Integer[] elements) {
        // 查找数组最大的元素
        Integer maxRange = elements[0];
        for(int i = 1; i < elements.length; ++i) {
            if(maxRange < elements[i]) {
                maxRange = elements[i];
            }
        }

        // 创建范围数组，并且遍历待排序数组，在范围数组对应的下标加一
        int[] ranges = new int[maxRange + 1];
        for(int i = 1; i < elements.length; ++i) {
            ranges[elements[i]]++;
        }

        // 对范围数组进行顺序求和
        int rangeSum = 0;
        for(int i = 0; i < ranges.length; ++i) {
            rangeSum += ranges[i];
            ranges[i] = rangeSum;
        }

        // 创建临时数组，遍历待排序数组，根据元素在范围数组中大小，拷贝到对应临时数组中
        int[] temps = new int[elements.length];
        for(int i = elements.length - 1; i >= 0; --i) {
            temps[ranges[elements[i]]--] = elements[i];
        }

        for(int i = 0; i < temps.length; ++i) {
            elements[i] = temps[i];
        }
    }
}
