package com.zyb.study.algorithm.lru;

import com.zyb.study.algorithm.Linked.LinkedList;

public class LinkedListLruCache<T> {

    private final LinkedList<T> cache;

    private final int cacheSize;

    public LinkedListLruCache(final int cacheSize) {
        if(cacheSize < 1) {
            throw new IllegalArgumentException("cache size: " + cacheSize);
        }
        cache = new LinkedList<>();
        this.cacheSize = cacheSize;
    }

    public T get(T t) {
        if(cache.contains(t)) {
            cache.remove(t);
            cache.insert(0, t);
            return t;
        }
        return null;
    }

    public void add(T e) {
        if(cache.contains(e)) {
            cache.remove(e);
        } else {
            if(cache.length() == cacheSize) {
                cache.remove(cacheSize - 1);
            }
        }
        cache.insert(0, e);
    }

    public boolean remove(T t) {
        if(cache.contains(t)) {
            return cache.remove(t);
        }
        return false;
    }

}
