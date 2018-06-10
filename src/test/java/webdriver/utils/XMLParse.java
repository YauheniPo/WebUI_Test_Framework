package webdriver.utils;

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
public class XMLParse extends BaseEntity {
    private String filepath;

    /**
     * Instantiates a new Xml parse.
     *
     * @param filepath the filepath
     */
    public XMLParse(String filepath) {
        this.filepath = filepath;
    }

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
            error(e.getMessage());
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException | IOException e) {
            error(e.getMessage());
        }
        doc.getDocumentElement().normalize();
        info(String.format("Data received from %s", filepath));
        return doc;
    }
}