/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.as;

import message.ASMessage;
import message.ASTicket;
import message.TGSTicket;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import utils.CryptoUtils;
import utils.RandomUtils;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ASMain {

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            int serverPort = 8000; // the Auth Server port
            ServerSocket listenSocket = new ServerSocket(serverPort);

            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connected with: " + clientSocket.getRemoteSocketAddress());
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

}

class Connection extends Thread {
    
    ObjectInputStream inStream = null;
    ObjectOutputStream outStream = null;
    public static CryptoUtils criptoCliente = null;
    public static CryptoUtils criptoTgs = null;
    Socket clientSocket;
    Database db = new Database();
    public Connection(Socket aClientSocket) throws ClassNotFoundException {
        try {
            clientSocket = aClientSocket;            
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            ASMessage data = (ASMessage)inStream.readObject();
            String usuario = data.getUsuario();
            System.out.println("Cliente: " + usuario);
            String pass = db.usuarios.get(usuario);
            criptoCliente =  new CryptoUtils(pass);
            ASTicket asticket = (ASTicket)criptoCliente.decrypt(data.getASTicket());
            String sessionKey = String.valueOf(RandomUtils.getRandom(100000000));
            System.out.println("SessionKey: " + sessionKey);
            asticket.setSessionKey(sessionKey);
            TGSTicket tgsTicket = new TGSTicket(usuario, asticket.timeStamp, sessionKey);
            criptoTgs = new CryptoUtils(db.usuarios.get("tgs"));
            SealedObject asticketcrypto = criptoCliente.encrypt(asticket);
            SealedObject tgsticketcrypto = criptoTgs.encrypt(tgsTicket);
            data.setASTicket(asticketcrypto);
            data.setTGSTicket(tgsticketcrypto);
            outStream.writeObject(data);

            
            //String data = (String) Oin.readObject();                  // read a line of data from the stream
//            if (data != null) {
//                System.out.println("" + data);
//                Oout.writeObject(data + " alguma coisa");
//            }

        } catch (Exception e) {
            System.out.println("Erro:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                inStream.close();
                outStream.close();

            } catch (IOException e) {/*close failed*/
            }
        }

    }
}
