package logic.interfaces;

import entities.Customer;
import entities.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.ejb.Local;

/**
 * Local interface for an EJB (Enterprise JavaBeans) session bean responsible
 * for managing customer data. This interface defines methods for updating
 * personal information, deleting a user, inserting a new user, and retrieving
 * customer information.
 *
 * @author 2dam
 */
public interface CustomerManager {

    /**
     * Updates the personal information of a customer identified by their user
     * ID.
     *
     * @param customer The Customer object containing updated information.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void updateCustomer(Customer customer) throws UpdateException;

    /**
     * Deletes a user (customer) identified by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @throws DeleteException If an error occurs during the delete process.
     */
    public void deleteCustomerById(Integer id) throws DeleteException;

    /**
     * Inserts a new user (customer) into the system.
     *
     * @param user The User object representing the new user.
     * @throws CreateException If an error occurs during the creation process.
     */
    public void insertUser(User user) throws CreateException;

    /**
     * Retrieves customer information for a user identified by their user ID.
     *
     * @param userId The ID of the user for whom customer information is
     * requested.
     * @return The Customer object containing customer information.
     * @throws ReadException If an error occurs during the read process.
     */
    public Customer getCustomer(Integer userId) throws ReadException;

    public void updateBalance(Double balance, Integer customerId) throws UpdateException;
}
