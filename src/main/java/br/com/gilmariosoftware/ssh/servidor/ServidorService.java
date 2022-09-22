package br.com.gilmariosoftware.ssh.servidor;

import javax.enterprise.context.Dependent;
import org.modelmapper.ModelMapper;

/**
 *
 * @author gilmario
 */
@Dependent
public class ServidorService {

    private final ModelMapper modelMapper = new ModelMapper();

    public Servidor salvar(ServidorRequest request) {
        Servidor s = modelMapper.map(request, Servidor.class);
        s.persist();
        return s;
    }

    public void delete(Long id) throws Exception {
        if (Servidor.deleteById(id)) {
            throw new Exception("Não foi possível excluir");
        }
    }

}
