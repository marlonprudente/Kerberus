/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Vitor Tozzi
 * @author Marlon Prudente
 */
public class HashUtils {

    public static String generateSessionKey(String mensagem) throws NoSuchAlgorithmException {
        String s1 = mensagem + TimeUtils.getDate().toString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(s1.getBytes()));
        String s2 = hash.toString();
        s2 = s2.substring(0, 8);
        return s2;
    }
    
    public static String getHash(String mensagem) throws NoSuchAlgorithmException{
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(mensagem.getBytes()));
        String s2 = hash.toString(16);
        s2 = s2.substring(0, 8);
        return s2;
        
    }

}
