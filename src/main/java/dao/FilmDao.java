package dao;

import entities.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDao extends AbstractDao<Film> {
    public FilmDao(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {
        Query<Film> query = getCurrentSession().createQuery("select f from Film f " +
                "where f.film_id not in(select distinct film.film_id from Inventory ) ", Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
