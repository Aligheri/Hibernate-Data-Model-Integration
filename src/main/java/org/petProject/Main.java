package org.petProject;

import dao.*;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;


public class Main {
    private final SessionFactory sessionFactory;

    private final ActorDao actorDao;
    private final AddressDao addressDao;
    private final CategoryDao categotyDao;
    private final CityDao cityDao;
    private final CountryDao countryDao;
    private final CustomerDao customerDao;
    private final FilmDao filmDao;
    private final FilmTextDao filmTextDao;
    private final InventoryDao inventoryDao;
    private final LanguageDao languageDao;
    private final PaymentDao paymentDao;
    private final RentalDao rentalDao;
    private final StaffDao staffDao;
    private final StoreDao storeDao;


    public Main() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "yevs2007");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Categoty.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Feature.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rating.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addProperties(properties)
                .buildSessionFactory();

        actorDao = new ActorDao(sessionFactory);
        addressDao = new AddressDao(sessionFactory);
        categotyDao = new CategoryDao(sessionFactory);
        cityDao = new CityDao(sessionFactory);
        countryDao = new CountryDao(sessionFactory);
        customerDao = new CustomerDao(sessionFactory);
        filmDao = new FilmDao(sessionFactory);
        filmTextDao = new FilmTextDao(sessionFactory);
        inventoryDao = new InventoryDao(sessionFactory);
        languageDao = new LanguageDao(sessionFactory);
        paymentDao = new PaymentDao(sessionFactory);
        rentalDao = new RentalDao(sessionFactory);
        staffDao = new StaffDao(sessionFactory);
        storeDao = new StoreDao(sessionFactory);
    }


    public static void main(String[] args) {
        Main main = new Main();
        Customer customer = main.createCustomer();
        main.customerReturnInventoryToStore();
        main.customerRentInventory(customer);
        main.newFilmWasMade();
    }

    private void newFilmWasMade() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDao.getItems(0, 20).stream().unordered().findAny().get();
            List<Categoty> categories = categotyDao.getItems(0, 5);
            List<Actor> actors = actorDao.getItems(0, 20);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.NC17);
            film.setSpecial_features(Set.of(Feature.COMMENTARIES));
            film.setLength((short) 123);
            film.setReplacement_cost(BigDecimal.TEN);
            film.setRental_rate(BigDecimal.ZERO);
            film.setLanguage_id(language);
            film.setDescription("new scary film");
            film.setTitle("my scary movie");
            film.setRental_duration((byte) 44);
            film.setOriginal_language_id(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.now());
            filmDao.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setFilm_id(film.getFilm_id());
            filmText.setDescription("new scary film");
            filmText.setTitle("my scary movie");
            filmTextDao.save(filmText);

            session.getTransaction().commit();
        }
    }

    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDao.getFirstAvailableFilmForRent();
            Store store = storeDao.getItems(0, 1).get(0);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDao.save(inventory);

            Staff staff = store.getStaff();

            Rental rental = new Rental();
            rental.setRental_date(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDao.save(rental);

            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setPayment_date(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(55.47));
            payment.setStaff(staff);
            paymentDao.save(payment);

            session.getTransaction().commit();
        }
    }

    private void customerReturnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDao.getAnyUnreternedRental();
            rental.setReturn_date(LocalDateTime.now());

            rentalDao.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Store store = storeDao.getItems(0, 1).get(0);

            City city = cityDao.getByName("Kragujevac");

            Address address = new Address();
            address.setAddress("Maidan Nezalezhnosti");
            address.setPhone("097-111-111");
            address.setCity(city);
            address.setDistrict("Shevchenkivskyi");
            addressDao.save(address);

            Customer customer = new Customer();
            customer.setActive(true);
            customer.setEmail("test@gmail.com");
            customer.setAddress(address);
            customer.setStore(store);
            customer.setFirst_name("Bob");
            customer.setLast_name("Bobchenko");
            customerDao.save(customer);

            session.getTransaction().commit();
            return customer;
        }
    }
}
