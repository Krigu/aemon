package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.service.BookService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by krigu on 25.12.14.
 */
@Path("/books")
@RequestScoped
public class BookResource {

    @EJB
    private BookService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookDTO> listAllBookDTOs() {
        return service.findAllOrderedByName();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDTO lookupBookDTOById(@PathParam("id") Integer id) {
        BookDTO BookDTO = service.findById(id);
        if (BookDTO == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return BookDTO;
    }
}
