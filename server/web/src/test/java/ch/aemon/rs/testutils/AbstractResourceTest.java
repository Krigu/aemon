package ch.aemon.rs.testutils;

import ch.aemon.web.AemonWebApplication;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;

@RunWith(Arquillian.class)
@RunAsClient
public abstract class AbstractResourceTest<T> {

    private static final String RESOURCE_PREFIX = AemonWebApplication.class.getAnnotation(ApplicationPath.class).value().substring(1);

    private final RequestBuilder<T> requestBuilder;

    @ArquillianResource
    private URL deploymentUrl;

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

    public AbstractResourceTest(Class<T> clazz) {
        this.requestBuilder = new RequestBuilder<>(clazz);
    }

    protected String getRequestBaseUri() {
        return deploymentUrl.toString() + RESOURCE_PREFIX;
    }


    protected T getById(Long id, String uri) {
        return requestBuilder.getById(id, getRequestBaseUri() + uri);
    }

    protected List<T> getList(String uri) {
        return requestBuilder.getList(getRequestBaseUri() + uri);
    }

    protected T post(T item, String uri) {
        return requestBuilder.post(item, getRequestBaseUri() + uri);
    }


    protected Response delete(Long id, String uri) {
        return requestBuilder.delete(id, getRequestBaseUri() + uri);
    }

}
