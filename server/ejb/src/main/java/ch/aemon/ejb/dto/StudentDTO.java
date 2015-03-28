
package ch.aemon.ejb.dto;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "student")
public class StudentDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String firstName;
    private String familyName;

    public StudentDTO() {
    }
    
    public StudentDTO(String firstName, String familyName) {
        this.firstName = firstName;
        this.familyName = familyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return familyName;
    }

    public void setLastName(String familyName) {
        this.familyName = familyName;
    }
}
