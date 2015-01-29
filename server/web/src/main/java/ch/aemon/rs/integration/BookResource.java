package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.service.BookService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("media/books")
@RequestScoped
public class BookResource {

    @EJB
    private BookService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response lookupAllBooks() {
        List<BookDTO> list = service.findAllBooks();
        System.out.println("List: " + list);
        if (list == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDTO lookupBookDTOById(@PathParam("id") Long id) {
        BookDTO bookDTO = service.getById(id);
        if (bookDTO == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return bookDTO;
    }
}
