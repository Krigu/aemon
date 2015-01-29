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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaDTO mediaDTO = (MediaDTO) o;

        if (id != null ? !id.equals(mediaDTO.id) : mediaDTO.id != null) return false;
        if (name != null ? !name.equals(mediaDTO.name) : mediaDTO.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
