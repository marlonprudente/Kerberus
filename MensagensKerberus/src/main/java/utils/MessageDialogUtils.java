/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.JOptionPane;

/**
 *
 * @author Vitor Tozzi
 */
public class MessageDialogUtils {
    
    public static void showDialog(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public static void showErrorEncryptDialog(){
        JOptionPane.showMessageDialog(null, "Houve um erro ao criptografar a mensagem.\nVerifique se a senha ou chave de sessão estão corretas.");
    }
    
    public static void showErrorDecryptDialog(){
        JOptionPane.showMessageDialog(null, "Houve um erro ao descriptografar a mensagem.\nVerifique se a senha ou chave de sessão estão corretas.");
    }
}
