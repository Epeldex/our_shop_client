package logic.interfaces;

import java.util.List;
import logic.exceptions.LogicException;
import transfer.objects.Product;

/**
 * Interface for managing operations with {@link Product} entities. This
 * interface defines methods for updating, deleting, retrieving, and inserting
 * products in the underlying application storage.
 *
 * @author Alexander Epelde
 */
public interface ProductManager {

    /**
     * Updates a product's information in the underlying application storage.
     *
     * @param product The {@link Product} object containing the details of the
     * product to be updated.
     * @throws LogicException If there is any exception during the update
     * process.
     */
    public void updateProduct(Product product) throws LogicException;

    /**
     * Deletes a product from the underlying application storage.
     *
     * @param productId The ID of the product to be deleted.
     * @throws LogicException If there is any exception during the delete
     * process.
     */
    public void deleteProduct(Integer productId) throws LogicException;

    /**
     * Retrieves a list of all products from the application data storage.
     *
     * @return A List of {@link Product} objects.
     * @throws LogicException If there is any exception during the retrieval
     * process.
     */
    public List<Product> selectAllProducts() throws LogicException;

    /**
     * Retrieves a product by its ID from the application data storage.
     *
     * @param productId The ID of the product to be retrieved.
     * @return The {@link Product} object containing product data.
     * @throws LogicException If there is any exception during the retrieval
     * process.
     */
    public Product selectProductById(Integer productId) throws LogicException;

    /**
     * Inserts a new product into the underlying application storage.
     *
     * @param product The {@link Product} object containing the details of the
     * new product.
     * @throws LogicException If there is any exception during the insertion
     * process.
     */
    public void insertProduct(Product product) throws LogicException;
}
