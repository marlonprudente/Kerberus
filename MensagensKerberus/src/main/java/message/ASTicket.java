/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ASTicket implements Serializable{
    public String nomeServico;
    public Date timeStamp;
    public String randomNumber;
    public String sessionKey;
    
    public ASTicket(String IDServico, Date tempoSolicitado, String numeroAleatorio){
        this.nomeServico = IDServico;
        this.timeStamp = tempoSolicitado;
        this.randomNumber = numeroAleatorio;
    }
    
    public String getRandomNumber(){
        return this.randomNumber;
    }
    public String getSessionKey(){
        return this.sessionKey;
    }
    public void setSessionKey(String sessionKey){
        this.sessionKey = sessionKey;
    }
    
}
