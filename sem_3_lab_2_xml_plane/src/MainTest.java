import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void Test_DOM() {
        //Variables for validation and some for parser purpose(file_name)
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String file_name = "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xml";
        String schema_name = "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xsd";
        boolean isValid = true; // Check whether xml is valid with xsd
        Validation validator = new Validation();

        try {
            validator.validate(language, file_name, schema_name);
        } catch (IOException e) {
            isValid = false;
            System.err.println("File " + file_name + " is not valid because " + e);
        } catch (SAXException e) {
            isValid = false;
            System.err.println("File " + file_name + "is not valid because " + e);
        }

        if(isValid) {
            plane_builder_factory builderFactory = new plane_builder_factory();
            // Choosing parser
            Abstract_planes_builder builder = builderFactory.create_planes_builder("DOM");
            builder.build_list_planes(file_name);
            List<Plane> planes = builder.get_planes();

            planes.sort(new Comparator<Plane>() { // Sorting items in reverse order
                @Override
                public int compare(Plane p1, Plane p2) {
                    if (p1.get_price() > p2.get_price())
                        return -1;
                    else
                        return 1;
                }
            });

            assertEquals(planes.get(0).get_parameters().toString(), ("\nParameters\nHeight 6.36" +
                    "\nLength 20.4" + "\nWingspan 13.5"));

            assertEquals(planes.get(0).toString(), ("\nModel J-20" + "\nID ID-8751" + "\nOrigin China" +
                    "\nChars\nType fighter" + "\nSeats 1" +
                    "\nAmmunition yes\nRockets 8" + "\nRadar yes" + "\nParameters\nHeight 6.36" +
                    "\nLength 20.4" + "\nWingspan 13.5" +
                    "\nPrice 4.0E7" + '\n'));
        }
    }

    @org.junit.Test
    public void Test_SAX() {
        //Variables for validation and some for parser purpose(file_name)
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String file_name = "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xml";
        String schema_name = "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xsd";
        boolean isValid = true; // Check whether xml is valid with xsd
        Validation validator = new Validation();

        try {
            validator.validate(language, file_name, schema_name);
        } catch (IOException e) {
            isValid = false;
            System.err.println("File " + file_name + " is not valid because " + e);
        } catch (SAXException e) {
            isValid = false;
            System.err.println("File " + file_name + "is not valid because " + e);
        }

        if(isValid) {
            plane_builder_factory builderFactory = new plane_builder_factory();
            // Choosing parser
            Abstract_planes_builder builder = builderFactory.create_planes_builder("SAX");
            builder.build_list_planes(file_name);
            List<Plane> planes = builder.get_planes();

            planes.sort(new Comparator<Plane>() { // Sorting items in reverse order
                @Override
                public int compare(Plane p1, Plane p2) {
                    if (p1.get_price() > p2.get_price())
                        return -1;
                    else
                        return 1;
                }
            });

            assertEquals(planes.get(0).get_parameters().toString(), ("\nParameters\nHeight 6.36" +
                    "\nLength 20.4" + "\nWingspan 13.5"));

            assertEquals(planes.get(0).toString(), ("\nModel J-20" + "\nID ID-8751" + "\nOrigin China" +
                    "\nChars\nType fighter" + "\nSeats 1" +
                    "\nAmmunition yes\nRockets 8" + "\nRadar yes" + "\nParameters\nHeight 6.36" +
                    "\nLength 20.4" + "\nWingspan 13.5" +
                    "\nPrice 4.0E7" + '\n'));
        }
    }

    @org.junit.Test
    public void Test_STAX() {
        //Variables for validation and some for parser purpose(file_name)
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String file_name = "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xml";
        String schema_name = "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xsd";
        boolean isValid = true; // Check whether xml is valid with xsd
        Validation validator = new Validation();

        try {
            validator.validate(language, file_name, schema_name);
        } catch (IOException e) {
            isValid = false;
            System.err.println("File " + file_name + " is not valid because " + e);
        } catch (SAXException e) {
            isValid = false;
            System.err.println("File " + file_name + "is not valid because " + e);
        }

        if(isValid) {
            plane_builder_factory builderFactory = new plane_builder_factory();
            // Choosing parser
            Abstract_planes_builder builder = builderFactory.create_planes_builder("STAX");
            builder.build_list_planes(file_name);
            List<Plane> planes = builder.get_planes();

            planes.sort(new Comparator<Plane>() { // Sorting items in reverse order
                @Override
                public int compare(Plane p1, Plane p2) {
                    if (p1.get_price() > p2.get_price())
                        return -1;
                    else
                        return 1;
                }
            });

            assertEquals(planes.get(0).get_parameters().toString(), ("\nParameters\nHeight 6.36" +
                    "\nLength 20.4" + "\nWingspan 13.5"));

            assertEquals(planes.get(0).toString(), ("\nModel J-20" + "\nID ID-8751" + "\nOrigin China" +
                    "\nChars\nType fighter" + "\nSeats 1" +
                    "\nAmmunition yes\nRockets 8" + "\nRadar yes" + "\nParameters\nHeight 6.36" +
                    "\nLength 20.4" + "\nWingspan 13.5" +
                    "\nPrice 4.0E7" + '\n'));
        }
    }
}