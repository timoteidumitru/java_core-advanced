package com.javaPlayground.javaBatch.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EmployeePartitioner implements Partitioner {

    private static final Logger logger = LoggerFactory.getLogger(EmployeePartitioner.class);

    private final int min;
    private final int max;

    public EmployeePartitioner(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        if (gridSize <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0.");
        }

        int range = max - min + 1;
        int targetSize = (range + gridSize - 1) / gridSize;
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 1;
        int start = min;

        while (start <= max) {
            int end = Math.min(start + targetSize - 1, max);

            ExecutionContext value = new ExecutionContext();
            value.putInt("min-value", start);
            value.putInt("max-value", end);

            result.put("partition " + number, value);

            logger.debug("Partition {}: min-value={}, max-value={}", number, start, end);

            start = end + 1;
            number++;
        }

        return result;
    }
}
