package com.javaPlayground.comparableAndComparator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentCollections {
    public static void main(String[] args) {
        // Concurrent collection example on Arraylists
//        List<Integer> nums = new ArrayList<>(); // will result in a ConcurrentException
        List<Integer> nums = new CopyOnWriteArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);

        Iterator<Integer> listIterator = nums.iterator();

//        while (listIterator.hasNext()){
//            System.out.println(listIterator.next());
//            nums.add(4);
//        }

        // Concurrent collection example on Hashmap

//        Map<Integer, String> pairs = new HashMap<>(); // will result in ConcurrentModificationException
        Map<Integer, String> pairs = new ConcurrentHashMap<>();
        pairs.put(1, "a");
        pairs.put(2, "b");

        Iterator<Integer> pairsIterator = pairs.keySet().iterator();

        while (pairsIterator.hasNext()){
            System.out.println(pairsIterator.next());
            pairs.put(3, "c");
        }

    }
}
