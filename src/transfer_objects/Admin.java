package transfer_objects;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing administrators. It extends the User class and adds a
 * field for the last access date. It also provides methods for getting and
 * setting this field, as well as overridden methods for equality and string
 * representation.
 *
 * Note: The equals method is overridden from the User class, but the
 * implementation is incorrect. It should compare Admin objects instead of User
 * objects. The correct method should be provided based on the actual
 * requirements.
 *
 * @author dani
 */
@XmlRootElement(name = "admin")
public class Admin extends User {

    /**
     * Date representing the last access of the administrator.
     */
    private Date lastAccess;

    /**
     * Get the last access date of the administrator.
     *
     * @return the last access date
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * Set the last access date of the administrator.
     *
     * @param lastAccess the last access date to be set
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    /**
     * Compare two Admin objects for equality based on their ID.
     *
     * Note: The implementation is incorrect. It should compare Admin objects
     * instead of User objects. Correct the method based on the actual
     * requirements.
     *
     * @param object The other Admin object to compare to.
     * @return true if IDs are equal
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Admin)) {
            return false;
        }

        Admin other = (Admin) object;
        if ((super.id == null && other.id != null) || (super.id != null && !super.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    /**
     * Obtain a string representation of the Admin.
     *
     * @return The String representing the Admin.
     */
    @Override
    public String toString() {
        return "entities.Admin[ id=" + id + " ]";
    }
}
