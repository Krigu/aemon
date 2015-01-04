package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.dto.MediaDTO;
import ch.aemon.ejb.service.BookService;
import ch.aemon.ejb.service.MediaService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by krigu on 25.12.14.
 */
@Path("/media")
@RequestScoped
public class MediaResource {

    @EJB
    private MediaService mediaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MediaDTO> getAllMedias() {
        return mediaService.findAllOrderedByName();
    }

}
