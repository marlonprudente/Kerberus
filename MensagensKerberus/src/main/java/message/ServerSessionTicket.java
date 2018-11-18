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
public class ServerSessionTicket implements Serializable{
    public String clienteID;
    public Date tempo;
    public String servico;
    public String numeroAleatorio;
    
    public ServerSessionTicket(String cliente, Date tempo, String servico, String numAleat){
        this.clienteID = cliente;
        this.tempo = tempo;
        this.servico = servico;
        this.numeroAleatorio = numAleat;
    }
    
    
}
