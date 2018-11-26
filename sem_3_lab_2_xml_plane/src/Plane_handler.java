import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

class Plane_handler extends DefaultHandler {
    private List<Plane> planes;
    private Plane current = null;
    private Plane_enum currentEnum = null;
    private EnumSet<Plane_enum> withText;

    public  Plane_handler(){
        planes = new ArrayList<>();
        withText = EnumSet.range(Plane_enum.ORIGIN, Plane_enum.SEATS);
    }

    //Check whether new plane was started or tag with textContent
    //Other check whether ammunition was started. We need it to
    //Find ammunition attribute
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs){
        if("plane".equals(qName)) {
            current = new Plane();
            current.set_model(attrs.getValue(0));
            current.set_id(attrs.getValue(1));
        }
        else {
            Plane_enum temp = null;
            try {
                temp = Plane_enum.valueOf(qName.toUpperCase());}
            catch(IllegalArgumentException e){
                System.err.println(localName.toUpperCase() + " " + qName);
            }

            if(temp != null)
                if (withText.contains(temp))
                    currentEnum = temp;

            if("ammunition".equals(qName) && attrs.getLength() == 1)
                current.get_chars().set_rockets(Integer.parseInt(attrs.getValue(0)));

        }
    }

    //Check whether we know everything about current plane
    @Override
    public void endElement(String uri, String localName, String qName) {
        if("plane".equals(qName))
            planes.add(current);
    }

    //Check whether textContent is available
    @Override
    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();

        if(currentEnum != null) {
            switch(currentEnum) {
                case ID:
                    current.set_id(s);
                    break;
                case TYPE:
                    current.get_chars().set_type(s);
                    break;
                case AMMUNITION:
                    current.get_chars().set_ammunition(Boolean.parseBoolean(s));
                    break;
                case ROCKETS:
                    current.get_chars().set_rockets(Integer.parseInt(s));
                    break;
                case RADAR:
                    current.get_chars().set_radar(Boolean.parseBoolean(s));
                    break;
                case PRICE:
                    current.set_price(Double.parseDouble(s));
                    break;
                case HEIGHT:
                    current.get_parameters().set_height(Double.parseDouble(s));
                    break;
                case LENGTH:
                    current.get_parameters().set_length(Double.parseDouble(s));
                    break;
                case WINGSPAN:
                    current.get_parameters().set_wingspan(Double.parseDouble(s));
                    break;
                case MODEL:
                    current.set_model(s);
                    break;
                case ORIGIN:
                    current.set_origin(s);
                    break;
                case SEATS:
                    current.get_chars().set_seats(Integer.parseInt(s));
                    break;
                default:
                    throw new EnumConstantNotPresentException(currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }

    public List<Plane> get_planes() {
        return planes;
    }
}
