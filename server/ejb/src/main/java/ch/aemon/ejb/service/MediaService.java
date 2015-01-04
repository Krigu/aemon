package ch.aemon.ejb.service;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.dto.MediaDTO;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by krigu on 04.01.15.
 */
@Stateless
@PermitAll
public class MediaService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public List<MediaDTO> findAllOrderedByName() {
        final String getAllBooksJPQL = "select new ch.aemon.ejb.dto.MediaDTO(m.id, m.name) from Media m order by m.name";
        return em.createQuery(getAllBooksJPQL, MediaDTO.class).getResultList();
    }
}
