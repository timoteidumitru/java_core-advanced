package com.javaPlayground.concurrency.forkJoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TreeStats {

    private final long sum;
    private final long count;
    private final long max;
    private final long height;
}
