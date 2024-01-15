
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import logic.exceptions.LogicException;
import logic.interfaces.ProductManager;
import rest.ProductRESTClient;
import transfer.objects.Product;

/**
 * This class implements {@link ProductManager} business logic interface using a
 * RESTful web client to access business logic in a Java EE application server.
 *
 * @author Alexander Epelde
 */
public class ProductManagerImplementation implements ProductManager {

    // REST products web client
    private ProductRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("Product Manager");

    /**
     * Create a ProductManagerImplementation object. It constructs a web client
     * for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public ProductManagerImplementation() {
        webClient = new ProductRESTClient();
    }

    /**
     * Updates an existing product by sending a PUT request to the RESTful web
     * service.
     *
     * @param product The {@link Product} object to be updated.
     * @throws LogicException If there is any error while processing the update
     * operation.
     */
    @Override
    public void updateProduct(Product product) throws LogicException {
        try {
            LOGGER.info("ProductManager: Updating product with ID " + product.getProduct_id());
            webClient.update(product);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception updating product, {0}", ex.getMessage());
            throw new LogicException("Error updating product:\n" + ex.getMessage());
        }
    }

    /**
     * Deletes an existing product by sending a DELETE request to the RESTful
     * web service.
     *
     * @param productId The ID of the product to be deleted.
     * @throws LogicException If there is any error while processing the delete
     * operation.
     */
    @Override
    public void deleteProduct(Integer productId) throws LogicException {
        try {
            LOGGER.info("ProductManager: Deleting product with ID " + productId);
            webClient.delete(productId.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception deleting product, {0}", ex.getMessage());
            throw new LogicException("Error deleting product:\n" + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of all products by sending a GET request to the RESTful
     * web service.
     *
     * @return List of {@link Product} objects representing all products.
     * @throws LogicException If there is any error while processing the select
     * operation.
     */
    @Override
    public List<Product> selectAllProducts() throws LogicException {
        List<Product> products = null;
        try {
            LOGGER.info("ProductManager: Finding all products from REST service (XML).");
            // Ask webClient for all products' data.
            products = webClient.findAll(new GenericType<List<Product>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception finding all products, {0}", ex.getMessage());
            throw new LogicException("Error finding all products:\n" + ex.getMessage());
        }
        return products;
    }

    /**
     * Retrieves product information by ID by sending a GET request to the
     * RESTful web service.
     *
     * @param productId The ID of the product to retrieve.
     * @return The {@link Product} object representing the product with the
     * specified ID.
     * @throws LogicException If there is any error while processing the select
     * operation.
     */
    @Override
    public Product selectProductById(Integer productId) throws LogicException {
        Product product = null;
        try {
            LOGGER.info("ProductManager: Finding product by ID from REST service (XML).");
            // Ask webClient for the product data by ID.
            product = webClient.find(Product.class, productId.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception finding product by ID, {0}", ex.getMessage());
            throw new LogicException("Error finding product by ID:\n" + ex.getMessage());
        }
        return product;
    }

    /**
     * Inserts a new product by sending a POST request to the RESTful web
     * service.
     *
     * @param product The {@link Product} object to be inserted.
     * @throws LogicException If there is any error while processing the insert
     * operation.
     */
    @Override
    public void insertProduct(Product product) throws LogicException {
        try {
            LOGGER.log(Level.INFO, "ProductManager: Inserting product {0}.", product.getProduct_id());
            // Send product data to web client for creation.
            webClient.create(product);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ProductManager: Exception inserting product, {0}", ex.getMessage());
            throw new LogicException("Error inserting product:\n" + ex.getMessage());
        }
    }
}
