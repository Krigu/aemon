package ch.aemon.ejb.dto;

import ch.aemon.ejb.entity.Publisher;

import java.io.Serializable;

public class PublisherDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    public PublisherDTO() {
    }

    public PublisherDTO(Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
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
