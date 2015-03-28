
package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.AuthorDTO;
import ch.aemon.ejb.dto.StudentDTO;
import ch.aemon.ejb.service.StudentService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("students")
@RequestScoped
public class StudentResource {
    
    @EJB
    private StudentService service;
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response lookupAllStudents() {
        List<StudentDTO> list = service.findAllStudents();
        if (list == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        return Response.ok(list).build();
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public StudentDTO lookupStudentByID(@PathParam("id") long id) {
        StudentDTO studentDTO = service.findByID(id);
        if (studentDTO == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        return studentDTO;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(final StudentDTO student) {
        StudentDTO studentPersisted = service.save(student);
        return Response.ok(studentPersisted).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(final @PathParam("id") Long id) {
        service.delete(id);
        return Response.ok().build();
    }
}
