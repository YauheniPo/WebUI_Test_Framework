package webdriver.utils;

import lombok.AllArgsConstructor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import webdriver.BaseEntity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * The type Xml parse.
 */
@AllArgsConstructor
public class XMLParse extends BaseEntity {
    private final String filepath;

    /**
     * Fetch document document.
     *
     * @return the document
     */
    public Document fetchDocument() {
        File fXmlFile = new File(filepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage());
        }
        Document doc = null;
        try {
            if (dBuilder != null) {
                doc = dBuilder.parse(fXmlFile);
            }
        } catch (SAXException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        LOGGER.info(String.format("Data received from %s", filepath));
        return doc;
    }
}