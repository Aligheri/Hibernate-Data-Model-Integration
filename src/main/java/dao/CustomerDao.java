package dao;

import entities.Customer;
import org.hibernate.SessionFactory;

public class CustomerDao extends AbstractDao<Customer> {
    public CustomerDao(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }
}
