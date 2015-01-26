package ch.aemon.service.unittest;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.entity.Author;
import ch.aemon.ejb.entity.Book;
import ch.aemon.ejb.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private static final String AUTHOR_NAME = "Steve Mason";
    private static final String BOOK_NAME = "Effective Mockito";

    @InjectMocks
    private BookService bookService = new BookService();

    @Mock
    private EntityManager mockedEntityManager;

    @Mock
    private Logger log;
    private List<BookDTO> bookList;

    @Before
    public void before(){
        final Author authorEntity = new Author();
        authorEntity.setId(1L);
        authorEntity.setName(AUTHOR_NAME);


        final Book bookEntity = new Book();
        bookEntity.setId(1L);
        bookEntity.setName(BOOK_NAME);
        bookEntity.setAuthors(Arrays.asList(authorEntity));

        bookList = Arrays.asList(new BookDTO(bookEntity));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findAllBooksTest()  {

        final TypedQuery<BookDTO> q = mock(TypedQuery.class);
        when(q.getResultList()).thenReturn(bookList);
        when(mockedEntityManager.createQuery(anyString(), eq(BookDTO.class))).thenReturn(q);

        final List<BookDTO> result = bookService.findAllBooks();

        verify(mockedEntityManager).createQuery(anyString(), eq(BookDTO.class));

        assertEquals(1, result.size());
        assertEquals(bookList, result);

        final BookDTO testBook = result.get(0);
        assertNotNull(testBook.getAuthors());
        assertEquals(1, testBook.getAuthors().size());
        assertEquals(AUTHOR_NAME, testBook.getAuthors().get(0).getName());
        assertEquals(new Long("1"), testBook.getAuthors().get(0).getId());

    }

    @Test
    public void findAllBooksNoAuthorTest()  {

        final Book bookEntityNoAuthors = new Book();
        bookEntityNoAuthors.setId(1L);
        bookEntityNoAuthors.setName(BOOK_NAME);

        final List<BookDTO> bookListNoAuthors =  Arrays.asList(new BookDTO(bookEntityNoAuthors));

        final TypedQuery q = mock(TypedQuery.class);
        when(q.getResultList()).thenReturn(bookListNoAuthors);
        when(mockedEntityManager.createQuery(anyString(), eq(BookDTO.class))).thenReturn(q);

        final List<BookDTO> result = bookService.findAllBooks();

        verify(mockedEntityManager).createQuery(anyString(), eq(BookDTO.class));

        assertEquals(1, result.size());
        assertEquals(bookListNoAuthors, result);
        final BookDTO testBook = result.get(0);

        assertNotNull(testBook.getAuthors());
        assertTrue(testBook.getAuthors().isEmpty());

    }


}
