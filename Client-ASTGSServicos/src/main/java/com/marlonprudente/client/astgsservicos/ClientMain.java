/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.client.astgsservicos;

import message.ASMessage;
import message.TGSSessionTicket;
import message.ASTicket;
import message.TGSMessage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import message.ServerMessage;
import message.ServerSessionTicket;
import message.Ticket;
import utils.*;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ClientMain {

    static Socket s = null;
    public static CryptoUtils criptografia = null;
    public static CryptoUtils criptoSessionTgs = null;
    public static CryptoUtils criptoSessionServidor = null;

    public static void main(String[] args) {
        String login = null;
        String nomeServico = "AulaComMauro";
        Date date = TimeUtils.getDate();
        Date tempo = TimeUtils.addMinutes(date, 1);
        ASTicket ASTicket;
        SealedObject TGSTicket = null;
        SealedObject ServerTicket = null;
        String sessionKeyTGS = "";
        String sessionKeyServidor = "";
        try {
            while (true) {
                System.out.println(" 1 - Autenticar no AS / Receber Ticket para TGS");
                System.out.println(" 2 - Enviar Ticket para TGS / Receber Ticket Servidor");
                System.out.println(" 3 - Enviar Ticket para Servidor / Receber resposta!");
                Scanner scanner = new Scanner(System.in);
                Integer op = scanner.nextInt();
                switch (op) {
                    case 1:
                        System.out.println("Digite seu login: ");
                        login = scanner.next();
                        login = login.trim();
                        System.out.println("Digite sua senha: ");
                        String senha = scanner.next();
                        senha = senha.trim();
                        String numeroAleatorio1 = String.valueOf(RandomUtils.getRandom(10000));
                        System.out.println("N1 gerado pelo cliente: " + numeroAleatorio1);
                        criptografia = new CryptoUtils(Hash.stringHexa(Hash.gerarHash(senha, "MD5")));
                        ASTicket ticketas = new ASTicket(nomeServico, tempo, numeroAleatorio1);
                        SealedObject asticketcripto = criptografia.encrypt(ticketas);
                        ASMessage mensagemAS = new ASMessage(login, asticketcripto);
                        System.out.println("Conectando ao Authenticator Server...");
                        ASMessage retornoAS = ConectarAoServidorAS(8000, mensagemAS);
                        ASTicket = (ASTicket) criptografia.decrypt(retornoAS.getASTicket());
                        System.out.println("N1 recebido do AS: " + ASTicket.getRandomNumber()); 
                        sessionKeyTGS = ASTicket.getSessionKey();
                        TGSTicket = retornoAS.getTGSTicket();
                        if (retornoAS != null) {
                            System.out.println("Ticket do AS recebido com sucesso!");
                        }
                        break;
                    case 2:
                        System.out.println("Enviando requisição ao TGS...");
                        String numeroAleatorio2 = String.valueOf(RandomUtils.getRandom(10000));
                        System.out.println("N2 gerado pelo Cliente:" + numeroAleatorio2);
                        criptoSessionTgs = new CryptoUtils(Hash.stringHexa(Hash.gerarHash(sessionKeyTGS, "MD5")));
                        TGSSessionTicket tgssticket = new TGSSessionTicket(login, nomeServico, tempo, numeroAleatorio2);
                        SealedObject tgssticketcripto = criptoSessionTgs.encrypt(tgssticket);
                        TGSMessage mensagemTGS = new TGSMessage(TGSTicket, tgssticketcripto);
                        System.out.println("Enviando...");
                        TGSMessage retornoTGS = ConectarAoServidorTGS(8001,mensagemTGS);
                        ServerTicket = retornoTGS.getServerTicket();
                        TGSSessionTicket retornoSession = (TGSSessionTicket)criptoSessionTgs.decrypt(retornoTGS.getSessionTicket());
                        System.out.println("N2 recebido do TGS: " + retornoSession.randomNumber);
                        sessionKeyServidor = retornoSession.getSessionKeyServidor();
                        break;
                    case 3:
                        System.out.println("Enviando Ticket para Gerenciador de Serviços");
                        String numeroAleatorio3 = String.valueOf(RandomUtils.getRandom(10000));
                        System.out.println("N3 gerado pelo cliente: " + numeroAleatorio3);
                        criptoSessionServidor = new CryptoUtils(Hash.stringHexa(Hash.gerarHash(sessionKeyServidor, "MD5")));
                        ServerSessionTicket serversticket = new ServerSessionTicket(login,tempo,nomeServico,numeroAleatorio3);
                        SealedObject serverSessionTicketCripto = criptoSessionServidor.encrypt(serversticket);
                        ServerMessage mensagemServidor = new ServerMessage(ServerTicket, serverSessionTicketCripto);
                        ServerMessage retornoServidor = ConectarAoServidor(8002, mensagemServidor);
                        Ticket ticket = (Ticket)criptoSessionServidor.decrypt(retornoServidor.getTicket());
                        System.out.println("N3 recebido do Servidor: " + ticket.numeroAleatorio);
                        System.out.println("Resposta do Servidor: " + ticket.getResposta());
                        break;
                    default:
                        System.out.println("Opção Inválida:");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    public static ASMessage ConectarAoServidorAS(int porta, ASMessage mensagem) {
        ASMessage data = null;
        try {
            int AserverPort = porta;
            s = new Socket("localhost", AserverPort);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(mensagem);      	// UTF is a string encoding see Sn. 4.4
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            data = (ASMessage) in.readObject();	    // read a line of data from the stream            

        } catch (Exception e) {
            System.out.println("Socket:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }

            }
            return data;
        }
    }

    public static TGSMessage ConectarAoServidorTGS(int porta, TGSMessage mensagem){
        TGSMessage data = null;
        try {
            int AserverPort = porta;
            s = new Socket("localhost", AserverPort);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(mensagem);      	// UTF is a string encoding see Sn. 4.4
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            data = (TGSMessage) in.readObject();	    // read a line of data from the stream            

        } catch (Exception e) {
            System.out.println("Socket:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }

            }
            return data;
        }
    }
    
        public static ServerMessage ConectarAoServidor(int porta, ServerMessage mensagem){
        ServerMessage data = null;
        try {
            int AserverPort = porta;
            s = new Socket("localhost", AserverPort);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(mensagem);      	// UTF is a string encoding see Sn. 4.4
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            data = (ServerMessage) in.readObject();	    // read a line of data from the stream            

        } catch (Exception e) {
            System.out.println("Socket:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }

            }
            return data;
        }
    }
}
