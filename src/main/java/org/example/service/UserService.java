package org.example.service;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;

import static org.example.OrganizerApp.logger;
import static org.example.OrganizerApp.scanner;
import static org.example.service.UserXmlService.*;
import static org.example.util.XmlUtil.readXmlFile;
import static org.example.util.XmlUtil.saveXmlFile;

public class UserService {
    private UserService(){}

    public static void addUser() {
        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        Element userElement = createUserElement(document);
        if (userElement == null) {
            return;
        }

        Element rootElement = document.getRootElement();
        rootElement.addContent(userElement);
        saveXmlFile(document);
        logger.info("Пользователь успешно добавлен.");
    }

    public static void editUser() {
        logger.info("Введите табельный номер пользователя для редактирования:");
        String employeeId = scanner.nextLine();

        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        Element userElement = findUserElement(document, employeeId);
        if (userElement == null) {
            logger.error("Пользователь с указанным табельным номером не найден.");
            return;
        }

        logger.info("Текущие данные пользователя:");
        printUserDetails(userElement);
        logger.info("Введите новые данные пользователя:");

        Element updatedUserElement = createUserElement(document);
        if (updatedUserElement == null) {
            logger.error("Ошибка создания элемента пользователя в XML.");
            return;
        }

        Element rootElement = document.getRootElement();
        rootElement.removeContent(userElement);
        rootElement.addContent(updatedUserElement);
        saveXmlFile(document);
        logger.info("Данные пользователя успешно обновлены.");
    }

    public static void listUsers() {
        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        List<Element> userElements = document.getRootElement().getChildren("user");
        if (userElements.isEmpty()) {
            logger.error("Список пользователей пуст.");
            return;
        }

        logger.info("Список пользователей:\n");
        for (Element userElement : userElements) {
            printUserDetails(userElement);
        }
    }

    public static void findUser() {
        logger.info("Выберите атрибут для поиска пользователя:");
        logger.info("1. Табельный номер");
        logger.info("2. ФИО");
        logger.info("3. Должность");
        logger.info("4. Организация");
        logger.info("5. Адрес электронной почты");
        logger.info("6. Список телефонов");

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
                logger.error("Некорректный выбор атрибута. Повторите попытку.");
                return;
            }
        }

        logger.info("Введите значение атрибута для поиска:");
        String attributeValue = scanner.nextLine();

        Document document = readXmlFile();
        if (document == null) {
            return;
        }

        Element userElement = findUserElementByAttribute(document, attributeName, attributeValue);
        if (userElement == null) {
            logger.error("Пользователь с указанными атрибутами не найден.");
            return;
        }

        logger.info("Найденный пользователь:");
        printUserDetails(userElement);
    }
}
