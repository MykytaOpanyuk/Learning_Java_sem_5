
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;

//Actually SAX parser
public class SAX_builder extends Abstract_planes_builder{
    private Plane_handler ph;//Handler for parsing
    private XMLReader reader;

    public SAX_builder() {
        ph = new Plane_handler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(ph);
        } catch (SAXException e) {
            System.err.println("Error of SAX parser " + e);
        }
    }

    //Parsing. But all works is doing with handler.
    public void build_list_planes(String fileName){
        try {
            reader.parse(fileName);
        } catch(IOException e){
            System.err.println("Error of IO " + e);
        } catch (SAXException e){
            System.err.println("Error of SAX parser " + e);
        }
        planes = ph.get_planes();
    }
}
