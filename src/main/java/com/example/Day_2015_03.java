package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
 *
 * Santa is delivering presents to an infinite two-dimensional grid of houses.
 *
 * He begins by delivering a present to the house at his starting location, and
 * then an elf at the North Pole calls him via radio and tells him where to move
 * next. Moves are always exactly one house to the north (^), south (v), east
 * (>), or west (<). After each move, he delivers another present to the house
 * at his new location.
 *
 * However, the elf back at the north pole has had a little too much eggnog, and
 * so his directions are a little off, and Santa ends up visiting some houses
 * more than once. How many houses receive at least one present?
 *
 * For example:
 *
 * > delivers presents to 2 houses: one at the starting location, and one to the
 * east.
 * ^>v< delivers presents to 4 houses in a square, including twice to the house
 * at his starting/ending location.
 * ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2
 * houses.
 *
 * --- Part Two ---
 *
 * The next year, to speed up the process, Santa creates a robot version of
 * himself, Robo-Santa, to deliver presents with him.
 *
 * Santa and Robo-Santa start at the same location (delivering two presents to
 * the same starting house), then take turns moving based on instructions from
 * the elf, who is eggnoggedly reading from the same script as the previous
 * year.
 *
 * This year, how many houses receive at least one present?
 *
 * For example:
 *
 * ^v delivers presents to 3 houses, because Santa goes north, and then
 * Robo-Santa goes south.
 * ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back
 * where they started.
 * ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction
 * and Robo-Santa going the other.
 *
 *
 *
 */
public class Day_2015_03 {
    private static final String PATH_DATA = "src" + File.separator + "data" + File.separator;
    public static int[][] posSanta = { { 0, 0 } };
    public static int[][] posRoboSanta = { { 0, 0 } };
    public static HashMap<String, Integer> alreadyVisited = new HashMap<>();

    public static int[][] increasePos(int[][] pos, int mov) {
        if (mov == '^') {
            pos[0][0] += 1;
        } else if (mov == 'v') {
            pos[0][0] -= 1;
        } else if (mov == '<') {
            pos[0][1] -= 1;
        } else if (mov == '>') {
            pos[0][1] += 1;
        }

        String key = pos[0][0] + "," + pos[0][1];
        if (alreadyVisited.containsKey(key)) {
            alreadyVisited.put(key, alreadyVisited.get(key) + 1);
        } else {
            alreadyVisited.put(key, 1);
        }

        return pos;
    }

    public static void main(String[] args) {
        String fichier = "Day_2015_03.txt";
        alreadyVisited.put("0,0", 1);
        try (Scanner scanner = new Scanner(new FileInputStream(PATH_DATA + fichier))) {
            while (scanner.hasNextLine()) {
                String ch = scanner.nextLine();
                ch = ch.trim();

                if (!ch.isEmpty()) {
                    for (int i = 0; i < ch.length(); i++) {

                        char chr = ch.charAt(i);
                        if (i % 2 == 0) {
                            posRoboSanta = increasePos(posRoboSanta, chr);
                        } else {
                            posSanta = increasePos(posSanta, chr);
                        }
                    }
                }
            }
        } catch (FileNotFoundException fne) {
            System.err.println(fichier + " is missing ! ");
        }

        System.out.println("result: " + alreadyVisited.size());
    }
}
