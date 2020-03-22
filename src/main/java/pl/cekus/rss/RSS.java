package pl.cekus.rss;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
class RSS {
    @Id
    @GeneratedValue(generator = "inc", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;
    private String hyperlink;
    private Integer email;

    /**
     * Hibernate (JPA) needs it.
     */
    @SuppressWarnings("unused")
    public RSS() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

}
