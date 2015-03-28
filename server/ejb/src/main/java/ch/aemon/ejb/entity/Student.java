
package ch.aemon.ejb.entity;


import javax.persistence.*;

@Entity
@DiscriminatorValue("STUDENT")
@Table(name = "STUDENT")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)   
    private long id;
    
    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    private String firstName;
    
    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
}
