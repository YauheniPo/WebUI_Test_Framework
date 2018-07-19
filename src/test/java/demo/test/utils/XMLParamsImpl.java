package demo.test.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webdriver.utils.XMLParse;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Xml parameters.
 */
public class XMLParamsImpl extends InitParams {

    /**
     * Fetch test data object [ ].
     *
     * @param dataBaselocation the data baselocation
     *
     * @return the object [ ]
     */
    @Override
    public Object[] fetchTestData(String dataBaselocation) {
        Document doc = new XMLParse(dataBaselocation).fetchDocument();
        NodeList nList = doc.getElementsByTagName("account");
        List<String> data = new ArrayList<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                data.add(eElement.getElementsByTagName("login").item(0).getTextContent());
                data.add(eElement.getElementsByTagName("password").item(0).getTextContent());
            }
        }
        return getTestDataMails(data);
    }
}