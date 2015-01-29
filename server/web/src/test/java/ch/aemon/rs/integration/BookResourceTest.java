package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.web.AemonWebApplication;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URL;

@RunWith(Arquillian.class)
@RunAsClient
public class BookResourceTest {

    private static final String RESOURCE_PREFIX = AemonWebApplication.class.getAnnotation(ApplicationPath.class).value().substring(1);

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "ch.aemon")
                .addAsResource("import.sql")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @ArquillianResource
    URL deploymentUrl;

    @Test
    public void testAllBooks() throws Exception {

        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX + "media/books";

        Client client = ClientBuilder.newClient();
        WebTarget myResource = client.target(requestUri);

        String response = myResource.request(MediaType.APPLICATION_JSON).get(String.class);

        Assert.assertEquals("[{\"id\":1,\"name\":\"NHL Advanced\",\"authors\":[{\"id\":1,\"name\":\"Kurt Sauer\"}]}]", response);
    }

    @Test
    public void testGetBookById() throws Exception {

        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX + "media/books/1";

        Client client = ClientBuilder.newClient();
        WebTarget myResource = client.target(requestUri);

        BookDTO bookDTO = myResource.request(MediaType.APPLICATION_JSON).get(BookDTO.class);

        Assert.assertEquals("NHL Advanced", bookDTO.getName());
    }
}

