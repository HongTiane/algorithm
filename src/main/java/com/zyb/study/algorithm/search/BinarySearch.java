package com.zyb.study.algorithm.search;

import java.util.Objects;

public final class BinarySearch {

    /**
     * 二分查找
     * @param elements 待查找的元素集合
     * @param target 查找的元素
     * @return 返回查找元素的下标，如果未找到返回{@code -1}
     */
    public static int search(int[] elements, int target) {
        Objects.requireNonNull(elements);

        int low = 0;
        int high = elements.length - 1;

        while( low <= high) {
            int mid = low + ((high - low) >> 1);
            if(elements[mid] == target) {
                return mid;
            }
            if(elements[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

        }

        return -1;
    }

    /**
     * 基于递归的二分查找
     * @param elements 待查找的元素集合
     * @param target 查找的元素
     * @return 返回查找元素的下标，如果未找到返回{@code -1}
     */
    public static int search2(int[] elements, int target) {
        Objects.requireNonNull(elements);

        return searchByRecursion(elements, 0, elements.length - 1, target);
    }

    private static int searchByRecursion(int[] elements, int low, int high, int target) {
        if(low > high) {
            return -1;
        }

        int mid = low + ((high - low) >> 1);
        if(elements[mid] == target) {
            return mid;
        }
        if(elements[mid] < target) {
            return searchByRecursion(elements, mid + 1, high, target);
        } else {
            return searchByRecursion(elements, low, mid - 1, target);
        }
    }

    /**
     * 查找数组中第一个等于{@code target}的元素
     * @param elements 有序数组
     * @param target 待查找的值
     * @return 返回元素下标，如果没有找到返回{@code -1}
     */
    public static int searchFirstEqual(int[] elements, int target) {
        Objects.requireNonNull(elements);

        int low = 0;
        int high = elements.length - 1;

        while(low <= high) {
            int mid = low + ((high - low) >> 1);
            if(elements[mid] >= target) { //
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        /**
         * 在循环中{@code elements[mid] >= target}作为判断条件，说明
         *  1、只有在{@target}大于数组中最后一个元素时，才会出现{@code low > elements.length}
         *  2、最后查找的结果就是{@code elements[low]}，因为{@code high = mid - 1}
         */
        if(low < elements.length && elements[low] == target) {
            return low;
        }
        return -1;
    }

    /**
     * 查找数组中最后一个等于{@code target}的元素
     * @param elements
     * @param target
     * @return
     */
    public static int searchLastEqual(int[] elements, int target) {
        Objects.requireNonNull(elements);

        int low = 0;
        int high = elements.length - 1;

        while(low <= high) {
            int mid = low + ((high - low) >> 1);
            if(elements[mid] > target) { //
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        /**
         * 在循环中{@code elements[mid] > target}作为判断条件，说明
         *  1、只有在{@target}小于数组中第一个元素时，才会出现{@code high < 0}
         *  2、最后查找的结果就是{@code elements[high]}，因为{@code elements[mid] <= target}时{@code low = mid + 1}
         */
        if(high >= 0 && elements[high] == target) {
            return high;
        }
        return -1;
    }

    /**
     * 查找数组中第一个大于等于{@code target}的元素
     * @param elements
     * @param target
     * @return
     */
    public static int searchFirstGe(int[] elements, int target) {
        Objects.requireNonNull(elements);

        int low = 0;
        int high = elements.length - 1;

        while(low <= high) {
            int mid = low + ((high - low) >> 1);
            if(elements[mid] >= target) { //
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if(low < elements.length) {
            return low;
        }
        return -1;
    }

    /**
     * 查找数组中最后一个小于等于{@code target}的元素
     * @param elements
     * @param target
     * @return
     */
    public static int searchLastLe(int[] elements, int target) {
        Objects.requireNonNull(elements);

        int low = 0;
        int high = elements.length - 1;

        while(low <= high) {
            int mid = low + ((high - low) >> 1);
            if(elements[mid] > target) { //
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if(high >= 0) {
            return high;
        }
        return -1;
    }

    /**
     * 在循环数组中查找给定值
     * @param elements
     * @param target
     * @return
     */
    public static int searchInCircularArray(int[] elements, int target) {
        Objects.requireNonNull(elements);

        // 查找循环数组第一个元素
        int beginIndex = 0;
        for(int i = 0, n = elements.length - 1; i < n; ++i) {
            if(elements[i] > elements[i + 1]) {
                beginIndex = i + 1;
                break;
            }
        }

        int low = 0, high = elements.length - 1;

        while( low <= high) {
            int mid = low + ((high - low) >> 1);
            int currentIndex = (beginIndex + mid) % elements.length;
            if(elements[currentIndex] == target) {
                return currentIndex;
            }
            if(elements[currentIndex] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

        }
        return -1;

    }
}
