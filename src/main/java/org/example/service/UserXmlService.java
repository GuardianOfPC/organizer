package org.example.service;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

import static org.example.OrganizerApp.logger;
import static org.example.OrganizerApp.scanner;
import static org.example.util.RegexUtil.*;

public class UserXmlService {
    private static final String employeeIdAttribute = "employeeId";
    private UserXmlService(){}

    static Element createUserElement(Document document) {
        Element userElement = new Element("user");

        logger.info("Введите табельный номер:");
        String employeeId = scanner.nextLine();
        if (!isNumeric(employeeId)) {
            logger.error("Табельный номер должен быть числом. Пример: 123");
            return null;
        }
        if (findUserElementByAttribute(document, employeeIdAttribute, employeeId) != null) {
            logger.error("Пользователь с таким табельным номером уже существует.");
            return null;
        }
        userElement.setAttribute(employeeIdAttribute, employeeId);

        logger.info("Введите ФИО:");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            logger.error("Введите ФИО по образцу. Пример: Иванов И.И.");
            return null;
        }
        userElement.setAttribute("name", name);

        logger.info("Введите должность:");
        String position = scanner.nextLine();
        if (!isValidPosition(position)) {
            logger.error("Должность должна состоять только из букв и пробелов. Пример: Главный агроном");
            return null;
        }
        userElement.setAttribute("position", position);

        logger.info("Введите организацию:");
        String organization = scanner.nextLine();
        if (!isValidOrganization(organization)) {
            logger.error("Организация должна состоять только из букв и пробелов. Пример: Рога и копыта");
            return null;
        }
        userElement.setAttribute("organization", organization);

        logger.info("Введите адрес электронной почты:");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            logger.error("Некорректный адрес электронной почты. Пример: ivanov@hornsHooves.ru");
            return null;
        }
        userElement.setAttribute("email", email);

        List<String> phones = new ArrayList<>();
        boolean morePhones = true;
        while (morePhones) {
            logger.info("Введите номер телефона:");
            String phone = scanner.nextLine();
            if (!isValidPhone(phone)) {
                logger.error("Некорректный номер телефона. Пример: 8-800-123-45-67 или 11-22-33");
                return null;
            }
            phones.add(phone);

            logger.info("Хотите добавить еще номер телефона? (да/нет):");
            String answer = scanner.nextLine();
            morePhones = answer.equalsIgnoreCase("да");
        }

        userElement.setAttribute("phones", String.join(",", phones));

        return userElement;
    }

    static Element findUserElement(Document document, String employeeId) {
        List<Element> userElements = document.getRootElement().getChildren("user");

        for (Element userElement : userElements) {
            if (userElement.getAttributeValue(employeeIdAttribute).equals(employeeId)) {
                return userElement;
            }
        }

        return null;
    }

    static Element findUserElementByAttribute(Document document, String attributeName, String attributeValue) {
        List<Element> userElements = document.getRootElement().getChildren("user");

        for (Element userElement : userElements) {
            if (userElement.getAttributeValue(attributeName).equals(attributeValue)) {
                return userElement;
            }
        }

        return null;
    }

    static void printUserDetails(Element userElement) {
        logger.info("Табельный номер: " + userElement.getAttributeValue(employeeIdAttribute));
        logger.info("ФИО: " + userElement.getAttributeValue("name"));
        logger.info("Должность: " + userElement.getAttributeValue("position"));
        logger.info("Организация: " + userElement.getAttributeValue("organization"));
        logger.info("Адрес электронной почты: " + userElement.getAttributeValue("email"));
        logger.info("Список телефонов: " + userElement.getAttributeValue("phones") + "\n");
    }
}
