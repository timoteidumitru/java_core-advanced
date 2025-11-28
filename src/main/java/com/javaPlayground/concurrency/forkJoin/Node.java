package com.javaPlayground.concurrency.forkJoin;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final long value;
    private final List<Node> children = new ArrayList<>();

    public Node(long value) {
        this.value = value;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public long getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }
}

