/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.tgs;

import message.TGSMessage;
import message.TGSTicket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.SealedObject;
import message.ServerTicket;
import message.TGSSessionTicket;
import utils.CryptoUtils;
import utils.Hash;
import utils.RandomUtils;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class TGSMain {
    public static void main(String[] args) {
                try {
            int serverPort = 8001; // the Ticket Gen Server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
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
    public static CryptoUtils criptoSession = null;
    public static CryptoUtils criptoTgs = null;
    public static CryptoUtils criptoServer = null;
    Socket clientSocket;
    Database db = new Database();
    String tgsKey = db.usuarios.get("tgs");
    String serverKey = db.usuarios.get("server");

    public Connection(Socket aClientSocket) {
        try {
            criptoTgs = new CryptoUtils(tgsKey);
            criptoServer = new CryptoUtils(serverKey);
            clientSocket = aClientSocket;
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (Exception e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            TGSMessage data = (TGSMessage)inStream.readObject();
            TGSTicket ticketTgs = (TGSTicket)criptoTgs.decrypt(data.getTGSTicket());
            String sessionTGSKey = ticketTgs.sessionKey;
            criptoSession = new CryptoUtils(Hash.stringHexa(Hash.gerarHash(sessionTGSKey, "MD5")));
            TGSSessionTicket tgssTicket = (TGSSessionTicket)criptoSession.decrypt(data.getSessionTicket()); 
            System.out.println("Cliente: " + tgssTicket.clienteID);
            String sessionKeyServer = String.valueOf(RandomUtils.getRandom(100000000));
            System.out.println("SessionKeyServidor: " + sessionKeyServer);
            tgssTicket.setSessionKey(sessionKeyServer);
            ServerTicket serverTicket = new ServerTicket(tgssTicket.clienteID, tgssTicket.timeStamp, sessionKeyServer);
            SealedObject serverTicketCripto = criptoServer.encrypt(serverTicket);
            SealedObject sessionTicketCripto = criptoSession.encrypt(tgssTicket);            
            data.setServerTicket(serverTicketCripto);
            data.setSessionTicket(sessionTicketCripto);
            outStream.writeObject(data);
        } catch (Exception e) {
            System.out.println("Run:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/
            }
        }

    }
}
