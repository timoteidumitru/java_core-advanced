package com.javaPlayground.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindProductOfAListExceptCurrentElement {
    public static void main(String[] args) {
        //List<Integer> input = Arrays.asList(-1, 1, 0, -3, 3);
        List<Integer> input = Arrays.asList(1, 2, 3, 4);

        findProductOfAListExceptCurrentElement(input);
    }

    private static void findProductOfAListExceptCurrentElement(List<Integer> input) {
        // Output list (same size as input)
        List<Integer> res = new ArrayList<>(Arrays.asList(new Integer[input.size()]));
        int n = input.size();

        // Step 1: Compute prefix product and store in res
        int prefix = 1;
        for (int i = 0; i < n; i++) {
            res.set(i, prefix);
            prefix *= input.get(i);
        }

        // Step 2: Compute suffix product on the fly and update res
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            res.set(i, res.get(i) * suffix);
            suffix *= input.get(i);
        }

        System.out.println(res);
    }
}
