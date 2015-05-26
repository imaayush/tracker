/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.calculator;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author DEEPESH
 */
public class Password {

    private static final Random RANDOM = new SecureRandom();

    public static String pass() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";
        String pw = "";
        for (int i = 0; i < 10; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }
}
