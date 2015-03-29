package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.StudentDTO;
import ch.aemon.rs.testutils.AbstractResourceTest;

import java.util.List;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class StudentResourceTest extends AbstractResourceTest<StudentDTO> {


    private static final String RESOURCE_PATH = "students";

    public StudentResourceTest() {
        super(StudentDTO.class);
    }


    public StudentDTO buildStudentDTO() {
        return new StudentDTO("Till", "Eulenspiegel");
    }

    @Test
    public void crudExample() {

        StudentDTO studentDTO = buildStudentDTO();
        StudentDTO studentDTOPersisted = post(studentDTO, RESOURCE_PATH);

        Long studentId = studentDTOPersisted.getId();
        Assert.assertNotNull(studentId);

        StudentDTO studentDTO2 = getById(studentId, RESOURCE_PATH);

        Assert.assertNotNull(studentDTO2);
        Assert.assertEquals(studentId, studentDTO2.getId());
        Assert.assertEquals(studentDTO.getLastName(), studentDTO2.getLastName());

        Response resp = delete(studentId, RESOURCE_PATH);
        Assert.assertEquals(Response.Status.OK, resp.getStatusInfo());
        resp.close();

        List<StudentDTO> authors = getList(RESOURCE_PATH);
        Assert.assertFalse(authors.contains(studentDTO2));
    }

}
