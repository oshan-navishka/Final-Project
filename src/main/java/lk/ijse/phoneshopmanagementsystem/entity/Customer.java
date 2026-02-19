package lk.ijse.phoneshopmanagementsystem.entity;

public class Customer {
    private String customerId;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String nic;

    public Customer() {
    }

    public Customer(String customerId, String name, String address, String contact, String email, String nic) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.nic = nic;
    }


    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getNic() {
        return nic;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
