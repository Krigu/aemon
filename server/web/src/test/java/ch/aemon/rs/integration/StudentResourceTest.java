package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.StudentDTO;
import ch.aemon.web.AemonWebApplication;
import java.net.URL;
import java.util.List;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

@RunWith(Arquillian.class)
@RunAsClient
public class StudentResourceTest {

    private static final String RESOURCE_PREFIX = AemonWebApplication.class.getAnnotation(ApplicationPath.class).value().substring(1);

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

    public StudentDTO buildStudent() {       
        return new StudentDTO("Till", "Eulenspiegel");
    }

    @Test
    public void crudExample() {
        final String requestUri = deploymentUrl.toString() + RESOURCE_PREFIX + "students";

        StudentDTO studentDTO = buildStudent();
        StudentDTO studentDTOPersisted = client
                .target(requestUri)
                .request()
                .post(Entity.entity(studentDTO, MediaType.APPLICATION_JSON),
                        StudentDTO.class);

        Long studentId = studentDTOPersisted.getId();
        Assert.assertNotNull(studentId);

        StudentDTO studentDTO2 = client.target(requestUri).path("/{id}")
                .resolveTemplate("id", studentId).request().get(StudentDTO.class);
                
        Assert.assertNotNull(studentDTO2);
        Assert.assertEquals(studentId, studentDTO2.getId());
        Assert.assertEquals(studentDTO.getLastName(), studentDTO2.getLastName());

        Response resp = client.target(requestUri).path("/{id}")
                .resolveTemplate("id", studentId).request().delete();

        Assert.assertEquals(Response.Status.OK, resp.getStatusInfo());
        resp.close();

        GenericType<List<StudentDTO>> authorType = new GenericType<List<StudentDTO>>() {
        };
        List<StudentDTO> authors = client.target(requestUri).request().get(authorType);
        System.out.println(authors);
        Assert.assertFalse(authors.contains(studentDTO2));
    }

}
