package ch.aemon.rs.integration;

import ch.aemon.ejb.dto.MediaDTO;
import ch.aemon.rs.testutils.AbstractResourceTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MediaResourceTest extends AbstractResourceTest<MediaDTO> {


    private static final String RESOURCE_PATH = "media";

    public MediaResourceTest() {
        super(MediaDTO.class);
    }

    @Test
    public void testAllBooks() throws Exception {

        List<MediaDTO> list = getList(RESOURCE_PATH);
        Assert.assertFalse(list.isEmpty());
    }
}

