/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.as;

import asmessage.ASMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    Socket clientSocket;

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

            System.out.println("Cliente: " + data.getUsuario());
            outStream.writeObject(data);

            
            //String data = (String) Oin.readObject();                  // read a line of data from the stream
//            if (data != null) {
//                System.out.println("" + data);
//                Oout.writeObject(data + " alguma coisa");
//            }

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
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
