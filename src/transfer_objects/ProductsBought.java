package transfer_objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The ProductsBought class represents information about products that were
 * bought, including the amount purchased and the timestamp of the purchase.
 *
 * @author alexIrusta
 */

@XmlRootElement
public class ProductsBought implements Serializable {

    // The amount of products bought
    private Integer amount;

    private ProductsBoughtId id;

    // The timestamp indicating when the products were bought
    private Date boughtTimestamp;


    private Customer customer;


    private Product product;

    /**
     * Gets the amount of products bought.
     *
     * @return The amount of products bought.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount of products bought.
     *
     * @param amount The new amount of products bought.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets the timestamp of when the products were bought.
     *
     * @return The timestamp of the purchase.
     */
    public Date getBoughtTimestamp() {
        return boughtTimestamp;
    }

    /**
     * Sets the timestamp of when the products were bought.
     *
     * @param boughtTimestamp The new timestamp of the purchase.
     */
    public void setBoughtTimestamp(Date boughtTimestamp) {
        this.boughtTimestamp = boughtTimestamp;
    }

    public ProductsBoughtId getId() {
        return id;
    }

    public void setId(ProductsBoughtId id) {
        this.id = id;
    }

    @XmlTransient
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Computes the hash code for this object based on its identifier.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductsBought other = (ProductsBought) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
