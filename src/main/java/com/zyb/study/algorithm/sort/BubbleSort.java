package com.zyb.study.algorithm.sort;

/**
 * 冒泡排序
 */
public class BubbleSort extends AbstractSort {

    protected void doSort(Comparable[] data) {
        // 冒泡排序的过程中是否进行数据交换，如果没有，则说明已经达到完全有序，不需要再继续执行后续的冒泡操作
        boolean exchange = false;
        for(int i = 1; i < data.length; ++i) { // i = 1，减少代码的复杂性，提高效率
            for (int j = 0; j < data.length - i; ++j) {
                if(comparator.compare(data[j],data[j + 1] ) > 0) {
                    swap(data, j, j + 1);
                    exchange = true;
                }
            }
            if(!exchange) {
                break;
            }
            exchange = false;
        }
    }

}
