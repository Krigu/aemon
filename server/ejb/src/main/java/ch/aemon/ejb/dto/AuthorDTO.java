package ch.aemon.ejb.dto;

import ch.aemon.ejb.entity.Author;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "author")
public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }

    public AuthorDTO(Long id, String name) {
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

        AuthorDTO authorDTO = (AuthorDTO) o;

        if (id != null ? !id.equals(authorDTO.id) : authorDTO.id != null) return false;
        if (name != null ? !name.equals(authorDTO.name) : authorDTO.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
