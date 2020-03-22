package pl.cekus.user;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public
class User {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;
    private String email;

    /**
     * Hibernate (JPA) needs it.
     */
    @SuppressWarnings("unused")
    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
