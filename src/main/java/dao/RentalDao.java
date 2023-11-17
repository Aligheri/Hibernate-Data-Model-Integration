package dao;

import entities.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class RentalDao extends AbstractDao<Rental> {
    public RentalDao(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyUnreternedRental() {
        Query<Rental> query = getCurrentSession().createQuery("select r from Rental r where r.return_date is null", Rental.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
