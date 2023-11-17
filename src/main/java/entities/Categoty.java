package entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(schema = "movie", name = "category")
public class Categoty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte category_id;

    private String name;
    @UpdateTimestamp
    private LocalDateTime last_update;

    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"))
            private Set<Film> films;

}
