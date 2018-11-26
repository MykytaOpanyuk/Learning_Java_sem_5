import java.util.ArrayList;
import java.util.List;

//Base abstract class for parsers
public abstract class Abstract_planes_builder {
    protected List<Plane> planes;

    public Abstract_planes_builder() {
        planes = new ArrayList<>();
    }

    public List<Plane> get_planes() {
        return planes;
    }

    abstract public void build_list_planes(String file_name); // save to file a list of
}
