package br.com.gilmariosoftware.ssh.seguranca;

import br.com.gilmariosoftware.ssh.usuario.Usuario;
import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import javax.enterprise.context.Dependent;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author gilmario
 */
@Dependent
public class SegurancaService {

    public String login(String username, String password) {

//        if (!Objects.equals(username, defaulUsername) || !Objects.equals(password, defaulPassword)) {
//            return Response.status(403).entity("Username OR Passsword is incorrect").build();
//        }
        Usuario usuario = Usuario.findByUsername(username);

        return Jwt.issuer("http://localhost")
                .upn("jdoe@quarkus.io")
                .groups(new HashSet<>(Arrays.asList("ADMIN")))
                .claim(Claims.full_name.name(), usuario.getNome())
                .expiresIn(Duration.ofDays(1))
                .sign();

    }
}
