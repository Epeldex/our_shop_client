package transfer.objects;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Entity representing users. It contains fields such as user ID, username,
 * password, user type, and active status. It also provides methods for
 * getting and setting these fields, as well as overridden methods for hash
 * code, equality, and string representation.
 *
 * @author dani
 */
@XmlRootElement(name = "user")
public class User implements Serializable {

    protected Integer id;

    private String username;

    private String password;
    
    private boolean active;

    
    private UserType userType;

    /**
     * Get the user ID.
     *
     * @return the user ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the user type.
     *
     * @return the user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Check if the user is active.
     *
     * @return true if the user is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    // setters

    /**
     * Set the user ID.
     *
     * @param id the user ID to be set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the username.
     *
     * @param username the username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password.
     *
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the user type.
     *
     * @param userType the user type to be set
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Set the user's active status.
     *
     * @param active true if the user is active, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Generate a hash code for the user based on its ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compare two User objects for equality based on their ID.
     *
     * @param obj The other User object to compare to.
     * @return true if IDs are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    /**
     * Compare two User objects for equality based on all attributes.
     *
     * @param obj object to compare with this user
     * @return true if every attribute of both objects are identical, including
     * the id. false if not.
     */
    public boolean everythingIsEqual(Object obj) {
        if (!equals(obj)) {
            return false;
        }

        User user = User.class.cast(obj);

        return this.username == user.getUsername()
                && this.password == user.getPassword()
                && this.active == user.active
                && this.userType == user.getUserType();
    }

    /**
     * Obtain a string representation of the User.
     *
     * @return The String representing the User.
     */
    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }
}
