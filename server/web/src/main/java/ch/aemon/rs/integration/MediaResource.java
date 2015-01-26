package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.MediaDTO;
import ch.aemon.ejb.service.MediaService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
