package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.rs.testutils.AbstractResourceTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BookResourceTest extends AbstractResourceTest<BookDTO> {


    private static final String RESOURCE_PATH = "media/books";

    public BookResourceTest() {
        super(BookDTO.class);
    }

    @Test
    public void testAllBooks() throws Exception {

        List<BookDTO> response = getList(RESOURCE_PATH);

        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(1, response.size());
        BookDTO bookUnderTest = response.get(0);
        Assert.assertEquals("NHL Advanced", bookUnderTest.getName());
    }

    @Test
    public void testGetBookById() throws Exception {

        BookDTO bookDTO = getById(1L, RESOURCE_PATH);

        Assert.assertEquals("NHL Advanced", bookDTO.getName());
        Assert.assertNotNull(bookDTO.getPusblisher());
        Assert.assertEquals("SuperPublisher", bookDTO.getPusblisher().getName());
    }
}

