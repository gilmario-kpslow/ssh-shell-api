package br.com.gilmariosoftware.ssh.servidor;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.io.Serializable;
import javax.persistence.Entity;
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
public class Servidor extends PanacheEntity implements Serializable {

    private String hostName;
    private String nome;
    private String ip;
    private Integer port;

}
