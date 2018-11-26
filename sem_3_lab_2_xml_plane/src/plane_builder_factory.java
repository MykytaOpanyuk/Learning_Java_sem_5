public class plane_builder_factory {
    private enum Parser_type {
        SAX, DOM, STAX
    }
    public Abstract_planes_builder create_planes_builder(String parser_type) {
        Parser_type type = Parser_type.valueOf(parser_type.toUpperCase());

        switch (type) {
            case SAX:
                return new SAX_builder();
            case DOM:
                return new DOM_builder();
            case STAX:
                return new STAX_builder();
            default:
                return new DOM_builder();
        }
    }
}
