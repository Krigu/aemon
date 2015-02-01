package ch.aemon.ejb.entity;

import javax.persistence.*;

@Entity
@Table(name = "PUBLISHER")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_PUBLISHER", unique = true, nullable = false)
    private Long id;

    @Column(length = 250)
    private String name;

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
