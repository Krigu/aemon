package ch.aemon.ejb.dto;

import ch.aemon.ejb.entity.Book;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "book")
public class BookDTO extends MediaDTO {

    private PublisherDTO pusblisher;
    private List<AuthorDTO> authors = new ArrayList<>();

    public BookDTO() {
    }

    public BookDTO(Long id, String name) {
        super(id, name);
    }


    public BookDTO(Book bookEntity) {
        super(bookEntity.getId(), bookEntity.getName());

        if (bookEntity.getAuthors() != null)
            authors.addAll(bookEntity.getAuthors().stream().map(AuthorDTO::new).collect(Collectors.toList()));

        if (bookEntity.getPublisher() != null)
            this.pusblisher = new PublisherDTO(bookEntity.getPublisher());
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public PublisherDTO getPusblisher() {
        return pusblisher;
    }

    public void setPusblisher(PublisherDTO pusblisher) {
        this.pusblisher = pusblisher;
    }
}
