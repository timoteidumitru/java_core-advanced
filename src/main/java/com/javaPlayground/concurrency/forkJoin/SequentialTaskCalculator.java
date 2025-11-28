package com.javaPlayground.concurrency.forkJoin;

import java.util.ArrayList;
import java.util.List;

public class SequentialTaskCalculator {

    public TreeStats compute(Node node) {
        long baseValue = TreeUtils.heavyWork(node.getValue());

        List<TreeStats> childStats = new ArrayList<>();
        for (Node child : node.getChildren()) {
            childStats.add(compute(child));
        }

        return TreeUtils.aggregate(baseValue, childStats);
    }
}
