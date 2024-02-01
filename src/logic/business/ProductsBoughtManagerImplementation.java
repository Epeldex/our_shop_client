package logic.business;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import logic.exceptions.LogicException;
import logic.interfaces.ProductsBoughtManager;
import rest.ProductsBoughtRESTClient;
import transfer.objects.ProductsBought;

/**
 * This class implements the {@link ProductsBoughtManager} business logic
 * interface using a RESTful web client to access business logic in a Java EE
 * application server.
 *
 * It provides methods for purchasing a product, updating the amount of a
 * product bought, and retrieving a list of products bought by a customer. The
 * class uses an instance of {@link ProductsBoughtRESTClient} for communication
 * with the RESTful service and logs relevant information and exceptions using a
 * {@link Logger}.
 *
 * @author Alex Irusta
 */
public class ProductsBoughtManagerImplementation implements ProductsBoughtManager {

    // REST products bought web client
    private ProductsBoughtRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("ProductsBought Manager");

    /**
     * Create a ProductsBoughtManagerImplementation object. It constructs a web
     * client for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public ProductsBoughtManagerImplementation() {
        webClient = new ProductsBoughtRESTClient();
    }

    /**
     * Purchases a product for a customer by sending a POST request to the
     * RESTful web service.
     *
     * @param productBought The {@link ProductsBought} object representing the
     * purchased product.
     * @throws LogicException If there is any error during the product purchase
     * operation.
     */
    @Override
    public void purchaseProduct(ProductsBought productBought) throws LogicException {
        try {
            LOGGER.info("ProductsBoughtManager: Purchasing product for customer with ID " + productBought.getId().getCustomerId());
            webClient.purchaseProduct(productBought);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception purchasing product, {0}", ex.getMessage());
            throw new LogicException("Error purchasing product:\n" + ex.getMessage());
        }
    }

    /**
     * Updates the amount of a product bought by a customer by sending a PUT
     * request to the RESTful web service.
     *
     * @param productBought The {@link ProductsBought} object with updated
     * amount.
     * @throws LogicException If there is any error during the amount update
     * operation.
     */
    @Override
    public void updateAmount(ProductsBought productBought) throws LogicException {
        try {
            LOGGER.info("ProductsBoughtManager: Updating amount for product bought by customer with ID " + productBought.getId().getCustomerId());
            webClient.updateAmount(productBought);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception updating amount, {0}", ex.getMessage());
            throw new LogicException("Error updating amount:\n" + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of products bought by a customer by sending a GET
     * request to the RESTful web service.
     *
     * @param customerId The ID of the customer for whom products are retrieved.
     * @return List of {@link ProductsBought} objects representing the products
     * bought by the customer.
     * @throws LogicException If there is any error during the products
     * retrieval operation.
     */
    @Override
    public List<ProductsBought> getProductsBought(Integer customerId) throws LogicException {
        try {
            LOGGER.info("ProductsBoughtManager: Retrieving products bought by customer with ID " + customerId);
            return webClient.getProductsBought(new GenericType<List<ProductsBought>>() {
            }, customerId.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductsBoughtManager: Exception retrieving products bought, {0}", ex.getMessage());
            throw new LogicException("Error retrieving products bought:\n" + ex.getMessage());
        }
    }
}
