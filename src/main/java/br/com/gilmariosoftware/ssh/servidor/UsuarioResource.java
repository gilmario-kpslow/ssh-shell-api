package br.com.gilmariosoftware.ssh.servidor;

import br.com.gilmariosoftware.ssh.usuario.UsuarioRequest;
import br.com.gilmariosoftware.ssh.usuario.UsuarioResponse;
import br.com.gilmariosoftware.ssh.usuario.UsuarioService;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 *
 * @author gilmario
 */
@Path("/hello")
public class UsuarioResource {

    @Inject
    private UsuarioService service;

    public UsuarioResponse salvar(UsuarioRequest request) {
        return service.salvar(request);
    }

}
