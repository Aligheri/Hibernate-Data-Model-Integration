package dao;

import entities.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDao extends AbstractDao<City> {
    public CityDao(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        Query<City> query = getCurrentSession().createQuery("select c from City c where c.city =:NAME", City.class);
        query.setParameter("NAME", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
