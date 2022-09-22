package br.com.gilmariosoftware.ssh.servidor;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
public class ServidorRequest {

    private Long id;
    private String hostName;
    private String nome;
    private String ip;
    private Integer port;

}
