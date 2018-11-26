import javax.xml.XMLConstants;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

            for (Plane plane : planes)
                System.out.println(plane.toString());

        }
        //Transform xml to html
        //With schema transform doesn't see elements
        Transform transform = new Transform();
        transform.transform("/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planes.xsl",
                            "/home/nikita/IdeaProjects/sem_3_lab_2_xml_plane/src/planesWithoutSchema.xml",
                       "newPlanes.html");
    }
}
