package dao;

import entities.Language;
import org.hibernate.SessionFactory;

public class LanguageDao extends AbstractDao<Language> {
    public LanguageDao(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
