package ch.aemon.service;

import ch.aemon.ejb.entity.Book;
import ch.aemon.ejb.service.BookService;
import ch.aemon.util.ResourceProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

/**
 * Created by krigu on 21.12.14.
 */
@RunWith(Arquillian.class)
public class BookServiceTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Book.class, BookService.class, ResourceProducer.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    BookService bookService;

    @Inject
    Logger log;

    @Test
    public void testNewBook() throws Exception {
        Book newBook = new Book();
        newBook.setName("Arquillian for beginners");
        bookService.save(newBook);
        assertNotNull(newBook.getId());

        log.info(newBook.getName() + " was persisted with id " + newBook.getId());
    }
}
