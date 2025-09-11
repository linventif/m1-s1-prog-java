package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * --- Day 2: I Was Told There Would Be No Math ---
 *
 * The elves are running low on wrapping paper, and so they need to submit an
 * order for more. They have a list of the dimensions (length l, width w, and
 * height h) of each present, and only want to order exactly as much as they
 * need.
 *
 * Fortunately, every present is a box (a perfect right rectangular prism),
 * which makes calculating the required wrapping paper for each gift a little
 * easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. The
 * elves also need a little extra paper for each present: the area of the
 * smallest side.
 *
 * For example:
 *
 * A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of
 * wrapping paper plus 6 square feet of slack, for a total of 58 square feet.
 * A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet
 * of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.
 *
 * All numbers in the elves' list are in feet. How many total square feet of
 * wrapping paper should they order?
 *
 * --- Part Two ---
 *
 * The elves are also running low on ribbon. Ribbon is all the same width, so
 * they only have to worry about the length they need to order, which they would
 * again like to be exact.
 *
 * The ribbon required to wrap a present is the shortest distance around its
 * sides, or the smallest perimeter of any one face. Each present also requires
 * a bow made out of ribbon as well; the feet of ribbon required for the perfect
 * bow is equal to the cubic feet of volume of the present. Don't ask how they
 * tie the bow, though; they'll never tell.
 *
 * For example:
 *
 * A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap
 * the present plus 2*3*4 = 24 feet of ribbon for the bow, for a total of 34
 * feet.
 * A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap
 * the present plus 1*1*10 = 10 feet of ribbon for the bow, for a total of 14
 * feet.
 *
 * How many total feet of ribbon should they order *
 *
 */
public class Day_2015_02 {
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
