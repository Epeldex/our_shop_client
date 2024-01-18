package logic.interfaces;

import logic.exceptions.LogicException;
import transfer.objects.Customer;

/**
 * Interface for communication between the REST client layer and the logic
 * layer. This interface defines methods for updating personal information,
 * deleting a user, inserting a new user, retrieving customer information, and
 * updating the customer balance.
 *
 * @author Alex Irusta
 */
public interface CustomerManager {

    /**
     * Updates the personal information of a customer identified by their user
     * ID.
     *
     * @param customer The Customer object containing updated information.
     * @throws LogicException If an error occurs during the update process.
     */
    public void updateCustomer(Customer customer) throws LogicException;

    /**
     * Deletes a user (customer) identified by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @throws LogicException If an error occurs during the delete process.
     */
    public void deleteCustomerById(Integer id) throws LogicException;

    /**
     * Inserts a new user (customer) into the system.
     *
     * @param customer The User object representing the new user.
     * @throws LogicException If an error occurs during the creation process.
     */
    public void insertCustomer(Customer customer) throws LogicException;

    /**
     * Retrieves customer information for a user identified by their user ID.
     *
     * @param userId The ID of the user for whom customer information is
     * requested.
     * @return The Customer object containing customer information.
     * @throws LogicException If an error occurs during the read process.
     */
    public Customer getCustomer(Integer userId) throws LogicException;

    /**
     * Updates the balance of a customer identified by their customer ID.
     *
     * @param customer The new balance for the customer.
     * @throws LogicException If an error occurs during the balance update
     * process.
     */
    public void updateBalance(Customer customer) throws LogicException;
}
