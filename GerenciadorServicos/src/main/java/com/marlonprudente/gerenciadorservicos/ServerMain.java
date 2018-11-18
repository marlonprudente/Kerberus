/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.gerenciadorservicos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.SealedObject;
import message.ServerMessage;
import message.ServerSessionTicket;
import message.ServerTicket;
import message.Ticket;
import utils.CryptoUtils;
import utils.Hash;
import utils.TimeUtils;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ServerMain {
    public static void main(String[] args) {
                try {
            int serverPort = 8002; // the Server port
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
    public static CryptoUtils criptoServer = null;
    public static CryptoUtils criptoSession = null;
    Socket clientSocket;
    Database db = new Database();
    String serverKey = db.usuarios.get("server");
    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            criptoServer = new CryptoUtils(serverKey);
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (Exception e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {			                 // an echo server
            ServerMessage data = (ServerMessage)inStream.readObject();	                  // read a line of data from the stream
            ServerTicket serverTicket = (ServerTicket)criptoServer.decrypt(data.getServerTicket());
            String sessionServidorKey = serverTicket.sessionKeyServidor;
            criptoSession = new CryptoUtils(Hash.stringHexa(Hash.gerarHash(sessionServidorKey, "MD5")));
            ServerSessionTicket sessionTicket = (ServerSessionTicket)criptoSession.decrypt(data.getSessionTicket()); 
            System.out.println("Cliente: " + sessionTicket.clienteID);
            Ticket ticket = new Ticket(sessionTicket.clienteID, sessionTicket.tempo, sessionTicket.servico, sessionTicket.numeroAleatorio);
            String resposta = "Cliente " + sessionTicket.clienteID;
            if(TimeUtils.checkValidTimestamp(sessionTicket.tempo)){
                resposta += " >Autorizado para usar o serviço: " + sessionTicket.servico;
            }else{
                resposta += " Não está autorizado para usar este serviço, Prazo expirado!";
            }
            ticket.setResposta(resposta);
            SealedObject ticketCripto = criptoSession.encrypt(ticket);
            data.setTicket(ticketCripto);
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
