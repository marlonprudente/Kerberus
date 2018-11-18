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
public class ServerMessage implements Serializable {
    public SealedObject ServerTicket;
    public SealedObject sessionTicket;
    public SealedObject Ticket;
    
    public ServerMessage(SealedObject serverTicket, SealedObject sessionTicket){
        this.ServerTicket = serverTicket;
        this.sessionTicket = sessionTicket;
    }
    
    public SealedObject getServerTicket(){
        return this.ServerTicket;
    }
    public SealedObject getSessionTicket(){
        return this.sessionTicket;
    }
    public SealedObject getTicket(){
        return this.Ticket;
    }
    public void setTicket(SealedObject ticket){
        this.Ticket = ticket;
    }
    
    
}
