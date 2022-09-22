package br.com.gilmariosoftware.ssh.usuario;

import br.com.gilmariosoftware.ssh.generics.ServiceGeneric;

/**
 *
 * @author gilmario
 */
public class UsuarioService extends ServiceGeneric<Usuario> {

    public UsuarioResponse salvar(UsuarioRequest request) {
        Usuario usuario = getModelMapper().map(request, Usuario.class);
        usuario.persist();
        return getModelMapper().map(usuario, UsuarioResponse.class);
    }

}
