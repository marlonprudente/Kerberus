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
public class Ticket implements Serializable{
    public String clienteId;
    public Date tempoExpiracao;
    public String servico;
    public String numeroAleatorio;
    public String resposta;
    
    public Ticket(String cliente, Date tempo, String servico, String numeroAleatorio){
        this.clienteId = cliente;
        this.tempoExpiracao = tempo;
        this.servico = servico;
        this.numeroAleatorio = numeroAleatorio;
    }
    
    
    public void setResposta(String resposta){
        this.resposta = resposta;
    }
    public String getResposta(){
        return this.resposta;
    }
    
}
