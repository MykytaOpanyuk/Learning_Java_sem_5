public class Plane {

    static public class Chars {
        private String type;
        private int seats;
        private boolean ammunition;
        private int rockets;
        private boolean radar;

        public void set_type(String type) {
            this.type = type;
        }

        public void set_seats(int seats) {
            this.seats = seats;
        }

        public void set_ammunition(boolean ammunition) {
            this.ammunition = ammunition;
        }

        public void set_rockets(int rockets) {
            this.rockets = rockets;
        }

        public  void set_radar(boolean radar) {
            this.radar = radar;
        }

        @Override
        public String toString() {
            String ammunition_s, radar_s;

            if (ammunition)
                ammunition_s = ("yes\nRockets " + rockets);
            else
                ammunition_s = "no";

            if (radar)
                radar_s = "yes";
            else
                radar_s = "no";
            return "\nChars\nType " + type +"\nSeats "+seats +
                    "\nAmmunition " + ammunition_s + "\nRadar " + radar_s;
        }
    }

    static public class Parameters {
        private double height;
        private double length;
        private double wingspan;

        public void set_height(double height) {
            this.height = height;
        }

        public void set_length(double length) {
            this.length = length;
        }

        public void set_wingspan(double wingspan) {
            this.wingspan = wingspan;
        }

        @Override
        public String toString() {
            return "\nParameters\nHeight " + height +
                    "\nLength " + length + "\nWingspan " + wingspan;
        }
    }

    private String id;
    private String model;
    private String origin;

    private Chars chars;
    private Parameters parameters;
    private double price;

    public Plane() {
        chars = new Chars();
        parameters = new Parameters();
    }
    public void set_id(String id) {
        this.id = id;
    }

    public void set_model(String model) {
        this.model = model;
    }

    public void set_origin(String origin) {
        this.origin = origin;
    }

    public  void set_price(double price) {
        this.price = price;
    }

    public Chars get_chars() {
        return chars;
    }

    public Parameters get_parameters() {
        return parameters;
    }

    public double get_price() {
        return price;
    }

    @Override
    public String toString() {
        return "\nModel "+ model+"\nID " + id + "\nOrigin " + origin +
                chars.toString() + parameters.toString() +
                "\nPrice " + price +'\n';
    }
}
