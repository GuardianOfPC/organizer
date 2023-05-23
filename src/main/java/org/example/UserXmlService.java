package org.example;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

import static org.example.CheckingUtil.*;
import static org.example.OrganizerApp.scanner;

public class UserXmlService {

    static Element createUserElement() {
        Element userElement = new Element("user");

        System.out.println("Введите табельный номер:");
        String employeeId = scanner.nextLine();
        if (!isNumeric(employeeId)) {
            System.out.println("Табельный номер должен быть числом.");
            return null;
        }
        userElement.setAttribute("employeeId", employeeId);

        System.out.println("Введите ФИО:");
        String name = scanner.nextLine();
        if (!isValidName(name)) {
            System.out.println("Введите ФИО по образцу: Иванов И.И.");
            return null;
        }
        userElement.setAttribute("name", name);

        System.out.println("Введите должность:");
        String position = scanner.nextLine();
        if (!isValidPosition(position)) {
            System.out.println("Должность должна состоять только из букв и пробелов.");
            return null;
        }
        userElement.setAttribute("position", position);

        System.out.println("Введите организацию:");
        String organization = scanner.nextLine();
        if (!isValidOrganization(organization)) {
            System.out.println("Организация должна состоять только из букв и пробелов.");
            return null;
        }
        userElement.setAttribute("organization", organization);

        System.out.println("Введите адрес электронной почты:");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println("Некорректный адрес электронной почты.");
            return null;
        }
        userElement.setAttribute("email", email);

        List<String> phones = new ArrayList<>();
        boolean morePhones = true;
        while (morePhones) {
            System.out.println("Введите номер телефона:");
            String phone = scanner.nextLine();
            if (!isValidPhone(phone)) {
                System.out.println("Номер телефона должен быть в формате XXX-XXX-XXX.");
                return null;
            }
            phones.add(phone);

            System.out.println("Хотите добавить еще номер телефона? (да/нет):");
            String answer = scanner.nextLine();
            morePhones = answer.equalsIgnoreCase("да");
        }

        userElement.setAttribute("phones", String.join(",", phones));

        return userElement;
    }

    static Element findUserElement(Document document, String employeeId) {
        List<Element> userElements = document.getRootElement().getChildren("user");

        for (Element userElement : userElements) {
            if (userElement.getAttributeValue("employeeId").equals(employeeId)) {
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
        System.out.println("Табельный номер: " + userElement.getAttributeValue("employeeId"));
        System.out.println("ФИО: " + userElement.getAttributeValue("name"));
        System.out.println("Должность: " + userElement.getAttributeValue("position"));
        System.out.println("Организация: " + userElement.getAttributeValue("organization"));
        System.out.println("Адрес электронной почты: " + userElement.getAttributeValue("email"));
        System.out.println("Список телефонов: " + userElement.getAttributeValue("phones"));
        System.out.println();
    }
}
