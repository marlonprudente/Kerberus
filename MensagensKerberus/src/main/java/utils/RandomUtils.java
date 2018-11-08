/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Random;

/**
 *
 * @author Vitor Tozzi
 */
public class RandomUtils {

    static Random random;

    public static int getRandom(int bound) {

        if (bound > 0) {
            random = new Random();
            return random.nextInt(bound);
        } else {
            return 0;
        }
    }

}
