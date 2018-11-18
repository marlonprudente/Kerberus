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
public class ServerTicket  implements Serializable{
    public String clienteID;
    public Date tempoAutorizado;
    public String sessionKeyServidor;
    
    public ServerTicket(String cliente, Date tempo, String sessionkey){
        this.clienteID = cliente;
        this.tempoAutorizado = tempo;
        this.sessionKeyServidor = sessionkey;
    }
    
    
}
