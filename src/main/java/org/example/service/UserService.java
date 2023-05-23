package org.example.service;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;

import static org.example.OrganizerApp.scanner;
import static org.example.service.UserXmlService.*;
import static org.example.util.XmlUtil.readXmlFile;
import static org.example.util.XmlUtil.saveXmlFile;

public class UserService {
    public static void addUser() {
        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        Element userElement = createUserElement();
        if (userElement == null) {
            return;
        }

        Element rootElement = document.getRootElement();
        rootElement.addContent(userElement);
        saveXmlFile(document);
        System.out.println("Пользователь успешно добавлен.");
    }

    public static void editUser() {
        System.out.println("Введите табельный номер пользователя для редактирования:");
        String employeeId = scanner.nextLine();

        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        Element userElement = findUserElement(document, employeeId);
        if (userElement == null) {
            System.out.println("Пользователь с указанным табельным номером не найден.");
            return;
        }

        System.out.println("Текущие данные пользователя:");
        printUserDetails(userElement);
        System.out.println("Введите новые данные пользователя:");

        Element updatedUserElement = createUserElement();
        if (updatedUserElement == null) {
            return;
        }

        Element rootElement = document.getRootElement();
        rootElement.removeContent(userElement);
        rootElement.addContent(updatedUserElement);
        saveXmlFile(document);
        System.out.println("Данные пользователя успешно обновлены.");
    }

    public static void listUsers() {
        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        List<Element> userElements = document.getRootElement().getChildren("user");
        if (userElements.isEmpty()) {
            System.out.println("Список пользователей пуст.");
            return;
        }

        System.out.println("Список пользователей:");
        for (Element userElement : userElements) {
            printUserDetails(userElement);
        }
    }

    public static void findUser() {
        System.out.println("Выберите атрибут для поиска пользователя:");
        System.out.println("1. Табельный номер");
        System.out.println("2. ФИО");
        System.out.println("3. Должность");
        System.out.println("4. Организация");
        System.out.println("5. Адрес электронной почты");
        System.out.println("6. Список телефонов");

        int attributeChoice = scanner.nextInt();
        scanner.nextLine();

        String attributeName;
        switch (attributeChoice) {
            case 1 -> attributeName = "employeeId";
            case 2 -> attributeName = "name";
            case 3 -> attributeName = "position";
            case 4 -> attributeName = "organization";
            case 5 -> attributeName = "email";
            case 6 -> attributeName = "phones";
            default -> {
                System.out.println("Некорректный выбор атрибута. Повторите попытку.");
                return;
            }
        }

        System.out.println("Введите значение атрибута для поиска:");
        String attributeValue = scanner.nextLine();

        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        Element userElement = findUserElementByAttribute(document, attributeName, attributeValue);
        if (userElement == null) {
            System.out.println("Пользователь с указанными атрибутами не найден.");
            return;
        }

        System.out.println("Найденный пользователь:");
        printUserDetails(userElement);
    }
}
