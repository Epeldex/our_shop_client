package logic.business;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.encryption.EncriptionManager;
import logic.encryption.EncriptionManagerFactory;
import logic.exceptions.LogicException;
import logic.interfaces.CustomerManager;
import rest.CustomerRESTClient;
import transfer.objects.Customer;

/**
 * This class implements {@link CustomerManager} business logic interface using
 * a RESTful web client to access business logic in a Java EE application
 * server.
 *
 * It provides methods for updating, deleting, inserting, and retrieving
 * customer information, as well as updating customer balance. The class uses an
 * instance of {@link CustomerRESTClient} for communication with the RESTful
 * service and logs relevant information and exceptions using a {@link Logger}.
 *
 * @author Alex Irusta
 */
public class CustomerManagerImplementation implements CustomerManager {

    // REST customer web client
    private CustomerRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("Customer Manager");
    private static EncriptionManager em;

    /**
     * Create a CustomerManagerImplementation object. It constructs a web client
     * for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public CustomerManagerImplementation() {
        webClient = new CustomerRESTClient();
        em = EncriptionManagerFactory.getInstance();
    }

    /**
     * Updates an existing customer by sending a PUT request to the RESTful web
     * service.
     *
     * @param customer The {@link Customer} object to be updated.
     * @throws LogicException If there is any error during the customer update
     * operation.
     */
    @Override
    public void updateCustomer(Customer customer) throws LogicException {
        try {
            LOGGER.info("CustomerManager: Updating customer with ID " + customer.getId());
            customer.setPassword(Base64.getEncoder().encodeToString(em.encryptMessage(customer.getPassword())));
            webClient.updatePersonalInfo(customer);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception updating customer, {0}", ex.getMessage());
            throw new LogicException("Error updating customer:\n" + ex.getMessage());
        }
    }

    /**
     * Deletes an existing customer by sending a DELETE request to the RESTful
     * web service.
     *
     * @param id The ID of the customer to be deleted.
     * @throws LogicException If there is any error during the customer deletion
     * operation.
     */
    @Override
    public void deleteCustomerById(Integer id) throws LogicException {
        try {
            LOGGER.info("CustomerManager: Deleting customer with ID " + id);
            webClient.deleteCustomer(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception deleting customer, {0}", ex.getMessage());
            throw new LogicException("Error deleting customer:\n" + ex.getMessage());
        }
    }

    /**
     * Inserts a new customer by sending a POST request to the RESTful web
     * service.
     *
     * @param customer The {@link Customer} object to be inserted.
     * @throws LogicException If there is any error during the customer
     * insertion operation.
     */
    @Override
    public void insertCustomer(Customer customer) throws LogicException {
        try {
            LOGGER.info("CustomerManager: Inserting new customer");
            customer.setPassword(Base64.getEncoder().encodeToString(em.encryptMessage(customer.getPassword())));
            webClient.insertCustomer(customer);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception inserting customer, {0}", ex.getMessage());
            throw new LogicException("Error inserting customer:\n" + ex.getMessage());
        }
    }

    /**
     * Retrieves customer information by ID by sending a GET request to the
     * RESTful web service.
     *
     * @param userId The ID of the customer to retrieve.
     * @return The {@link Customer} object representing the customer with the
     * specified ID.
     * @throws LogicException If there is any error during the customer
     * retrieval operation.
     */
    @Override
    public Customer getCustomer(Integer userId) throws LogicException {
        try {
            LOGGER.info("CustomerManager: Retrieving customer with ID " + userId);
            Customer customer = webClient.getCustomer(Customer.class, userId.toString());
            customer.setPassword(Base64.getEncoder().encodeToString(em.decryptMessage(customer.getPassword())));
            return customer;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception retrieving customer, {0}", ex.getMessage());
            throw new LogicException("Error retrieving customer:\n" + ex.getMessage());
        }
    }

    /**
     * Updates the balance for an existing customer by sending a PUT request to
     * the RESTful web service.
     *
     * @param customer The {@link Customer} object with updated balance.
     * @throws LogicException If there is any error during the balance update
     * operation.
     */
    @Override
    public void updateBalance(Customer customer) throws LogicException {
        try {
            LOGGER.info("CustomerManager: Updating balance for customer with ID " + customer.getId());
            customer.setPassword(Base64.getEncoder().encodeToString(em.encryptMessage(customer.getPassword())));
            webClient.updatePersonalInfo(customer);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CustomerManager: Exception updating balance, {0}", ex.getMessage());
            throw new LogicException("Error updating balance:\n" + ex.getMessage());
        }
    }
}
