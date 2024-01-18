package transfer.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing suppliers. It contains fields such as supplier ID, name,
 * phone, country, zip code, and create timestamp. It also provides methods for
 * getting and setting these fields, as well as overridden methods for hash
 * code, equality, and string representation.
 *
 * @author Alexander Epelde
 */
@XmlRootElement
public class Supplier implements Serializable {

    /**
     * Identification field for the supplier.
     */
    private Integer supplier_id;

    /**
     * Name of the supplier.
     */
    private String name;

    /**
     * Phone number of the supplier.
     */
    private String phone;

    /**
     * Country where the supplier is located.
     */
    private String country;

    /**
     * ZIP code of the supplier's location.
     */
    private Integer zip;

    private Set<Product> products;

    /**
     * Timestamp indicating when the supplier was created.
     */
    private Date createTimestamp;

    /**
     * Get the supplier ID.
     *
     * @return the supplier ID
     */
    public Integer getSupplier_id() {
        return supplier_id;
    }

    /**
     * Set the supplier ID.
     *
     * @param supplier_id the supplier ID to be set
     */
    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    /**
     * Get the name of the supplier.
     *
     * @return the name of the supplier
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the supplier.
     *
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the phone number of the supplier.
     *
     * @return the phone number of the supplier
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone number of the supplier.
     *
     * @param phone the phone number to be set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the country where the supplier is located.
     *
     * @return the country of the supplier
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country where the supplier is located.
     *
     * @param country the country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the ZIP code of the supplier's location.
     *
     * @return the ZIP code of the supplier
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Set the ZIP code of the supplier's location.
     *
     * @param zip the ZIP code to be set
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Get the create timestamp indicating when the supplier was created.
     *
     * @return the create timestamp of the supplier
     */
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Set the create timestamp indicating when the supplier was created.
     *
     * @param createTimestamp the create timestamp to be set
     */
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * Get the set of products associated with the supplier.
     *
     * @return the set of products
     */
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * Set the set of products associated with the supplier.
     *
     * @param products the set of products to be set
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    /**
     * Generate a hash code for the supplier based on its ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((supplier_id == null) ? 0 : supplier_id.hashCode());
        return result;
    }

    /**
     * Compare two Supplier objects for equality based on their ID.
     *
     * @param obj The other Supplier object to compare to.
     * @return true if IDs are equal
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
        Supplier other = (Supplier) obj;
        if (supplier_id == null) {
            if (other.supplier_id != null) {
                return false;
            }
        } else if (!supplier_id.equals(other.supplier_id)) {
            return false;
        }
        return true;
    }

    /**
     * Obtain a string representation of the Supplier.
     *
     * @return The String representing the Supplier.
     */
    @Override
    public String toString() {
        return "Supplier [supplier_id=" + supplier_id + ", name=" + name + "]";
    }
}
