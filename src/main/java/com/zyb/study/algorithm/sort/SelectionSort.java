package com.zyb.study.algorithm.sort;

public class SelectionSort extends AbstractSort {
    @Override
    protected void doSort(Comparable[] data) {
        for(int i = 0; i < data.length - 1; ++i) {
            int minElemntIndex = i; // 保存无序区间最小元素的下标
            for(int j = i + 1; j < data.length; ++j) {
                if(comparator.compare(data[j], data[minElemntIndex]) < 0) {
                    minElemntIndex = j;
                }
            }
            if(minElemntIndex == i) {
                continue;
            }
            swap(data, i, minElemntIndex);
        }
    }
}
