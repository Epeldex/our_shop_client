package transfer.objects;

import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Customer class represents a customer entity, extending the User class,
 * and encapsulates information about a customer, such as their full name,
 * email, address, phone number, and balance.
 *
 * @author Alex Irusta
 */
@XmlRootElement
public class Customer extends User {

    // The full name of the customer
    private String fullName;

    // The email address of the customer
    private String email;

    // The street address of the customer
    private String street;

    // The postal code of the customer's address
    private Integer postalCode;

    // The city of the customer's address
    private String city;

    // The phone number of the customer
    private String phone;

    // The balance of the customer's account
    private Double balance;

    // The set of products bought by the customer
    private Set<ProductsBought> productsBought;

    /**
     * Gets the full name of the customer.
     *
     * @return The full name of the customer.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the customer.
     *
     * @param fullName The new full name of the customer.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the email address of the customer.
     *
     * @return The email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email The new email address of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the street address of the customer.
     *
     * @return The street address of the customer.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address of the customer.
     *
     * @param street The new street address of the customer.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the postal code of the customer's address.
     *
     * @return The postal code of the customer's address.
     */
    public Integer getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer's address.
     *
     * @param postalCode The new postal code of the customer's address.
     */
    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the city of the customer's address.
     *
     * @return The city of the customer's address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the customer's address.
     *
     * @param city The new city of the customer's address.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The new phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the balance of the customer's account.
     *
     * @return The balance of the customer's account.
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the customer's account.
     *
     * @param balance The new balance of the customer's account.
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<ProductsBought> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(Set<ProductsBought> productsBought) {
        this.productsBought = productsBought;
    }

}
