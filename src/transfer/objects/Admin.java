package transfer.objects;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing administrators. It extends the User class and adds a
 * field for the last access date. It also provides methods for getting and
 * setting this field, as well as overridden methods for equality and string
 * representation.
 *
 * @author Daniel Barrios
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
