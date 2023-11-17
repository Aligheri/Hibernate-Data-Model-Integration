package entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(schema = "movie", name = "film_text")
public class FilmText {

    @Id
    private Short film_id;

    @OneToOne
    @JoinColumn(name = "film_id")
    private Film film;
    private String title;
    @Column(columnDefinition = "text")
    @Type(type = "text")
    private String description;

    public Short getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Short film_id) {
        this.film_id = film_id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
