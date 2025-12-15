package com.javaPlayground.concurrency.realWorldConcurrencyPatterns;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class SalaryAdjustDisruptorApproach {

    static class Event {
        Employee employee;
    }

    static record Employee(long id, String name, int age, double salary, String dept) {}

    public static void main(String[] args) throws Exception {

        long start = System.nanoTime();

        Path out = Path.of(
                "src/main/resources/IO_operations/concurrency/disruptor.csv"
        );
        BufferedWriter writer = Files.newBufferedWriter(out);
        writer.write("ID,Name,Age,Salary,Department\n");

        int bufferSize = 8192;

        Disruptor<Event> disruptor = new Disruptor<>(
                Event::new,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        AtomicLong counter = new AtomicLong();

        disruptor.handleEventsWith((event, seq, end) -> {
            Employee e = adjustSalary(event.employee);
            writer.write(e.id()+","+e.name()+","+e.age()+","+e.salary()+","+e.dept()+"\n");
            counter.incrementAndGet();
        });

        RingBuffer<Event> ring = disruptor.start();

        Files.lines(Path.of("src/main/resources/IO_operations/large_dataset.csv"))
                .skip(1)
                .map(SalaryAdjustDisruptorApproach::parse)
                .forEach(e -> {
                    long seq = ring.next();
                    try {
                        ring.get(seq).employee = e;
                    } finally {
                        ring.publish(seq);
                    }
                });

        disruptor.shutdown();
        writer.close();

        long timeMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println("=========================================");
        System.out.println("Disruptor Salary Adjustment");
        System.out.println("Records processed : " + counter.get());
        System.out.println("Execution time    : " + timeMs + " ms");
        System.out.println("=========================================");
    }

    static Employee parse(String l) {
        String[] t = l.split(",");
        return new Employee(
                Long.parseLong(t[0]), t[1],
                Integer.parseInt(t[2]),
                Double.parseDouble(t[3]),
                t[4]
        );
    }

    static Employee adjustSalary(Employee e) {
        double s = e.salary();
        if (s < 50_000) s *= 1.15;
        else if (s <= 75_000) s *= 1.05;
        else if (s <= 100_000) s *= 1.07;
        else s *= 1.10;
        return new Employee(e.id(), e.name(), e.age(), s, e.dept());
    }
}

