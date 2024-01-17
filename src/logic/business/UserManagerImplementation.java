package logic.business;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.LogicException;
import logic.interfaces.UserManager;
import rest.UserRESTClient;
import transfer.objects.User;

/**
 * This class implements {@link UserManager} business logic interface using a
 * RESTful web client to access business logic in a Java EE application server.
 *
 * It provides methods for finding users by ID, username, and active status, as
 * well as finding all users, updating password, authenticating users, creating,
 * updating, and removing users. The class uses an instance of
 * {@link UserRESTClient} for communication with the RESTful service and logs
 * relevant information and exceptions using a {@link Logger}.
 *
 * @author Daniel Barrios
 */
public class UserManagerImplementation implements UserManager {

    // REST user web client
    private UserRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("User Manager");

    /**
     * Create a UserManagerImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public UserManagerImplementation() {
        webClient = new UserRESTClient();
    }

    /**
     * Finds a user by ID by sending a GET request to the RESTful web service.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} object representing the user with the specified
     * ID.
     * @throws LogicException If there is any error during the user retrieval
     * operation.
     */
    @Override
    public User findUserById(Integer id) throws LogicException {
        try {
            LOGGER.info("UserManager: Finding user by ID " + id);
            return webClient.findUserById(User.class, id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by ID, {0}", ex.getMessage());
            throw new LogicException("Error finding user by ID:\n" + ex.getMessage());
        }
    }

    /**
     * Finds a user by username by sending a GET request to the RESTful web
     * service.
     *
     * @param username The username of the user to retrieve.
     * @return The {@link User} object representing the user with the specified
     * username.
     * @throws LogicException If there is any error during the user retrieval
     * operation.
     */
    @Override
    public User findUserByUsername(String username) throws LogicException {
        try {
            LOGGER.info("UserManager: Finding user by username " + username);
            return webClient.findUserByUsername(User.class, username);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by username, {0}", ex.getMessage());
            throw new LogicException("Error finding user by username:\n" + ex.getMessage());
        }
    }

    /**
     * Finds users by active status by sending a GET request to the RESTful web
     * service.
     *
     * @param active The active status of the users to retrieve.
     * @return List of {@link User} objects representing users with the
     * specified active status.
     * @throws LogicException If there is any error during the users retrieval
     * operation.
     */
    @Override
    public List<User> findUserByActive(Boolean active) throws LogicException {
        try {
            LOGGER.info("UserManager: Finding users by active status " + active);
            return webClient.findUserByActive(List.class, active.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding users by active status, {0}", ex.getMessage());
            throw new LogicException("Error finding users by active status:\n" + ex.getMessage());
        }
    }

    /**
     * Finds all users by sending a GET request to the RESTful web service.
     *
     * @return List of {@link User} objects representing all users.
     * @throws LogicException If there is any error during the users retrieval
     * operation.
     */
    @Override
    public List<User> findAllUsers() throws LogicException {
        try {
            LOGGER.info("UserManager: Finding all users");
            return webClient.findAllUsers(List.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding all users, {0}", ex.getMessage());
            throw new LogicException("Error finding all users:\n" + ex.getMessage());
        }
    }

    /**
     * Updates the password for an existing user by sending a PUT request to the
     * RESTful web service.
     *
     * @param user The {@link User} object with updated password.
     * @throws LogicException If there is any error during the password update
     * operation.
     */
    @Override
    public void updatePassword(User user) throws LogicException {
        try {
            LOGGER.info("UserManager: Updating password for user with ID " + user.getId());
            webClient.updateUser(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating password, {0}", ex.getMessage());
            throw new LogicException("Error updating password:\n" + ex.getMessage());
        }
    }

    /**
     * Authenticates a user by sending a POST request to the RESTful web
     * service.
     *
     * @param user The {@link User} object with authentication credentials.
     * @return The authenticated {@link User} object.
     * @throws LogicException If there is any error during the user
     * authentication operation.
     */
    @Override
    public User signIn(User user) throws LogicException {
        try {
            LOGGER.info("UserManager: Authenticating user with username " + user.getUsername());
            return webClient.signIn(user, User.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception authenticating user, {0}", ex.getMessage());
            throw new LogicException("Error authenticating user:\n" + ex.getMessage());
        }
    }

    /**
     * Creates a new user by sending a POST request to the RESTful web service.
     *
     * @param user The {@link User} object to be created.
     * @throws LogicException If there is any error during the user creation
     * operation.
     */
    @Override
    public void createUser(User user) throws LogicException {
        try {
            LOGGER.info("UserManager: Creating a new user");
            webClient.createUser(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating user, {0}", ex.getMessage());
            throw new LogicException("Error creating user:\n" + ex.getMessage());
        }
    }

    /**
     * Updates an existing user by sending a PUT request to the RESTful web
     * service.
     *
     * @param user The {@link User} object to be updated.
     * @throws LogicException If there is any error during the user update
     * operation.
     */
    @Override
    public void updateUser(User user) throws LogicException {
        try {
            LOGGER.info("UserManager: Updating user with ID " + user.getId());
            webClient.updateUser(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating user, {0}", ex.getMessage());
            throw new LogicException("Error updating user:\n" + ex.getMessage());
        }
    }

    /**
     * Removes an existing user by sending a DELETE request to the RESTful web
     * service.
     *
     * @param id The ID of the user to be removed.
     * @throws LogicException If there is any error during the user removal
     * operation.
     */
    @Override
    public void removeUser(Integer id) throws LogicException {
        try {
            LOGGER.info("UserManager: Removing user with ID " + id);
            webClient.deleteUser(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception removing user, {0}", ex.getMessage());
            throw new LogicException("Error removing user:\n" + ex.getMessage());
        }
    }
}
