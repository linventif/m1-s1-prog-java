package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * -- Day 5: Doesn't He Have Intern-Elves For This? ---
 *
 * Santa needs help figuring out which strings in his text file are naughty or
 * nice.
 *
 * A nice string is one with all of the following properties:
 *
 * It contains at least three vowels (aeiou only), like aei, xazegov, or
 * aeiouaeiouaeiou.
 * It contains at least one letter that appears twice in a row, like xx, abcdde
 * (dd), or aabbccdd (aa, bb, cc, or dd).
 * It does not contain the strings ab, cd, pq, or xy, even if they are part of
 * one of the other requirements.
 *
 * For example:
 *
 * ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...),
 * a double letter (...dd...), and none of the disallowed substrings.
 * aaa is nice because it has at least three vowels and a double letter, even
 * though the letters used by different rules overlap.
 * jchzalrnumimnmhp is naughty because it has no double letter.
 * haegwjzuvuyypxyu is naughty because it contains the string xy.
 * dvszwmarrgswjxmb is naughty because it contains only one vowel.
 *
 * How many strings are nice?
 */
public class Day_2015_05 {
    private static final String PATH_DATA = "src" + File.separator + "data" + File.separator;
    public static int sqFeet;
    public static int riFeet;

    public static void main(String[] args) {
        String fichier = "Day_2015_02.txt";
        try (Scanner scanner = new Scanner(new FileInputStream(PATH_DATA + fichier))) {
            while (scanner.hasNextLine()) {
                String ch = scanner.nextLine();
                ch = ch.trim();

                if (!ch.isEmpty()) {
                    String[] parts = ch.split("x");

                    int[] tbl = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        tbl[i] = Integer.parseInt(parts[i]);
                    }

                    int[] tblRtn = new int[parts.length];

                    // 2*l*w + 2*w*h + 2*h*l
                    // [l,w,h]
                    tblRtn[0] = tbl[0] * tbl[1];
                    tblRtn[1] = tbl[1] * tbl[2];
                    tblRtn[2] = tbl[2] * tbl[0];

                    int extra = Math.min(tblRtn[0], Math.min(tblRtn[1], tblRtn[2]));
                    int max = Math.max(tblRtn[0], Math.max(tblRtn[1], tblRtn[2]));
                    System.out.println("" + max + ":" + "");
                    sqFeet += 2 * tblRtn[0] + 2 * tblRtn[1] + 2 * tblRtn[2] + extra;

                    // cube ribon
                    // riFeet += (tbl[0] + tbl[1] + tbl[2] - max) * 2;
                    riFeet += tbl[0] * tbl[1] + tbl[2];
                }
            }
        } catch (FileNotFoundException fne) {
            System.err.println(fichier + " is missing ! ");
        }

        System.out.println("Total square feet of paper: " + sqFeet);
        System.out.println("Total square feet of rub: " + riFeet);
    }
}
