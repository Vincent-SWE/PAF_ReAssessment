package ibf2022.batch2.paf.server.models;

public class Address {
    
    private String building;
    private String street;
    private String zipcode;
    private String borough;

    public Address() {}

    public Address(String building, String street, String zipcode, String borough) {
        this.building = building;
        this.street = street;
        this.zipcode = zipcode;
        this.borough = borough;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    @Override
    public String toString() {
        return building + " " + street + " " + zipcode + " " + borough;
    }

    


}
