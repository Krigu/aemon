package ch.aemon.ejb.service;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.entity.Book;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@PermitAll
public class BookService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public BookDTO findById(Integer id) {
        Book b = em.find(Book.class, id);
        if (b == null)
            return null;

        return new BookDTO(b);
    }

    public List<BookDTO> findAllBooks() {
        final String getAllBooksJPQL = "select new ch.aemon.ejb.dto.BookDTO(b) from Book b order by b.name";
        return em.createQuery(getAllBooksJPQL, BookDTO.class).getResultList();
    }

}
