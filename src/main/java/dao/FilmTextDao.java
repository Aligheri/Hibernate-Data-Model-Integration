package dao;

import entities.FilmText;
import org.hibernate.SessionFactory;

public class FilmTextDao extends AbstractDao<FilmText> {
    public FilmTextDao(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
