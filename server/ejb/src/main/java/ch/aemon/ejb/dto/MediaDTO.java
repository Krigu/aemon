package ch.aemon.ejb.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "media")
public class MediaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public MediaDTO() {
    }

    public MediaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
