package ch.aemon.ejb.service;


import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.ejb.entity.Author;
import ma.glasnost.orika.MapperFacade;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@PermitAll
public class AuthorService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private MapperFacade mapperFacade;

    public AuthorDTO getById(Long id) {
        Author b = em.find(Author.class, id);
        if (b == null)
            return null;

        return mapperFacade.map(b,AuthorDTO.class);
    }

    public List<AuthorDTO> findAll() {
        final String getAllBooksJPQL = "select new ch.aemon.ejb.dto.AuthorDTO(a) from Author a order by a.name";
        return em.createQuery(getAllBooksJPQL, AuthorDTO.class).getResultList();
    }

    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = mapperFacade.map(authorDTO, Author.class);
        author = em.merge(author);
        return mapperFacade.map(author, AuthorDTO.class);
    }

    @Transactional
    public void delete(String id) {
        em.createQuery("delete from Author where id = :id").setParameter("id", Long.valueOf(id)).executeUpdate();
    }
}