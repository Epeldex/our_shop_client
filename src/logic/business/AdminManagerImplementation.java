package logic.business;

import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.LogicException;
import logic.interfaces.AdminManager;
import rest.AdminRESTClient;
import transfer.objects.Admin;

/**
 * This class implements {@link AdminManager} business logic interface using a
 * RESTful web client to access business logic in a Java EE application server.
 *
 * It provides methods for signing in, creating, updating, and removing
 * administrators. The class uses an instance of {@link AdminRESTClient} for
 * communication with the RESTful service, and logs relevant information and
 * exceptions using a {@link Logger}.
 *
 * @author Daniel Barrios
 */
public class AdminManagerImplementation implements AdminManager {

    // REST admin web client
    private AdminRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("Admin Manager");

    /**
     * Create an AdminManagerImplementation object. It constructs a web client
     * for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public AdminManagerImplementation() {
        webClient = new AdminRESTClient();
    }

    /**
     * Signs in an administrator by sending a request to the RESTful web
     * service.
     *
     * @param admin The {@link Admin} object containing sign-in credentials.
     * @return The signed-in {@link Admin} object.
     * @throws LogicException If there is any error during the sign-in
     * operation.
     */
    @Override
    public Admin signIn(Admin admin) throws LogicException {
        try {
            LOGGER.info("AdminManager: Signing in with username " + admin);
            return webClient.signIn(admin, Admin.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception during sign-in, {0}", ex.getMessage());
            throw new LogicException("Error during sign-in:\n" + ex.getMessage());
        }
    }

    /**
     * Creates a new administrator by sending a POST request to the RESTful web
     * service.
     *
     * @param admin The {@link Admin} object to be created.
     * @throws LogicException If there is any error during the admin creation
     * operation.
     */
    @Override
    public void createAdmin(Admin admin) throws LogicException {
        try {
            LOGGER.info("AdminManager: Creating new admin");
            webClient.createAdmin(admin);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception creating admin, {0}", ex.getMessage());
            throw new LogicException("Error creating admin:\n" + ex.getMessage());
        }
    }

    /**
     * Updates an existing administrator by sending a PUT request to the RESTful
     * web service.
     *
     * @param admin The {@link Admin} object to be updated.
     * @throws LogicException If there is any error during the admin update
     * operation.
     */
    @Override
    public void updateAdmin(Admin admin) throws LogicException {
        try {
            LOGGER.info("AdminManager: Updating admin with ID " + admin.getId());
            webClient.updateAdmin(admin);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception updating admin, {0}", ex.getMessage());
            throw new LogicException("Error updating admin:\n" + ex.getMessage());
        }
    }

    /**
     * Removes an existing administrator by sending a DELETE request to the
     * RESTful web service.
     *
     * @param id The ID of the admin to be removed.
     * @throws LogicException If there is any error during the admin removal
     * operation.
     */
    @Override
    public void removeAdmin(Integer id) throws LogicException {
        try {
            LOGGER.info("AdminManager: Removing admin with ID " + id);
            webClient.removeAdmin(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception removing admin, {0}", ex.getMessage());
            throw new LogicException("Error removing admin:\n" + ex.getMessage());
        }
    }
}
