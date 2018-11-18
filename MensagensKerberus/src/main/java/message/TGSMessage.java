/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package message;

import java.io.Serializable;
import javax.crypto.SealedObject;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class TGSMessage implements Serializable{
    SealedObject TGSTicket;
    SealedObject TGSSessionTicket;
    SealedObject ServerTicket;
    
    public TGSMessage(SealedObject tgsTicket, SealedObject sessionTicket){
        this.TGSTicket = tgsTicket;
        this.TGSSessionTicket = sessionTicket;
    }
    
    public SealedObject getTGSTicket(){
        return this.TGSTicket;
    }
    public SealedObject getSessionTicket(){
        return this.TGSSessionTicket;
    }
    public SealedObject getServerTicket(){
        return this.ServerTicket;
    }
    
    public void setServerTicket(SealedObject ticket){
        this.ServerTicket = ticket;
    }
    public void setSessionTicket(SealedObject ticket){
        this.TGSSessionTicket = ticket;
    }
    
}
