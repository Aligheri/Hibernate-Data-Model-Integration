package dao;

import entities.Staff;
import org.hibernate.SessionFactory;

public class StaffDao extends AbstractDao<Staff> {
    public StaffDao(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
