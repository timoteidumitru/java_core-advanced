package com.javaPlayground.concurrency.forkJoin;


public class TreeStats {
    public long sum;
    public long count;
    public long max;
    public long height;

    public TreeStats(long sum, long count, long max, long height) {
        this.sum = sum;
        this.count = count;
        this.max = max;
        this.height = height;
    }

    public void combine(TreeStats other) {
        this.sum += other.sum;
        this.count += other.count;
        this.max = Math.max(this.max, other.max);
        this.height = Math.max(this.height, other.height);
    }

    @Override
    public String toString() {
        return "TreeStats { sum=" + sum +
                ", count=" + count +
                ", max=" + max +
                ", height=" + height + " }";
    }
}

