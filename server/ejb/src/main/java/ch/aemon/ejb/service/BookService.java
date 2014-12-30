package ch.aemon.ejb.service;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.entity.Book;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
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

    public BookDTO findById(Integer id) {
        // TODO
        Book b = em.find(Book.class, id);
        if (b == null)
            return null;

        return new BookDTO(b.getId(), b.getName());
    }

    public List<BookDTO> findAllOrderedByName() {
        final String getAllBooksJPQL = "select new ch.aemon.ejb.dto.BookDTO(b.id, b.name) from Book b order by b.name";
        return em.createQuery(getAllBooksJPQL, BookDTO.class).getResultList();
    }
}
