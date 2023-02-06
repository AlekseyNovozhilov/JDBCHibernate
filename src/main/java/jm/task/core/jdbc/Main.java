package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("a1", "b1", (byte) 11);
        userService.saveUser("a2", "b2", (byte) 33);
        userService.saveUser("a3", "b3", (byte) 55);
        userService.saveUser("a4", "b4", (byte) 77);
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("==========================================");
        userService.cleanUsersTable();
        userService.getAllUsers();
        System.out.println("==========================================");
        userService.dropUsersTable();

    }
}
