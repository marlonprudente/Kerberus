/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.io.Serializable;
import java.util.Date;
import javax.crypto.SealedObject;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ASMessage implements Serializable{
    String login;
    SealedObject ASTicket;
    SealedObject TGSTicket;
    
    public ASMessage(String usuario, SealedObject ticketAS){
        this.login = usuario;
        this.ASTicket = ticketAS;
    }
    public void print(){
        System.out.println("*AS Ticket*");
        System.out.println("ID do cliente: " + login);
//        System.out.println("ID do serviço: " + serviceId);
//        System.out.println("Ticket timestamp: " + timeStamp);
//        System.out.println("Número aleatorio: " + randomNumber);
//        System.out.println("Chave de sessão: " + sessionKey);
    }
    
    public SealedObject getASTicket(){
        return this.ASTicket;
    }
    public SealedObject getTGSTicket(){
        return this.TGSTicket;
    }
    
    public String getUsuario(){
        return this.login;
    }
    
    public void setASTicket(SealedObject ticket){
        this.ASTicket = ticket;
    }
    public void setTGSTicket(SealedObject ticket){
        this.TGSTicket = ticket;
    }
}
