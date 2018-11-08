/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asmessage;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ASTicket implements Serializable{
    public Integer serviceId;
    public Date timeStamp;
    public String randomNumber;
    public String sessionKey;
    
    public ASTicket(Integer IDServico, Date tempoSolicitado, String numeroAleatorio){
        this.serviceId = IDServico;
        this.timeStamp = tempoSolicitado;
        this.randomNumber = numeroAleatorio;
    }
    
}
