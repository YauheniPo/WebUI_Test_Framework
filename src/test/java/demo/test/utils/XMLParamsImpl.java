package demo.test.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Xml params.
 */
public class XMLParamsImpl extends InitParams {

    @Override
    public InitParams fetchTestData(String dataBaselocation) {
        File fXmlFile = new File(dataBaselocation);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage());
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("account");
        List<String> data = new ArrayList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                data.add(eElement.getElementsByTagName("login").item(0).getTextContent());
                data.add(eElement.getElementsByTagName("password").item(0).getTextContent());
            }
        }
        setParams(data);
        LOGGER.info(String.format("Вata received from %s", dataBaselocation));
        return this;
    }
}