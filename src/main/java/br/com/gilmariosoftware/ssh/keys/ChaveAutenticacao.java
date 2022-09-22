package br.com.gilmariosoftware.ssh.keys;

import br.com.gilmariosoftware.ssh.servidor.Servidor;
import br.com.gilmariosoftware.ssh.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChaveAutenticacao extends PanacheEntity implements Serializable {

    private String valor;
    @ManyToOne
    private Servidor servidor;
    @ManyToOne
    private Usuario usuario;

}
