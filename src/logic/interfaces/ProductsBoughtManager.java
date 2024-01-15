package logic.interfaces;

import entities.Customer;
import entities.ProductsBought;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * Local interface for an EJB (Enterprise JavaBeans) session bean responsible
 * for managing products bought by customers. This interface defines methods for
 * purchasing products, updating product amounts, and retrieving products bought
 * by a customer.
 *
 * @author Alex Irusta
 */
public interface ProductsBoughtManager {

    /**
     * Purchases a product for a customer by updating the product amount and
     * associated customer's balance.
     *
     * @param productBought The product to be purchased.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void purchaseProduct(ProductsBought productBought) throws UpdateException;

    /**
     * Updates the amount of a purchased product for a customer.
     *
     * @param customerId The ID of the customer.
     * @param product_id The ID of the product.
     * @param amount The quantity to update the product amount.
     * @throws UpdateException If an error occurs during the update process.
     */
    public void updateAmount(Integer customerId, Integer product_id, Integer amount) throws UpdateException;

    /**
     * Retrieves a list of products bought by a customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of products bought by the customer.
     * @throws ReadException If an error occurs during the read process.
     */
    public List<ProductsBought> getProductsBought(Integer customerId) throws ReadException;

    /**
     * Deletes products bought by a customer based on the specified customer ID.
     *
     * @param customer_id The ID of the customer to delete products for.
     * @throws DeleteException If an error occurs during the delete process.
     */
    public void deleteByCustomerId(Integer customer_id) throws DeleteException;

    /**
     * Deletes products bought by customers based on the specified product ID.
     *
     * @param product_id The ID of the product to delete.
     * @throws DeleteException If an error occurs during the delete process.
     */
    public void deleteByProductId(Integer product_id) throws DeleteException;

}
