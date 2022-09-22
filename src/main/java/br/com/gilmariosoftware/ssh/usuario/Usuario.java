package br.com.gilmariosoftware.ssh.usuario;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.io.Serializable;
import javax.persistence.Column;
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
public class Usuario extends PanacheEntity implements Serializable {

    @Column(unique = true)
    private String username;
    private String password;
    private String nome;

    public static Usuario findByUsername(String username) {
        return find("username", username).firstResult();
    }

}
