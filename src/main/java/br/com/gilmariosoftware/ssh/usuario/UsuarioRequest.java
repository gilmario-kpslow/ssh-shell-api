package br.com.gilmariosoftware.ssh.usuario;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
public class UsuarioRequest {

    private String username;
    private String password;
    private String nome;

}
