package ch.aemon.ejb.dto;

import java.util.List;

/**
 * Created by krigu on 04.01.15.
 */
public class MediaDTO {

    private Integer id;
    private String name;

    private List<AuthorDTO> authors;

    public MediaDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
