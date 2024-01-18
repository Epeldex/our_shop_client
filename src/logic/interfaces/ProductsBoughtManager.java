package logic.interfaces;

import java.util.List;
import logic.exceptions.LogicException;
import transfer.objects.ProductsBought;

/**
 * Interface for managing products bought by customers. This interface defines
 * methods for purchasing products, updating product amounts, and retrieving
 * products bought by a customer.
 *
 * @author Alex Irusta
 */
public interface ProductsBoughtManager {

    /**
     * Purchases a product for a customer by updating the product amount and
     * associated customer's balance.
     *
     * @param productBought The product to be purchased.
     * @throws LogicException If an error occurs during the purchase process.
     */
    public void purchaseProduct(ProductsBought productBought) throws LogicException;

    /**
     * Updates the amount of a purchased product for a customer.
     *
     * @param productBought The product bought by the customer.
     * @throws LogicException If an error occurs during the update process.
     */
    public void updateAmount(ProductsBought productBought) throws LogicException;

    /**
     * Retrieves a list of products bought by a customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of products bought by the customer.
     * @throws LogicException If an error occurs during the retrieval process.
     */
    public List<ProductsBought> getProductsBought(Integer customerId) throws LogicException;
}
