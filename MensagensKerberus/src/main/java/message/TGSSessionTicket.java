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
public class TGSSessionTicket implements Serializable{
    
    public String clienteID;
    public String servico;
    public Date timeStamp;
    public String randomNumber;
    public String sessionKeyServidor;
    
    public TGSSessionTicket(String cliente, String servico, Date tempo, String numeroAleatorio){
        this.clienteID = cliente;
        this.servico = servico;
        this.timeStamp = tempo;
        this.randomNumber = numeroAleatorio;
    }
    
    public void setSessionKey(String sessionKey){
        this.sessionKeyServidor = sessionKey;
    }
    public String getSessionKeyServidor(){
        return this.sessionKeyServidor;
    }
    
}
