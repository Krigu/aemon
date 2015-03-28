package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.rs.testutils.AbstractResourceTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

public class AuthorResourceTest extends AbstractResourceTest<AuthorDTO> {

    private static final String RESOURCE_PATH = "authors";

    private static final String AUTHOR_NAME = "John Ashton";

    public AuthorResourceTest() {
        super(AuthorDTO.class);
    }

    public AuthorDTO buildAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName(AUTHOR_NAME);
        return author;
    }

    @Test
    public void testFindById() throws Exception {

        List<AuthorDTO> response = getList(RESOURCE_PATH);

        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(1, response.size());
        AuthorDTO authorUnderTest = response.get(0);
        Assert.assertEquals("Kurt Sauer", authorUnderTest.getName());
    }


    @Test
    public void testFindAll() throws Exception {

        List<AuthorDTO> response = getList(RESOURCE_PATH);

        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(1, response.size());
        AuthorDTO authorUnderTest = response.get(0);
        Assert.assertEquals("Kurt Sauer", authorUnderTest.getName());
    }


    @Test
    public void crudExample() {

        // 1. Save a new author
        AuthorDTO authorDTO = buildAuthor();
        AuthorDTO authorDTOPersisted = post(authorDTO, RESOURCE_PATH);

        Long authorId = authorDTOPersisted.getId();
        Assert.assertNotNull(authorId);

        // 2. Fetch author by id
        AuthorDTO authorDTO2 = getById(authorId, RESOURCE_PATH);
        Assert.assertNotNull(authorDTO2);
        Assert.assertEquals(authorId, authorDTO2.getId());
        Assert.assertEquals(authorDTO.getName(), authorDTO2.getName());

        // 3. Delete a author
        Response resp = delete(authorId, RESOURCE_PATH);
        Assert.assertEquals(Response.Status.OK, resp.getStatusInfo());
        resp.close();

        // 4. Fetch all and check for occurence
        List<AuthorDTO> authors = getList(RESOURCE_PATH);
        Assert.assertFalse(authors.contains(authorDTO2));
    }


}

