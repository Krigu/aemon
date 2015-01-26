package ch.aemon.ejb.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("BOOK")
@Table(name = "BOOK")
public class Book extends Media {

    @ManyToMany
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = {@JoinColumn(name = "BOOK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID")}
    )
    private List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
