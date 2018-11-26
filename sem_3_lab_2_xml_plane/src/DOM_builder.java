import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

//Actually DOM parser
public class DOM_builder extends Abstract_planes_builder {
    private DocumentBuilder document_builder;

    public DOM_builder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            document_builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e){
            System.err.println("Error of DOM parser" + e);
        }
    }

    public void build_list_planes(String fileName){
        Document doc = null;

        try {
            doc = document_builder.parse(fileName);
            //Getting root element
            Element root = doc.getDocumentElement();
            //Getting list of tags <plane>
            NodeList planesList = root.getElementsByTagName("plane");

            for(int i = 0; i < planesList.getLength(); i++){
                Element planeElement = (Element) planesList.item(i);
                Plane plane = build_plane(planeElement);
                planes.add(plane);
            }
        } catch (IOException e) {
            System.err.println("Error of IO " + e);
        }
        catch (SAXException e) {
            System.err.println("Error of DOM parser " + e);
        }
    }

    //Parsing <plane>...</plane>
    private Plane build_plane(Element planeEl){
        Plane plane = new Plane();

        //Get attributes and simpleTypes
        plane.set_model(planeEl.getAttribute("model"));
        plane.set_id(planeEl.getAttribute("id"));
        plane.set_origin(getElementTextContent(planeEl, "origin"));
        plane.set_price(Double.parseDouble(getElementTextContent(planeEl, "price")));
        //Get complexTypes
        Element charsEl = (Element) planeEl.getElementsByTagName("chars").item(0);
        build_chars(charsEl, plane.get_chars());
        Element paramsEl = (Element) planeEl.getElementsByTagName("parameters").item(0);
        build_params(paramsEl, plane.get_parameters());

        return plane;
    }
    //Parsing Chars
    private void build_chars(Element charsEl, Plane.Chars chars){

        chars.set_type(getElementTextContent(charsEl, "type"));
        chars.set_seats(Integer.parseInt(getElementTextContent(charsEl, "seats")));
        chars.set_radar(Boolean.parseBoolean(getElementTextContent(charsEl, "radar")));

        Node ammunition_node = charsEl.getElementsByTagName("ammunition").item(0);
        chars.set_ammunition(Boolean.parseBoolean(ammunition_node.getTextContent()));

        //Check whether attribute rackets is existed and set value
        Element ammunition_element = (Element) ammunition_node;
        String rocketsS = ammunition_element.getAttribute("rockets");

        if (!rocketsS.equals(""))
            chars.set_rockets(Integer.parseInt(rocketsS));

    }
    //Parsing parameters
    private void build_params(Element paramsEl, Plane.Parameters parameters){
        parameters.set_height(Double.parseDouble(getElementTextContent(paramsEl, "height")));
        parameters.set_length(Double.parseDouble(getElementTextContent(paramsEl, "length")));
        parameters.set_wingspan(Double.parseDouble(getElementTextContent(paramsEl, "wingspan")));
    }
    //Auxiliary method which get textContent from tag elementName which is child of element
    private static String getElementTextContent(Element element, String elementName){
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);

        return node.getTextContent();
    }
}
