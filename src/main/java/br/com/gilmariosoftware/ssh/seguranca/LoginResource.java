package br.com.gilmariosoftware.ssh.seguranca;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author gilmario
 */
@Path("login")
public class LoginResource {

    @Inject
    private SegurancaService segurancaService;

    @POST
    @Produces(value = MediaType.TEXT_PLAIN)
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    public Response getToken(@FormParam("username") String username, @FormParam("password") String password) {

        return Response.status(200).entity(segurancaService.login(username, password)).build();

    }
}
