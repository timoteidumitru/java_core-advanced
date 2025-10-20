package com.javaPlayground.algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class DynamicGroupSalary {
    public static void main(String[] args) {
        Map<String, Integer> data = new HashMap<>();
        data.put("Mike", 1200);
        data.put("Josh", 1400);
        data.put("Ana", 1200);
        data.put("Maria", 1400);
        data.put("Daniel", 1000);
        data.put("Alex", 1200);
        data.put("Ion", 1000);
        data.put("Vasile", 1100);
        data.put("Geo", 1700);

//        System.out.println(dynamicSalaryGrouping(data, 3));
//        System.out.println(groupAllBySalary(data));
        System.out.println(groupBySalaryRange(data, 1200, 1700));
    }
    public static Map.Entry<Integer, List<String>> dynamicSalaryGrouping(Map<String, Integer> data, Integer index){
        return data.entrySet().stream()
         .collect(Collectors.groupingBy(Map.Entry::getValue,
             Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                 .entrySet()
                     .stream()
                         .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                             .toList()
                                .get(index - 1);
    }

    public static List<Map.Entry<Integer, List<String>>> groupAllBySalary(Map<String, Integer> data){
        return data
        .entrySet()
            .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                    .entrySet()
                        .stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                                .toList();
    }

    public static List<Map.Entry<Integer, List<String>>> groupBySalaryRange(Map<String, Integer> data, Integer min, Integer max){
        return data
        .entrySet()
            .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                    .entrySet()
                        .stream()
                            .filter(e -> e.getKey() >= min && e.getKey() <= max)
                                .toList();
    }

}
