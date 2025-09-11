package com.example;

import java.security.MessageDigest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * --- Day 4: The Ideal Stocking Stuffer ---
 *
 * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as
 * gifts for all the economically forward-thinking little girls and boys.
 *
 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at
 * least five zeroes. The input to the MD5 hash is some secret key (your puzzle
 * input, given below) followed by a number in decimal. To mine AdventCoins, you
 * must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...)
 * that produces such a hash.
 *
 * For example:
 *
 * If your secret key is abcdef, the answer is 609043, because the MD5 hash of
 * abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest
 * such number to do so.
 * If your secret key is pqrstuv, the lowest number it combines with to make an
 * MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of
 * pqrstuv1048970 looks like 000006136ef....
 *
 * Your puzzle input is yzbqklnj.
 */
public class Day_2015_04 {
    public static final String INPUT = "yzbqklnj";
    public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    public static final int ZEROES = 10; // Number of leading zeroes to find (1 to 24)
    // For 5 zeroes: check digest[0]==0 && digest[1]==0 && (digest[2] & 0xF0) == 0
    // For 6 zeroes: digest[0]==0 && digest[1]==0 && digest[2]==0
    // For 7 zeroes: digest[0]==0 && digest[1]==0 && digest[2]==0 && (digest[3] &
    // 0xF0) == 0
    // For 8 zeroes: digest[0]==0 && digest[1]==0 && digest[2]==0 && digest[3]==0

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        String key = INPUT;
        AtomicInteger currentNumber = new AtomicInteger(0);
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger foundNumber = new AtomicInteger(-1);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    StringBuilder sb = new StringBuilder(key);
                    while (!found.get()) {
                        int number = currentNumber.getAndIncrement();
                        sb.setLength(key.length());
                        sb.append(number);
                        byte[] textBytes = sb.toString().getBytes();
                        md.update(textBytes);
                        byte[] digest = md.digest();
                        // Check for leading zero bytes in hex
                        boolean isValid = true;
                        int bytes = ZEROES / 2;
                        for (int j = 0; j < bytes; j++) {
                            if (digest[j] != 0) {
                                isValid = false;
                                break;
                            }
                        }
                        if (isValid && ZEROES % 2 == 1) {
                            if ((digest[bytes] & 0xF0) != 0) {
                                isValid = false;
                            }
                        }
                        if (isValid) {
                            if (found.compareAndSet(false, true)) {
                                foundNumber.set(number);
                                // Build hash string only when found
                                StringBuilder hashSb = new StringBuilder();
                                for (byte b : digest) {
                                    hashSb.append(String.format("%02x", b & 0xff));
                                }
                                System.out.println("Found number: " + number + " with hash: " + hashSb.toString());
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(10); // Small sleep to avoid busy waiting
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Time taken: " + elapsedTime + " seconds");

        if (found.get()) {
            System.out.println("Lowest number: " + foundNumber.get());
        } else {
            System.out.println("No number found.");
        }
    }
}
