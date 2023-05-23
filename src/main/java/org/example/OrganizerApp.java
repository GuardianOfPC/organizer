package org.example;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static org.example.service.UserService.*;
import static org.example.util.XmlUtil.createEmptyXmlFileIfNeeded;

public class OrganizerApp {
    public static final Scanner scanner = new Scanner(System.in);
    public static final Logger logger = LogManager.getLogger(OrganizerApp.class);

    public static void main(String[] args) {
        createEmptyXmlFileIfNeeded();

        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> editUser();
                case 3 -> listUsers();
                case 4 -> findUser();
                case 0 -> logger.info("Выход из программы.");
                default -> logger.error("Некорректный выбор. Повторите попытку.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printMenu() {
        logger.info("Меню:");
        logger.info("1. Добавить нового пользователя");
        logger.info("2. Редактировать пользователя");
        logger.info("3. Получить список пользователей");
        logger.info("4. Получить пользователя по заданным атрибутам");
        logger.info("0. Выход");
        logger.info("Выберите операцию:");
    }
}