package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

public class OrganizerApp {
    private static final String XML_FILE_PATH = "users.xml";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        File xmlFile = new File(XML_FILE_PATH);
        if (!xmlFile.exists()) {
            System.out.println("XML-файл не найден. Создание нового файла...");
            try {
                xmlFile.createNewFile();
                createEmptyXmlFile();
            } catch (IOException e) {
                System.out.println("Ошибка при создании XML-файла: " + e.getMessage());
                System.exit(1);
            }
        }

        while (true) {
            System.out.println("Выберите операцию:");
            System.out.println("1. Добавить нового пользователя");
            System.out.println("2. Редактировать пользователя");
            System.out.println("3. Получить список пользователей");
            System.out.println("4. Получить пользователя по заданным атрибутам");
            System.out.println("0. Выйти из приложения");

            int choice = scanner.nextInt();
            scanner.nextLine(); // считываем символ новой строки после ввода числа

            switch (choice) {
                case 0 -> {
                    System.out.println("Работа приложения завершена.");
                    return;
                }
                case 1 -> addUser();
                case 2 -> editUser();
                case 3 -> listUsers();
                case 4 -> findUser();
                default -> System.out.println("Некорректный выбор операции. Повторите попытку.");
            }

            System.out.println();
        }
    }

    private static void addUser() {
        Element userElement = createUserElement();

        Document document = readXmlFile();
        if (document == null) {
            System.out.println("XML-файл не найден или поврежден. Создание нового файла...");
            new File(XML_FILE_PATH);
            createEmptyXmlFile();
            document = readXmlFile();
        }

        if (document != null) {
            Element rootElement = document.getRootElement();
            rootElement.addContent(userElement);
            saveXmlFile(document);
            System.out.println("Пользователь успешно добавлен.");
        }
    }

    private static void editUser() {
        System.out.println("Введите табельный номер пользователя для редактирования:");
        String employeeId = scanner.nextLine();

        Document document = readXmlFile();
        if (document != null) {
            Element userElement = findUserElement(document, employeeId);

            if (userElement != null) {
                System.out.println("Текущие данные пользователя:");
                printUserDetails(userElement);
                System.out.println("Введите новые данные пользователя:");

                Element updatedUserElement = createUserElement();

                Element rootElement = document.getRootElement();
                rootElement.removeContent(userElement);
                rootElement.addContent(updatedUserElement);

                saveXmlFile(document);
                System.out.println("Пользователь успешно обновлен.");
            } else {
                System.out.println("Пользователь с указанным табельным номером не найден.");
            }
        }
    }

    private static void listUsers() {
        Document document = readXmlFile();
        if (document != null) {
            List<Element> userElements = document.getRootElement().getChildren();

            if (userElements.isEmpty()) {
                System.out.println("Список пользователей пуст.");
            } else {
                for (Element userElement : userElements) {
                    printUserDetails(userElement);
                }
            }
        }
    }

    private static void findUser() {
        System.out.println("Выберите атрибут для поиска пользователя:");
        System.out.println("1. Табельный номер");
        System.out.println("2. ФИО");
        System.out.println("3. Должность");
        System.out.println("4. Организация");
        System.out.println("5. Почтовый адрес");
        System.out.println("6. Телефон");
        int attributeChoice = scanner.nextInt();
        scanner.nextLine(); // считываем символ новой строки после ввода числа

        String attributeName;
        switch (attributeChoice) {
            case 1 -> attributeName = "employeeId";
            case 2 -> attributeName = "name";
            case 3 -> attributeName = "position";
            case 4 -> attributeName = "organization";
            case 5 -> attributeName = "address";
            case 6 -> attributeName = "phone";
            default -> {
                System.out.println("Некорректный выбор атрибута. Повторите попытку.");
                return;
            }
        }

        System.out.println("Введите значение атрибута для поиска:");
        String attributeValue = scanner.nextLine();

        Document document = readXmlFile();
        if (document != null) {
            Element userElement = findUserElementByAttribute(document, attributeName, attributeValue);

            if (userElement != null) {
                printUserDetails(userElement);
            } else {
                System.out.println("Пользователь с заданными атрибутами не найден.");
            }
        }
    }

    private static Element createUserElement() {
        Element userElement = new Element("user");

        System.out.println("Введите табельный номер:");
        String employeeId = scanner.nextLine();
        userElement.setAttribute("employeeId", employeeId);

        System.out.println("Введите ФИО:");
        String name = scanner.nextLine();
        userElement.setAttribute("name", name);

        System.out.println("Введите должность:");
        String position = scanner.nextLine();
        userElement.setAttribute("position", position);

        System.out.println("Введите организацию:");
        String organization = scanner.nextLine();
        userElement.setAttribute("organization", organization);

        System.out.println("Введите почтовый адрес:");
        String address = scanner.nextLine();
        userElement.setAttribute("address", address);

        System.out.println("Введите список телефонов (разделяйте номера запятой):");
        String phones = scanner.nextLine();
        userElement.setAttribute("phone", phones);

        return userElement;
    }

    private static Document readXmlFile() {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            return saxBuilder.build(new File(XML_FILE_PATH));
        } catch (IOException e) {
            System.out.println("Ошибка чтения XML-файла: " + e.getMessage());
        } catch (JDOMException e) {
            System.out.println("Ошибка парсинга XML-файла: " + e.getMessage());
        }
        return null;
    }

    private static void createEmptyXmlFile() {
        Element rootElement = new Element("users");
        Document document = new Document(rootElement);
        saveXmlFile(document);
    }

    private static void saveXmlFile(Document document) {
        try {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            FileWriter writer = new FileWriter(XML_FILE_PATH);
            xmlOutputter.output(document, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка сохранения XML-файла: " + e.getMessage());
        }
    }

    private static Element findUserElement(Document document, String employeeId) {
        List<Element> userElements = document.getRootElement().getChildren("user");

        for (Element userElement : userElements) {
            if (userElement.getAttributeValue("employeeId").equals(employeeId)) {
                return userElement;
            }
        }

        return null;
    }

    private static Element findUserElementByAttribute(Document document, String attributeName, String attributeValue) {
        List<Element> userElements = document.getRootElement().getChildren("user");

        for (Element userElement : userElements) {
            if (userElement.getAttributeValue(attributeName).equals(attributeValue)) {
                return userElement;
            }
        }

        return null;
    }

    private static void printUserDetails(Element userElement) {
        System.out.println("Табельный номер: " + userElement.getAttributeValue("employeeId"));
        System.out.println("ФИО: " + userElement.getAttributeValue("name"));
        System.out.println("Должность: " + userElement.getAttributeValue("position"));
        System.out.println("Организация: " + userElement.getAttributeValue("organization"));
        System.out.println("Почтовый адрес: " + userElement.getAttributeValue("address"));
        System.out.println("Телефоны: " + userElement.getAttributeValue("phone"));
        System.out.println();
    }
}