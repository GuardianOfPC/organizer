package org.example;

import java.util.Scanner;

import static org.example.service.UserService.*;
import static org.example.util.XmlUtil.createEmptyXmlFileIfNeeded;

public class OrganizerApp {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        createEmptyXmlFileIfNeeded();

        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // считываем символ новой строки после ввода числа

            switch (choice) {
                case 1 -> addUser();
                case 2 -> editUser();
                case 3 -> listUsers();
                case 4 -> findUser();
                case 0 -> System.out.println("Выход из программы.");
                default -> System.out.println("Некорректный выбор. Повторите попытку.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Меню:");
        System.out.println("1. Добавить нового пользователя");
        System.out.println("2. Редактировать пользователя");
        System.out.println("3. Получить список пользователей");
        System.out.println("4. Получить пользователя по заданным атрибутам");
        System.out.println("0. Выход");
        System.out.println("Выберите операцию:");
    }
}