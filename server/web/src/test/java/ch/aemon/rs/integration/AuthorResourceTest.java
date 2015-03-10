package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.web.AemonWebApplication;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;

@RunWith(Arquillian.class)
@RunAsClient
public class AuthorResourceTest {

    private static final String RESOURCE_PREFIX = AemonWebApplication.class.getAnnotation(ApplicationPath.class).value().substring(1);
    private static final String AUTHOR_NAME = "John Ashton";

    private Client client = ClientBuilder.newClient();

    @Deployment
    public static WebArchive createDeployment() {
        // Bug: https://github.com/mmatloka/arquillian-gradle-sample/issues/2
        // TODO: Remove with next version of GradleImporter
        System.getProperties().remove("javax.xml.parsers.SAXParserFactory");

        final WebArchive webArchive = ShrinkWrap.create(EmbeddedGradleImporter.class)
                .forThisProjectDirectory()
                .importBuildOutput().as(WebArchive.class)
                .addAsResource("import.sql")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");

        System.setProperty("javax.xml.parsers.SAXParserFactory", "__redirected.__SAXParserFactory");
        return webArchive;
    }

    @ArquillianResource
    private URL deploymentUrl;

    public AuthorDTO buildAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName(AUTHOR_NAME);
        return author;
    }


    @Test
    public void testFindById() throws Exception {

        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX + "authors";

        Invocation.Builder requestBuilder = client.target(requestUri).request(MediaType.APPLICATION_JSON);

        GenericType<List<AuthorDTO>> authorType = new GenericType<List<AuthorDTO>>() {
        };
        List<AuthorDTO> response = requestBuilder.get(authorType);

        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(1, response.size());
        AuthorDTO authorUnderTest = response.get(0);
        Assert.assertEquals("Kurt Sauer", authorUnderTest.getName());
    }

    @Test
    public void testFindAll() throws Exception {

        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX + "authors";

        Invocation.Builder requestBuilder = ClientBuilder.newClient().target(requestUri).request(MediaType.APPLICATION_JSON);

        GenericType<List<AuthorDTO>> authorType = new GenericType<List<AuthorDTO>>() {
        };
        List<AuthorDTO> response = requestBuilder.get(authorType);

        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(1, response.size());
        AuthorDTO authorUnderTest = response.get(0);
        Assert.assertEquals("Kurt Sauer", authorUnderTest.getName());
    }

    @Test
    public void crudExample() {
        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX + "authors";

        // 1. Save a new author
        AuthorDTO authorDTO = buildAuthor();
        AuthorDTO authorDTOPersisted = client
                .target(requestUri)
                .request()
                .post(Entity.entity(authorDTO, MediaType.APPLICATION_JSON),
                        AuthorDTO.class);

        Long authorId = authorDTOPersisted.getId();
        Assert.assertNotNull(authorId);

        // 2. Fetch author by id
        AuthorDTO authorDTO2 = client.target(requestUri).path("/{id}")
                .resolveTemplate("id", authorId).request().get(AuthorDTO.class);
        Assert.assertNotNull(authorDTO2);
        Assert.assertEquals(authorId, authorDTO2.getId());
        Assert.assertEquals(authorDTO.getName(), authorDTO2.getName());

        // 3. Delete a author
        Response resp = client.target(requestUri).path("/{id}")
                .resolveTemplate("id", authorId).request().delete();

        Assert.assertEquals(Response.Status.OK, resp.getStatusInfo());
        resp.close();

        // 4. Fetch all and check for occurence
        GenericType<List<AuthorDTO>> authorType = new GenericType<List<AuthorDTO>>() {};
        List<AuthorDTO> authors = client.target(requestUri).request().get(authorType);
        System.out.println(authors);
        Assert.assertFalse(authors.contains(authorDTO2));
    }



}

