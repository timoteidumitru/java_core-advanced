package com.javaPlayground.concurrency.forkJoin;

import lombok.Data;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Data
public class Node {

    private final long value;
    private final List<Node> children = new ArrayList<>();

    public Node(long value) {
        this.value = value;
    }

    public void addChild(Node child) {
        children.add(child);
    }

}
