
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//Actually STAX parser
public class STAX_builder extends Abstract_planes_builder{
    private XMLInputFactory inputFactory;
    public STAX_builder(){
        inputFactory = XMLInputFactory.newInstance();
    }

    //Parsing
    public void build_list_planes(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;

        try {
            inputStream = new FileInputStream(fileName);
            reader = inputFactory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (Plane_enum.valueOf(name.toUpperCase()) == Plane_enum.PLANE) {
                        Plane plane = build_plane(reader);
                        planes.add(plane);
                    }
                }
            }
        } catch (XMLStreamException e) {
            System.err.println("Error of STAX parser " + e);
        } catch (FileNotFoundException e) {
            System.err.println("File not found " + e);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                System.err.println("Error of IO " + e);
            }
        }
    }

    //Parsing plane
    private Plane build_plane(XMLStreamReader reader) throws XMLStreamException{
        Plane plane = new Plane();
        plane.set_model(reader.getAttributeValue(null, Plane_enum.MODEL.getValue()));
        plane.set_id(reader.getAttributeValue(null, Plane_enum.ID.getValue()));
        String name;

        while(reader.hasNext()){ //Get values of next elements (children of plane or end of plane)

            int type = reader.next();
            switch (type){
                //In case of start, we get info about children
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch(Plane_enum.valueOf(name.toUpperCase())){
                        case ORIGIN:
                            plane.set_origin(getXMLText(reader));
                            break;
                        case PRICE:
                            plane.set_price(Double.parseDouble(getXMLText(reader)));
                            break;
                        case CHARS:
                            buildChars(reader, plane.get_chars());
                            break;
                        case PARAMETERS:
                            buildParameters(reader, plane.get_parameters());
                            break;
                    }
                    break;
                //In case of end, we know everything about current plane
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if(Plane_enum.valueOf(name.toUpperCase()) == Plane_enum.PLANE)
                        return plane;

                    break;
            }
        }
        return plane;
    }

    //Parsing chars the same as parsing plane
    private void buildChars(XMLStreamReader reader, Plane.Chars chars){
        int type;
        String name;

        try {
            while (reader.hasNext()) {
                type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        name = reader.getLocalName();
                        switch (Plane_enum.valueOf(name.toUpperCase())) {
                            case TYPE:
                                chars.set_type(getXMLText(reader));
                                break;
                            case SEATS:
                                chars.set_seats(Integer.parseInt(getXMLText(reader)));
                                break;
                            case RADAR:
                                chars.set_radar(Boolean.parseBoolean(getXMLText(reader)));
                                break;
                            case AMMUNITION:
                                if (reader.getAttributeCount() == 1)
                                    chars.set_rockets(Integer.parseInt(reader.getAttributeValue(0)));

                                chars.set_ammunition(Boolean.parseBoolean(getXMLText(reader)));
                                break;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (Plane_enum.valueOf(name.toUpperCase()) == Plane_enum.CHARS)
                            return;

                        break;

                }
            }
        } catch (XMLStreamException e){
            System.err.println("Unknown element in tag chars");
        }
    }

    //Parsing parameters the same as parsing plane
    private void buildParameters(XMLStreamReader reader, Plane.Parameters params){
        int type;
        String name;

        try {
            while (reader.hasNext()) {
                type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        name = reader.getLocalName();
                        switch (Plane_enum.valueOf(name.toUpperCase())) {
                            case HEIGHT:
                                params.set_height(Double.parseDouble(getXMLText(reader)));
                                break;
                            case LENGTH:
                                params.set_length(Double.parseDouble(getXMLText(reader)));
                                break;
                            case WINGSPAN:
                                params.set_wingspan(Double.parseDouble(getXMLText(reader)));
                                break;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (Plane_enum.valueOf(name.toUpperCase()) == Plane_enum.PARAMETERS)
                            return;
                }
            }
        } catch (XMLStreamException e) {
            System.err.println("Unknown element in tag parameters");
        }
    }

    //Auxiliary method for getting textContent
    private String getXMLText(XMLStreamReader reader) throws XMLStreamException{
        String text = null;

        if(reader.hasNext()){
            reader.next();
            text = reader.getText();
        }

        return text;
    }
}
