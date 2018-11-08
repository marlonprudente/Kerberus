/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.client.astgsservicos;


import asmessage.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import utils.*;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ClientMain {
    static Socket s = null;
    public static CryptoUtils criptografia = null;
    public static void main(String[] args) throws ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchPaddingException, NoSuchPaddingException, NoSuchPaddingException {
        int ServiceId = 1;
        int random = RandomUtils.getRandom(10000);
        Date date = TimeUtils.getDate();
        criptografia = new CryptoUtils(Hash.stringHexa(Hash.gerarHash("senha", "MD5")));
        ASTicket ticketas = new ASTicket(ServiceId, TimeUtils.addHours(date, 1),String.valueOf(random));
        SealedObject asticketcripto = criptografia.encrypt(ticketas);
        ASMessage mensagem = new ASMessage("cliente", asticketcripto);
        ConectarAoServidor(8000,mensagem);
        //ConectarAoServidor(8001,"Mensagem TGS");
        //ConectarAoServidor(8002,"Mensagem S");
    }
    
    
    public static void ConectarAoServidor(int porta, ASMessage mensagem) throws ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException{        
		try{
			int AserverPort = porta;
			s = new Socket("localhost", AserverPort);                        
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(mensagem);      	// UTF is a string encoding see Sn. 4.4
                        ObjectInputStream in = new ObjectInputStream( s.getInputStream());
			ASMessage data = (ASMessage)in.readObject();	    // read a line of data from the stream
			System.out.println("Received: " + data.getUsuario() + "==" +data.getTicket());
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }
}
