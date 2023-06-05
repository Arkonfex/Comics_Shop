package com.comicsshop.Filter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLFilterOptionsUpdater {

    public static void main(String[] args) {
        // Путь к существующему файлу filter_btn.xml
        String filePath = "filter_btn.xml";

        // Создаем объект FilterOptions и устанавливаем параметры фильтрации
        FilterOptions filterOptions = new FilterOptions(1);
        filterOptions.setFilterByAuthorName(true);
        filterOptions.setAuthorName("John Doe");
        filterOptions.setFilterByPrice(true);
        filterOptions.setSortByPriceDescending(true);

        try {
            // Загружаем XML-файл
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Получаем корневой элемент
            Element rootElement = doc.getDocumentElement();

            // Создаем элементы для каждого параметра фильтрации и добавляем их в корневой элемент
            Element filterByAuthorName = doc.createElement("filterByAuthorName");
            filterByAuthorName.appendChild(doc.createTextNode(String.valueOf(filterOptions.isFilterByAuthorName())));
            rootElement.appendChild(filterByAuthorName);

            Element authorName = doc.createElement("authorName");
            authorName.appendChild(doc.createTextNode(filterOptions.getAuthorName()));
            rootElement.appendChild(authorName);

            Element filterByPrice = doc.createElement("filterByPrice");
            filterByPrice.appendChild(doc.createTextNode(String.valueOf(filterOptions.isFilterByPrice())));
            rootElement.appendChild(filterByPrice);

            Element sortByPriceDescending = doc.createElement("sortByPriceDescending");
            sortByPriceDescending.appendChild(doc.createTextNode(String.valueOf(filterOptions.getSortByPriceDescending())));
            rootElement.appendChild(sortByPriceDescending);

            // Сохраняем изменения в XML-файле
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Параметры фильтрации успешно добавлены в файл filter_btn.xml.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
