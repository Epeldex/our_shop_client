package transfer.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing tags used to specify proctct types It contains fields
 * such as tag id, type, label, active status, and creation timestamp.
 *
 * @author Alexander Epelde
 */

@XmlRootElement
public class Tag implements Serializable {

    /**
     * Identification field for the tag.
     */
    private Integer tag_id;

    /**
     * Type of the tag.
     */
    private String type;

    /**
     * Label or description of the tag.
     */
    private String label;

    /**
     * Status indicating whether the tag is active or not.
     */
    private Boolean active;

    /**
     * Timestamp indicating when the tag was created.
     */
    private Date createTimestamp;
    /**
     * Collection of products with that tag
     */
    private Set<Product> products;

    /**
     * Get the tag id.
     *
     * @return the tag_id
     */
    public Integer getTag_id() {
        return tag_id;
    }

    /**
     * Set the tag id.
     *
     * @param tag_id the tag_id to set
     */
    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    /**
     * Get the type of the tag.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the tag.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the label or description of the tag.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label or description of the tag.
     *
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Check if the tag is active.
     *
     * @return the active status
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Set the active status of the tag.
     *
     * @param active the active status to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Get the creation timestamp of the tag.
     *
     * @return the createTimestamp
     */
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Set the creation timestamp of the tag.
     *
     * @param createTimestamp the createTimestamp to set
     */
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * Get the set of products associated with the Tag.
     *
     * @return the set of products
     */
    @XmlTransient
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * Set the set of products associated with the Tag.
     *
     * @param products the set of products to be set
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    /**
     * Generate a hash code for the tag based on tag_id and type.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tag_id == null) ? 0 : tag_id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * Compare two Tag objects for equality based on tag_id and type.
     *
     * @param obj The other Tag object to compare to.
     * @return true if tag_id and type are equal.
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
        Tag other = (Tag) obj;
        if (tag_id == null) {
            if (other.tag_id != null) {
                return false;
            }
        } else if (!tag_id.equals(other.tag_id)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    /**
     * Obtain a string representation of the Tag.
     *
     * @return The String representing the Tag.
     */
    @Override
    public String toString() {
        return "Tag [tag_id=" + tag_id + ", type=" + type + ", label=" + label + "]";
    }
}
