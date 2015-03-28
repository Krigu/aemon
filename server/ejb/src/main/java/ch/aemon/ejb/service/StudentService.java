
package ch.aemon.ejb.service;

import ch.aemon.ejb.entity.Student;
import ch.aemon.ejb.dto.StudentDTO;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import ma.glasnost.orika.MapperFacade;


@Stateless
@PermitAll
public class StudentService {
    
    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;
    
    @Inject
    private MapperFacade mapperFacade;
    
    public StudentDTO findByID(long id){
        
        Student s = em.find(Student.class, id);
        
        if (s == null) return null;
        
        return mapperFacade.map(s, StudentDTO.class);
    }
    
    public List<StudentDTO> findAllStudents(){
        final String getAllStudentsJPQL = "select new ch.aemon.ejb.dto.StudentDTO(s) from Student s order by s.lastName";       
        return em.createQuery(getAllStudentsJPQL, StudentDTO.class).getResultList();
    }
    
    @Transactional
    public StudentDTO save(StudentDTO studentDTO){

       Student student = mapperFacade.map(studentDTO, Student.class);
       student = em.merge(student);
       
       return mapperFacade.map(student, StudentDTO.class);
    }
      
    @Transactional
    public void delete(long id) {
        em.createQuery("delete from Student where id = :id").setParameter("id", id).executeUpdate();
    }

}
