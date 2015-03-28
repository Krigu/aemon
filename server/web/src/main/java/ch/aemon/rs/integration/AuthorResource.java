package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.ejb.service.AuthorService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("authors")
@RequestScoped
public class AuthorResource {

    @EJB
    private AuthorService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<AuthorDTO> list = service.findAll();
        if (list == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorDTO findById(@PathParam("id") Long id) {
        AuthorDTO AuthorDTO = service.getById(id);
        if (AuthorDTO == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return AuthorDTO;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(final AuthorDTO author) {
        AuthorDTO authorPersisted = service.save(author);
        return Response.ok(authorPersisted).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(final @PathParam("id") Long id) {
        service.delete(id);
        return Response.ok().build();
    }
}
