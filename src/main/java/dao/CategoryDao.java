package dao;

import entities.Categoty;
import org.hibernate.SessionFactory;

public class CategoryDao extends AbstractDao<Categoty> {

    public CategoryDao(SessionFactory sessionFactory) {
        super(Categoty.class, sessionFactory);
    }
}
