package ch.aemon.rs.integration;

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
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

@RunWith(Arquillian.class)
@RunAsClient
public class MediaResourceTest {

    private static final String RESOURCE_PREFIX = AemonWebApplication.class.getAnnotation(ApplicationPath.class).value().substring(1);

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

    @Test
    public void testAllBooks() throws Exception {

        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX  + "media";

        Client client = ClientBuilder.newClient();

        Invocation call = client.target(requestUri)
                .request("text/plain").header("Accept", MediaType.APPLICATION_JSON).buildGet();

        Response response = call.invoke();
        Assert.assertEquals(200, response.getStatus());
    }
}

