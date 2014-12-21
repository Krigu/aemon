package ch.aemon.ejb.service;

import ch.aemon.ejb.entity.Book;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.logging.Logger;

/**
 * Created by krigu on 21.12.14.
 */

@Stateless
@PermitAll
public class BookService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        em.persist(book);
    }
}
