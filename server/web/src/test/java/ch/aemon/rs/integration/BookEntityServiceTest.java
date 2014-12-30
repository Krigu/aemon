package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.ejb.dto.BookDTO;
import ch.aemon.ejb.entity.Book;
import ch.aemon.ejb.service.BookService;
import ch.aemon.util.ResourceProducer;
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
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

/**
 * Created by krigu on 21.12.14.
 */
@RunWith(Arquillian.class)
@RunAsClient
public class BookEntityServiceTest {

    private static final String RESOURCE_PREFIX = AemonWebApplication.class.getAnnotation(ApplicationPath.class).value().substring(1);

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Book.class, BookDTO.class, AuthorDTO.class, BookService.class, BookResource.class, AemonWebApplication.class, ResourceProducer.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @ArquillianResource
    URL deploymentUrl;

    @Test
    public void testAllBooks() throws Exception {

        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX  + "books";

        Client client = ClientBuilder.newClient();

        Invocation call = client.target(requestUri)
                .request("text/plain").header("Accept", MediaType.APPLICATION_JSON).buildGet();

        Response response = call.invoke();
        Assert.assertEquals(200, response.getStatus());
    }
}

