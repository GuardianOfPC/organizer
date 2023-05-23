package org.example;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlUtil {
    private static final String XML_FILE_PATH = "src\\main\\resources\\users.xml";

    static void createEmptyXmlFileIfNeeded() {
        File xmlFile = new File(XML_FILE_PATH);
        if (!xmlFile.exists()) {
            Element rootElement = new Element("users");
            Document document = new Document(rootElement);
            saveXmlFile(document);
        }
    }

    static Document readXmlFile() {
        File xmlFile = new File(XML_FILE_PATH);
        if (!xmlFile.exists()) {
            System.out.println("XML-файл не существует.");
            return null;
        }

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            return saxBuilder.build(xmlFile);
        } catch (IOException e) {
            System.out.println("Ошибка чтения XML-файла: " + e.getMessage());
        } catch (JDOMException e) {
            System.out.println("Ошибка парсинга XML-файла: " + e.getMessage());
        }

        return null;
    }

    static void saveXmlFile(Document document) {
        try {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            try (FileWriter writer = new FileWriter(XML_FILE_PATH)) {
                xmlOutputter.output(document, writer);
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения XML-файла: " + e.getMessage());
        }
    }
}
