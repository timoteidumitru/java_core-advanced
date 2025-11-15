package com.javaPlayground.concurrency.threads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChunkDownloader extends Thread {
    private String fileUrl;
    private int start;
    private int end;

    public ChunkDownloader(String fileUrl, int start, int end, String name) {
        super(name);
        this.fileUrl = fileUrl;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        System.out.println(getName() + " downloading bytes " + start + " to " + end);
        try {
            Thread.sleep(1000); // simulate download
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted!");
            return;
        }
        System.out.println(getName() + " finished.");
    }

    public static void main(String[] args) throws Exception {

        Thread p1 = new ChunkDownloader("http://file.com", 0,   1000, "Part-1");
        Thread p2 = new ChunkDownloader("http://file.com", 1001,2000, "Part-2");
        Thread p3 = new ChunkDownloader("http://file.com", 2001,3000, "Part-3");

        p1.start();
        p2.start();
        p3.start();

        // Wait for all parts to complete
        p1.join();
        p2.join();
        p3.join();

        System.out.println("All parts downloaded. Merging file...");
    }
}

