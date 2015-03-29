
package ch.aemon.service.unittest;

import ch.aemon.ejb.dto.StudentDTO;
import ch.aemon.ejb.service.StudentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    
    private static final String FIRST_NAME = "Doktor";
    private static final String LAST_NAME = "X";
    
    @InjectMocks
    private final StudentService studentSrv = new StudentService();
    
    @Mock
    private EntityManager mockedEntityManager;
    
    @Mock
    private Logger log;
    private List<StudentDTO> studentList;
    

    @Before
    public void before(){
        final StudentDTO studentDTO = new StudentDTO(); 
        studentDTO.setId(1L);
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);
        
        studentList = Arrays.asList(studentDTO);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testFindAllStudents() throws Exception {
        
        final TypedQuery<StudentDTO> q = mock(TypedQuery.class);
        
        when(q.getResultList()).thenReturn(studentList);
        when(mockedEntityManager.createQuery(anyString(), eq(StudentDTO.class))).thenReturn(q);
        
        final List<StudentDTO> result = studentSrv.findAllStudents();
        
        verify(mockedEntityManager).createQuery(anyString(), eq(StudentDTO.class));
        
        assertEquals(1, result.size());
        assertEquals(studentList, result);
        
        final StudentDTO testDTO = result.get(0);
        assertNotNull(testDTO);
        assertEquals(new Long(1), testDTO.getId());
        assertEquals(FIRST_NAME, testDTO.getFirstName());
        assertEquals(LAST_NAME, testDTO.getLastName() );
    }
    
}
