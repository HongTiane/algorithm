package com.zyb.study.algorithm.sort;

public class InsertionSort extends AbstractSort {
    @Override
    protected void doSort(Comparable[] data) {
        for(int i = 1; i < data.length; ++i) {
            Comparable<?> currentElement = data[i];
            int j = i - 1; // 从有序序列的最后一个元素开始比较
            for( ; j >= 0; --j) {
                if(comparator.compare(data[j], currentElement)> 0) {
                    data[j + 1] = data[j]; // 如果大于当前的元素，后移一位
                } else { // 否则结束当前循环
                    break;
                }
            }
            data[j + 1] = currentElement; // 当前元素插入到当前有序序列元素的下一个位置
        }
    }
}
