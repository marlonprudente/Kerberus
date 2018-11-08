/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Marlon Prudente
 */
public class CryptoUtils {

    Cipher cipher;
    SecretKeySpec secretKey;
    private byte[] key;
    private static final String transformation = "DES";

    public CryptoUtils(String password) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        password = password.substring(0, 8);
        this.key = password.getBytes();
        secretKey = new SecretKeySpec(this.key, transformation);       
        this.cipher = Cipher.getInstance(transformation);
    }

    public SealedObject encrypt(Serializable ticket) throws InvalidKeyException, IOException, IllegalBlockSizeException {

// Length is 16 byte
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        SealedObject sealedObject = new SealedObject(ticket, cipher);
        // Wrap the output stream
        return sealedObject;
    }

    public Object decrypt(Serializable ticket) throws InvalidKeyException, IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        SealedObject sealedObject;
        sealedObject = (SealedObject) ticket;
        return sealedObject.getObject(cipher);
    }

}
