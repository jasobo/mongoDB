package de.sobotta.DTO;

import jakarta.validation.constraints.NotBlank;

public class CustomerDTO {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    private AddressDTO address;

    private PaymentMethodDTO paymentMethod;

    public CustomerDTO() {
    }

    public CustomerDTO(String firstName, String lastName, AddressDTO address, PaymentMethodDTO paymentMethod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paymentMethod = paymentMethod;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public PaymentMethodDTO getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
