package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Transaction transaction = null;
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (Id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(60), lastName VARCHAR(60), age INT)";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь " + name + " добавлен в базу.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка добавления пользователя.");
            transaction.rollback();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь " + user.getName() + " удалён.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка удаления пользователя");
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            System.out.println("Все пользователи в таблице:");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка получения пользователей");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена.");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            System.out.println("Ошибка при очистке таблицы");
        }
    }
}
