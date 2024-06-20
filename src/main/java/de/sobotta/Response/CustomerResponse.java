package de.sobotta.Response;

public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private AddressResponse addressResponse;

    public CustomerResponse() {
    }

    public CustomerResponse(String id, String firstName, String lastName, AddressResponse addressResponse) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressResponse = addressResponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }

    public void setAddressResponse(AddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }
}
