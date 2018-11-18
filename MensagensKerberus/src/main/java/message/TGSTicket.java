/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package message;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class TGSTicket implements Serializable{
    public String clienteID;
    public Date timeStamp;
    public String sessionKey;
    
    public TGSTicket(String cliente, Date tempo, String session){
        this.clienteID = cliente;
        this.sessionKey = session;
        this.timeStamp = tempo;
    }
}
