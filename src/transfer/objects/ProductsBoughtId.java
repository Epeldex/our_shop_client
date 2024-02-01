package transfer.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * The ProductsBoughtId class represents the composite key for the
 * ProductsBought entity. It combines the identifiers for both the product and
 * customer to uniquely identify a record in the "products_bought" table.
 *
 * @author Alex Irusta
 */
public class ProductsBoughtId implements Serializable {

    // The identifier for the product
    private Integer productId;

    // The identifier for the customer
    private Integer customerId;

    /**
     * Gets the product identifier.
     *
     * @return The product identifier.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product identifier.
     *
     * @param productId The new product identifier.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Gets the customer identifier.
     *
     * @return The customer identifier.
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer identifier.
     *
     * @param customerId The new customer identifier.
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Computes the hash code for this object based on its identifiers.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.productId);
        hash = 17 * hash + Objects.hashCode(this.customerId);
        return hash;
    }

    /**
     * Checks if this object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProductsBoughtId other = (ProductsBoughtId) obj;
        return Objects.equals(this.productId, other.productId) && Objects.equals(this.customerId, other.customerId);
    }

    /**
     * Returns a string representation of the ProductsBoughtId object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "ProductsBoughtId{" + "productId=" + productId + ", customerId=" + customerId + '}';
    }
}
