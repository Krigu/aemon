package ch.aemon.service.unittest;

import ch.aemon.ejb.dto.MediaDTO;
import ch.aemon.ejb.service.MediaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceTest {

    @InjectMocks
    private final MediaService mediaService = new MediaService();

    @Mock
    private EntityManager mockedEntityManager;

    @Mock
    private Logger log;

    @Test
    @SuppressWarnings("unchecked")
    public void testCreate() throws Exception {

        List<MediaDTO> bookList = Collections.singletonList(new MediaDTO(1L, "Effective Mockito"));

        TypedQuery q = mock(TypedQuery.class);

        when(q.getResultList()).thenReturn(bookList);
        when(mockedEntityManager.createQuery(anyString(), eq(MediaDTO.class))).thenReturn(q);

        List<MediaDTO> result = mediaService.findAllOrderedByName();

        verify(mockedEntityManager).createQuery(anyString(), eq(MediaDTO.class));

        assertEquals(1, result.size());
        assertEquals(bookList, result);
    }


}
